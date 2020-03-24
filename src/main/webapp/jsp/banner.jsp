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
    <script type="text/javascript">
        $(function () {
            $("#bannerTab").jqGrid(
                {
                    url: "${pa}/banner/ByPageBanner",
                    datatype: "json",
                    colNames: ['ID', '标题', '图片', '超链接', '创建时间', '描述', '状态'],
                    colModel: [
                        {name: 'id', hidden: true},
                        {name: 'title', align: "center", editable: true, editrules: {required: true}},
                        {
                            name: 'url',
                            align: "center",
                            editable: true,
                            edittype: "file",
                            editoptions: {enctype: "multipart/form-data"},
                            formatter: function (data) {
                                return "<img style='width: 100%' src='${pa}" + data + "'/>"
                            }
                        },
                        {name: 'href', align: "center", editable: true},
                        {name: 'create_date', align: "center", editable: true, editrules: {required: true}},
                        {name: 'description', align: "center", editable: true, editrules: {required: true}},
                        {
                            name: 'status',
                            align: "center",
                            formatter: function (data) {
                                if (data == "1") {
                                    return "展示中";
                                } else {
                                    return "冻结";
                                }
                                t
                            },
                            editable: true,
                            editrules: {required: true},
                            edittype: "select",
                            editoptions: {value: "1:展示;2:冻结"}
                        }
                    ],
                    rowNum: 4,
                    rowList: [5, 10, 15],
                    pager: '#bannerPage',
                    mtype: "post",
                    viewrecords: true,
                    caption: "轮播图信息",
                    styleUI: "BootStrap",
                    autowidth: true,
                    multiselect: true,
                    height: "400px",
                    editurl: "${pa}/banner/save"
                });
            $("#bannerTab").jqGrid('navGrid', '#bannerPage', {
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
                        url: "${pa}/banner/upload",
                        type: "post",
                        datatype: "json",
                        data: {bannerId: bannerId},
                        fileElementId: "url",
                        success: function (data) {
                            $("#bannerTab").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            }, {
                closeAfterDel: true,
            });
        });

      $("#daoChu").click(function () {
            $.post("${pa}/banner/outPutBannerExcel",
                function (result) {

                },
                "json");
            alert("导出成功");
        });
        $("#daoRu").click(function () {
            $('#myModal').modal('show');
        });
        $("#insertBanner").click(function () {
            $.ajaxFileUpload({
                url:"${pa}/banner/inPutBannerExcel",
                type:"post",
                datatype:"json",
                fileElementId: "inputBannerImg",
                success:function (data) {

                },
            });
            $('#myModal').modal('hide');
        });


    </script>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-body">
        轮播图管理
    </div>
</div>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">轮播图信息</a></li>
    <li><a href="#" id="daoChu">导出轮播图信息</a></li>
    <li><a href="#" id="daoRu">导入轮播图信息</a></li>
    <li><a href="${pa}/banner/download">Excel模板下载</a></li>
</ul>

<table id="bannerTab"></table>
<div id="bannerPage"></div>

<div class="modal fade" tabindex="-1" role="dialog" id="myModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Modal title</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="inputBannerImg" class="col-sm-2 control-label">封面</label>
                    <div class="col-sm-8">
                        <input type="file" formenctype="multipart/form-data" class="form-control" id="inputBannerImg" name="inputBannerImg">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="insertBanner" class="btn btn-primary">Save changes</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>