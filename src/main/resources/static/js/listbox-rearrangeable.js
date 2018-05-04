window.addEventListener('load', function () {

  var ex2 = document.getElementById('ex2');
  var ex2ImportantListbox = new aria.Listbox(document.getElementById('ms_imp_list'));
  var ex2UnimportantListbox = new aria.Listbox(document.getElementById('ms_unimp_list'));

  ex2ImportantListbox.setupMove(document.getElementById('ex2-add'), ex2UnimportantListbox);
  ex2UnimportantListbox.setupMove(document.getElementById('ex2-delete'), ex2ImportantListbox);
  ex2UnimportantListbox.setHandleItemChange(function (event, items) {
    var updateText = '';
    var itemText = items.length === 1 ? '1 item' : items.length + ' items';

    switch (event) {
      case 'added':
        updateText = 'Added ' + itemText + ' to chosen features.';
        break;
      case 'removed':
        updateText = 'Removed ' + itemText + ' from chosen features.';
        break;
    }

    if (updateText) {
      var ex1LiveRegion = document.getElementById('ms_live_region');
      ex1LiveRegion.innerText = updateText;
    }
  });
});
