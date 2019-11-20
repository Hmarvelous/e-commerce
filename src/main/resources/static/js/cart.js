$(function(){

    // 初始化统计按钮区
    checkOperation();

    // 全选框
    $('.cart-list-header>ul>li>input[type=checkbox]').click(function(){
        selectAllOrNotSelect($(this).is(':checked'));
        checkOperation();
    });
    $('.statistics-operational-area>ul>li>input[type=checkbox]').click(function(){
        selectAllOrNotSelect($(this).is(':checked'));
        checkOperation();
    });

    // 商品复选框单击事件
    $('.cart-item>ul>li>input[type=checkbox]').click(function(){
        checkOperation();
    });


    // 商品数量加减事件
    $(document).on('click', '.add-number', function(){
        commodityNumberAddLess($(this), 0);
        checkOperation();
    });
    $(document).on('click', '.less-number', function(){
    	commodityNumberAddLess($(this), 1);
        checkOperation();
    });
    
    // 商品数量输入框失去焦点事件
    $(document).on('blur', 'input[name=commodity-number]', function(){
    	// 直接修改商品数量
    	commodityNumberAddLess($(this).siblings('a.add-number'), -1);
    });

    // 单条删除按钮单击事件
    $(document).on('click', '.cart-item>ul>li>a.delete-cart-item', function(){
        deleteCartItemToRemotely($(this));
    });

    // 多条删除按钮单击事件
    $(document).on('click', 'a.delete-cart-item-plural', function(){
        var count = 0;
        // 遍历勾选的购物车项
        $('.cart-item>ul>li>input[type=checkbox]:checked').each(function(i, n){
        	deleteCartItemToRemotely($(n).parent().siblings().find('a.delete-cart-item'));
            count++;
        });
        if (count == 0){
        	alert('请先选择商品');
        	return;
        }
        
        // 更新界面
        // 全选框取消选中
        $('div.cart-list-header>ul>li>input[type=checkbox]').prop("checked", false);
        $('div.statistics-operational-area>ul>li>input[type=checkbox]').prop("checked", false);
        // 修改已选商品数量
        $('div.statistics-operational-area>ul>li>span>a').text("0");
        // 修改合计金额
        $('div.statistics-operational-area>ul>li>a.statistics-amount').text("0.00");
    });
    
    
    
    // 结算按钮
    $('button.settlement-button').click(function(){
    	// 发起结算请求
    	settlementRequest();
    });
});





// 全选或不选
function selectAllOrNotSelect(checked){
    // 遍历其它复选框
    $('input[type=checkbox]').each(function(i, n){
        $(n).prop("checked", checked);
    });
}

// 复选框操作
function checkOperation(){
    var totalAmount = 0.0;
    var selectedNumber = 0;
    // 获取商品数量
    var commodityNumber = parseInt($('.cart-header-selected>a').text());
    var commodityNumberCount = 0;
    // 遍历选中的复选框
    $('.cart-item>ul>li>input[type=checkbox]:checked').each(function(i, n){
        // 获取总金额元素
        var amount = parseFloat($(n).parent().siblings().children('span.total-amount').attr('price'));
        totalAmount += amount;
        selectedNumber++;
        commodityNumberCount++;
    });

    // 修改已选商品数量
    $('.statistics-operational-area>ul>li>span>a').text(selectedNumber);
    // 修改合计金额
    $('.statistics-operational-area>ul>li>a.statistics-amount').text(totalAmount.toFixed(2));

    // 判断是否有选中商品，否则不能结算
    if (selectedNumber > 0){
        $('.settlement-button').css('cursor', 'pointer');
        $('.settlement-button').css('background-color', '#FF4400');
    } else {
        $('.settlement-button').css('cursor', 'not-allowed');
        $('.settlement-button').css('background-color', '#B0B0B0');
    }
    // 如果复选框全部勾选，则自动勾选全选框
    if (commodityNumber != 0 && commodityNumber == commodityNumberCount){
        $('.cart-list-header>ul>li>input[type=checkbox]').prop("checked", true);
        $('div.statistics-operational-area>ul>li>input[type=checkbox]').prop("checked", true);
    } else{
        $('div.cart-list-header>ul>li>input[type=checkbox]').prop("checked", false);
        $('div.statistics-operational-area>ul>li>input[type=checkbox]').prop("checked", false);
    }
}


// 商品数量加减
function commodityNumberAddLess($this, operational){
    // 获取参数
	var id = $this.attr('id');;
	var commodityId = $this.attr('commodity-id');
    var money = parseFloat($this.parent().siblings().children('span.unit-price').attr('price'));
    var parameter = '';
    $this.parent().siblings().find('div.commodity-parameter>span').each(function(i, n){
    	var p = arr=$(n).text().split(':');
    	parameter += p[0] + '-' + p[1] + ',';
    });
    parameter = parameter.substring(0, parameter.lastIndexOf(','));
    
    // 更新数量
    var numberInput = $this.siblings('input[type=number]');
    var number = parseInt(numberInput.val());
    if (operational == 0){
        // 加
    	number += 1;
    } else if (operational == 1) {
        // 减
    	number -= 1;
    }
	
    if (number < 1){
    	number = 1;
    }
    if (number > 999){
    	number = 999;
    }
    numberInput.val(number);
    // 开始请求数量更新
    $.post('/cart/update', {
    	id: id,
    	commodityId: commodityId,
		count: number,
		parameter: parameter
	}, function(data){
		numberInput.val(data);
		number = parseInt(data);
	    money *= number;
	    money = money.toFixed(2);
	    // 更新总金额
	    $this.parent().siblings().children('span.total-amount').attr('price', money);
	    $this.parent().siblings().children('span.total-amount').text('¥' + money);
	    // 更新统计金额
	    var statistical = 0.0;
	    $.each($('div.cart-item'), function(i, n){
	    	// 判断勾选的商品
	    	if ($(n).find('ul>li>input[type=checkbox]').prop("checked")){
		    	statistical += parseInt($(n).find('ul>li>span.total-amount').attr('price'));
	    	}
	    });
	    $('a.statistics-amount').text(statistical.toFixed(2));
	});
}

// 删除购物车项(服务器端)
function deleteCartItemToRemotely($this){
	$.post('/cart/delete', {
		id: $this.attr('id')
	}, function(json){
		if ($.trim(json.code) == 'success'){
			// 删除成功
			$this.parents('div.cart-item').remove();
			SeccussPopUps('删除成功');
			// 修改商品数量
	        $('#cart-count').text(json.reCount);
	        $('a.commodity-number').text(parseInt($('a.commodity-number').text()) - 1);
		} else{
			// 删除失败
			FialPopUps('删除失败');
		}
	}, 'json');
}


// 结算请求
function settlementRequest(){
	var parameter = '';
	// 遍历勾选的购物车项
	$('.cart-item>ul>li>input[type=checkbox]:checked').each(function(i, n){
		// 获取商品ID
		var commodityId = $(n).attr('data-commodity-id');
		// 获取商品参数
		var p = '';
		$.each($(n).parent('li').siblings('li').children('div.commodity-parameter').find('span'), function(i, c){
			p += $(c).text().split(':')[1] + ',';
		});
		p = p.substring(0, p.lastIndexOf(','));
		// 获取商品数量
		var number = $(n).parent('li').siblings('li').children('input[name=commodity-number]').val();
		
		// 构造参数
		parameter += commodityId + '_' + p + '_' + number + '-';
	});
	parameter = parameter.substring(0, parameter.lastIndexOf('-'));
	
//	console.log(parameter);
	window.location = '/order/confirm_order?parameter=' + parameter;
}