// 登录按钮单击事件
$('form').submit(function(){
    var username = $('#username').val();
    var password = $('#password').val();

    // 用户名验证
    if (username.length == 0){
        tips('请输入用户名', 'username', '#3399FF');
        return false;
    }

    // 密码验证
    if (password.length == 0){
        tips('请输入密码', 'password', '#3399FF');
        return false;
    }
});

// 关闭提示框
$(document).on('click', '#error-info>i', function() {
    $('#error-info').remove();
});


// 提示弹窗
function tips(text, id, color){
    layui.use('layer', function(){
        layer = layui.layer;
        layer.tips(text, '#' + id, {
            tips: [2, color]
        });
    });
}