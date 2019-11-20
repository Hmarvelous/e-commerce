// 页面加载完毕执行
$(function(){
	
	
	// 默认获取全部评论信息,默认获取第1页,每页10条
	getComment(1, 10);
	
	
	// 评论级别单选框改变
	$('input[type=radio][name=comment-radio]').change(function() {
		getComment(1, 10);
	});
	
	// 绑定商品小图片移入事件
	$(document).on('mouseover', '.small-image>ul>li>img', function(){
	    var mainImage = $('.main-image>img');

	    // 遍历同胞节点
	    $(this).parent('li').siblings().find('img').css('border', '2px solid #FFF');
	    $(this).css('border', '2px solid #000');

	    mainImage.attr('src', $(this).attr('src'));
	});

	// 商品参数选中
	$(document).on('click', '.parameter-option>ul>li', function(){
	    $(this).siblings().removeClass('parameter-selected');
	    $(this).addClass('parameter-selected');
	    
	    // 获取当前选中参数价格
	    var selectedPrice = parseFloat($(this).attr('price'));
	    // 获取会员价格
	    var basePrice = parseFloat($('.member-price>a').attr('price'));
	    // 设置总价格
	    var result = basePrice + selectedPrice;
	    $('.member-price>a').text('¥' + result.toFixed(2));
	});

	// 商品数量
	$('.add-count').click(function(){
	    var number = $('#commodity-count');
	    var count = number.val();
	    count++;
	    number.val(count);
	    if (number.val() > 999){
	        number.val(999);
	    }
	});
	$('.cut-back-count').click(function(){
	    var number = $('#commodity-count');
	    var count = number.val();
	    count--;
	    number.val(count);
	    if (number.val() < 1){
	        number.val(1);
	    }
	});

	// 购买按钮单击事件
	$('.buy-button').click(function(){
	    // 获取选取参数
	    var parameters = '';
	    $('.parameter-option>ul>li.parameter-selected').each(function(i, n){
	        // 获取参数ID
	        parameters += $(n).text() + ',';
	    });
	    parameters = parameters.substring(0, parameters.lastIndexOf(','));

	    // 获取商品数量
	    var count = $('#commodity-count').val();
	    if (count < 1 || count > 999){
	        // 商品数量不正确
	        simple_msg('商品数量只允许 0-999');
	    }
	    
	    // 获取商品ID
	    var commodityId = $('div.details-box').attr('id');
	    
	    // 跳转到购买页面
	    // 构造参数
	    var parameter = commodityId + '_' + parameters + '_' + count;
	    $(window).attr('location', '/order/confirm_order/?parameter=' + parameter);

	});
	// 添加购物车按钮单击事件
	$('.add-cart').click(function(){
	    // 获取选取参数
	    var parameter = '';
	    $('.parameter-option').each(function(i, n){
	    	// 获取参数名
	    	var name = $(n).find('span').text();
	        // 获取参数
	    	var par = $(n).find('ul>li.parameter-selected').text();
	    	// 拼接
	    	parameter += name + '-' + par + ',';
	    });
	    parameter = parameter.substring(0, parameter.lastIndexOf(','));
	    var commodityId = $('div.details-box').attr('id');

	    // 获取商品数量
	    var count = $('#commodity-count').val();
	    if (count < 1 || count > 999){
	        // 商品数量不正确
	        simple_msg('商品数量只允许 0-999');
	        return;
	    }
	    
	    // 加入购物车
	    $.ajax({
    		type: 'POST',
	    	url: '/cart/add',
	    	dataType: 'json',
	    	data: {
	    		commodityId: commodityId,
		    	count: count,
		    	parameter: parameter
	    	},
	    	success: function(json){
	    		// 判断是否已经登录
	    		if (json.code == null || json.length == 0){
	    			// 跳转到登录页面
	    			window.location.href="/member/login";
	    			return;
	    		} else if ($.trim(json.code) == 'success'){
	    			// 添加成功
	    			SeccussPopUps('成功添加到购物车');
	    			// 更新头部导航栏购物车数量
	    			$('#cart-count').text(json.reCount);
	    		} else if ($.trim(json.code) == 'fail'){
	    			// 添加失败
	    			FialPopUps('添加到购物车失败');
	    		}
	    	},
	    	error: function(xhr, status){
	    		if (xhr.responseText.indexOf("登录") > 0){
	    			// 未登录
	    			// 跳转到登录页面
	    			window.location.href="/member/login";
	    		}
	    	}});
	    
	});
	
	// 分页单击事件
	$(document).on('click', '.comment-pagination>ul>li', function(){
		var page = $(this).attr('id');
		if (typeof(page) != "undefined" && page.length > 0){
			getComment(page, 10);
		}
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


// 获取选中的评论级别
function getCommmentLevel(){
	return $('input[name=comment-radio]:checked').val();
}
// 获取商品ID
function getCommodityId(){
	return $('#commodityId').val();
}

// 获取评论并显示
function getComment(page, size){
	$.getJSON('/comment/getComments', {
		level: getCommmentLevel(),
		commodityId: getCommodityId(),
		pageNow: page,
		pageSize: size
	}, function(json){
//		console.log(json);
		
		var commentUl = $('.comment-area>ul');
		// 情况评论
		commentUl.html('');
		// 遍历评论
		if (json.list.length > 0){
			$.each(json.list, function(i, data){
				var commentLevel = '好评';
				if (data.commentLevel == 1){
					commentLevel = '中评';
				}
				if (data.commentLevel == 2){
					commentLevel = '差评';
				}
				commentUl.append(commentHtml(data, commentLevel));
			});
		} else{
			// 没有任何评论
			var html = '<li class="no-comment">暂无评论</li>';
			commentUl.append(html);
		}
		
		// 分页
		var pageUl = $('.comment-pagination>ul');
		// 清空分页
		pageUl.html('');
		// 上一页
		var previousHtml = '';
		if (json.hasPreviousPage){
			previousHtml = '<li id="' + json.prePage + '">上一页</li>';
		} else {
			previousHtml = '<li class="pagination-disable">上一页</li>';
		}
		pageUl.append(previousHtml);
		
		// 遍历页码
		$.each(json.navigatepageNums, function(i, page){
			var pageHtml = '';
			if (page == json.pageNum){
				pageHtml = '<li class="pagination-selected">' + page + '</li>';
			} else {
				pageHtml = '<li id="' + page + '">' + page + '</li>';
			}
			pageUl.append(pageHtml);
		});
		// 如果没有页码
		if (json.navigatepageNums.length <= 0){
			var pageHtml = '<li class="pagination-selected">1</li>';
			pageUl.append(pageHtml);
		}
		
		// 下一页
		var nextHtml = '';
		if (json.hasNextPage){
			nextHtml = '<li id="' + json.nextPage + '">下一页</li>';
		} else {
			nextHtml = '<li class="pagination-disable">下一页</li>';
		}
		pageUl.append(nextHtml);
		
	});
}

// 构造商品评论HTML
function commentHtml(data, commentLevel){
	html = '';
	html += '<li>';
	html += '<div class="comment-left">';
	html += '<span>' + data.content + '</span>';
	html += '<a>评论时间：' + data.commentDateString + '</a>';
	html += '<a style="font-weight: bold; padding-left: 40px;">' + commentLevel + '</a>';
	html += '</div>';
	html += '<div class="comment-right"><span>' + data.member.username + '</span></div>';
	html += '<div class="clean"></div>';
	html += '</li>';
	return html;
}


// 简单的提示窗口
function simple_msg(text){
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.msg(text, function(){});
    });
}
