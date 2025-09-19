  // 手机端导航开始
  var ua = window.navigator.userAgent;
  var winW = $(window).width();
  $(document).on("click", "#header .bt-mn,#siteFunctions .bt-close", menuCtr);
  $(document).on("click", ".mobMenu > li > a", subMenu);

  function menuCtr(e) {
    e.preventDefault();

    $("#header .bt-mn,#siteFunctions .bt-close").toggleClass("open");

    $(".subDepth").slideUp(200);
    $(".mobMenu li").removeClass("active");

    if (ua.indexOf('MSIE 7') > -1 || ua.indexOf('MSIE 8') > -1) {
      $("body").toggleClass("ovf_hdn");
    } else {
      $("html, body").toggleClass("ovf_hdn");
    }

    if (!$(this).hasClass("open")) {
      $("#siteFunctions").stop().animate({ right: '-100%' }, 250);
      lm_open = false;

      if (ua.indexOf('MSIE 7') > -1 || ua.indexOf('MSIE 8') > -1) {
        $("html").css({ "height": "100%" });
        $("body").css({ "height": "100%", "overflow": "visible", "position": "static" });
      }
    } else {
      $("#siteFunctions").stop().animate({ right: 0 }, 250);
      //$(".mbg").fadeIn(200);
      lm_open = true;

      if (ua.indexOf('MSIE 7') > -1 || ua.indexOf('MSIE 8') > -1) {
        $("html").css({ "height": $(window).height() + "px" });
        $("body").css({ "height": $(window).height() + "px", "overflow": "hidden", "position": "relative" });
      }
    }
  };
  function subMenu(e) {
    $thisp = $(this).parent();

    var chk = false;
    $(".mobMenu > li").removeClass("active");

    var dropDown = $(this).next(".subDepth");
    $(".subDepth").not(dropDown).slideUp("fast");
    dropDown.stop(false, true).slideToggle("fast", function () {
      if ($(this).is(":hidden")) {
        $thisp.removeClass("active");
        chk = false;
      } else {
        $thisp.addClass("active");
        chk = true;
      }
    });

    if (!chk) {
      $(this).parent().find(".mdepth3").each(function (idx) {
        if ($(this).css("display") == "block") {
          $(this).parent().removeClass("active");
          $(this).parent().find(".mdepth3").hide();
        }
      });
    }
  }


  $("#gnb > li").each(function () {
    $(this).bind({
      "mouseenter focusin": function () {
        $("#header #gnb > li").removeClass('active');
        $('#header .subDepth').removeClass('show_menu');
        $('#header .subBg').stop().slideDown(200);
        $(this).find('.subDepth').stop().slideDown(200, function () {
          $(this).addClass('show_menu');
        })
      }
    });
  });
  $("#gnb > li,#gnb > li.gnb5").each(function () {
    $(this).bind({
      "mouseleave focusout": function () {
        $("#header").removeClass("on");
        $("#header #gnb > li").removeClass('active');
        $('#header .subBg').stop().slideUp(100);
        $('#header .subDepth').stop().slideUp(100, function () {
          $(this).addClass('show_menu');
        });
      }
    });
  });
  // 手机端导航结束



      // 搜索开始

      var flag = true;
      $(".ds").click(function (e) {
        e.stopPropagation();
        if (flag) {
          $(".searchbox").slideDown();
          $(this).removeClass('icon-fangdajing');
          $(this).addClass('icon-guanbi');
          flag = false;
        } else {
          $(".searchbox").slideUp();
          $(this).removeClass('icon-guanbi');
          $(this).addClass('icon-fangdajing');
          flag = true;
        }
      });
      // 搜索结束

      // 导航显示
      $("#mNavShowBtn").click(function(e) {
        e.stopPropagation();
        $("#mNav").slideDown();
      });
      //导航隐藏
      $("#mNavCloseBtn").click(function(e) {
        e.stopPropagation();
        $("#mNav").slideUp();
      });
      $(document).click(function(e) {
        e.stopPropagation();
        if(!$("#mNav").is(e.target) && $("#mNav").has(e.target).length === 0) {
          $("#mNav").slideUp();
        }
      })


// 导航
var $window = $(window);

$window.scroll(function () {
    var scrollTop = $(document).scrollTop();
    var hig=$(".map-div").height();
    // console.log(hig,scrollTop)



    if (scrollTop > hig) {
      $(".daohang").addClass("active");
  } else {
    $(".daohang").removeClass("active");
  }




});

$('.fix_ul li.tops').click(function () {

  $("html, body").animate({
      scrollTop: 0
  }, {
      duration: 1000,
      easing: "swing"
  });

})
$('.searchs .closes').click(function () {
  $('.searchbox ').slideUp();
})




// wow
var wow = new WOW({
  boxClass: 'wow',
  animateClass: 'animated',
  offset: 0,
  mobile: true,
  live: true
});
wow.init();

$(" .index-new .new-cont .new-ul li").hover(function (e) {
  e.stopPropagation();
  $(this).addClass("active").siblings().removeClass("active");
  let index = $(this).index();
  $(".index-new .new-cont .new-img-ul li").eq(index).addClass("active").siblings("").removeClass("active");
});
$(" .index-new .new-cont .new-ul li").eq(0).addClass("active");
$(".index-new .new-cont .new-img-ul li").eq(0).addClass("active");


