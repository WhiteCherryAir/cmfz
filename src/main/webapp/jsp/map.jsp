<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="pa"/>
<html>
<head>
    <title>轮播图管理</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pa}/boot/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pa}/jqgrid/css/ui.jqgrid.css">
    <link rel="stylesheet" type="text/css" href="${pa}/jqgrid/css/css/ui-lightness/jquery-ui-1.8.16.custom.css">
    <script type="text/javascript" src="${pa}/boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript" src="${pa}/boot/js/bootstrap.js"></script>
    <script type="text/javascript" src="${pa}/jqgrid/js/jquery.jqGrid.src.js"></script>
    <script type="text/javascript" src="${pa}/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="${pa}/boot/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="../kindeditor/kindeditor-all-min.js"></script>
    <script type="text/javascript" src="../kindeditor/lang/zh-CN.js"></script>
    <script type="text/javascript" src="../echarts/echarts.min.js"></script>
    <script type="text/javascript" src="../echarts/china.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0
        }

        html, body {
            width: 100%;
            height: 100%;
        }

        #userMap {
            width: 100%;
            height: 600px;
            margin: 0px auto;
            border: 1px solid #ddd;
        }
    </style>
    <script type="text/javascript">
        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io',//应用所在的区域地址，杭州：hangzhou.goeasy.io，新加坡：singapore.goeasy.io
            appkey: "BC-1853cc09b905435db55c370114483489",
            forceTLS: false, //如果需要使用HTTPS/WSS，请设置为true，默认为false
            onConnected: function () {
                console.log('连接成功！')
            },
            onDisconnected: function () {
                console.log('连接断开！')
            },
            onConnectFailed: function (error) {
                console.log('连接失败或错误！')
            }
        });

        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('userMap'));
            var option = {
                title: {
                    text: '用户分布图',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['男', '女']
                },
                visualMap: {
                    min: 0,
                    max: 1500,
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],//取值范围的文字
                    inRange: {
                        color: ['#e0ffff', '#006edd']//取值范围的颜色
                    },
                    show: true//图注
                },
                geo: {
                    map: 'china',
                    roam: false,//不开启缩放和平移
                    zoom: 1.23,//视角缩放比例
                    label: {
                        normal: {
                            show: true,
                            fontSize: '10',
                            color: 'rgba(0,0,0,0.7)'
                        }
                    },
                    itemStyle: {
                        normal: {
                            borderColor: 'rgba(0, 0, 0, 0.2)'
                        },
                        emphasis: {
                            areaColor: '#F3B329',//鼠标选择区域颜色
                            shadowOffsetX: 0,
                            shadowOffsetY: 0,
                            shadowBlur: 20,
                            borderWidth: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: '男',
                        type: 'map',
                        mapType: 'china',
                        geoIndex: 0,
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: [
                            //Math.round(Math.random()*1000)
                            {name: "南海诸岛", value: 0},
                            {name: '北京', value: 0},
                            {name: '天津', value: 0},
                            {name: '上海', value: 0},
                            {name: '重庆', value: 0},
                            {name: '河北', value: 0},
                            {name: '河南', value: 0},
                            {name: '云南', value: 0},
                            {name: '辽宁', value: 0},
                            {name: '黑龙江', value: 0},
                            {name: '湖南', value: 0},
                            {name: '安徽', value: 0},
                            {name: '山东', value: 0},
                            {name: '新疆', value: 0},
                            {name: '江苏', value: 0},
                            {name: '浙江', value: 0},
                            {name: '江西', value: 0},
                            {name: '湖北', value: 0},
                            {name: '广西', value: 0},
                            {name: '甘肃', value: 0},
                            {name: '山西', value: 0},
                            {name: '内蒙古', value: 0},
                            {name: '陕西', value: 0},
                            {name: '吉林', value: 0},
                            {name: '福建', value: 0},
                            {name: '贵州', value: 0},
                            {name: '广东', value: 0},
                            {name: '青海', value: 0},
                            {name: '西藏', value: 0},
                            {name: '四川', value: 0},
                            {name: '宁夏', value: 0},
                            {name: '海南', value: 0},
                            {name: '台湾', value: 0},
                            {name: '香港', value: 0},
                            {name: '澳门', value: 0}

                        ]
                    },
                    {
                        name: '女',
                        type: 'map',
                        mapType: 'china',
                        geoIndex: 0,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: [
                            {name: "南海诸岛", value: 0},
                            {name: '北京', value: 0},
                            {name: '天津', value: 0},
                            {name: '上海', value: 0},
                            {name: '重庆', value: 0},
                            {name: '河北', value: 0},
                            {name: '河南', value: 0},
                            {name: '云南', value: 0},
                            {name: '辽宁', value: 0},
                            {name: '黑龙江', value: 0},
                            {name: '湖南', value: 0},
                            {name: '安徽', value: 0},
                            {name: '山东', value: 0},
                            {name: '新疆', value: 0},
                            {name: '江苏', value: 0},
                            {name: '浙江', value: 0},
                            {name: '江西', value: 0},
                            {name: '湖北', value: 0},
                            {name: '广西', value: 0},
                            {name: '甘肃', value: 0},
                            {name: '山西', value: 0},
                            {name: '内蒙古', value: 0},
                            {name: '陕西', value: 0},
                            {name: '吉林', value: 0},
                            {name: '福建', value: 0},
                            {name: '贵州', value: 0},
                            {name: '广东', value: 0},
                            {name: '青海', value: 0},
                            {name: '西藏', value: 0},
                            {name: '四川', value: 0},
                            {name: '宁夏', value: 0},
                            {name: '海南', value: 0},
                            {name: '台湾', value: 0},
                            {name: '香港', value: 0},
                            {name: '澳门', value: 0}
                        ]
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            $.get("${pa}/user/showUserLocation", "json", function (data) {
                myChart.setOption({
                    series: [{
                        name: '男',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: data.man,
                    }, {
                        name: '女',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: data.women,
                    }]
                })

            })

            myChart.on('click', function (params) {
                console.log(params);
                alert(params.name+params.value);
            });

            goEasy.subscribe({
                channel: "cmfz",
                onMessage: function (message) {
                    console.log(message.content);
                    //var data = eval('(' + message.content + ')');
                    var data = JSON.parse(message.content);
                    myChart.setOption({
                        series: [{
                            name: '男',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: data.man,
                        }, {
                            name: '女',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: data.women,
                        }]
                    })
                }
            });

        });
    </script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="userMap"></div>
</body>
</html>