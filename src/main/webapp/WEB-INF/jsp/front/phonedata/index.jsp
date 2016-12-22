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
        <hg:Sidebar id="phonedata"/>
		<!-- begin #content -->
		<div id="content" class="content">
            <div id="data-loader" class="fade in hide"><span class="spinner" style="top: 40%;z-index:9999"></span></div>
            <div class="email-btn-row hidden-xs">
                <a href="${ctx}/front/phonedata/add" class="btn btn-sm btn-success"><i class="fa fa-plus m-r-5"></i> 上传数据</a>
            </div>
            <!-- begin row -->
            <div class="row">
                <!-- begin col-12 -->
                <div class="col-md-12">
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <div class="panel-heading-btn">
                                <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" data-click="panel-expand"><i class="fa fa-expand"></i></a>
                            </div>
                            <h4 class="panel-title">008手机数据管理</h4>
                        </div>
                        <div class="panel-body">
                            008数据剩余数量：
                            <span class="h1 text-success">
                                <fmt:formatNumber value="${phoneDataSize}" pattern="#,###"/>
                            </span>
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