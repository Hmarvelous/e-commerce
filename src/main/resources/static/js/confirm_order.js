$(function(){

    // 地址切换
    $(document).on('click', 'div.address-area>ul>li', function(){
        getAddressById($(this), $('div.address-area>ul>li.address-selected').attr('id'));
    });
    
    
    // 提交订单按钮
    $('div.settlement-button').click(function(){
    	// 生成订单
    	generateOrder();
    });
    
    
    // 弹窗方式按钮
    $(document).on('click', 'div.pay-way>ul>li.pay-way-item', function(){
        if ($(this).is('.pay-way-selected')){
            return;
        }
        // 清除其他选中状态
        $(this).siblings('li').removeClass('pay-way-selected');
         // 设置自己为选中状态
         $(this).addClass('pay-way-selected');

         // 加载对应的界面
         var id = $(this).attr('id');
         var container = $('div.pay-item');
         container.html('');
         if (id == 1){
             // 余额支付
        	 $.get('/order/getBalance', {
        		 orderNumber: $('div.order-list-box').attr('data-order-number')
        	 }, function(json){
                 container.html(balanceHtml(json.balance, json.balance - json.order.totalAmount));
                 
        	 }, 'json');
         }
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
        	orderNumber: $('div.order-list-box').attr('data-order-number')
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
    
    // 屏幕滚动事件
    $(window).bind("scroll", function () {
		setDivCenter('div.pay-pop-ups');
    });
});


// 根据ID请求收货地址信息
function getAddressById($this, id){
	$.getJSON('/receiver/getAddress', {
		address_id: id
	}, function(json){
		
		// 设置提交订单位置的收货地址
		var send_address_span = $('div.settlement-area>div>span.send-address');
		send_address_span.html('寄送至：');
		$.each(json.areaAddress, function(i, n){			
			send_address_span.append('<a>' + n + '</a>');
		});
		send_address_span.append('<a>' + json.detailedAddress + '</a>');
		
		var receiver = $('div.settlement-area>div>span.receiver');
		receiver.html('收货人：');
		receiver.append('<a>' + json.name + '</a>');
		receiver.append('<a>' + json.phone + '</a>');
		
		// 清除其他选中状态
		$this.siblings('li').removeClass('address-selected');
        // 设置自己为选中状态
		$this.addClass('address-selected');
	});
}

// 创建订单
function generateOrder(){
	// 判断订单是否已提交
	var orderNumber = $('div.order-list-box').attr('data-order-number');
	// 构造参数
	var parameter = {};
	// 收货地址ID
	parameter['receiver_id'] = $('div.address-area>ul>li.address-selected').attr('id');
	parameter['orderNumber'] = orderNumber;
	if (typeof($('li>span.preferential-way').attr('data-way')) != "undefined"){
		parameter['preferentialWay'] = $('li>span.preferential-way').attr('data-way');
	}
	var commoditys = [];
	// 遍历商品项
	$.each($('div.order-list-box>ul>li'), function(i, n){
		var commodity = {};
		$this = $(n);
		// 商品ID
		commodity['id'] = $this.attr('id');
		// 商品参数(数组)
		var parameters = [];
		$.each($this.find('ul>li.parameter>span'), function(ix, nx){
			var x = {};
			x['name'] = $(nx).text().split("：")[1];
			parameters.push(x);
		});
		commodity['parameter'] = parameters;
		// 商品数量(数组)
		commodity['count'] = $this.find('ul>li>span.commodity-count').text();
		
		commoditys.push(commodity);
	});
	parameter['commodity'] = commoditys;
	parameter['comments'] = $('div.leave-comments>textarea[name=comments]').val();
	
	
	$.post('/order/generateOrder', {
		parameter: JSON.stringify(parameter)
	}, function(json){
		console.log(json);
		if ('success' == $.trim(json.code)){
			// 创建订单成功
			$('div.order-list-box').attr('data-order-number', json.orderNumber);
			// 弹窗提示付款
			$('body').append(paymentPopUps(json.balance, json.totalAmount));
			setDivCenter('div.pay-pop-ups');
		} else {
			// 创建订单失败
			FialPopUps('订单创建失败');
		}
	}, 'json');
}


// 付款弹窗HTML
function paymentPopUps(balance, totalAmount, orderNumber){
	html = '<div class="pay-pop-ups">';
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