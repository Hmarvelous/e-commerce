var t;
$(function(){

	
	// 获取秒杀活动场次信息
	getSpikeSessions();
	
	
	// 秒杀按钮点击事件
    $(document).on('click', 'div.instant-spike', function(){
        // 获取商品ID
        var commodityId = $(this).attr('data-id');

        if (typeof(commodityId) == "undefined"){
            return;
        }

        // 跳转商品详情页面
        $(window).attr('location', '/spike/spikeDetails/' + commodityId);
    });
});


// 获取秒杀活动场次信息
function getSpikeSessions(){
	$.get('/spike/spikeSession', function(json){
//		console.log(json);
		var spikeArea = $('div.spike-area');
		spikeArea.html('');
		// 遍历添加秒杀信息
		$.each(json, function(i, n){
//			console.log(n);
			spikeArea.append(structureSpikeHtml(n));
		});
	}, 'json');
}

// 构造秒杀场次HTML
function structureSpikeHtml(spikeSession){
	html = '';
	html += '<div class="layui-container spike-item" data-session-id="' + spikeSession.id + '">';
	html += '<div class="spike-head">';
	html += '<span class="spike-title">' + spikeSession.name + '</span>';
	html += '<span class="spike-time">' + spikeSession.description + '</span>';
	html += '</div>';
	html += '<div class="spike-commodity-list">';
	html += '<ul>';
	// 遍历该场次全部参与商品的数据
	$.each(spikeSession.spikeCommoditys, function(i, n){
		html += '<a href="/spike/spikeDetails/' + n.id + '"><li>';
		html += '<img src="/static/commodity/' + n.commodity.homePicture + '" alt="">';
		html += '<span>' + n.startTimeIntegerString + '准时开抢</span>';
		if (n.activeStatus == 0){
			// 已售完
			html += '<div class="immediately spike-over" data-id="' + n.id + '"><span>已结束</span></div>';
		} else if (n.activeStatus == 1) {
			// 正在秒杀,库存未完
			html += '<div class="immediately instant-spike" data-id="' + n.id + '"><span>立即秒杀</span></div>';
			
		} else if (n.activeStatus == 2) {
			// 已结束,超时30分钟
			html += '<div class="immediately spike-over" data-id="' + n.id + '"><span>已结束</span></div>';
		} else if (n.activeStatus == 3) {
			// 开始倒计时
			html += '<div class="immediately about-to-start" data-id="' + n.id + '"><span>00:00:00</span></div>';
			// 开启倒计时
			t = setInterval("toCountdown(timeToTimestamp('" + n.startTimeString + "') - (getNowTime()), " + n.id + ")", 10);
		} else {
			// 时间超过一天,等待开始
			html += '<div class="immediately" data-id="' + n.id + '"><span>即将开始</span></div>';
		}
		html += '</li></a>';
	});
	html += '</ul>';
	html += '<div class="clean"></div>';
	html += '</div>';
	html += '</div>';
	return html;
}


// 获取当前系统时间戳
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
function toCountdown(s, id){
    s = parseInt(s * 100);
    // 转换
    var millisecond = s % 100;
    var second =  Math.floor(s / 100 % 60);
    var minute = Math.floor(s / 100 / 60 % 60);
    var hour = Math.floor(s / 100 / 60 / 60 % 24);
    var day = Math.floor(s / 100 / 60 / 60 / 24 % 30);
    if (day > 0){
        // 还剩至少一天
        $('div.immediately[data-id=' + id + ']>span').text('即将开始');
        $('div.immediately[data-id=' + id + ']').removeClass('about-to-start');
        clearInterval(t);
    }
    if (s <= 0){
        $('div.immediately[data-id=' + id + ']>span').text('立即秒杀');
        $('div.immediately[data-id=' + id + ']').removeClass('about-to-start');
        $('div.immediately[data-id=' + id + ']').addClass('instant-spike');
        clearInterval(t);
        return;
    }

    // 显示
    if (hour > 0){
        $('div.about-to-start[data-id=' + id + ']>span').text(makeUp(hour) + ':' + makeUp(minute) + ':' + makeUp(second));
    } else if (minute > 0) {
        $('div.about-to-start[data-id=' + id + ']>span').text(makeUp(minute) + ':' + makeUp(second) + ':' + makeUp(millisecond));
    } else {
        $('div.about-to-start[data-id=' + id + ']>span').text(makeUp(second) + ':' + makeUp(millisecond));
    }
}