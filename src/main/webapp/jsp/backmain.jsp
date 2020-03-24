<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set value="${pageContext.request.contextPath}" var="pa"></c:set>
<!DOCTYPE html>
<html>

<head>
    <title>持明法洲后台管理系统</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pa}/boot/css/bootstrap.css">
    <script type="text/javascript" src="${pa}/boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript" src="${pa}/boot/js/bootstrap.js"></script>
    <script type="text/javascript" src="../kindeditor/kindeditor-all-min.js"></script>
    <script type="text/javascript" src="../kindeditor/lang/zh-CN.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id', {
                uploadJson: "${pa}/article/uploadImg",
                allowFileManager: true,
                fileManagerJson: "${pa}/article/showImg",
                //失去焦点触发事件
                afterBlur: function () {
                    //同步数据
                    this.sync();
                }
            });

        });

    </script>

</head>

<body>
<nav class="navbar navbar-default" style="background-color: coral;">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">持明法洲后台管理系统</a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="javascript:void(0)">欢迎：<b><shiro:principal/></b></a>
            </li>
            <li>
                <a href="${pa}/admin/quit">退出登录</a>
            </li>
        </ul>
    </div>
    <!-- /.navbar-collapse -->
    <!-- /.container-fluid -->
</nav>
<div class="container-fluid">
    <div class="col-md-2">
        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

            <shiro:hasRole name="admin">
            <div class="panel panel-primary">
                <div class="panel-heading" role="tab" id="headingOne">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                           aria-expanded="true" aria-controls="collapseOne">
                            用户模块
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                    <div class="panel-body">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="">用户信息</a>
                            </li>
                            <li class="list-group-item">
                                <a href="javascript:$('#IMAX').load('${pa}/jsp/echarts.jsp')">用户活跃度分析</a>
                            </li>
                            <li class="list-group-item">
                                <a href="javascript:$('#IMAX').load('${pa}/jsp/map.jsp')">用户地区分布</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            </shiro:hasRole>
            <div class="panel panel-success">
                <div class="panel-heading" role="tab" id="headingTwo">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            上师模块
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                    <div class="panel-body">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="">上师管理</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-heading" role="tab" id="headingThree">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            文章模块
                        </a>
                    </h4>
                </div>
                <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                    <div class="panel-body">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="javascript:$('#IMAX').load('${pa}/jsp/article.jsp')">文章管理</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="panel panel-warning">
                <div class="panel-heading" role="tab" id="headingFour">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseFour" aria-expanded="false" aria-controls="collapseThree">
                            专辑模块
                        </a>
                    </h4>
                </div>
                <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                    <div class="panel-body">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="javascript:$('#IMAX').load('${pa}/jsp/album.jsp')">专辑管理</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="panel panel-danger">
                <div class="panel-heading" role="tab" id="headingFive">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseFive" aria-expanded="false" aria-controls="collapseThree">
                            轮播图模块
                        </a>
                    </h4>
                </div>
                <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                    <div class="panel-body">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="javascript:$('#IMAX').load('${pa}/jsp/banner.jsp')">轮播图管理</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <shiro:hasPermission name="admin:*">
            <div class="panel panel-success">
                <div class="panel-heading" role="tab" id="headingSix">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseSix" aria-expanded="false" aria-controls="collapseThree">
                            管理员模块
                        </a>
                    </h4>
                </div>
                <div id="collapseSix" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSix">
                    <div class="panel-body">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="javascript:$('#IMAX').load('${pa}/jsp/admin.jsp')">管理员管理</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            </shiro:hasPermission>
        </div>
    </div>
    <div class="col-md-10" id="IMAX">
        <div class="jumbotron">
            <h3><b>欢迎使用持明法洲后台管理系统！</b></h3>
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" data-interval="1500">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                </ol>
                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <img src="${pa}/img/timg111.jpeg" alt="...">

                    </div>
                    <div class="item">
                        <img src="${pa}/img/377e.jpg" alt="...">

                    </div>
                    <div class="item">
                        <img src="${pa}/img/a05.jpeg" alt="...">

                    </div>
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="panel panel-default">
        <div class="panel-footer" style="text-align: center;"><b>@百知教育&nbsp;baizhi@zparkhr.com.cn</b></div>
    </div>
</div>
<%--模态框--%>
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">文章信息</h4>
            </div>
            <div class="modal-body">
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
                        <label for="inputfile" class="col-sm-2 control-label">封面</label>
                        <div class="col-sm-8">
                            <input type="file" class="form-control" id="inputfile" name="inputfile">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editor_id" class="col-sm-2 control-label">内容</label>
                        <div class="col-sm-8">
           <textarea id="editor_id" name="editor_id" style="width:100%;height:300px;">
               &lt;strong&gt;HTML内容&lt;/strong&gt;
           </textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="status" class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="status" name="status">

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
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="sub()">Save changes</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


</body>

</html>