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

function isMobile() {
    // 判断是否为移动设备
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
}

function get_rmb_data() {
    $.ajax({
        url: '/api-admin/homeData',
        type: 'get',
        contentType: 'application/json',
        dataType: 'json',
        success: function (res) {
            // console.log(JSON.stringify(res))
            if (res) {
                // const monthEnArr = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
                // const monthRuArr = ['января', 'февраля', 'марта', 'апреля', 'мая', 'июня', 'июля', 'августа', 'сентября', 'октября', 'ноября', 'декабря']
                const language = getLanguage("userLanguage")
                // const year = res.data.year
                if (language === 'zh_CN') {
                    $('#statisticInfo1').html(res.data[0].contentCn)
                    if (res.data.length > 1) {
                        $('#statisticInfo2').html(res.data[1].contentCn)
                        $('.static_data_1').css("display", "block");
                        if(!isMobile())   $('.static_data').css("margin", "95px 480px");
                    } else {
                        $('.static_data_1').css("display", "none");
                        if(!isMobile())   $('.static_data').css("margin", "0 480px");
                    }
                } else if (language === 'en_US') {
                    $('#statisticInfo1').html(res.data[0].contentEn)
                    if (res.data.length > 1) {
                        $('#statisticInfo2').html(res.data[1].contentEn)
                        $('.static_data_1').css("display", "block");
                        if(!isMobile())   $('.static_data').css("margin", "175px 480px");
                    } else {
                        $('.static_data_1').css("display", "none");
                        if(!isMobile())     $('.static_data').css("margin", "0 480px");
                    }
                } else if (language === 'ru_RU') {
                    $('#statisticInfo1').html(res.data[0].contentRu)
                    if (res.data.length > 1) {
                        $('#statisticInfo2').html(res.data[1].contentRu)
                        $('.static_data_1').css("display", "block");
                        if(!isMobile()) $('.static_data').css("margin", "240px 480px");
                    } else {
                        $('.static_data_1').css("display", "none");
                        if(!isMobile())    $('.static_data').css("margin", "0 480px");
                    }
                }
            }
        }
    })
}

function getContent(val,isMoney){
    const language = getLanguage("userLanguage")
    let wan = "万";
    if (language === 'zh_CN') {
        wan = "万";
    } else if (language === 'en_US') {
        wan = "million";
    } else if (language === 'ru_RU') {
        wan = "млн";
    }
    if(isMoney){
        if(val === null){
            return "-";
        }else{
            return val+wan;
        }
    }else{
        if(val === null){
            return "-";
        }else{
            return val;
        }
    }
}

// 获取银行数据
function get_bank_data() {
    $.ajax({
        url: '/api-admin/homeData/bank',
        type: 'get',
        contentType: 'application/json',
        dataType: 'json',
        success: function (res) {
            const language = getLanguage("userLanguage")
            let bankTitle = "";
            if (language === 'zh_CN') {
                bankTitle =  res.data.contentCn ;
            } else if (language === 'en_US') {
                bankTitle =  res.data.contentEn ;
            } else if (language === 'ru_RU') {
                bankTitle =  res.data.contentRu ;
            }
            $("#bankTitle").append(bankTitle) ;
            let inthtml = "";
            for (var j = 0; j < res.data.bankList.length; j++) {
                let bank = res.data.bankList[j]
                const language = getLanguage("userLanguage")
                let bankName = "";
                if (language === 'zh_CN') {
                    bankName =  bank.contentCn ;
                } else if (language === 'en_US') {
                    bankName =  bank.contentEn ;
                } else if (language === 'ru_RU') {
                    bankName =  bank.contentRu ;
                }
                if(j < res.data.bankList.length - 1){
                    inthtml = inthtml + " <tr><td style='font-size: 12px;color: white;text-align:center;height: 30px; border-bottom: 1px solid #63A8FF;'>" +bankName + "</td>\n" +
                        "<td  style='text-align:center;font-size: 13px;color: white;border-bottom: 1px solid #63A8FF;'>" +getContent( bank.bidPrice1,false) + "</td>\n" +
                        "<td  style='text-align:center;font-size: 13px;color: white;border-bottom: 1px solid #63A8FF;'>" + getContent( bank.sellRate1,false) + "</td>\n" +
                        " <td  style='text-align:center;font-size: 13px;color: white;border-bottom: 1px solid #63A8FF; border-right: 1px solid #63A8FF;'>" +getContent( bank.turnover1,true) +"</td>\n" +
                        " <td  style='text-align:center;font-size: 13px;color: white;border-bottom: 1px solid #63A8FF;'>" +getContent( bank.bidPrice2,false)  + "</td>\n" +
                        " <td  style='text-align:center;font-size: 13px;color: white;border-bottom: 1px solid #63A8FF;'>" + getContent( bank.sellRate2,false) + "</td>\n" +
                        "<td  style='text-align:center;font-size: 13px;color: white;border-bottom: 1px solid #63A8FF;'>" + getContent( bank.turnover2,true)  + "</td></tr>";
                }else{
                    inthtml = inthtml + " <tr><td style='font-size: 12px;color: white;text-align:center;height: 30px;'>" + bankName + "</td>\n" +
                        "<td  style='text-align:center;font-size: 13px;color: white;'>" +getContent( bank.bidPrice1,false) + "</td>\n" +
                        "<td  style='text-align:center;font-size: 13px;color: white;'>" + getContent( bank.sellRate1,false) + "</td>\n" +
                        " <td  style='text-align:center;font-size: 13px;color: white; border-right: 1px solid #63A8FF;'>" +getContent( bank.turnover1,true) +"</td>\n" +
                        " <td  style='text-align:center;font-size: 13px;color: white;'>" +getContent( bank.bidPrice2,false)  + "</td>\n" +
                        " <td  style='text-align:center;font-size: 13px;color: white;'>" + getContent( bank.sellRate2,false) + "</td>\n" +
                        "<td  style='text-align:center;font-size: 13px;color: white;'>" + getContent( bank.turnover2,true)  + "</td></tr>";
                }
            }
            $("#dataTable").append(inthtml)


        }
    })
}

let chartData;

function get_chart_data() {
    $.ajax({
        url: '/api-admin/homeChartData/getHomeChartData',
        type: 'get',
        contentType: 'application/json',
        dataType: 'json',
        success: function (res) {
            if (res && res.code === 0) {
                chartData = JSON.parse(JSON.stringify(res.data))
                chartData.map(item => {
                    $.getJSON("/js/i18n/i18n_zh_CN.json", function (i18Data) {
                        let resultMap = []
                        item.content.country.forEach(country => {
                            for (let key in i18Data) {
                                if (i18Data[key] === country) {
                                    resultMap.push(key);
                                    break;
                                }
                            }
                        });
                        item.content.country = resultMap
                    })
                    return item
                })
                formatChartData()
            }
        }
    })
}

get_rmb_data()

get_chart_data()

get_bank_data()

function formatChartData() {
    $.getJSON("/js/i18n/i18n_" + getLanguage("userLanguage") + ".json", function (i18Data) {
        const data_1 = formatPieItem(chartData[0], i18Data)
        const data_2 = formatPieItem(chartData[1], i18Data)
        const data_3 = formatLineItem(chartData[2], i18Data, ["#FF78F1", "#A770FF", "#299BF8", "#76D8FF", "#FF7387", "#FEDC80", "#79E093", "#F6A96F", "#D2F399"])
        const data_4 = formatLineItem(chartData[3], i18Data, ["#76D8FF", "#FF78F1", "#A770FF", "#FF7387", "#299BF8", "#FEDC80", "#79E093", "#F6A96F", "#D2F399"])
        const data_5 = formatLineItem(chartData[4], i18Data, ["#79E093", "#F6A96F", "#FF7387", "#FEDC80", "#76D8FF", "#299BF8", "#A770FF", "#FF78F1", "#D2F399"])

        var options = [
            {
                tooltip: {
                    trigger: "axis",
                    axisPointer: {
                        type: "shadow",
                    },
                },
                // legend: {},
                grid: {
                    left: "3%",
                    right: "4%",
                    // bottom: "3%",
                    containLabel: true,
                },
                xAxis: [
                    {
                        type: "value",
                        boundaryGap: [0, 0.01],
                        axisLabel: {
                            //修改坐标系字体颜色
                            textStyle: {
                                color: "#fff",
                            },
                        },
                    },
                    {
                        type: 'value',
                        show: false,
                        interval: 30,
                        axisLabel: {
                            formatter: '{value}%',
                            textStyle: {
                                color: "#fff",
                            }
                        }
                    }
                ],
                yAxis: {
                    type: "category",
                    data: data_4.country.reverse(),
                    axisLabel: {
                        //修改坐标系字体颜色
                        textStyle: {
                            color: "#fff",
                        },
                    },
                },
                series: [
                    {
                        name: i18Data.Totalimportvolume,
                        type: "bar",
                        data: data_4.percentage.reverse()
                    },
                    {
                        name: i18Data.YOY,
                        type: 'line',
                        xAxisIndex: 1,
                        showSymbol: false,
                        symbol: "none",
                        itemStyle: {
                            type: 'dashed',
                            color: 'rgba(255,0,0,0)'
                        },
                        tooltip: {
                            valueFormatter: function (value) {
                                return value + '%';
                            }
                        },
                        data: data_4.proportion.reverse()
                    }
                ],
            },
            {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center'
                },
                xAxis: [
                    {
                        type: 'category',
                        axisTick: {show: false},
                        data: data_5.country,
                        axisLabel: {
                            //修改坐标系字体颜色
                            interval: 0,
                            rotate: 30,
                            textStyle: {
                                color: "#fff",
                            }
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        axisLabel: {
                            //修改坐标系字体颜色
                            textStyle: {
                                color: "#fff",
                            }
                        },
                    },
                    {
                        type: 'value',
                        show: false,
                        interval: 30,
                        axisLabel: {
                            formatter: '{value}%',
                            textStyle: {
                                color: "#fff",
                            }
                        }
                    }
                ],
                series: [
                    {
                        name: i18Data.Totalexportvolume,
                        type: 'bar',
                        // barGap: 0,
                        // emphasis: {
                        //     focus: 'series'
                        // },
                        tooltip: {
                            valueFormatter: function (value) {
                                return value;
                            }
                        },
                        data: data_5.percentage
                    },
                    {
                        name: i18Data.YOY,
                        type: 'line',
                        yAxisIndex: 1,
                        showSymbol: false,
                        symbol: "none",
                        itemStyle: {
                            type: 'dashed',
                            color: 'rgba(255,0,0,0)'
                        },
                        tooltip: {
                            valueFormatter: function (value) {
                                return value + '%';
                            }
                        },
                        data: data_5.proportion
                    }
                ]
            },
            /*
            {
                title: {},
                tooltip: {
                    trigger: "item",
                    formatter: "{b} : {d}%",
                },
                legend: {
                    left: "center",
                    top: "bottom",
                    data: [
                        "rose1",
                        "rose2",
                        "rose3",
                        "rose4",
                        "rose5",
                        "rose6",
                        "rose7",
                        "rose8",
                    ],
                },
                color:['#79E093','#F6A96F','#FEDC80','#FF7387','#76D8FF','#299BF8','#A770FF','#FF78F1','#D2F399'],
                series: [
                    {
                        type: "pie",
                        center: ["50%", "50%"],
                        label: {
                            show: false,
                        },
                        labelLine: {
                            normal: {
                                show: false,
                            },
                        },
                        emphasis: {
                            label: {
                                show: false,
                            },
                        },
                        data: data_2,
                    },
                ],
            },
             */
            {
                tooltip: {
                    trigger: 'axis',
                },
                grid: {
                    left: '15%',
                    right: '-5%',
                },
                xAxis: [
                    {
                        type: 'category',
                        data: data_3.country,
                        axisPointer: {
                            type: 'shadow'
                        },
                        axisLabel: {
                            //修改坐标系字体颜色
                            interval: 0,
                            rotate: 30,
                            textStyle: {
                                color: "#fff",
                            }
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        interval: 200,
                        axisLabel: {
                            formatter: '{value}',
                            textStyle: {
                                color: "#fff",
                            }
                        }
                    },
                    {
                        type: 'value',
                        show: false,
                        interval: 30,
                        axisLabel: {
                            formatter: '{value}%',
                            textStyle: {
                                color: "#fff",
                            }
                        }
                    }
                ],
                series: [
                    {
                        name: i18Data.Totalimportandexportvolume,
                        type: 'bar',
                        tooltip: {
                            valueFormatter: function (value) {
                                return value;
                            }
                        },
                        data: data_3.percentage
                    },
                    {
                        name: i18Data.YOY,
                        type: 'line',
                        yAxisIndex: 1,
                        showSymbol: false,
                        symbol: "none",
                        itemStyle: {
                            type: 'dashed',
                            color: 'rgba(255,0,0,0)'
                        },
                        tooltip: {
                            valueFormatter: function (value) {
                                return value + '%';
                            }
                        },
                        data: data_3.proportion
                    }
                ]
            },
            {
                title: {
                    text: "",
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{d}%'
                },
                color: ['#79E093', '#F6A96F', '#FEDC80', '#FF7387', '#76D8FF', '#299BF8', '#A770FF', '#FF78F1', '#D2F399'],
                series: [
                    {
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '18'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: data_1
                    }
                ]
            }]

        // var nodes = ['a','b','c','d','e']
        var nodes = ['a', 'b', 'c', 'e']

        nodes.forEach(function (e, i) {
            var dom = document.getElementById('my-' + e);
            var chart = echarts.init(dom, null, {
                renderer: 'canvas',
                useDirtyRect: false
            })

            chart.setOption(options[i])
            window.addEventListener('resize', chart.resize);
        })

        const classNames = ['sh_yds', 'sh_elss', 'sh_ylds', 'sh_bjsts', 'sh_jrjsds', 'sh_yl', 'sh_wzbkds', 'sh_tjkds', 'sh_hstds']

        let lineHtml1 = ''
        for (let i = 0; i < data_1.length; i++) {
            lineHtml1 += '<dl><i class="' + classNames[i] + '"></i><span>' + data_1[i].name + ' ' + data_1[i].value + '%</span></dl>'
        }
        $('#chart_pie_1').html(lineHtml1)

        let lineHtml2 = ''
        for (let i = 0; i < data_2.length; i++) {
            lineHtml2 += '<dl><i class="' + classNames[i] + '"></i><span>' + data_2[i].name + ' ' + data_2[i].value + '%</span></dl>'
        }
        $('#chart_pie_2').html(lineHtml2)
    })
}

function formatLineItem(chartData, i18Data, colors) {
    let countries = []
    let percentages = []
    let proportions = []
    chartData.content.country.forEach(country => {
        countries.push(i18Data[country])
    })
    for (let i = 0; i < chartData.content.percentage.length; i++) {
        percentages.push({
            value: chartData.content.percentage[i].replace('%', ''),
            itemStyle: {
                color: colors[i],
            },
        })
    }
    if (chartData.content.proportion) {
        for (let i = 0; i < chartData.content.proportion.length; i++) {
            proportions.push(parseFloat(chartData.content.proportion[i]))
        }
        return {
            'country': countries,
            'percentage': percentages,
            'proportion': proportions
        }
    } else {
        return {
            'country': countries,
            'percentage': percentages,
        }
    }
}

function formatPieItem(chartData, i18Data) {
    let items = []
    for (let i = 0; i < chartData.content.country.length; i++) {
        items.push({
            'name': i18Data[chartData.content.country[i]],
            'value': parseFloat(chartData.content.percentage[i])
        })
    }
    return items
}