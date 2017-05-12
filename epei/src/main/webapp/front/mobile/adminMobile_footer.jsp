<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="footer cf">
    <ul>
        <li class="on"><a href="/front/mobile/index.jsp"><i class="i1"></i><span>首页</span></a></li>
        <li><a href="/front/mobile/adminIndex.jsp"><i class="i2"></i><span>订单</span></a></li>
        <li><a href="/profile/adminCenter.html"><i class="i3"></i><span>我的</span></a></li>
    </ul>
</div>

<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script>
    var myCenter = new
            Array("/servicedPerson/personEdit.html","/servicedPerson/personList.html","/profile/adminCenter.html","/coupon/list.html","/front/mobile/faq.jsp","/front/mobile/about.jsp");

    var orderPage = new Array("/order/showList.html","/order/detail.html","","");
    $(function(){
        $(".footer li").each(function(i){
            if(isOrderPage(window.location.pathname+"")
                    && $(this).find("a").attr("href")+""=="/order/showList.html"){
                $(this).addClass("on").siblings("li").removeClass("on");
            }

            if(isMyCenterPage(window.location.pathname+"") &&
                    $(this).find("a").attr("href")+""=="/profile/adminCenter.html"){
                $(this).addClass("on").siblings("li").removeClass("on");
            }

            if($(this).find("a").attr("href")+"" == window.location.pathname+""){
                $(this).addClass("on").siblings("li").removeClass("on");
            }
        });
    })

    function isMyCenterPage(path){
        for(i = 0;i < myCenter.length;i ++){
            if(myCenter[i] == path){
                return true;
            }
        }
        return false;
    }

    function isOrderPage(path){
        console.log(path);
        for(i = 0;i < orderPage.length;i ++){
            if(orderPage[i] == path){
                return true;
            }
        }
        return false;
    }

</script>
<!-- header end -->