
function loopImageFunction(){
  images = ["spring.jpg","summer.jpg","fall.jpg","winter.jpg"];
  i=0;

  setInterval(function(){
    if (i === images.length) i=0;
    $(".center").css("background","url(../images/"+images[i]+")");
    $(".center").css("background-size","50%");
    i++;
  }, 3000);
}

function rename(){
  var nameprop = Packages.com.newmonopoly.renaming.getcompanyName();
  alert(nameprop);
}