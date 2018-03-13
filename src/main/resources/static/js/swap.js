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

var column = 1;
var row = 7;

function move() {
  var piece = document.getElementById("piece");
  piece.style.gridColumn = column;
  piece.style.gridRow = row;
}

window.onload = function() {
  // var test_1 = "group_2";
  // var test_2 = "group_6";

//   swap(test_1, test_2);
// }
}
