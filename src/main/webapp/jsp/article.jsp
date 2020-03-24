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
            $("#articleTable").jqGrid(
                {
                    url: "${pa}/article/articleByPage",
                    datatype: "json",
                    colNames: ['ID', '标题', '图片', '内容', '创建时间', '发布时间', '状态', '所属上师', '操作'],
                    colModel: [
                        {name: 'id', hidden: true},
                        {name: 'title', align: "center", editable: true, editrules: {required: true}},
                        {
                            name: 'img',
                            align: "center",
                            editable: true,
                            edittype: "file",
                            editoptions: {enctype: "multipart/form-data"},
                            formatter: function (data) {
                                return "<img style='width: 100%' src='" + data + "'/>"
                            }
                        },
                        {name: 'content', align: "center", editable: true},
                        {name: 'createDate', align: "center", editable: true, editrules: {required: true}},
                        {name: 'publishDate', align: "center", editable: true, editrules: {required: true}},
                        {
                            name: 'status',
                            align: "center",
                            formatter: function (data) {
                                if (data == "1") {
                                    return "展示";
                                } else {
                                    return "冻结";
                                }
                                t
                            },
                            editable: true,
                            editrules: {required: true},
                            edittype: "select",
                            editoptions: {value: "1:展示;2:冻结"}
                        },
                        {
                            name: 'guruId', align: "center",

                        },
                        {
                            name: "option",
                            formatter: function (cellvalue, options, rowObject) {
                                var button = "<button type=\"button\" class=\"btn btn-danger\" onclick=\"deleteArticle('" + rowObject.id + "')\">删除</button>&nbsp;";
                                button += "<button type=\"button\" class=\"btn btn-primary\" onclick=\"updateArticle('" + rowObject.id + "')\">修改</button>";
                                return button;
                            },
                            align: "center"
                        },
                    ],
                    rowNum: 4,
                    rowList: [5, 10, 15],
                    pager: '#articlePage',
                    mtype: "post",
                    viewrecords: true,
                    caption: "文章信息",
                    styleUI: "BootStrap",
                    autowidth: true,
                    multiselect: true,
                    height: "400px",
                    editurl: "${pa}/article/insertArticle"
                });
            $("#articleTable").jqGrid('navGrid', '#articlePage', {
                add: true,
                edittext: "编辑",
                addtext: "添加",
                deltext: "删除"
            }, {closeAfterEdit: true}, {
                closeAfterAdd: true,
                //数据库添加轮播图后，进行上传
                afterSubmit: function (response, postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url: "${pa}/article/uploadImg",
                        type: "post",
                        datatype: "json",
                        data: {bannerId: bannerId},
                        fileElementId: "url",
                        success: function (data) {
                            $("#articleTable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            }, {
                closeAfterDel: true,
            });
        });

        //点击添加文章触发
        function showArticle() {
            //添加时清空表单里的数据
            $("#KindForm")[0].reset();
            //清空kindeditor数据
            KindEditor.html("#editor_id", " ");
            $.ajax({
                url: "${pa}/guru/showAllGuru",
                datatype: "json",
                type: "post",
                success: function (data) {
                    //处理状态
                    $("#status").val(data.status);
                    var option1 = "";
                    if (data.status == "展示") {
                        option1 += "<option value=\"1\" selected>展示</option>";
                        option1 += "<option value=\"2\">冻结</option>";
                    } else {
                        option1 += "<option value=\"1\">展示</option>";
                        option1 += "<option value=\"2\" selected>冻结</option>";
                    }
                    $("#status").html(option1);
                    var option = "<option value=\"0\">选择所属上师</option>";
                    //遍历集合中的每一个对象
                    data.forEach(function (guru) {
                        option += "<option value=" + guru.id + ">" + guru.name + "</option>"
                    })
                    $("#guru_list").html(option);
                }
            })
            $('#myModal').modal('show');
        }

        //修改触发事件
        function updateArticle(id) {
            //屏蔽使用序列化问题jqGrid("getRowData",id)
            var data = $("#articleTable").jqGrid("getRowData", id);
            $("#id").val(data.id);
            $("#title").val(data.title);
            //KindEditor.html用于做数据替换
            KindEditor.html("#editor_id", data.content);
            //处理状态
            $("#status").val(data.status);
            var option = "";
            if (data.status == "展示") {
                option += "<option value=\"1\" selected>展示</option>";
                option += "<option value=\"2\">冻结</option>";
            } else {
                option += "<option value=\"1\">展示</option>";
                option += "<option value=\"2\" selected>冻结</option>";
            }
            $("#status").html(option);
            //处理上师信息
            $.ajax({
                url: "${pa}/guru/showAllGuru",
                datatype: "json",
                type: "post",
                success: function (gutuList) {
                    var options = "<option value=\"0\">选择所属上师</option>";
                    //遍历集合中的每一个对象
                    gutuList.forEach(function (guru) {
                        if (guru.id == data.guruId) {
                            options += "<option selected value=" + guru.id + ">" + guru.name + "</option>"
                        }
                        options += "<option value=" + guru.id + ">" + guru.name + "</option>"

                    })
                    $("#guru_list").html(options);
                }
            })
            $('#myModal').modal('show');
        }

        //文章添加和修改方法
        function sub() {
            $.ajaxFileUpload({
                url: "${pa}/article/insertArticle",
                type: "post",
                //异步提交时 无法传输修改后的kindeditor内容 ,需要刷新
                data: {
                    "id": $("#id").val(),
                    "title": $("#title").val(),
                    "content": $("#editor_id").val(),
                    "status": $("#status").val(),
                    "guruId": $("#guru_list").val()
                },
                datatype: "json",
                fileElementId: "inputfile",
                success: function (data) {

                }
            })
        }


        // 点击删除时触发事件
        function deleteArticle(id) {
            $.ajax({
                url: "${pa}/article/deleteArticle",
                type: "post",
                data: {
                    "id": id
                },
                datatype: "json",
                success: function (data) {
                    $("#articleTable").trigger("reloadGrid");
                }
            })
        }


    </script>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-body">
        文章管理
    </div>
</div>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">文章信息</a></li>
    <li role="presentation"><a href="#" onclick="showArticle()">添加文章</a></li>
</ul>

<table id="articleTable"></table>
<div id="articlePage"></div>
</body>
</html>
