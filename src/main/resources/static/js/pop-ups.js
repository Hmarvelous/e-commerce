

// 成功弹窗
function SeccussPopUps(text){
    html = '';
    html += '<div class="cart-message-box success-color">';
    html += '<span>' + text + '</span>';
    html += '</div>';
    $('body').append(html);
    setTimeout(function(){
        $('div.cart-message-box').remove();
    }, 4000);
}

// 失败弹窗
function FialPopUps(text){
    html = '';
    html += '<div class="cart-message-box fail-color">';
    html += '<span>' + text + '</span>';
    html += '</div>';
    $('body').append(html);
    setTimeout(function(){
        $('div.cart-message-box').remove();
    }, 4000);
}

//让指定的DIV始终显示在屏幕正中间  
function setDivCenter(divName){  
	var top = ($(window).height() - $(divName).height())/2;  
	var left = ($(window).width() - $(divName).width())/2;  
	var scrollTop = $(document).scrollTop();  
	var scrollLeft = $(document).scrollLeft();  
	$(divName).css( { position : 'absolute', 'top' : top + scrollTop, 'left' : left + scrollLeft } ).show();
}