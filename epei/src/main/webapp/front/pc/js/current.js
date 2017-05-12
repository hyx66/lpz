function current(){
      if (!document.getElementById("nav") || !document.getElementById("subnav")) return false;/*进行必要的测试，避免没有id时出错*/   
     var nav = document.getElementById("nav").getElementsByTagName("a"),
           active = document.getElementById("nav").getElementsByTagName("li"),
           subnav = document.getElementById("subnav"),
           subnavLi = subnav.getElementsByTagName("li"),
           subnavA = subnav.getElementsByTagName("a"),
           links = window.location.href;

      //MainNav
      for (var i = 0; i <nav.length; i++) {
        var a = nav[i].href.length,
              newStr=links.substring(0, a);

        if(nav[i] == links || (nav[i].href==newStr && i != 0)) {
          active[i].className = "current";
        } else {
              active[i].removeAttribute('class');
        }
      }

      //SubNav
      for(var x=0; x<subnavA.length; x++) {
        var b = subnavA[x].href.length,
              newStr1 = links.substring(0, b);

        if(subnavA[x].href == links || (subnavA[x].href==newStr1 && x != 0)) {
            subnavA[x].className += " cur";
            subnavLi[x].id = "first";
            subnav.insertBefore(subnavLi[x],subnav.firstChild);  //move current item to first
        } else {
            subnavLi[x].removeAttribute("id");
            subnavA[x].className = "behavior";
        }
      }
 }
 window.onload=current;