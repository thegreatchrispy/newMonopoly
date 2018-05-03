var group_1 = ['income-tax', 'baltic', 'com-chest-1', 'mediterranean'];
var group_2 = ['connecticut', 'vermont', 'chance-2', 'oriental'];
var group_3 = ['virginia', 'states', 'electric', 'saint'];
var group_4 = ['new', 'tenessee', 'com-chest-4', 'james'];
var group_5 = ['kentucky', 'chance-5', 'indiana', 'illionis'];
var group_6 = ['atlantic', 'ventnor', 'waterworks', 'marvin'];
var group_7 = ['pacific', 'north', 'com-chest-7', 'penn'];
var group_8 = ['chance-8', 'park', 'luxury', 'boardw'];
var groups = [group_1, group_2, group_3, group_4, group_5, group_6, group_7, group_8];

function reverse(groupNumber) {
    var group = [];
    var num = -1;

    for(i = 0; i < 8; i++) {
        if ((i + 1) == Number(groupNumber)) {
            num = i;
            group = groups[i];
            break;
        }
    }

    element_1 = document.getElementById(group[0]);
    element_2 = document.getElementById(group[1]);
    element_3 = document.getElementById(group[2]);
    element_4 = document.getElementById(group[3]);
    
    $('#group_' + groupNumber).empty();
    document.getElementById("group_" + groupNumber).appendChild(element_4);
    document.getElementById("group_" + groupNumber).appendChild(element_3);
    document.getElementById("group_" + groupNumber).appendChild(element_2);
    document.getElementById("group_" + groupNumber).appendChild(element_1);

    console.log("SUCCESS");
}

function reverseSwap(swap_1, swap_2) {
}

function swap(swap_1, swap_2) {
    // Declaring variables.
    var element_1 = document.getElementById(swap_1);
    var element_1_class = element_1.className;
    var element_2 = document.getElementById(swap_2);
    var element_2_class = element_2.className;
  
    // Swap
    element_1.className = element_2_class;
    element_2.className = element_1_class;
  }