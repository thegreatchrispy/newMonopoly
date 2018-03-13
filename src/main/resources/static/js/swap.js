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

// window.onload = function() {
//   var test_1 = "group_3";
//   var test_2 = "group_7";

//   swap(test_1, test_2);
// };

//////////////////////////////////


// $(document).ready(function(){
//   $(".center").style.background = url(fall.jpg);
// });

/*$(function() {
  
  
  
});*/
//document.getElementsByClassName("center").style.backgroundImage = "url('fall.jpg')";
// function loopImageFunction(){
//   var c = document.getElementById("center");
//   images = ["spring.jpg","summer.jpg","fall.jpg","winter.jpg"];
//   i=0;

//   setInterval(function(){
//     if (i === images.length) i=0;
//     c.style.background = "url(../images/"+images[i]+")";
//     c.style.backgroundSize = "50%";
//     i++;
//   }, 3000);
// }
/*
$(document).ready(function(){
  $(".center").style.background = url(fall.jpg);
});

var wallpapers = [
  'spring.jpg','summer.jpg','fall.jpg','winter.jpg'
];
var fullBgElement = $$('.center').first();
var i = 0;
window.setInterval(function() {
  fullBgElement.setStyle({
    'background': 'url(' + wallpapers[i] + ')'
  });
  if (i === (wallpapers.length -1)) {
    i = 0;
  }
  i++;  
}, 3000);

var bg = $('.center').css('background-image');
$('.center').fadeOut(3000,function() {
        $('.center').css({
            'background-image' : "url('fall.jpg')"
        });
$('.center').fadeIn(3000);*/
