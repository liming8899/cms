$(document).ready(function () {
    /**
     * 加载会议室
     */
    loadMeetingRooms()
})

/**
 * 加载日历
 * @type {{setDate: dateObj.setDate, getDate: (function(): Date)}}
 */
var roomId = '';
var dateObj = (function () {
    var _date = new Date();
    return {
        getDate: function () {
            return _date;
        },
        setDate: function (date) {
            _date = date;
        }
    };
})();

const language = getLanguage("userLanguage")
showCalendarData(language)

function getLanguage(cname) {
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

function showCalendarData() {
    const language = getLanguage("userLanguage")
    var _year = dateObj.getDate().getFullYear();
    var _month = dateObj.getDate().getMonth() + 1;
    var orders = [];
    $.ajax({
        url: '/meeting/order/month?date=' + _year + '-' + _month+'&roomId='+roomId,
        type: 'GET',
        success: function (res) {
            if (res.code > 0) {
                swal(res.message);
                return;
            }
            orders = res.data;
        },
        error: function (err) {
            console.log(JSON.stringify(err))
            swal('请求失败，请稍后重试！')
        },
        complete: function () {
            var _days = new Date(_year, _month, 0).getDate();
            var _week = new Date(_year, _month - 1).getDay()
            var monthT = document.getElementById('calendar-month')
            monthT.innerHTML = _year + "-" + (_month > 9 ? _month : "0" + _month)
            var calendar = document.getElementById("calendar-date")
            while (calendar.hasChildNodes()) {
                calendar.removeChild(calendar.firstChild);
            }
            if (_week < 8) {
                for (var i = 1; i < _week; i++) {
                    var div = document.createElement('div')
                    div.innerHTML = "<span></span>"
                    calendar.appendChild(div)
                }
            }
            for (var i = 1; i < _days + 1; i++) {
                let tempW = new Date(_year, _month - 1, i).getDay()
                if (true) {
                    var div = document.createElement('div')
                    var count = 0;
                    var day = i <= 9 ? '0' + i : i;
                    for (let index = 0; index < orders.length; index++) {
                        const order = orders[index];
                        // console.log(order)
                        var key = _year + '-' + (_month > 9 ? _month : "0" + _month) + '-' + day;
                        if (order.startTime.indexOf(key) != -1) {
                            count++
                        }
                    }
                    if (count > 0) {
                        if (language === 'zh_CN') {
                            div.innerHTML = `<span>${day}</span><p class="is-active" style="cursor:pointer;">已预约(${count})场</p>`
                        } else if (language === 'en_US') {
                            div.innerHTML = `<span>${day}</span><p class="is-active" style="cursor:pointer;">Reserved (${count})</p>`
                        } else if (language === 'ru_RU') {
                            div.innerHTML = `<span>${day}</span><p class="is-active" style="cursor:pointer;">Записаны на (${count}) сеанса</p>`
                        }
                    } else {
                        div.innerHTML = `<span>${day}</span>`
                    }
                    calendar.appendChild(div)
                }
            }
            // 添加点击事件
            /**
             * 点击效果
             */
            $('.mt-calendar__body__date > div').on('click', function () {
                if ($('#calendarModal').is(':visible')) {
                    return false;
                }
                var that = $(this);
                var month = $('#calendar-month').text();
                var day = $(this).children('span').first().text();
                var date = month + '-' + (parseInt(day) > 9 ? day : '0' + parseInt(day));
                $('#dateShow').text('(' + date + ')');
                $.ajax({
                    url: '/meeting/order?date=' + date+'&roomId='+roomId,
                    type: 'GET',
                    success: function (res) {
                        if (res.code > 0) {
                            swal(res.message);
                            return;
                        }
                        // that.children('p').first('p').text('已预约('+res.data.length+')场')
                        if (res.data.length > 0) {
                            var html = '';
                            for (var i = 0; i < res.data.length; i++) {
                                html += '<tr>\n' +
                                    '                            <td width="30%">\n' +
                                    '                                <span class="ellipsis1">' + res.data[i].meetingTitle + '</span>\n' +
                                    '                            </td>\n' +
                                    '                            <td width="15%">\n' +
                                    '                                <span class="ellipsis1">' + res.data[i].applicant + '</span>\n' +
                                    '                            </td>\n' +
                                    '                            <td width="20%"><span class="ellipsis1">' + formatShortDateTime(res.data[i].startTime) + ' - ' + formatShortDateTime(res.data[i].endTime) + '</span>\n' +
                                    '                            </td>\n' +
                                    '                            <td width="25%">\n' +
                                    '                                <span class="ellipsis1">' + res.data[i].meetingName + '</span>\n' +
                                    '                            </td>\n' +
                                    '                            <td width="10%">\n';
                                if (language === 'zh_CN') {
                                    html += '                                <span class="ellipsis1" style="color: #393939;cursor:pointer;" onclick="cancelMeeting('+res.data[i].id+')">取消</span>\n';
                                } else if (language === 'en_US') {
                                    html += '                                <span class="ellipsis1" style="color: #393939;cursor:pointer;" onclick="cancelMeeting('+res.data[i].id+')">cancel</span>\n';
                                } else if (language === 'ru_RU') {
                                    html += '                                <span class="ellipsis1" style="color: #393939;cursor:pointer;" onclick="cancelMeeting('+res.data[i].id+')">Отменить</span>\n';
                                }
                                html += '                            </td>\n' +
                                    '                        </tr>';
                            }
                            $('#calendarModalBody').append(html)
                        } else {
                            if (language === 'zh_CN') {
                                $('#calendarModalBody').html('<tr><td colspan="5" align="center">暂无预约</td></tr>')
                            } else if (language === 'en_US') {
                                $('#calendarModalBody').html('<tr><td colspan="5" align="center">No reservation yet</td></tr>')
                            } else if (language === 'ru_RU') {
                                $('#calendarModalBody').html('<tr><td colspan="5" align="center">Без предварительной записи</td></tr>')
                            }
                        }
                        // that.siblings('div').removeClass('is-active').children('p').hide();
                        // that.addClass('is-active').children('p').fadeIn('fast');
                        // mask遮罩层
                        $('<div class="modal-mask"></div>').appendTo('body').fadeIn('fast');
                        $('#calendarModal').fadeIn('fast');
                        $('.modal-mask').on('click', function () {
                            $('.modal-mask').fadeOut('fast').remove('.modal-mask')
                            $('#calendarModal').fadeOut('fast');
                            $('#calendarModalBody').empty()
                        })
                    },
                    error: function (err) {
                        console.log(JSON.stringify(err))
                        swal('请求失败，请稍后重试！')
                    },
                    complete: function () { }
                })
            })
        }
    })
}

/**
 * 取消会议预约
 */
var cancelMeeting = function (id){
    const language = getLanguage("userLanguage")
    $.ajax({
        url: '/meeting/cancelMeeting?id=' + id,
        type: 'GET',
        success: function (res) {
            if (res.code > 0) {
                swal(res.message);return
            }
            if (language === "zh_CN") {
                swal('操作成功')
            } else if (language === "en_US") {
                swal('Operation succeeded')
            } else if (language === "ru_RU") {
                swal('Операция прошла успешно')
            }
            location.reload()
        },
        error: function (err) {
            console.log(JSON.stringify(err))
            swal('请求失败，请稍后重试！')
        },
        complete: function () { }
    })
}

$('.mt-calendar__btn--prev').on('click', function () {
    var date = dateObj.getDate();
    dateObj.setDate(new Date(date.getFullYear(), date.getMonth() - 1, 1));
    showCalendarData();
});
$('.mt-calendar__btn--next').on('click', function () {
    var date = dateObj.getDate();
    dateObj.setDate(new Date(date.getFullYear(), date.getMonth() + 1, 1));
    showCalendarData();
});

$('.mt-modal__close').on('click', function () {
    $('.modal-mask').fadeOut('fast').remove('.modal-mask')
    $('#calendarModal').fadeOut('fast');
    $('#calendarModalBody').empty()
    $('#meetingInfoDetailModal').fadeOut('fast');
})

// var startTime = flatpickr('#startTime', {
//     enableTime: true,
//     dateFormat: "Y-m-d H:i:S",
//     locale: language.split('_')[0],
//     onChange: function (selectedDates, dateStr, instance) {
//         // instance.close();
//     }
// });
//
// var endTime = flatpickr('#endTime', {
//     enableTime: true,
//     dateFormat: "Y-m-d H:i:S",
//     locale: language.split('_')[0],
//     onChange: function (selectedDates, dateStr, instance) {
//         // instance.close();
//     }
// });

var formatShortDateTime = function (date) {
    console.log(date)
    if (date === "" || !date) {
        return "";
    }
    var datetime = new Date(date);
    var h = datetime.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = datetime.getMinutes();
    minute = minute < 10 ? ('0' + minute) : minute;
    return h + ':' + minute;
}

var loadMeetingRooms = function () {
    var options = $('.bm-selector__options1').first();
    options.empty()
    $.get('/meeting/room', function (res) {
        if (res.code > 0) {
            return false;
        }
        for (var i = 0; i < res.data.length; i++) {
            options.append('<li class="bm-selector__options__item" data-value="' + res.data[i].id + '">' + res.data[i].name + '</li>');
        }
    })
    $('.bm-selector1 > .bm-selector__value1').bmSelect({
        autoSetValue: true,
        callback: function (value, label) {
            roomId = value;
            $("#meetingRoomId").val(value);
            showCalendarData()
            initSelectPeoples();
        }
    })
}

var initSelectPeoples = function (){
    $.ajax({
        url: '/meeting/roomDetail/'+$("#meetingRoomId").val(),
        type: 'get',
        contentType: 'application/json',
        dataType: 'json',
        success: function (res) {
            if (res.code > 0) {
                swal(res.message);
                return
            }

            if (res.data.peopleNum !== undefined && res.data.peopleNum !== null && res.data.peopleNum !== '') {
                const peopleNum = res.data.peopleNum.split(',')
                myVue.$data['min'] = Number(peopleNum[0])
                myVue.$data['max'] = Number(peopleNum[1])
            }
            if (res.data.serviceItem !== undefined && res.data.serviceItem !== null && res.data.serviceItem !== '') {
                const inputs = document.querySelectorAll('.check-items input[type="checkbox"]');
                let values = [];
                inputs.forEach(input => {
                    values.push(input.value);
                });
                const serviceItem = res.data.serviceItem.split(',')
                const filterItem = serviceItem.filter(element => new Set(values).has(element));
                inputs.forEach(input => {
                    if (filterItem.includes(input.value)) {
                        input.parentElement.style.display = 'block';
                    } else {
                        input.parentElement.style.display = 'none';
                    }
                });
            } else {
                document.querySelectorAll('.check-items input[type="checkbox"]').forEach(input => {
                    input.parentElement.style.display = 'none';
                });
            }

            $('#env_detail').text(res.data.envDetail)
            $('#receive_email').text(res.data.receiveEmail)
            const meetingRoomImgList = res.data.meetingRoomImgList
            if (meetingRoomImgList.length === 0) {
                $('#img_span').css('display','none')
            } else {
                $('#img_span').css('display','block')
            }
            let html = ''
            for (const index in meetingRoomImgList) {
                html += `<figure class="gallery-item">
                          <img src="${meetingRoomImgList[index].logoUrl}" alt="">
                          <figcaption></figcaption>
                        </figure>`
            }
            $('#env_detail_img').html(html)
            $('.gallery-item').ma5gallery({
                preload:true,
                fullscreen:false
            });

        }
    })
}