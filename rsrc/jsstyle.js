/*$(function() {
  
  
  
});*/
//document.getElementsByClassName("center").style.backgroundImage = "url('fall.jpg')";
function loopImageFunction(){
  var c = document.getElementById("center");
  images = ["spring.jpg","summer.jpg","fall.jpg","winter.jpg"];
  i=0;

  setInterval(function(){
    if (i === images.length) i=0;
    c.style.background = "url("+images[i]+")";
    c.style.backgroundSize = "50%";
    i++;
  }, 3000);
}
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
