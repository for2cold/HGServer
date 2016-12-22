<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/common/taglibs.jspf" %>
<hg:PageHeader />
</head>
<body class="pace-top">
	<div id="page-loader" class="fade in"><span class="spinner"></span></div>
	<div class="login-cover">
	    <div class="login-cover-image"><img src="${ctx}/img/login-bg/bg-1.jpg" data-id="login-cover-image" alt="" /></div>
	    <div class="login-cover-bg"></div>
	</div>
	<div id="page-container" class="fade">
        <div class="login login-v2" data-pageload-addclass="animated fadeIn">
            <div class="login-header">
                <div class="brand">
                    <span class="logo"></span> Hugo助手管理平台
                    <small>Great minds have purpose, others have wishes.</small>
                </div>
                <div class="icon">
                    <i class="fa fa-sign-in"></i>
                </div>
            </div>
            <div class="text-danger login-error">
                ${error}
            </div>
            <div class="login-content">
                <form method="POST" class="margin-bottom-0" action="${ctx}/login">
                    <div class="form-group m-b-20">
                        <input type="text" name="username" id="username" class="form-control input-lg" placeholder="登录账号" value="${username}" />
                    </div>
                    <div class="form-group m-b-20">
                        <input type="password" name="password" class="form-control input-lg" placeholder="登录密码" />
                    </div>
                    <div class="login-buttons">
                        <button type="submit" class="btn btn-success btn-block btn-lg">Sign me in</button>
                    </div>
                </form>
            </div>
        </div>
        <ul class="login-bg-list">
            <li class="active"><a href="#" data-click="change-bg"><img src="${ctx}/img/login-bg/bg-1.jpg" alt="" /></a></li>
            <li><a href="#" data-click="change-bg"><img src="${ctx}/img/login-bg/bg-2.jpg" alt="" /></a></li>
            <li><a href="#" data-click="change-bg"><img src="${ctx}/img/login-bg/bg-3.jpg" alt="" /></a></li>
            <li><a href="#" data-click="change-bg"><img src="${ctx}/img/login-bg/bg-4.jpg" alt="" /></a></li>
            <li><a href="#" data-click="change-bg"><img src="${ctx}/img/login-bg/bg-5.jpg" alt="" /></a></li>
            <li><a href="#" data-click="change-bg"><img src="${ctx}/img/login-bg/bg-6.jpg" alt="" /></a></li>
        </ul>
	</div>
</body>
<hg:PageFooter />
<script src="${ctx}/js/modules/login.js?v=1.0"></script>
	<script>
		$(document).ready(function() {
			App.init();
			Login.init();
			$('#username').focus();
		});
	</script>
</html>

