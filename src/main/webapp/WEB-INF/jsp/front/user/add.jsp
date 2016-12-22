<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/common/taglibs.jspf" %>
<hg:PageHeader />
</head>
<body>
	<!-- begin #page-loader -->
	<div id="page-loader" class="fade in"><span class="spinner"></span></div>
	<!-- end #page-loader -->
	<!-- begin #page-container -->
	<div id="page-container" class="fade page-sidebar-fixed page-header-fixed">
        <hg:Header />
        <hg:Sidebar id="user"/>
		<!-- begin #content -->
		<div id="content" class="content">
		    <div id="data-loader" class="fade in hide"><span class="spinner" style="top: 40%;z-index:9999"></span></div>
			<!-- begin row -->
			<div class="row">
			    <!-- begin col-12 -->
			    <div class="col-md-12">
			        <div class="panel panel-success">
                        <div class="panel-heading">
                            <div class="panel-heading-btn">
                                <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" data-click="panel-expand"><i class="fa fa-expand"></i></a>
                            </div>
                            <h4 class="panel-title">添加管理员</h4>
                        </div>
                        <div class="panel-body panel-form">
                            <form action="${ctx}/front/user/add" method="post" class="form-horizontal form-bordered" data-parsley-validate="true">
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 required">登录账号</label>
                                    <div class="col-md-6 col-sm-6">
                                        <input class="form-control" type="text" id="username" name="username"  data-type="alphanum" placeholder="请输入登录账号"  data-parsley-required="true" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 required">登录密码</label>
                                    <div class="col-md-6 col-sm-6">
                                        <input class="form-control" type="password" id="password" name="password" placeholder="请输入登录密码"  data-parsley-required="true" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 required">真实姓名</label>
                                    <div class="col-md-6 col-sm-6">
                                        <input class="form-control" type="text" id="realname" name="realname" data-parsley-required="true" placeholder="请输入真实姓名" data-required="true" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4"></label>
                                    <div class="col-md-6 col-sm-6">
                                        <a href="${ctx}/front/user/index" class="btn btn-default">取消</a>
                                        <button type="submit" class="btn btn-success">提交</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- end col-12 -->
            </div>
            <!-- end row -->
		</div>
		<!-- end #content -->
		<!-- begin scroll to top btn -->
		<a href="javascript:;" class="btn btn-icon btn-circle btn-success btn-scroll-to-top fade" data-click="scroll-top"><i class="fa fa-angle-up"></i></a>
		<!-- end scroll to top btn -->
	</div>
</body>
<hg:PageFooter />
<script src="${ctx}/js/parsley.js"></script>
<script>
    $(document).ready(function() {
        App.init();
    });
</script>
</html>