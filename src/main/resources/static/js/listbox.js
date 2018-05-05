var aria = aria || {};

aria.Listbox = function (listboxNode) {
  this.listboxNode = listboxNode;
  this.activeDescendant = this.listboxNode.getAttribute('aria-activedescendant');
  this.multiselectable = this.listboxNode.hasAttribute('aria-multiselectable');
  this.moveUpDownEnabled = false;
  this.siblingList = null;
  this.upButton = null;
  this.downButton = null;
  this.moveButton = null;
  this.keysSoFar = '';
  this.handleFocusChange = function () {};
  this.handleItemChange = function (event, items) {};
  this.registerEvents();
};

aria.Listbox.prototype.registerEvents = function () {
  this.listboxNode.addEventListener('focus', this.setupFocus.bind(this));
  this.listboxNode.addEventListener('keydown', this.checkKeyPress.bind(this));
  this.listboxNode.addEventListener('click', this.checkClickItem.bind(this));
};

aria.Listbox.prototype.setupFocus = function () {
  if (this.activeDescendant) {
    return;
  }

  this.focusFirstItem();
};

aria.Listbox.prototype.focusFirstItem = function () {
  var firstItem;

  firstItem = this.listboxNode.querySelector('[role="option"]');

  if (firstItem) {
    this.focusItem(firstItem);
  }
};

aria.Listbox.prototype.focusLastItem = function () {
  var itemList = this.listboxNode.querySelectorAll('[role="option"]');

  if (itemList.length) {
    this.focusItem(itemList[itemList.length - 1]);
  }
};

aria.Listbox.prototype.checkKeyPress = function (evt) {
  var key = evt.which || evt.keyCode;
  var nextItem = document.getElementById(this.activeDescendant);

  if (!nextItem) {
    return;
  }

  switch (key) {
    case aria.KeyCode.PAGE_UP:
    case aria.KeyCode.PAGE_DOWN:
      if (this.moveUpDownEnabled) {
        evt.preventDefault();

        if (key === aria.KeyCode.PAGE_UP) {
          this.moveUpItems();
        }
        else {
          this.moveDownItems();
        }
      }

      break;
    case aria.KeyCode.UP:
    case aria.KeyCode.DOWN:
      evt.preventDefault();

      if (this.moveUpDownEnabled && evt.altKey) {
        if (key === aria.KeyCode.UP) {
          this.moveUpItems();
        }
        else {
          this.moveDownItems();
        }
        return;
      }

      if (key === aria.KeyCode.UP) {
        nextItem = nextItem.previousElementSibling;
      }
      else {
        nextItem = nextItem.nextElementSibling;
      }

      if (nextItem) {
        this.focusItem(nextItem);
      }

      break;
    case aria.KeyCode.HOME:
      evt.preventDefault();
      this.focusFirstItem();
      break;
    case aria.KeyCode.END:
      evt.preventDefault();
      this.focusLastItem();
      break;
    case aria.KeyCode.SPACE:
      evt.preventDefault();
      this.toggleSelectItem(nextItem);
      break;
    case aria.KeyCode.BACKSPACE:
    case aria.KeyCode.DELETE:
    case aria.KeyCode.RETURN:
      if (!this.moveButton) {
        return;
      }

      var keyshortcuts = this.moveButton.getAttribute('aria-keyshortcuts');
      if (key === aria.KeyCode.RETURN && keyshortcuts.indexOf('Enter') === -1) {
        return;
      }
      if (
        (key === aria.KeyCode.BACKSPACE || key === aria.KeyCode.DELETE) &&
        keyshortcuts.indexOf('Delete') === -1
      ) {
        return;
      }

      evt.preventDefault();

      var nextUnselected = nextItem.nextElementSibling;
      while (nextUnselected) {
        if (nextUnselected.getAttribute('aria-selected') != 'true') {
          break;
        }
        nextUnselected = nextUnselected.nextElementSibling;
      }
      if (!nextUnselected) {
        nextUnselected = nextItem.previousElementSibling;
        while (nextUnselected) {
          if (nextUnselected.getAttribute('aria-selected') != 'true') {
            break;
          }
          nextUnselected = nextUnselected.previousElementSibling;
        }
      }

      this.moveItems();

      if (!this.activeDescendant && nextUnselected) {
        this.focusItem(nextUnselected);
      }
      break;
    default:
      var itemToFocus = this.findItemToFocus(key);
      if (itemToFocus) {
        this.focusItem(itemToFocus);
      }
      break;
  }
};

aria.Listbox.prototype.findItemToFocus = function (key) {
  var itemList = this.listboxNode.querySelectorAll('[role="option"]');
  var character = String.fromCharCode(key);

  if (!this.keysSoFar) {
    for (i = 0; i < itemList.length; i++) {
      if (itemList[i].getAttribute('id') == this.activeDescendant) {
        this.searchIndex = i;
      }
    }
  }
  this.keysSoFar += character;
  this.clearKeysSoFarAfterDelay();

  var nextMatch = this.findMatchInRange(
    itemList,
    this.searchIndex + 1,
    itemList.length
  );
  if (!nextMatch) {
    nextMatch = this.findMatchInRange(
      itemList,
      0,
      this.searchIndex
    );
  }
  return nextMatch;
};

aria.Listbox.prototype.clearKeysSoFarAfterDelay = function () {
  if (this.keyClear) {
    clearTimeout(this.keyClear);
    this.keyClear = null;
  }
  this.keyClear = setTimeout((function () {
    this.keysSoFar = '';
    this.keyClear = null;
  }).bind(this), 500);
};

aria.Listbox.prototype.findMatchInRange = function (list, startIndex, endIndex) {
  // Find the first item starting with the keysSoFar substring, searching in
  // the specified range of items
  for (var n = startIndex; n < endIndex; n++) {
    var label = list[n].innerText;
    if (label && label.toUpperCase().indexOf(this.keysSoFar) === 0) {
      return list[n];
    }
  }
  return null;
};

aria.Listbox.prototype.checkClickItem = function (evt) {
  if (evt.target.getAttribute('role') === 'option') {
    this.focusItem(evt.target);
    this.toggleSelectItem(evt.target);
  }
};

aria.Listbox.prototype.toggleSelectItem = function (element) {
  if (this.multiselectable) {
    element.setAttribute(
      'aria-selected',
      element.getAttribute('aria-selected') === 'true' ? 'false' : 'true'
    );

    if (this.moveButton) {
      if (this.listboxNode.querySelector('[aria-selected="true"]')) {
        this.moveButton.setAttribute('aria-disabled', 'false');
      }
      else {
        this.moveButton.setAttribute('aria-disabled', 'true');
      }
    }
  }
};
aria.Listbox.prototype.defocusItem = function (element) {
  if (!element) {
    return;
  }
};

aria.Listbox.prototype.focusItem = function (element) {
  this.defocusItem(document.getElementById(this.activeDescendant));
  this.listboxNode.setAttribute('aria-activedescendant', element.id);
  this.activeDescendant = element.id;

  if (this.listboxNode.scrollHeight > this.listboxNode.clientHeight) {
    var scrollBottom = this.listboxNode.clientHeight + this.listboxNode.scrollTop;
    var elementBottom = element.offsetTop + element.offsetHeight;
    if (elementBottom > scrollBottom) {
      this.listboxNode.scrollTop = elementBottom - this.listboxNode.clientHeight;
    }
    else if (element.offsetTop < this.listboxNode.scrollTop) {
      this.listboxNode.scrollTop = element.offsetTop;
    }
  }

  if (!this.multiselectable && this.moveButton) {
    this.moveButton.setAttribute('aria-disabled', false);
  }

  this.checkUpDownButtons();
  this.handleFocusChange(element);
};

aria.Listbox.prototype.checkUpDownButtons = function () {
  var activeElement = document.getElementById(this.activeDescendant);

  if (!this.moveUpDownEnabled) {
    return false;
  }

  if (!activeElement) {
    this.upButton.setAttribute('aria-disabled', 'true');
    this.downButton.setAttribute('aria-disabled', 'true');
    return;
  }

  if (this.upButton) {
    if (activeElement.previousElementSibling) {
      this.upButton.setAttribute('aria-disabled', false);
    }
    else {
      this.upButton.setAttribute('aria-disabled', 'true');
    }
  }

  if (this.downButton) {
    if (activeElement.nextElementSibling) {
      this.downButton.setAttribute('aria-disabled', false);
    }
    else {
      this.downButton.setAttribute('aria-disabled', 'true');
    }
  }
};

aria.Listbox.prototype.addItems = function (items) {
  if (!items || !items.length) {
    return false;
  }

  items.forEach((function (item) {
    this.defocusItem(item);
    this.toggleSelectItem(item);
    this.listboxNode.append(item);
  }).bind(this));

  if (!this.activeDescendant) {
    this.focusItem(items[0]);
  }

  this.handleItemChange('added', items);
};

aria.Listbox.prototype.deleteItems = function () {
  var itemsToDelete;

  if (this.multiselectable) {
    itemsToDelete = this.listboxNode.querySelectorAll('[aria-selected="true"]');
  }
  else if (this.activeDescendant) {
    itemsToDelete = [ document.getElementById(this.activeDescendant) ];
  }

  if (!itemsToDelete || !itemsToDelete.length) {
    return [];
  }

  itemsToDelete.forEach((function (item) {
    item.remove();

    if (item.id === this.activeDescendant) {
      this.clearActiveDescendant();
    }
  }).bind(this));

  this.handleItemChange('removed', itemsToDelete);

  return itemsToDelete;
};

aria.Listbox.prototype.clearActiveDescendant = function () {
  this.activeDescendant = null;
  this.listboxNode.setAttribute('aria-activedescendant', null);

  if (this.moveButton) {
    this.moveButton.setAttribute('aria-disabled', 'true');
  }

  this.checkUpDownButtons();
};

aria.Listbox.prototype.moveUpItems = function () {
  var previousItem;

  if (!this.activeDescendant) {
    return;
  }

  currentItem = document.getElementById(this.activeDescendant);
  previousItem = currentItem.previousElementSibling;

  if (previousItem) {
    this.listboxNode.insertBefore(currentItem, previousItem);
    this.handleItemChange('moved_up', [ currentItem ]);
  }

  this.checkUpDownButtons();
};

aria.Listbox.prototype.moveDownItems = function () {
  var nextItem;

  if (!this.activeDescendant) {
    return;
  }

  currentItem = document.getElementById(this.activeDescendant);
  nextItem = currentItem.nextElementSibling;

  if (nextItem) {
    this.listboxNode.insertBefore(nextItem, currentItem);
    this.handleItemChange('moved_down', [ currentItem ]);
  }

  this.checkUpDownButtons();
};

aria.Listbox.prototype.moveItems = function () {
  if (!this.siblingList) {
    return;
  }

  var itemsToMove = this.deleteItems();
  this.siblingList.addItems(itemsToMove);
};

aria.Listbox.prototype.enableMoveUpDown = function (upButton, downButton) {
  this.moveUpDownEnabled = true;
  this.upButton = upButton;
  this.downButton = downButton;
  upButton.addEventListener('click', this.moveUpItems.bind(this));
  downButton.addEventListener('click', this.moveDownItems.bind(this));
};

aria.Listbox.prototype.setupMove = function (button, siblingList) {
  this.siblingList = siblingList;
  this.moveButton = button;
  button.addEventListener('click', this.moveItems.bind(this));
};

aria.Listbox.prototype.setHandleItemChange = function (handlerFn) {
  this.handleItemChange = handlerFn;
};

aria.Listbox.prototype.setHandleFocusChange = function (focusChangeHandler) {
  this.handleFocusChange = focusChangeHandler;
};
