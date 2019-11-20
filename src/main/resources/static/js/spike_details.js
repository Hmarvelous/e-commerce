var t;
var t2;

// 页面加载完毕执行
$(function(){
	
	
	// 默认获取全部评论信息,默认获取第1页,每页10条
	getComment(1, 10);
	
	// 开启倒计时
	startCountDown();

	// 查询订单结果
	queryOrderResult();
	
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
	});


	// 购买按钮单击事件
	$('button.buy-button').click(function(){
		
	    if (!$(this).is('.instant-spike')){
	    	return;
	    }
	    

		if ($(this).is('.go-buy')){
			// 去付款
			$(window).attr('location', $(this).attr('data-buy-url'));
			return;
		}
	    
	    // 进行秒杀
	    seckill();
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

	
	
	// 秒杀成功付款按钮
	$(document).on('click', 'button.immediate-payment', function(){
		// 获取地址
		var url = $(this).attr('data-buy-url');
		$(window).attr('location', url);
	});
	
	// 秒杀成功取消付款按钮
	$(document).on('click', 'button.cancel-payment', function(){
		// 获取商品ID
		var id = $('div.details-box').attr('id');
		// 取消付款
		$.get('/spike/cancelSeckill', {
			id: id
		}, function(json){
			console.log(json);
		}, 'json');
		$('div.result-popup').remove();
		// 刷新本页面
		$(window).attr('location', '/spike/spikeDetails/' + id);
	});
	
	
	
	// 判断商品库存
	$.get('/spike/seckillCommodityCount', {
		id: $('div.details-box').attr('id')
	}, function(json){
		if (json.count == 0) {
			$('button.buy-button').removeClass('instant-spike');
			$('button.buy-button').addClass('buy-disable');
			$('button.buy-button').text('已售完');
		}
	}, 'json');
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


// 开启倒计时
function startCountDown(){
	t = setInterval("toCountdown(timeToTimestamp('" + $('button.buy-button').attr('data-start-time') + "') - (getNowTime()))", 10);
}

//获取当前系统时间戳
function getNowTime(){
    return new Date().getTime() / 1000;
}

// 日期转时间戳
function timeToTimestamp(time){
    var f = time.split(' ', 2);
    var d = (f[0] ? f[0] : '').split('-', 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(
        parseInt(d[0], 10) || null,
        (parseInt(d[1], 10) || 1) - 1,
        parseInt(d[2], 10) || null,
        parseInt(t[0], 10) || null,
        parseInt(t[1], 10) || null,
        parseInt(t[2], 10) || null
    )).getTime() / 1000;
}

// 不足两位补0
function makeUp(number){
    if (number < 10) return '0' + number;
    return number + '';
}

// 毫秒转倒计时
function toCountdown(s){
    s = parseInt(s * 100);
    // 转换
    var millisecond = s % 100;
    var second =  Math.floor(s / 100 % 60);
    var minute = Math.floor(s / 100 / 60 % 60);
    var hour = Math.floor(s / 100 / 60 / 60 % 24);
    var day = Math.floor(s / 100 / 60 / 60 / 24 % 30);
    if (day > 0){
        // 还剩至少一天
        $('button.buy-button').text('即将开始');
        clearInterval(t);
    }
    if (s <= 0){
        $('button.buy-button').text('立即秒杀');
        $('button.buy-button').addClass('instant-spike');
        $('button.buy-button').removeClass('count-down');
        clearInterval(t);
        return;
    }

    // 显示
    if (hour > 0){
        $('button.buy-button').text(makeUp(hour) + ':' + makeUp(minute) + ':' + makeUp(second));
    } else if (minute > 0) {
        $('button.buy-button').text(makeUp(minute) + ':' + makeUp(second) + ':' + makeUp(millisecond));
    } else {
        $('button.buy-button').text(makeUp(second) + ':' + makeUp(millisecond));
    }
}


// 进行秒杀
function seckill(){
	// 获取秒杀商品参数
	var id = $('div.details-box').attr('id');
	var parameters = '';
    $('.parameter-option>ul>li.parameter-selected').each(function(i, n){
        // 获取参数ID
        parameters += $(n).text() + ',';
    });
    parameters = parameters.substring(0, parameters.lastIndexOf(','));
    
	// 开始秒杀
	$.get('/spike/start', {
		id: id,
		parameters: parameters
	}, function(json){
//		console.log(json);
		
		if ($.trim(json.status) == 'complete'){
			// 已售完,更新按钮状态
			$('button.buy-button').text('已售完');
		}
		
	}, 'json');
	
	// 请求结束,使按钮无法点击,防止重复购买的第一步
	$('button.buy-button').removeClass('instant-spike');
	$('button.buy-button').addClass('buy-disable');
	
	layui.use('layer', function(){
		layer.msg('正在秒杀，请稍等...', {
			  icon: 16,
			  area: ['230px', '65px'],
			  shade: 0.03,
			  time: 0
		});
	});
	
	// 提前查询一次,以提高查询速度
	queryOrderResult();
	
	// 轮询查询
	polling();
}



// 轮询查询结果
function polling(){
	t2 = setInterval(function(){
		queryOrderResult();
	}, 1000);
}


// 查询订单结果
function queryOrderResult(){
	var id = $('div.details-box').attr('id');
	
	$.get('/spike/state', {
		id: id
	}, function(json){
//		console.log(json);
		
		if ($.isEmptyObject(json.result)){
			// 判断商品库存
			$.get('/spike/seckillCommodityCount', {
				id: id
			}, function(json){
				console.log(json);
				if (json.count == 0) {
					$('button.buy-button').removeClass('instant-spike');
					$('button.buy-button').addClass('buy-disable');
					$('button.buy-button').text('已售完');
				}
			}, 'json');
			// 查询到空订单,未秒杀完毕
			return;
		}
		
		layui.use('layer', function(){
			// 关闭弹出层
			layer.closeAll();
		});
		
		
		// 停止定时器
		clearInterval(t2);
		
		// 更新按钮状态
		if ($.trim(json.result) == '待付款'){
			$('button.buy-button').addClass('go-buy');
			$('button.buy-button').text('去付款');
			// 提示去付款
			var buyUrl = '/order/confirm_order?parameter=' + json.commodityId + "_" + json.parameters + "_" + json.count + "_1_" + json.spikeCommodityId;
			$('button.buy-button').attr('data-buy-url', buyUrl);
			
			// 弹出提示框
			promptBox('秒杀成功！', 'success', buyUrl);
		} else if ($.trim(json.result) == '已付款') {
			$('button.buy-button').removeClass('instant-spike');
			$('button.buy-button').addClass('buy-disable');
			$('button.buy-button').text('无法再次秒杀');
		}
		
		
		// 判断商品库存
		$.get('/spike/seckillCommodityCount', {
			id: id
		}, function(json){
			console.log(json);
			if (json.count == 0) {
				$('button.buy-button').removeClass('instant-spike');
				$('button.buy-button').addClass('buy-disable');
				$('button.buy-button').text('已售完');
			}
		}, 'json');
		
	}, 'json');
}


// 提示框
function promptBox(message, status, url){
	html = '';
	html += '<div class="result-popup">';
	html += '<div>';
	html += '<span class="result-title">提示</span>';
	html += '<span>' + message + '</span>';
	if (status == 'success'){
		html += '<button class="immediate-payment" data-buy-url=' + url + '>立即付款</button>';
		html += '<button class="cancel-payment">取消</button>';
	} else {
		html += '<button class="cancel-payment">确定</button>';
	}
	html += '</div>';
	html += '</div>';
	$('body').append(html);
}