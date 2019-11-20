$(function(){

	// 二级菜单点击事件
	$(document).on('click', 'div.sort-area>ul>li', function(){
		// 构造选中项
		$(this).siblings('li').removeClass('sort-selected');
		$(this).addClass('sort-selected');
		// 构造URL
		var url = '/commodity/sort?primaryId=' + $('div.sort-area').attr('data-sort-id') + "&minorId=" + $('div.sort-area>ul>li.sort-selected').attr('data-sort-id');
		// 跳转页面
		$(window).attr('location', url);
	});
	
	
	// 三级菜单点击事件
	$(document).on('click', 'div.secondary-menu>ul>li', function(){
		// 构造选中项
		$(this).siblings('li').removeClass('sort-selected');
		$(this).addClass('sort-selected');
		// 构造URL
		var url = '/commodity/sort?primaryId=' + $('div.sort-area').attr('data-sort-id') + "&minorId=" + $('div.sort-area>ul>li.sort-selected').attr('data-sort-id') + '&sortId=' + $('div.secondary-menu>ul>li.sort-selected').attr('data-sort-id');
		// 跳转页面
		$(window).attr('location', url);
	});
});
