<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="pa"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>专辑页面</title>
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
    <script type="text/javascript">
        $(function () {
            $("#albumTable").jqGrid(
                {
                    url: "${pa}/album/queryByPageAlbum",
                    datatype: "json",
                    height: 400,
                    colNames: ['ID', '标题', '评分', '作者', '播音', '章节数量', '内容简介', '封面', '发布日期', '状态'],
                    colModel: [
                        {name: 'id', hidden: true},
                        {name: 'title', align: "center", editable: true, editrules: {required: true}},
                        {name: 'score', align: "center", editable: true},
                        {name: 'author', align: "center", editable: true, editrules: {required: true}},
                        {name: 'broadcast', align: "center", editable: true, editrules: {required: true}},
                        {name: 'count', align: "center", editable: true, editrules: {required: true}},
                        {name: 'description', align: "center", editable: true, editrules: {required: true}},
                        {
                            name: 'cover',
                            align: "center",
                            editable: true,
                            edittype: "file",
                            editoptions: {enctype: "multipart/form-data"},
                            formatter: function (data) {
                                return "<img style='width: 100%' src='" + data + "'/>"
                            }
                        },
                        {name: 'createDate', align: "center", editable: true},
                        {
                            name: 'status',
                            align: "center",
                            formatter: function (data) {
                                if (data == "1") {
                                    return "展示中";
                                } else {
                                    return "冻结";
                                }
                            },
                            editable: true,
                            editrules: {required: true},
                            edittype: "select",
                            editoptions: {value: "1:展示;2:冻结"}
                        }

                    ],
                    rowNum: 5,
                    rowList: [5, 10, 15, 20],
                    pager: '#albumPage',
                    sortname: 'id',
                    viewrecords: true,
                    sortorder: "desc",
                    mtype: "get",
                    multiselect: true,
                    autowidth: true,
                    caption: "专辑信息",
                    styleUI: "BootStrap",
                        editurl: "${pa}/album/editAlbum",
                    //开启子表格的支持
                    subGrid: true,
                    // subgrid_id:父级行的Id  row_id:当前的数据Id
                    subGridRowExpanded: function (subgrid_id, row_id) {
                        // 调用生产子表格的方法
                        // 生成表格 | 生产子表格工具栏
                        addSubgrid(subgrid_id, row_id);

                    },
                    // 删除表格的方法
                    subGridRowColapsed: function (subgrid_id, row_id) {

                    }
                });
            $("#albumTable").jqGrid('navGrid', '#albumPage', {
                    add: true, edittext: "修改", addtext: "添加", deltext: "删除"
                },
                {
                    closeAfterEdit: true,
                }, {
                    closeAfterAdd: true,
                    afterSubmit: function (response, postData) {
                        var albumId = response.responseJSON.albumId;
                        $.ajaxFileUpload({
                            url: "${pa}/album/uploadAlbum",
                            datatype: "json",
                            type: "post",
                            data: {albumId: albumId},
                            fileElementId: "cover",
                            success: function (data) {
                                $("#albumTable").trigger("reloadGrid");
                            }
                        });
                        return postData;
                    }
                }, {
                    closeAfterDel: true,
                }
            );
        });

        // subgrid_id行id, row_id数据id
        function addSubgrid(subgrid_id, row_id) {
            var subgrid_table_id, pager_id;
            subgrid_table_id = subgrid_id + "_t";
            pager_id = "p_" + subgrid_table_id;
            $("#" + subgrid_id).html(
                "<table id='" + subgrid_table_id
                + "' class='scroll'></table><div id='"
                + pager_id + "' class='scroll'></div>");
            $("#" + subgrid_table_id).jqGrid(
                {
                    url: "${pa}/chapter/ByPageChaper?albumId=" + row_id,
                    datatype: "json",
                    colNames: ['ID', '标题', '大小', '时长', '创建时间', '操作'],
                    colModel: [
                        {name: "id", hidden: true, align: "center"},
                        {name: "title", align: "center", editable: true},
                        {name: "size", align: "center"},
                        {name: "time", align: "center"},
                        {name: "createTime", align: "center", editable: true},
                        {
                            name: "url",
                            editable: true,
                            edittype: "file",
                            editoptions: {enctype: "multipart/form-data"},
                            formatter: function (cellvalue, options, rowObject) {
                                var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"download('"+cellvalue+"')\">下载</button>&nbsp;";
                                button += "<button type=\"button\" class=\"btn btn-danger\" onclick=\"onPaly('"+cellvalue+"')\">在线播放</button>";
                                return button;
                            }
                        }
                    ],
                    rowNum: 20,
                    pager: pager_id,
                    sortname: 'num',
                    sortorder: "asc",
                    height: '100%',
                    styleUI: "BootStrap",
                    autowidth: true,
                    editurl: "${pa}/chapter/editChapter?albumId=" + row_id
                });
            $("#" + subgrid_table_id).jqGrid('navGrid',
                "#" + pager_id, {
                    add: true,
                },
                {
                    closeAfterEdit: true,
                }, {
                    closeAfterAdd: true,
                    afterSubmit: function (response, postData) {
                        var chapterId = response.responseJSON.chapterId;
                        $.ajaxFileUpload({
                            url: "${pa}/chapter/uploadChapter",
                            datatype: "json",
                            type: "post",
                            data: {chapterId: chapterId},
                            fileElementId: "url",
                            success: function (data) {
                                $("#" + subgrid_table_id).trigger("reloadGrid");
                            }
                        });
                        return postData;
                    }
                }, {
                    closeAfterDel: true,
                });
        };

        function onPaly(cellValue) {
            $("#music").attr("src",cellValue);
            $("#myModal").modal("show");
        }
        function download(cellValue) {
            location.href="${pa}/chapter/downloadChapter?url="+cellValue;
        }
    </script>
</head>
<body>
<table id="albumTable"></table>
<div id="albumPage" style="height: 50px"></div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <audio src="" controls="controls" id="music">

        </audio>
    </div>
</div>
</body>
</html>
