<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="pa"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>11111111</title>
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
        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id', {
                uploadJson: "${pa}/article/uploadImg",
                allowFileManager: true,
                fileManagerJson: "${pa}/article/showImg",
                //失去焦点触发事件
                afterBlur:function () {
                    //同步数据
                    this.sync();
                }
            });

        });

        $.ajax({
            url: "${pa}/guru/showAllGuru",
            datatype: "json",
            type: "post",
            success: function (data) {
                var option = "<option value=\"0\">选择所属上师</option>";
                //遍历集合中的每一个对象
                data.forEach(function (guru) {
                    option += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option);
            }
        })
        function sub() {
            $.ajaxFileUpload({
                url:"${pa}/article/insertArticle",
                type:"post",
                //异步提交时 无法传输修改后的kindeditor内容 ,需要刷新
                data:{"id":$("#id").val(),"title":$("#title").val(),"content":$("#editor_id").val(),"status":$("#status").val(),"guruId":$("#guru_list").val()},
                datatype: "json",
                fileElementId:"inputImg",
                success:function (data) {

                }
            })
        }
    </script>
</head>
<body>

<form class="form-horizontal" id="KindForm">
    <div class="form-group">
        <label for="id" class="col-sm-2 control-label"></label>
        <div class="col-sm-8">
            <input type="hidden" class="form-control" id="id">
        </div>
    </div>
    <div class="form-group">
        <label for="title" class="col-sm-2 control-label">标题</label>
        <div class="col-sm-8">
            <input type="text" class="form-control" id="title" placeholder="标题" name="title">
        </div>
    </div>
    <div class="form-group">
        <label for="inputImg" class="col-sm-2 control-label">封面</label>
        <div class="col-sm-8">
            <input type="file" class="form-control" id="inputImg" name="inputImg">
        </div>
    </div>
    <div class="form-group">
        <label for="editor_id" class="col-sm-2 control-label">内容</label>
        <div class="col-sm-8">
           <textarea id="editor_id" name="editor_id" style="width:700px;height:300px;">
               &lt;strong&gt;HTML内容&lt;/strong&gt;
           </textarea>
        </div>
    </div>
    <div class="form-group">
        <label for="status" class="col-sm-2 control-label">状态</label>
        <div class="col-sm-8">
            <select class="form-control" id="status" name="status">
                <option value="1">展示</option>
                <option value="2">冻结</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="guru_list" class="col-sm-2 control-label">上师列表</label>
        <div class="col-sm-8">
            <select class="form-control" id="guru_list" name="guruId">

            </select>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-8">
            <button type="button" class="btn btn-default" onclick="sub()">Sign in</button>
        </div>
    </div>
</form>
</body>
</html>
