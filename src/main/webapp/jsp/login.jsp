<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="pa"></c:set>
<!doctype html>
<html>
<head>
    <title>登录</title>
    <meta charset="utf-8">

    <link href="favicon.ico" rel="shortcut icon"/>
    <link href="${pa}/boot/css/bootstrap.css" rel="stylesheet">
    <script src="${pa}/boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript">
        function login() {
            $.ajax({
                url: "${pa}/admin/login",
                type: "POST",
                datatype: "JSON",
                data: $("#loginForm").serialize(),
                success: function (data) {

                    if (data.status == 200) {
                        location.href = "${pa}/jsp/backmain.jsp";
                    } else {
                        alert(data.msg);
                    }
                }
            })
        }

        function changeImg() {
            $("#imgCode").prop("src", "${pa}/admin/verify?xx=" + Math.random());
        }
    </script>
</head>
<body style=" background: url(${pa}/img/timg.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="">
            <div class="modal-body" id="model-body">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="用户名" autocomplete="off" name="username">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
                </div>
                <span id="msg"></span>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="验证码" autocomplete="off" name="clientCode">
                    <a href="javascript:void(0)">
                        <img id="imgCode" src="${pa}/admin/verify" onclick="changeImg()"/>
                    </a>

                </div>
                <div class="modal-footer">
                    <div class="form-group">
                        <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录
                        </button>
                    </div>

                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
