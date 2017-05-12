<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" %>
<div id="popup" class="popup">
    <div class="popup_wrap">
        <div class="con"></div>
        <div class="btn cf">
            <a href="#" id="popup_cancel" onclick="hidePopup();" class="i1">取消</a>
            <a href="javascript:void(0);" id="popup_ok" onclick="hidePopup();"class="i2">知道了</a>
        </div>
    </div>
</div>
<script type="text/javascript">
    function popup(msg,leftBtn,rightBtn,leftCall,rightCall){
        if($("#popup")) {
            $("#popup").find(".con").html(msg);
            if(leftBtn && leftBtn!=""){
                $("#popup").find(".i1").html(leftBtn);
            }
            if(rightBtn && rightBtn!=""){
                $("#popup").find(".i2").html(rightBtn);
            }
            if(leftCall){
                $("#popup").find(".i1").click(function(){
                	leftCall();
                });
            }
            if(rightCall){
                $("#popup").find(".i2").click(function(){
                	rightCall();
                });
            }

            $("#popup").show();
        }
        if($(".popup_wrap")) {
            var popupH = $(".popup_wrap").outerHeight();
            $(".popup_wrap").animate({ "margin-top": "-" + popupH / 2 });
        }
    }
    function hidePopup() {
        $("#popup").hide();
    }
</script>
