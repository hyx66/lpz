$(function(){
	$(".header a").hover(function(){
		$(this).parent().addClass("active").siblings().removeClass("active")
	},function(){
		$(".header a").mouseleave(function(){
			$(".act").addClass("active").siblings().removeClass("active")
		});
	});
	/*$(".header a").mouseleave(function(){
		$(".act").addClass("active").siblings().removeClass("active")
	});*/
});