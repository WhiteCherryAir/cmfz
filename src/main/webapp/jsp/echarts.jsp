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
    <script type="text/javascript">
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '持明法洲用户注册趋势图'
                },
                tooltip: {},
                legend: {
                    data: ['男', '女']
                },
                xAxis: {
                    data: ["当日注册量", "一周注册量", "一月注册量", "一年注册量"]
                },
                yAxis: {},

            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            $.ajax({
                url: "${pa}/user/showUserByTime",
                type: "get",
                datatype: "json",
                success: function (data) {
                    myChart.setOption({
                        series: [{
                            name: '男',
                            type: 'bar',
                            data: data.man
                        }, {
                            name: '女',
                            type: 'bar',
                            data: data.women
                        }]
                    })
                }
            })
        })
    </script>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 100%;height:450px;"></div>
</body>
</html>