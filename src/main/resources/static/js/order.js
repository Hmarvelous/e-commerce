$(function(){

	// 屏幕滚动事件
    $(window).bind("scroll", function () {
    	setDivCenter('.comment-pop-ups');
    });
	
	// 初始化获取全部订单信息
	getOrder(-1, 1, 10);
	
    // 顶部菜单选中事件
    $('div.order-menu>ul>li').click(function(){
        // 清除其它项选中状态
        $(this).siblings('li').removeClass('order-menu-selected');
        // 设置自己为选中状态
        $(this).addClass('order-menu-selected');

        // 获取选中菜单ID
        var menuId = $(this).attr('id');
        
        // 获取订单状态ID
        var orderStatus = $(this).attr('id');
        // 请求订单数据
        getOrder(orderStatus, 1, 10);
    });


    // 订单回收站点击事件
    $('a.order-recycle-bin').click(function(){
        $('div.order-menu>ul>li.order-menu-selected').removeClass('order-menu-selected');
    });

    // 顶部分页
    $(document).on('click', 'div.simple-paging>a.previousPage', function(){
    	if ($(this).is('.button-disable')){
    		return;
    	}
    	// 获取点击的页码
        var page = $(this).attr('data-page');
        // 获取订单状态
        var orderStatus = $('div.order-menu>ul>li.order-menu-selected').attr('id');
        getOrder(orderStatus, page, 10);
    });
    $(document).on('click', 'div.simple-paging>a.nextPage', function(){
    	if ($(this).is('.button-disable')){
    		return;
    	}
    	// 获取点击的页码
        var page = $(this).attr('data-page');
        // 获取订单状态
        var orderStatus = $('div.order-menu>ul>li.order-menu-selected').attr('id');
        getOrder(orderStatus, page, 10);
    });
    
    // 下方分页控制
    $(document).on('click', 'div.paging-area>ul>li.paging-number', function(){
        // 清除其它项选中状态
        $(this).siblings('li').removeClass('paging-selected');
        // 设置自己为选中状态
        $(this).addClass('paging-selected');
        
        // 获取点击的页码
        var page = $(this).attr('data-page');
        // 获取订单状态
        var orderStatus = $('div.order-menu>ul>li.order-menu-selected').attr('id');
        getOrder(orderStatus, page, 10);
    });
    
    // 上一页控制
    $(document).on('click', 'div.paging-area>ul>li.previousPage', function(){
    	if ($(this).is('.paging-disable')){
    		return;
    	}
    	var page = $(this).attr('data-page');
    	var orderStatus = $('div.order-menu>ul>li.order-menu-selected').attr('id');
    	getOrder(orderStatus, page, 10);
    });
    // 下一页控制
    $(document).on('click', 'div.paging-area>ul>li.nextPage', function(){
    	if ($(this).is('.paging-disable')){
    		return;
    	}
    	var page = $(this).attr('data-page');
    	var orderStatus = $('div.order-menu>ul>li.order-menu-selected').attr('id');
    	getOrder(orderStatus, page, 10);
    });
    
    
    // 订单回收站
    $(document).on('click', 'div.order-menu>ul>a.order-recycle-bin', function(){
    	getOrder(5, 1, 10);
    });
    
    
    
    // 评论按钮
    $(document).on('click', 'a.evaluation-button', function(){
    	// 订单ID,商品ID
    	var orderNumber = $(this).attr('data-order-number');
    	var commodityId = $(this).attr('data-commodity-id');
    	showCommentBox(orderNumber, commodityId);
    });
    
    
    // 弹窗评论按钮单击事件
    $(document).on('click', 'div.comment-pop-ups>div.comment-button>button', function(){
    	var orderNumber = $('div.comment-pop-ups>input[name=order-number]').attr('data-order-number');
    	var commodityId = $('div.comment-pop-ups>input[name=commodity-id]').attr('data-commodity-id');
    	var content = $('div.comment-pop-ups>textarea').val();
    	var commentLevel = $('span.layui-inline').text();
    	if ($.trim(commentLevel) == '好评'){
    		commentLevel = 0;
    	} else if ($.trim(commentLevel) == '中评'){
    		commentLevel = 1;
    	} else {
    		commentLevel = 2;
    	}
    	if (content.length < 5 || content.length > 100){
    		alert('评论内容只能输入5-100字');
    		return;
    	}
    	$.post('/comment/addComment', {
    		orderNumber: orderNumber,
    		commodityId: commodityId,
    		content: content,
    		commentLevel: commentLevel
    	}, function(json){
    		if ($.trim(json.code) == 'success'){
    			// 添加成功
    			$('div.comment-pop-ups').remove();
    			// 刷新页面
    			var orderStatus = $('div.order-menu>ul>li.order-menu-selected').attr('id');
    	    	getOrder(orderStatus, 1, 10);
    		} else {
    			// 添加失败
    			alert('添加评论失败');
    		}
    	}, 'json');
    });
    
    // 关闭评论按钮
    $(document).on('click', 'div.comment-title>button', function(){
    	$('div.comment-pop-ups').remove();
    });
    
    
    // 确定收货
    $(document).on('click', 'span.receipt-button', function(){
    	// 获取订单号
    	var orderNumber = $(this).parent('div.statistics-box').prevAll('div.order-item-header').find('span.order-info>a:eq(1)').text();
    	$.post('/order/receipt', {
    		orderNumber: orderNumber
    	}, function(json){
    		if ($.trim(json.code) == 'success'){
    			// 确认收货成功
    			refreshPage();
    		} else {
    			// 确认收货失败
    			alert('确认收货失败');
    		}
    	}, 'json');
    });
    
    
    // 付款按钮
    $(document).on('click', 'span.payment-button', function(){
    	// 订单号
    	var orderNumber = $(this).parent('div.statistics-box').prevAll('div.order-item-header').find('span.order-info>a').last().text();
    	
    	// 获取余额信息
    	$.get('/order/getBalance', {
    		orderNumber: orderNumber
    	}, function(json){
//    		console.log(json);
    		
        	// 弹窗提示付款
    		$('body').append(paymentPopUps(parseInt(json.balance), parseInt(json.order.totalAmount), orderNumber));
    		setDivCenter('div.pay-pop-ups');
    	}, 'json');
    	
    });
    
    
    // 付款弹窗取消按钮
    $(document).on('click', 'button.pay-cancel', function(){
        $('div.pay-pop-ups').remove();
    });
    
    
    // 付款弹窗付款按钮
    $(document).on('click', 'button.pay-define', function(){
        // 如果是被禁用状态则点击失效
        if ($(this).is('.unavailable')){
            return;
        }

        // 付款
        $.post('/order/payment', {
        	orderNumber: $('div.pay-pop-ups').attr('data-order-number')
        }, function(json){
        	if ($.trim(json.code) == 'success'){
        		// 购买成功,跳转到订单页面
        	    $(window).attr('location', '/order');
        	} else {
        		// 付款失败
        		alert("购买失败");
        	}
        }, 'json');
    });
    
    
    
    // 订单催促按钮单击事件
    $(document).on('click', 'span.urge-button', function(){
    	if ($(this).is('.prohibition-of-urging')){
    		// 禁止点击
    		return;
    	}
    	
    	var orderNumber = $(this).parent('div.statistics-box').prevAll('div.order-item-header').find('span.order-info>a').last().text();

    	// 发送订单催促请求
    	$.get('/order/urge', {
    		orderNumber: orderNumber
    	}, function(json){
    		console.log(json);
    		if ($.trim(json.result) == 'success'){
    			// 催促成功
    			orderUrgePopupHtml("催促成功，我们会通知商家尽快发货！");
    			// 刷新界面
    			var orderStatus = $('div.order-menu>ul>li.order-menu-selected').attr('id');
    	    	getOrder(orderStatus, 1, 10);
    		} else {
    			// 催促失败
    			orderUrgePopupHtml("催促失败，请联系商家！");
    		}
    	}, 'json');
    });
    
    
    // 催促弹窗确定按钮
    $(document).on('click', 'div.order-urge-popup>div>button', function(){
    	$('div.order-urge-popup').remove();
    });
});

//获取订单信息
function getOrder(orderStatus, page, size){
	// 请求数据
	$.getJSON('/order/getOrder', {
		orderStatus: orderStatus,
		page: page,
		size: size
	}, function(json){
		// 显示区域
		var showArea = $('div.show-area>ul');
		showArea.html('');
		// 遍历订单
		$.each(json.list, function(i, n){
			var html = orderItemLi(n);
			showArea.append(html);
		});
		
		
		// 遍历分页
		var pagingArea = $('div.paging-area>ul');
		pagingArea.html('');
		if (json.navigatepageNums.length == 0){
			// 没有订单数据
			$('div.paging-area>ul').append('<div class="not-order-box"><span>暂无订单</span></div>');
		} else {
			// 有订单数据，添加订单项
			pagingArea.append('<li class="previousPage" data-page="' + json.prePage + '"><</li>');
			$.each(json.navigatepageNums, function(i, n){
				var html = '<li class="paging-number" data-page="' + n + '">' + n + '</li>';
				if (json.pageNum == n){
					html = '<li class="paging-selected paging-number" data-page="' + n + '">' + n + '</li>';
				}
				pagingArea.append(html);
			});
			pagingArea.append('<li class="nextPage" data-page="' + json.nextPage + '">></li>');
		}
		// 上一页
		if (json.hasPreviousPage){
			$('div.simple-paging>a.previousPage').removeClass('button-disable');
			$('div.paging-area>ul>li.previousPage').removeClass('paging-disable');
			$('div.simple-paging>a.previousPage').attr('data-page', json.prePage);
		} else {
			$('div.simple-paging>a.previousPage').addClass('button-disable');
			$('div.paging-area>ul>li.previousPage').addClass('paging-disable');
		}
		// 下一页
		if (json.hasNextPage){
			$('div.simple-paging>a.nextPage').removeClass('button-disable');
			$('div.paging-area>ul>li.nextPage').removeClass('paging-disable');
			$('div.simple-paging>a.nextPage').attr('data-page', json.nextPage);
		} else {
			$('div.simple-paging>a.nextPage').addClass('button-disable');
			$('div.paging-area>ul>li.nextPage').addClass('paging-disable');
		}
	});
}

// 构造订单项li
function orderItemLi(order){
	html = '';
	html += '<li data-order-id="' + order.id + '">';
	html += '<div class="order-item-header">';
	html += '<span class="order-info"><a class="order-time">' + order.updateTimeString + '</a>订单号：<a>' + order.orderNumberString + '</a></span>';
	html += '<span class="order-operational"><a class="layui-icon layui-icon-delete" href="/order/recycleBin?orderNumber=' + order.orderNumberString + '"></a></span>';
	html += '</div>';
	// 遍历订单项
	$.each(order.orderItems, function(i, n){
		html += '<ul>';
		html += '<li><img src="/static/commodity/' + n.commodity.homePicture + '" alt=""><a href="/commodity/details?id=' + n.commodity.id + '">' + n.commodity.name + '</a></li>';
		html += '<li><span>¥' + n.unitPriceString + '</span></li>';
		html += '<li><span>' + n.count + '</span></li>';
		html += '<li></li>';
		html += '<li><span>¥' + (n.count * n.unitPrice).toFixed(2) + '</span></li>';
		html += '<li><span>' + n.orderItemStatusString + '</span></li>';
		html += '<li>';
		if (n.orderItemStatus == "COMPLETE"){
			html += '<a href="/commodity/details?id=' + n.commodity.id + '">再次购买</a>';
		}
		if (n.orderItemStatus == "PENDINGEVALUATION"){
			html += '<a class="evaluation-button" data-order-number="' + order.orderNumberString + '" data-commodity-id="' + n.commodity.id + '">评论</a>';
		}
		html += '</li>';
		html += '</ul>';
	});
	html += '<div class="statistics-box">';
	html += '<span>商品总数：<a style="font-size: 14px;">' + order.totalCommodity + '</a></span>';
	html += '<span>应付金额：<a>¥' + order.totalAmount.toFixed(2) + '</a></span>';
	if (order.orderStatus == "PENDINGPAYMENT"){
		// 待付款
		html += '<span class="payment-button">付款</span>';
	} else if (order.orderStatus == "PENDINGRECEIPT") {
		html += '<span class="receipt-button">收货</span>';
	} else if (order.orderStatus == "PENDINGSHIP") {
		// 判断据上次催促时间是否大于一天,如果大于一天则允许再次催促,否则必须等时间满一天
		if ((order.updateTime + (24*60*60*1000)) <= (new Date().getTime())){
			// 据上次更新时间大于一天
			html += '<span class="urge-button">催促</span>';
		} else {
			// 距上次催促小于一天,不能在小于一天的时间连续催促两次
			html += '<span class="urge-button prohibition-of-urging">催促</span>';
		}
	}
	html += '</div>';
	html += '</li>';
	
	return html;
}


// 显示评论窗口
function showCommentBox(orderNumber, commodityId){
	html = '<div class="comment-pop-ups">';
	html += '<input type="hidden" name="commodity-id" data-commodity-id=' + commodityId + '>';
	html += '<input type="hidden" name="order-number" data-order-number=' + orderNumber + '>';
	html += '<div class="comment-title">';
	html += '<span>添加评论</span>';
	html += '<button></button>';
	html += '</div>';
	html += '<div class="comment-level">';
	html += '<span>评分：</span>';
	html += '<div id="score"></div>';
	html += '</div>';
	html += '<textarea placeholder="给个好评吧"></textarea>';
	html += '<div class="comment-button"><button>提交评论</button></div>';
	html += '</div>';
	$('body').append(html);
	setDivCenter('.comment-pop-ups');
	layui.use(['rate'], function(){
        var rate = layui.rate;
        rate.render({
            elem: '#score',
            value: 3,
            length: 3,
            text: true,
            setText: function(value){
                var arrs = {
                '1': '差评',
                '2': '中评',
                '3': '好评'
                };
                this.span.text(arrs[value] || ( value + "星"));
            }
        })
    });
}


// 刷新页面
function refreshPage(){
	// 获取订单状态
	var orderStatus = $('div.order-menu>ul>li.order-menu-selected').attr('id');
	getOrder(orderStatus, 1, 10);
}


//让指定的DIV始终显示在屏幕正中间  
function setDivCenter(divName){  
	var top = ($(window).height() - $(divName).height())/2;  
	var left = ($(window).width() - $(divName).width())/2;  
	var scrollTop = $(document).scrollTop();  
	var scrollLeft = $(document).scrollLeft();  
	$(divName).css( { position : 'absolute', 'top' : top + scrollTop, 'left' : left + scrollLeft } ).show();
}


//付款弹窗HTML
function paymentPopUps(balance, totalAmount, orderNumber){
	html = '<div class="pay-pop-ups" data-order-number=' + orderNumber + '>';
	html += '<div class="pay-area">';
	html += '<div class="pay-title"><span>确认付款</span></div>';
	html += '<div class="actual-pay-box">实付款：<span data-price="' + totalAmount + '">¥' + totalAmount.toFixed(2) + '</span></div>';
	html += '<div>';
	html += '<div class="pay-way">';
	html += '<ul>';
	html += '<li style="width: 120px;">请选择付款方式：</li>';
	html += '<li class="pay-way-item pay-way-selected" id="1">余额</li>';
	html += '</ul>';
	html += '</div>';
	html += '<div class="clean"></div>';
	html += '<div class="pay-item">';
	html += '<span>账户余额：<a>' + balance.toFixed(2) + '</a></span>';
	html += '<span>购买后余额：<a class="caveat balance" data-balance="' + (balance - totalAmount) + '">' + (balance - totalAmount).toFixed(2) + '</a>';
	if ((balance - totalAmount) < 0){		
		html += '<a class="caveat">[余额不足]</a></span>';
	}
	html += '</div>';
	html += '<div class="pay-buttons">';
	html += '<button class="pay-cancel">取消</button>';
	html += '<button class="pay-define ';
	if ((balance - totalAmount) < 0){
		html += 'unavailable';
	}
	html += '">付款</button>';
	html += '</div></div></div></div>';
	return html;
}

//余额界面
function balanceHtml(balance, surplus){
    html = '';
    html += '<span>账户余额：<a>' + balance.toFixed(2) + '</a></span>';
    html += '<span>购买后余额：';
    html += '<a class="caveat balance" data-balance="' + surplus + '">' + surplus.toFixed(2) + '</a>';
    if (surplus < 0){
        html += '<a class="caveat">[余额不足]</a>';
    }
    html += '</span>';
    
    // 判断余额
    if ($('div.pay-item>span>a.balance').attr('data-balance') < 0){
        $('button.pay-define').addClass('unavailable');
    } else {
        $('button.pay-define').removeClass('unavailable');
    }
    return html;
}



// 订单催促弹窗
function orderUrgePopupHtml(message){
	var html = '';
	html += '<div class="order-urge-popup">';
	html += '<div>';
	html += '<span>' + message + '</span>';
	html += '<button>确定</button>';
	html += '</div>';
	html += '</div>';
	$('body').append(html);
}