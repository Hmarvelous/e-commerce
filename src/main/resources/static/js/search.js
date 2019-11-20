$(function(){
	
	// 商品品牌选中事件
	$(document).on('click', 'div.screening-area>ul>li.brand-paramter', function(){
		// 取消其他的选中状态
		$(this).siblings('li').removeClass('condition-selected');
		// 设置自己为选中状态
		$(this).addClass('condition-selected');
		
		// 获取选中参数
		var keyword = $('div.search-box>input[type=search]').val();
		var page = $('div.pagination-area>ul>li.pagination-selected').attr('data-page');
		if (typeof(page) == 'undefined'){
			page = 1;
		}
		var size = 20;
		var brandId = $(this).attr('id');
		console.log(brandId);
		var url = '/commodity/search?keyword=' + keyword + '&page=' + page + '&size=' + size;
		if (brandId != -1){
			url += '&brand=' + brandId;
		}
		// 跳转地址
		window.location.href = url;
	});
	
	// 分页事件
	$(document).on('click', 'div.pagination-area>ul>li', function(){
		if ($(this).is('.pagination-disable')){
			return;
		}
		// 获取参数
		var keyword = $('div.search-box>input[type=search]').val();
		var page = $(this).attr('data-page');
		var size = 20;
		// 跳转地址
		window.location.href = '/commodity/search?keyword=' + keyword + "&page=" + page + "&size=" + size;
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