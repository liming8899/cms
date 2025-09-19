var bm = {}
bm.isBlank = function (str) {
    if (str !== undefined) {
        str = str.replace(/(^\s*)|(\s*$)/g, '');
    }
    return (str === '' || str === undefined || str == null)
}

bm.compareDate = function (startTime, endTime) {
    var start = startTime.replaceAll('-','')
    start = start.replaceAll(' ','')
    start = start.replaceAll(':','')
    var end = endTime.replaceAll('-','')
    end = end.replaceAll(' ','')
    end = end.replaceAll(':','')
    return parseInt(end) > parseInt(start)
}

bm.getLanguage = function(cname) {
    let name = cname + "=";
    let ca = document.cookie.split(";");
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i].trim();
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return 'zh_CN'
}

bm.areDatesOnSameDay= function (start, end) {
    const startTime = new Date(start)
    const endTime = new Date(end)
    return (
        startTime.getFullYear() === endTime.getFullYear() &&
        startTime.getMonth() === endTime.getMonth() &&
        startTime.getDate() === endTime.getDate()
    );
}

bm.search = function (name) {
    var keywords = $('input[name='+name+']').val();
    const language = bm.getLanguage("userLanguage")
    if (bm.isBlank(keywords)) {
        if (language === 'zh_CN') {
            swal('请填写搜索关键词'); return;
        } else if (language === 'en_US') {
            swal('Please fill in the search keyword'); return;
        } else if (language === 'ru_RU') {
            swal('Пожалуйста, заполните ключевые слова поиска'); return;
        }
    }
    bm.history.add(keywords)
    location.href = '/search?keywords=' + keywords;
}

function goToLink(linkUrl,linkName) {
    const isiOS = !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    const nowTime = Date.now()
    const param = {
        visitMoudle: linkName,
        toLink: linkUrl
    }
    const randomCode = bm.randomCode()

    $.ajax({
        url: '/loginInfo',
        type: 'post',
        async: false,
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("X-Nonce", randomCode);
            XMLHttpRequest.setRequestHeader("X-Time", nowTime);
            XMLHttpRequest.setRequestHeader("X-Sign", md5(randomCode+nowTime+bm.mapToParam(param)));
        },
        data: JSON.stringify(param),
        contentType: 'application/json',
        dataType: 'json',
        success: function (res) {

        }
    })

    if (linkUrl !== '' && linkUrl !== '\/') {
        if(isiOS) {   //ios终端
            setTimeout(function(){window.location.href = linkUrl;}, 0);
        } else {
            window.open(linkUrl,'_blank');
        }
    }
}

bm.commonLinkUrl = function (linkUrl,linkName) {
    //点击菜单时，没有登录弹出提示框
    let ca = document.cookie.split(";");
    let hasToken = false
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i].trim();
        //已经登录不弹出提示框
        if (c.indexOf('token=')>=0){
            hasToken = true
        }
    }

    if (!hasToken) {
        const needLogin = localStorage.getItem('needLogin')
        let overTime = true
        if (needLogin) {
            const {expire} = JSON.parse(needLogin)
            if (expire < new Date().getTime()) {
                overTime = true
            } else {
                overTime = false
            }
        } else {
            overTime = true
        }
        //选择超过24小时并且是首页时触发
        if (overTime && window.location.href === window.location.origin+"/") {
            const language = bm.getLanguage("userLanguage")
            let tip,btn1,btn2,btn3;
            if (language === 'zh_CN') {
                tip = '请登录平台以方便使用更多功能'
                btn1 = '有账号，直接登录'
                btn2 = '无账号，直接注册'
                btn3 = '暂不登录'
            } else if (language === 'en_US') {
                tip = 'Please log in to the platform to use more functions'
                btn1 = 'Log in'
                btn2 = 'Sign up'
                btn3 = 'Not now'
            } else if (language === 'ru_RU') {
                tip = 'Пожалуйста, войдите на платформу, чтобы'
                btn1 = 'Вход'
                btn2 = 'Регистрация'
                btn3 = 'Не войти'
            }

            $('.container_tip').css('display', 'block')
            $('#tip_title').text(tip)
            $('#tip_btn1').text(btn1)
            $('#tip_btn2').text(btn2)
            $('#tip_btn3').text(btn3)
            let obj = {expire: new Date().getTime() + 1000 * 60 * 60 * 24}
            $('#tip_btn1').on('click', function () {
                localStorage.setItem('needLogin', JSON.stringify(obj));
                $('.container_tip').css('display', 'none')
                window.location.href = ssoMainUrl+"/oauth2/authorize?client_id=20240001&response_type=code&redirect_uri="+encodeURIComponent(accountUrl+"/wtSSO/callback?back="+location.href)
            })
            $('#tip_btn2').on('click', function () {
                localStorage.setItem('needLogin', JSON.stringify(obj));
                $('.container_tip').css('display', 'none');
                debugger;
                window.location.href = ssoRegisterUrl+"/register-plateform";

            })
            $('#tip_btn3').on('click', function () {
                localStorage.setItem('needLogin', JSON.stringify(obj));
                $('.container_tip').css('display', 'none')
                goToLink(linkUrl,linkName)
            })
        } else {
            goToLink(linkUrl,linkName)
        }
    } else {
        goToLink(linkUrl,linkName)
    }
}

function goToLink2(linkUrl,linkName,open) {
    const nowTime = Date.now()
    const param = {
        visitMoudle: linkName,
        toLink: linkUrl
    }
    const randomCode = bm.randomCode()
    $.ajax({
        url: '/loginInfo',
        type: 'post',
        data: JSON.stringify(param),
        contentType: 'application/json',
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("X-Nonce", randomCode);
            XMLHttpRequest.setRequestHeader("X-Time", nowTime);
            XMLHttpRequest.setRequestHeader("X-Sign", md5(randomCode+nowTime+bm.mapToParam(param)));
        },
        success: function (res) {
            // if (open && linkUrl !== '' && linkUrl !== '\/') {
            if (open && linkUrl !== '') {
                window.location.href = linkUrl;
            }
        }
    })
}
bm.commonLinkUrl2 = function (linkUrl,linkName,open=true) {
//点击菜单时，没有登录弹出提示框
    let ca = document.cookie.split(";");
    let hasToken = false
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i].trim();
        //已经登录不弹出提示框
        if (c.indexOf('token=')>=0){
            hasToken = true
        }
    }

    if (!hasToken) {
        const needLogin = localStorage.getItem('needLogin')
        let overTime = true
        if (needLogin) {
            const {expire} = JSON.parse(needLogin)
            if (expire < new Date().getTime()) {
                overTime = true
            } else {
                overTime = false
            }
        } else {
            overTime = true
        }
        //选择超过24小时并且是首页时触发
        if (overTime && window.location.href === window.location.origin+"/") {
            const language = bm.getLanguage("userLanguage")
            let tip,btn1,btn2,btn3;
            if (language === 'zh_CN') {
                tip = '请登录平台以方便使用更多功能'
                btn1 = '有账号，直接登录'
                btn2 = '无账号，直接注册'
                btn3 = '暂不登录'
            } else if (language === 'en_US') {
                tip = 'Please log in to the platform to use more functions'
                btn1 = 'Log in'
                btn2 = 'Sign up'
                btn3 = 'Not now'
            } else if (language === 'ru_RU') {
                tip = 'Пожалуйста, войдите на платформу, чтобы'
                btn1 = 'Вход'
                btn2 = 'Регистрация'
                btn3 = 'Не войти'
            }
            $('.container_tip').css('display', 'block')
            $('#tip_title').text(tip)
            $('#tip_btn1').text(btn1)
            $('#tip_btn2').text(btn2)
            $('#tip_btn3').text(btn3)
            let obj = {expire: new Date().getTime() + 1000 * 60 * 60 * 24}
            $('#tip_btn1').on('click', function () {
                localStorage.setItem('needLogin', JSON.stringify(obj));
                $('.container_tip').css('display', 'none')
                window.location.href = ssoMainUrl+"/oauth2/authorize?client_id=20240002&response_type=code&redirect_uri="+encodeURIComponent(accountUrl+"/wtSSO/callback?back="+location.href)
            })
            $('#tip_btn2').on('click', function () {
                localStorage.setItem('needLogin', JSON.stringify(obj));
                $('.container_tip').css('display', 'none')
                window.location.href = ssoRegisterUrl+"/register-plateform";

            })
            $('#tip_btn3').on('click', function () {
                localStorage.setItem('needLogin', JSON.stringify(obj));
                $('.container_tip').css('display', 'none')
                goToLink(linkUrl,linkName)
            })
        } else {
            goToLink2(linkUrl,linkName,open)
        }
    } else {
        goToLink2(linkUrl,linkName,open)
    }
}

bm.showLoginTips = function () {
    //点击菜单时，没有登录弹出提示框
    let ca = document.cookie.split(";");
    let hasToken = false
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i].trim();
        //已经登录不弹出提示框
        if (c.indexOf('token=')>=0){
            hasToken = true
        }
    }

    if (!hasToken) {
        const needLogin = localStorage.getItem('needLogin')
        let overTime = true
        if (needLogin) {
            const {expire} = JSON.parse(needLogin)
            if (expire < new Date().getTime()) {
                overTime = true
            } else {
                overTime = false
            }
        } else {
            overTime = true
        }
        //选择超过24小时并且是首页时触发
        if (overTime && window.location.href === window.location.origin+"/") {
            const language = bm.getLanguage("userLanguage")
            let tip,btn1,btn2,btn3;
            if (language === 'zh_CN') {
                tip = '请登录平台以方便使用更多功能'
                btn1 = '有账号，直接登录'
                btn2 = '无账号，直接注册'
                btn3 = '暂不登录'
            } else if (language === 'en_US') {
                tip = 'Please log in to the platform to use more functions'
                btn1 = 'Log in'
                btn2 = 'Sign up'
                btn3 = 'Not now'
            } else if (language === 'ru_RU') {
                tip = 'Пожалуйста, войдите на платформу, чтобы'
                btn1 = 'Вход'
                btn2 = 'Регистрация'
                btn3 = 'Не войти'
            }
            $('.container_tip').css('display', 'block')
            $('#tip_title').text(tip)
            $('#tip_btn1').text(btn1)
            $('#tip_btn2').text(btn2)
            $('#tip_btn3').text(btn3)
            let obj = {expire: new Date().getTime() + 1000 * 60 * 60 * 24}
            $('#tip_btn1').on('click', function () {
                localStorage.setItem('needLogin', JSON.stringify(obj));
                $('.container_tip').css('display', 'none')
                window.location.href = ssoMainUrl+"/oauth2/authorize?client_id=20240002&response_type=code&redirect_uri="+encodeURIComponent(accountUrl+"/wtSSO/callback?back="+location.href)
            })
            $('#tip_btn2').on('click', function () {
                localStorage.setItem('needLogin', JSON.stringify(obj));
                $('.container_tip').css('display', 'none')
                window.location.href = ssoRegisterUrl+"/register-plateform";

            })
            $('#tip_btn3').on('click', function () {
                localStorage.setItem('needLogin', JSON.stringify(obj));
                $('.container_tip').css('display', 'none')
            })
        }
    }
}

bm.randomCode = function () {
    let str = '';
    for (let i = 0; i < 10; i++) {
        str += String.fromCharCode(
            Math.floor(Math.random() * 26) + "a".charCodeAt(0)
        )
    }
    return str;
}

bm.sortMap = function (map) {
    const keys = Object.keys(map);
    const len = keys.length;
    keys.sort();
    let sortMap = {}
    for (let i = 0; i < len; i++) {
        sortMap[keys[i]] = map[keys[i]]
    }
    return sortMap
}

bm.mapToParam = function (map) {
    const keys = Object.keys(map);
    const len = keys.length;
    keys.sort();

    let sortMapParam = ''
    for (let i = 0; i < len; i++) {
        sortMapParam += '&' + keys[i] + '=' + map[keys[i]]
    }
    return sortMapParam
}
/**
 * 提交会议申请
 */
bm.meetingApply = function () {
    const language = bm.getLanguage("userLanguage")
    var meetingName = $('#meetingName').text(),
        meetingRoomId = $("#meetingRoomId").val(),
        applicant = $('[name="applicant"]').val(),
        meetingTitle = $('[name="meetingTitle"]').val(),
        meetingType = $('[name="meetingType"]').val(),
        startTime = $('[name="startTime"]').val(),
        endTime = $('[name="endTime"]').val(),
        peoples = $("#peoples").val(),
        remarks = $('[name="remarks"]').val();
    // 获取选择的服务
    var servicesArray =[];
    $('input[name="services"]:checked').each(function(){
        servicesArray.push($(this).val());
    });
    meetingName = meetingName.trim();
    var services = servicesArray.join(",");
    if (bm.isBlank(meetingName) || meetingName === '请选择' ||bm.isBlank(meetingRoomId) || bm.isBlank(applicant) || bm.isBlank(meetingTitle) || bm.isBlank(meetingType) || bm.isBlank(startTime) || bm.isBlank(endTime) || bm.isBlank(peoples) || bm.isBlank(services)) {
        if (language === 'zh_CN') {
            swal('请填写完整预约信息');
        } else if (language === 'en_US') {
            swal('Please fill in the complete appointment information');
        } else if (language === 'ru_RU') {
            swal('Пожалуйста, заполните полную информацию о назначении');
        }
        return;
    }
    if (!bm.compareDate(startTime, endTime)) {
        if (language === 'zh_CN') {
            swal('开始时间不能大于或等于结束时间');
        } else if (language === 'en_US') {
            swal('Start time cannot be greater than or equal to end time');
        } else if (language === 'ru_RU') {
            swal('Время начала не может быть больше или равно времени окончания');
        }
        return;
    }
    // if (!bm.areDatesOnSameDay(startTime, endTime)) {
    //     if (language === 'zh_CN') {
    //         swal('开始时间必须和结束时间在同一天');
    //     } else if (language === 'en_US') {
    //         swal('The start time must be the same day as the end time');
    //     } else if (language === 'ru_RU') {
    //         swal('Время начала и время окончания должны быть в один день');
    //     }
    //     return;
    // }
    // startTime = startTime.replace(/-/g,"/");
    // endTime = endTime.replace(/-/g,"/");

    const nowTime = Date.now()
    if(nowTime > new Date(startTime).valueOf() || nowTime > new Date(endTime).valueOf()) {
        if (language === 'zh_CN') {
            swal('开始时间和结束时间必须是未来的时间');
        } else if (language === 'en_US') {
            swal('Start and end times must be in the future');
        } else if (language === 'ru_RU') {
            swal('Время начала и время окончания должны быть временем будущего');
        }
        return;
    }

    const param = {
        meetingName: meetingName,
        meetingRoomId:meetingRoomId,
        applicant: applicant,
        meetingTitle: meetingTitle,
        meetingType: meetingType,
        startTime: startTime,
        // startTime: new Date(startTime),
        endTime: endTime,
        // endTime: new Date(endTime),
        remarks: remarks,
        peoples: peoples,
        services: services
    }
    const randomCode = bm.randomCode()

    // const loader = document.createElement('div');
    // loader.className = 'spinner';
    // document.body.appendChild(loader);
    $.ajax({
        url: '/meeting',
        type: 'post',
        data: JSON.stringify(param),
        contentType: 'application/json',
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("X-Nonce", randomCode);
            XMLHttpRequest.setRequestHeader("X-Time", nowTime);
            XMLHttpRequest.setRequestHeader("X-Sign", md5(randomCode+nowTime+bm.mapToParam(param)));
        },
        success: function (res) {
            // const loader = document.querySelector('.spinner');
            // loader.parentNode.removeChild(loader);
            if (res.code > 0) {
                swal(res.message);
                return
            }
            if (language === 'zh_CN') {
                swal('提交成功')
            } else if (language === 'en_US') {
                swal('Submitted successfully')
            } else if (language === 'ru_RU') {
                swal('Успешная подача')
            }
            location.reload()
        },
    })
}

bm.history = {
    list: function () {
        var history = [];
        if (localStorage.getItem('search_history_list')) {
            history = JSON.parse(localStorage.getItem('search_history_list'))
        }
        return history;
    },
    add: function (val) {
        var history = bm.history.list()
        // 没有搜索记录，把搜索值push进数组首位，存入本地
        if (!history.includes(val)) {
            history.unshift(val);
            localStorage.setItem("search_history_list", JSON.stringify(history));
        }else{
            //有搜索记录，删除之前的旧记录，将新搜索值重新push到数组首位
            let i = history.indexOf(val);
            history.splice(i,1);
            history.unshift(val);
            localStorage.setItem('search_history_list',JSON.stringify(history))
        }
    },
    clean: function () {
        localStorage.setItem("search_history_list",JSON.stringify([]))
        bm.history.reload()
    },
    reload: function () {
        $('#searchHistoryList').text("")
        for (let i = 0; i < bm.history.list().length && i < 5; i++) {
            $('#searchHistoryList').append('<a href="/search?keywords='+bm.history.list()[i]+'" class="search-r-list-item history ellipsis1">'+bm.history.list()[i]+'</a>')
        }
    }
}

bm.translateDate = function (str, lang) {
    const enDateArr = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']

    const ruYear = 'года'
    const ruDateArr = ['января','февраля','Марта','апреля','мая','июня','июля','августа','сентября','октября','ноября','декабря']
    const arr = str.split('-')

    if (lang === 'zh_CN') {
        return arr[0] + '年' + arr[1] + '月' + arr[2] +'日'
    } else if (lang === 'ru_RU') {
        return arr[2] + ' ' + ruDateArr[arr[1]-1] + ' ' + arr[0] + ' ' + ruYear
    } else if (lang === 'en_US') {
        return enDateArr[arr[1]-1]+' '+arr[2]+","+arr[0]
    }
}

bm.setLang = function(langName) {
    let d = new Date();
    d.setTime(d.getTime()+(24*60*60*1000));
    const expires = "expires="+d.toGMTString();
    const path = "path=/";
    document.cookie = "userLanguage" + "=" + langName + "; " + expires + "; " + path;
    //刷新当前页面
    window.location.reload();
}

bm.goLink = function (jumpUrl,page) {
    if (jumpUrl === '') {
        jumpUrl = '?'
    } else {
        jumpUrl = jumpUrl + '&'
    }
    const gotoNum = $('#gotoNum').val()
    if (gotoNum > page.pages) {
        return
    }
    if (gotoNum !== undefined && gotoNum !== '') {
        window.location.href = jumpUrl+'currentPage=' + gotoNum + '&pageSize=' + page.size
    }
}

bm.showMeetingInfo = function () {
    const language = bm.getLanguage("userLanguage")
    const meetingRoomId = $("#meetingRoomId").val()
    if (meetingRoomId === undefined || meetingRoomId === null || meetingRoomId === '') {
        if (language === 'zh_CN') {
            swal('请选择会议室');
        } else if (language === 'en_US') {
            swal('Please select a conference room');
        } else if (language === 'ru_RU') {
            swal('Пожалуйста, выберите конференц-зал');
        }
        return
    }

    $('<div class="modal-mask"></div>').appendTo('body').fadeIn('fast');
    $('#meetingInfoDetailModal').fadeIn('fast');
    $('.modal-mask').on('click', function () {
        $('.modal-mask').fadeOut('fast').remove('.modal-mask')
        $('#meetingInfoDetailModal').fadeOut('fast');
    })
}

// select模拟
$.fn.bmSelect = function(params) {
    var settings = {
        click: Object,
        callback: Object,
        autoSetValue: false
    }
    $(this).each(function() {
        var self = this;
        var $this = $(this);
        this.settings = $.extend(settings, params);
        var options = $this.siblings('ul').first();
        $this.on('click', function(e) {
            self.settings.click && self.settings.click($this)
            options.slideToggle('fast',function() {
                options.children('li').unbind('click').bind('click', function () {
                    var value = $(this).data('value')
                    var label = $(this).text();
                    if (self.settings.autoSetValue) {
                        $this.text(label)
                    }
                    self.settings.callback && self.settings.callback(value, label)
                })
            });
            // 阻止冒泡
            e.stopPropagation()
            $(document).on('click',function() {
                options.slideUp('fast')
            })
        })
    })
}
