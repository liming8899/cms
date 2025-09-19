$(document).ready(function () {
    /**
     * 搜索历史更新
     */
    bm.history.reload();
    /**
     * -- 导航部分 -----------------------------------------------------------------------------------
     */
    $("#mNavShowBtn").click(function (e) {
        e.stopPropagation();
        $("#mNav").slideDown();
    });
    $("#mNavCloseBtn").click(function (e) {
        e.stopPropagation();
        $("#mNav").slideUp();
    });
    $(document).click(function (e) {
        e.stopPropagation();
        if (!$("#mNav").is(e.target) && $("#mNav").has(e.target).length === 0) {
            $("#mNav").slideUp();
        }
    })
    /**
     * -- 搜索部分 -----------------------------------------------------------------------------------
     */
    $('#mSearchShowBtn').click(function (e) {
        e.stopPropagation();
        var $target = $('#mSearchShowContainer');
        if ($target.is(':hidden')) {
            $(this).find('i').removeClass('icon-fangdajing').addClass('icon-guanbi');
            $target.slideDown('fast');
        } else {
            $(this).find('i').addClass('icon-fangdajing').removeClass('icon-guanbi');
            $target.slideUp('fast');
        }
        $(document).click(function (e) {
            e.stopPropagation();
            if (!$target.is(e.target) && $target.has(e.target).length === 0) {
                $('#mSearchShowBtn').find('i').addClass('icon-fangdajing').removeClass('icon-guanbi');
                $target.slideUp();
            }
        })
    })
    /**
     * 全局搜索
     */
    $('.global-search-submit').on('click', function (e) {
        const searchkeyWordsInput = $('input[name="global-search-keywords"]')
        var globalSearchKeywords = searchkeyWordsInput.filter(e=> {
            return $(searchkeyWordsInput[e]).val() !== undefined && $(searchkeyWordsInput[e]).val() !== ''
        }).val()
        const language = bm.getLanguage("userLanguage")
        if (bm.isBlank(globalSearchKeywords)) {
            if (language === 'zh_CN') {
                swal('请填写搜索关键词'); return;
            } else if (language === 'en_US') {
                swal('Please fill in the search keyword'); return;
            } else if (language === 'ru_RU') {
                swal('Пожалуйста, заполните ключевые слова поиска'); return;
            }
        }

        bm.history.add(globalSearchKeywords)
        location.href = '/search?keywords=' + globalSearchKeywords;
        $('#mSearchShowContainer').slideUp('fast');
        $('#mSearchShowBtn').find('i').addClass('icon-fangdajing').removeClass('icon-guanbi');
    })
    /**
     * -- 联系我们，咨询部分 -----------------------------------------------------------------------------------
     */
    $("#contactBtn").on("click", function (e) {
        $('<div class="modal-mask"></div>').appendTo('body').fadeIn('fast')
        $(".contact-popup").fadeIn('fast');
        e.stopPropagation();
    })
    $('#closePopup').on('click', function (e) {
        $('.modal-mask').fadeOut('fast').remove('.modal-mask')
        $(".contact-popup").fadeOut('fast');
        e.stopPropagation();
    })

    /**
     * 联系我们，咨询类型选择
     */
    $('#typeSelect > .select-value').bmSelect({
        callback: function (e) {
            console.log(e)
        },
        autoSetValue: true
    })
    /**
     * 联系我们，咨询提交
     */
    $('#contactSubmit').on('click', function () {
        var contactType = $("input[name='contactType']:checked").attr("value"), contactEmail = $('#contactEmail').val(),
            contactMobile = $('#contactMobile').val(), contactContent = $('#contactContent').val();
        if (bm.isBlank(contactType)) {
            swal('请选择咨询类别');
            return
        }
        if (bm.isBlank(contactEmail)) {
            swal('请填写联系邮箱');
            return
        } else {
            const regEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
            if (!regEmail.test(contactEmail)) {
                swal('请填正确邮箱格式');
                return
            }
        }
        if (bm.isBlank(contactMobile)) {
            swal('请填写联系电话');
            return
        } else {
            const regPhone = /^1[3|4|5|6|7|8|9][0-9]{9}$/
            if (!regPhone.test(contactMobile)) {
                swal('请填正确手机号');
                return
            }
        }
        if (bm.isBlank(contactContent)) {
            swal('请填写咨询内容');
            return
        } else {
            if (contactContent.length >= 190) {
                swal('咨询内容最多可以输入190个字');
                return
            }
        }

        const nowTime = Date.now()
        const param = {
            type: contactType,
            email:contactEmail,
            mobile: contactMobile,
            content: contactContent
        }
        const randomCode = bm.randomCode()

        $.ajax({
            url: "/consult",
            type: "post",
            data: JSON.stringify(param),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function (XMLHttpRequest) {
                XMLHttpRequest.setRequestHeader("X-Nonce", randomCode);
                XMLHttpRequest.setRequestHeader("X-Time", nowTime);
                XMLHttpRequest.setRequestHeader("X-Sign", md5(randomCode+nowTime+bm.mapToParam(param)));

            },
            success: function(res){
                console.log(res)
                if(res.code > 0) {
                    swal(res.message);
                    return
                }
                swal("提交成功")
                $('#contactType').text('类型')
                $('#contactEmail').val('')
                $('#contactMobile').val('')
                $('#contactContent').val('')
                $('.modal-mask').fadeOut('fast').remove('.modal-mask')
                $(".contact-popup").fadeOut('fast');
            },
            error: function(xhr, type, errorThrown){
                swal("异常：" + JSON.stringify(xhr));
            }
        });
    })
    /**
     * -- 友情链接 -----------------------------------------------------------------------------------
     */
    $('.link-wrap .select-value').bmSelect({
        callback: function (e) {
            window.open(e)
        }
    })
    /**
     * 网上会客厅
     */
    // 改进版本的手风琴菜单效果
    $('#accordion .tit').click(function () {
        $(this).addClass('is-active');
        $(this).next('.desc').slideToggle();
        $(this).find('.iconfont').removeClass('icon-Vector-down').addClass('icon-Vector-top')
        $(this).parent().siblings('li').find('.tit').removeClass('is-active')
        $(this).parent().siblings('li').find('.desc').slideUp();
        $(this).parent().siblings('li').find('.iconfont').removeClass('icon-Vector-top').addClass('icon-Vector-down')
        $(".accordion-img").attr('src', '')
        // if ($(this).next().length === 0) {
            // console.log($(this).children('.ellipsis1').children('.open_a').trigger('click'));
        // }
    });
})