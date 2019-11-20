
$(function() {
	// 轮播广告
	layui.use('carousel', function(){
        var carousel = layui.carousel;
        carousel.render({
            elem: '#test1',
            width: '100%',
            height: '500px',
            arrow: 'none',
            anim: 'fade'
        });
    });
	
	
	// 搜索按钮单击事件
	$('div.search-box>button').click(function(){
		// 获取关键字
		var keyword = $('div.search-box>input[type=search]').val();
		
		// 跳转页面
		window.location.href = '/commodity/search?keyword=' + keyword;
	});
	
	// 搜索框回车事件
	$(document).keydown(function(e) {
		if (e.keyCode == 13) {
			// 获取关键字
			var keyword = $('div.search-box>input[type=search]').val();
			
			// 跳转页面
			window.location.href = '/commodity/search?keyword=' + keyword;
		}
	});
});

