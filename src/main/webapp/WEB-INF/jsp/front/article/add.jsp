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
    <hg:Sidebar id="script"/>
    <!-- begin #content -->
    <div id="content" class="content">
        <div id="data-loader" class="fade in hide"><span class="spinner" style="top: 40%;z-index:9999"></span></div>
        <!-- begin row -->
        <hg:ShowMessage msg="${error}" type="error"/>
        <div class="row">
            <!-- begin col-12 -->
            <div class="col-md-12">
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <div class="panel-heading-btn">
                            <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" data-click="panel-expand"><i class="fa fa-expand"></i></a>
                        </div>
                        <h4 class="panel-title">添加链接</h4>
                    </div>
                    <div class="panel-body panel-form">
                        <form action="${ctx}/front/article/add" method="post" class="form-horizontal form-bordered" data-parsley-validate="true" enctype="multipart/form-data">
                            <input type="hidden" name="type" value="${type}">
                            <div class="form-group">
                                <label class="control-label col-md-4 col-sm-4 required">平台名称</label>
                                <div class="col-md-6 col-sm-6">
                                    <input class="form-control" type="text" id="platform" name="platform" placeholder="请输入平台名称"  data-parsley-required="true" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4 col-sm-4 required" for="url">文章链接</label>
                                <div class="col-md-6 col-sm-6">
                                    <textarea class="form-control" id="url" name="url" rows="4" data-parsley-range="[5,500]"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4 col-sm-4"></label>
                                <div class="col-md-6 col-sm-6">
                                    <c:if test="${empty type}">
                                        <a href="${ctx}/front/article/index" class="btn btn-default">取消</a>
                                    </c:if>
                                    <c:if test="${not empty type}">
                                        <a href="${ctx}/front/article/index?type=${type}" class="btn btn-default">取消</a>
                                    </c:if>
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