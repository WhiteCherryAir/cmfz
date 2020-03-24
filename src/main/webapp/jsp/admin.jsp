<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="pa"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>文章管理</title>
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
    <script type="text/javascript" src="../kindeditor/kindeditor-all.js"></script>
    <script type="text/javascript" src="../kindeditor/lang/zh-CN.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#adminTable").jqGrid(
                {
                    url: "${pa}/admin/byPageAdminController",
                    datatype: "json",
                    colNames: ['ID', '管理员名', '密码', '盐值'],
                    colModel: [
                        {name: 'id', hidden: true},
                        {name: 'username', align: "center", editable: true, editrules: {required: true}},
                        {name: 'password', align: "center", editable: true, editrules: {required: true}},
                        {name: 'salt', align: "center", editable: true, editrules: {required: true}},
                    ],
                    rowNum: 4,
                    rowList: [5, 10, 15],
                    pager: '#adminPage',
                    mtype: "post",
                    viewrecords: true,
                    caption: "管理员信息",
                    styleUI: "BootStrap",
                    autowidth: true,
                    multiselect: true,
                    height: "400px",
                    editurl: "${pa}/admin/editAdmin"
                });
            $("#adminTable").jqGrid('navGrid', '#adminPage', {
                add: true,
                edittext: "编辑",
                addtext: "添加",
                deltext: "删除"
            }, {closeAfterEdit: true}, {closeAfterAdd: true}, {closeAfterDel: true});
        });

    </script>
</head>
<body>

<table id="adminTable"></table>
<div id="adminPage"></div>
</body>
</html>
