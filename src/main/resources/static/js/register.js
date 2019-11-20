var count = 3;
var t;

$(function(){

    // 用户名输入框失去焦点事件
    $('input[name=username]').on('blur', function(){
        testData();
    });

    // 密码输入框失去焦点事件
    $('input[name=password]').on('blur', function(){
        testData();
    });

    // 重复密码输入框失去焦点事件
    $('input[name=againPassword]').on('blur', function(){
        testData();
    });

    // 注册协议勾选事件
    $('input[type=checkbox]').change(function(){
        testData();
    });

    // 注册按钮点击事件
    $('button').click(function(){
        if ($(this).is('.button-invalid')){
            return;
        }
        
        // 验证用输入
        if (verificationInput()){
        	var username = $('input[name=username]').val();
            var password = $('input[name=password]').val();
            var email = $('input[name=email]').val();
            console.log(username + " _ " + password + " _ " + email);
            
            $.post('/member/doRegister', {
            	username: username,
            	password: password,
            	email: email
            }, function(json){
            	console.log(json);
            	if ($.trim(json.result) == 'alreadyExist'){
            		toolTipHtml('用户已存在！', false);
            	} else if ($.trim(json.result) == 'fail'){
            		toolTipHtml('注册失败！', false);
            	} else if ($.trim(json.result) == 'success'){
            		toolTipHtml('注册成功！', true);
            	}
            }, 'json');
        }
    });
    
    
    // 弹窗点击事件
    $(document).on('click', 'div.tip-window>button', function(){
    	console.log('a');
    	// 如果a标签不存在,则表示不进行倒计时,直接关闭窗口
    	if ($('div.tip-window>button>a').length <= 0){
    		$('div.prompt-box').remove();
    		return; 
    	
    	// 进行倒计时,跳转到登录页面
    	$(window).attr('location', '/member/login');
    });
});


// 检验数据
function testData(){
    //获取参数
    var username = $('input[name=username]').val();
    var password = $('input[name=password]').val();
    var againPassword = $('input[name=againPassword]').val();
    var email = $('input[name=email]').val();
    
    var but = $('button');
    but.addClass('button-invalid');

    if (username.length == 0){
        console.log('请输入用户名');
        return false;
    }
    if (password.length == 0){
        console.log('请输入密码');
        return false;
    }
    if (againPassword.length == 0){
        console.log('请重复输入密码');
        return false;
    }
    if (password != againPassword){
        console.log('两次密码输入不一致');
        return false;
    }
    if (email.length == 0){
        console.log('请输入电子邮箱');
        return false;
    }
    if (!$('input[type=checkbox]').is(':checked')){
        console.log('请勾选注册协议');
        return false;
    }

    but.removeClass('button-invalid');
    return true;
}


// 提示弹窗
function toolTipHtml(message, isCountDown){
	count = 3;
	var html = '<div class="prompt-box">';
	html += '<div class="tip-window">';
	html += '<span>' + message + '</span>';
	html += '<button>确定';
	if (isCountDown){
		html += '<a>' + count + '</a>';
	}
	html += '</button>';
	html += '</div>';
	html += '</div>';
	$('body').append(html);
	
	// 开启倒计时
	t = setInterval(function(){
		count--;
		if (count == -1){
			clearInterval(t);
	    	$(window).attr('location', '/member/login');
			return;
		}
		$('div.tip-window>button>a').text(count);
	}, 1000);
}



// 判断输入正确性
function verificationInput(){
	var username = $('input[name=username]').val();
    var password = $('input[name=password]').val();
    var againPassword = $('input[name=againPassword]').val();
    var email = $('input[name=email]').val();
    
    if (username.length < 6 || username.length > 20) {
    	console.log("用户名长度必须在6-20之间");
    	return false;
    }
    if (password.length < 6 || password.length > 20) {
    	console.log("密码长度必须在6-20之间");
    	return false;
    }
    if (password != againPassword) {
    	console.log("两次密码不一致");
    	return false;
    }
    
    return true;
}


