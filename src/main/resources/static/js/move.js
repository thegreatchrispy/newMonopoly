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

function move(piece_id, row, column, in_jail) {
  var piece = document.getElementById(piece_id);
  
  switch(piece_id) {
    //PIECE_1
    case "piece_1":
      // IN-JAIL
      if (in_jail) {
        piece.style.top = "0%";
        piece.style.left = "4%";
        break;
      }
      // GO SPACE
      if (row == 11 && column == 11) {
        piece.style.top = "3%";
        piece.style.left = "0%";
        break;
      }
      // JV SPACE
      if (row == 11 && column == 1) {
        piece.style.top = "0%";
        piece.style.left = "0%";
        break;
      }
      // FREE
      if (row == 1 && column == 1) {
        piece.style.top = "6%";
        piece.style.left = "10%";
        break;
      }
      // GOTO JAIL
      if (row == 1 && column == 11) {
        piece.style.top = "6%";
        piece.style.left = "7%";
        break;
      }
      // BOTTOM ROW
      if (row == 11 && column != 11) {
        piece.style.top = "3%";
        piece.style.left = "0%";
        break;
      }
      // LEFT ROW
      if (row != 11 && column == 1) {
        piece.style.top = "0%";
        piece.style.left = "7%";
        break;
      }
      // TOP ROW
      if (row == 1 && column != 11) {
        piece.style.top = "6%";
        piece.style.left = "5%";
        break;
      }
      // RIGHT ROW
      if (row != 1 && column == 11) {
        piece.style.top = "5%";
        piece.style.left = "3%";
        break;
      }
      
      break;

    case "piece_2":
      // IN-JAIL
      if (in_jail) {
        piece.style.top = "3%";
        piece.style.left = "4%";
        break;
      }
      // GO SPACE
      if (row == 11 && column == 11) {
        piece.style.top = "6%";
        piece.style.left = "0%";
        break;
      }
      // JV SPACE
      if (row == 11 && column == 1) {
        piece.style.top = "3%";
        piece.style.left = "0%";
        break;
      }
      // FREE
      if (row == 1 && column == 1) {
        piece.style.top = "3%";
        piece.style.left = "10%";
        break;
      }
      // GOTO JAIL
      if (row == 1 && column == 11) {
        piece.style.top = "3%";
        piece.style.left = "7%";
        break;
      }
      // BOTTOM ROW
      if (row == 11 && column != 11) {
        piece.style.top = "6%";
        piece.style.left = "0%";
        break;
      }
      // LEFT ROW
      if (row != 11 && column == 1) {
        piece.style.top = "0%";
        piece.style.left = "4%";
        break;
      }
      // TOP ROW
      if (row == 1 && column != 11) {
        piece.style.top = "3%";
        piece.style.left = "5%";
        break;
      }
      // RIGHT ROW
      if (row != 1 && column == 11) {
        piece.style.top = "5%";
        piece.style.left = "6%";
        break;
      }
      
      break;

    case "piece_3":
      // IN-JAIL
      if (in_jail) {
        piece.style.top = "6%";
        piece.style.left = "4%";
        break;
      }
      // GO SPACE
      if (row == 11 && column == 11) {
        piece.style.top = "9%";
        piece.style.left = "0%";
        break;
      }
      // JV SPACE
      if (row == 11 && column == 1) {
        piece.style.top = "6%";
        piece.style.left = "0%";
        break;
      }
      // FREE
      if (row == 1 && column == 1) {
        piece.style.top = "0%";
        piece.style.left = "10%";
        break;
      }
      // GOTO JAIL
      if (row == 1 && column == 11) {
        piece.style.top = "0%";
        piece.style.left = "7%";
        break;
      }
      // BOTTOM ROW
      if (row == 11 && column != 11) {
        piece.style.top = "9%";
        piece.style.left = "0%";
        break;
      }
      // LEFT ROW
      if (row != 11 && column == 1) {
        piece.style.top = "0%";
        piece.style.left = "1%";
        break;
      }
      // TOP ROW
      if (row == 1 && column != 11) {
        piece.style.top = "0%";
        piece.style.left = "5%";
        break;
      }
      // RIGHT ROW
      if (row != 1 && column == 11) {
        piece.style.top = "5%";
        piece.style.left = "9%";
        break;
      }
      
      break;

    case "piece_4":
      // IN-JAIL
      if (in_jail) {
        piece.style.top = "0%";
        piece.style.left = "7%";
        break;
      }
      // GO SPACE
      if (row == 11 && column == 11) {
        piece.style.top = "3%";
        piece.style.left = "3%";
        break;
      }
      // JV SPACE
      if (row == 11 && column == 1) {
        piece.style.top = "9%";
        piece.style.left = "0%";
        break;
      }
      // FREE
      if (row == 1 && column == 1) {
        piece.style.top = "6%";
        piece.style.left = "7%";
        break;
      }
      // GOTO JAIL
      if (row == 1 && column == 11) {
        piece.style.top = "6%";
        piece.style.left = "4%";
        break;
      }
      // BOTTOM ROW
      if (row == 11 && column != 11) {
        piece.style.top = "3%";
        piece.style.left = "3%";
        break;
      }
      // LEFT ROW
      if (row != 11 && column == 1) {
        piece.style.top = "3%";
        piece.style.left = "7%";
        break;
      }
      // TOP ROW
      if (row == 1 && column != 11) {
        piece.style.top = "6%";
        piece.style.left = "2%";
        break;
      }
      // RIGHT ROW
      if (row != 1 && column == 11) {
        piece.style.top = "2%";
        piece.style.left = "3%";
        break;
      }
      
      break;
      
    case "piece_5":
      // IN-JAIL
      if (in_jail) {
        piece.style.top = "3%";
        piece.style.left = "7%";
        break;
      }
      // GO SPACE
      if (row == 11 && column == 11) {
        piece.style.top = "6%";
        piece.style.left = "3%";
        break;
      }
      // JV SPACE
      if (row == 11 && column == 1) {
        piece.style.top = "9%";
        piece.style.left = "3%";
        break;
      }
      // FREE
      if (row == 1 && column == 1) {
        piece.style.top = "3%";
        piece.style.left = "7%";
        break;
      }
      // GOTO JAIL
      if (row == 1 && column == 11) {
        piece.style.top = "3%";
        piece.style.left = "4%";
        break;
      }
      // BOTTOM ROW
      if (row == 11 && column != 11) {
        piece.style.top = "6%";
        piece.style.left = "3%";
        break;
      }
      // LEFT ROW
      if (row != 11 && column == 1) {
        piece.style.top = "3%";
        piece.style.left = "4%";
        break;
      }
      // TOP ROW
      if (row == 1 && column != 11) {
        piece.style.top = "3%";
        piece.style.left = "2%";
        break;
      }
      // RIGHT ROW
      if (row != 1 && column == 11) {
        piece.style.top = "2%";
        piece.style.left = "6%";
        break;
      }
      
      break;

    case "piece_6":
      // IN-JAIL
      if (in_jail) {
        piece.style.top = "6%";
        piece.style.left = "7%";
        break;
      }
      // GO SPACE
      if (row == 11 && column == 11) {
        piece.style.top = "9%";
        piece.style.left = "3%";
        break;
      }
      // JV SPACE
      if (row == 11 && column == 1) {
        piece.style.top = "9%";
        piece.style.left = "6%";
        break;
      }
      // FREE
      if (row == 1 && column == 1) {
        piece.style.top = "0%";
        piece.style.left = "7%";
        break;
      }
      // GOTO JAIL
      if (row == 1 && column == 11) {
        piece.style.top = "0%";
        piece.style.left = "4%";
        break;
      }
      // BOTTOM ROW
      if (row == 11 && column != 11) {
        piece.style.top = "9%";
        piece.style.left = "3%";
        break;
      }
      // LEFT ROW
      if (row != 11 && column == 1) {
        piece.style.top = "3%";
        piece.style.left = "1%";
        break;
      }
      // TOP ROW
      if (row == 1 && column != 11) {
        piece.style.top = "0%";
        piece.style.left = "2%";
        break;
      }
      // RIGHT ROW
      if (row != 1 && column == 11) {
        piece.style.top = "2%";
        piece.style.left = "9%";
        break;
      }
      
      break;
  }
  piece.style.gridColumn = column;
  piece.style.gridRow = row;
}

// window.onload = function() {
//   // var test_1 = "group_2";
//   // var test_2 = "group_6";

// //   swap(test_1, test_2);
// // }

// var column = 1;
// var row = 11;
// var piece_id = "piece_1";
// var in_jail = false;
// move(piece_id, row, column, in_jail);
// }