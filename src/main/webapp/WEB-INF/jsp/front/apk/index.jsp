<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/common/taglibs.jspf" %>
<hg:PageHeader />
<link href="${ctx}/css/jquery.gritter.css" rel="stylesheet" />
</head>
<body>
	<!-- begin #page-loader -->
	<div id="page-loader" class="fade in"><span class="spinner"></span></div>
	<!-- end #page-loader -->
	<!-- begin #page-container -->
	<div id="page-container" class="fade page-sidebar-fixed page-header-fixed">
        <hg:Header />
        <hg:Sidebar id="apk"/>
		<!-- begin #content -->
		<div id="content" class="content">
		    <div id="data-loader" class="fade in hide"><span class="spinner" style="top: 40%;z-index:9999"></span></div>
		    <div class="email-btn-row hidden-xs">
                <a href="${ctx}/front/apk/add" class="btn btn-sm btn-success"><i class="fa fa-plus m-r-5"></i> 上传APK</a>
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
                            <h4 class="panel-title">APK管理</h4>
                        </div>
                        <div class="panel-body">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>名称</th>
                                        <th>路径</th>
                                        <th>创建时间</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${fn:length(apks) > 0}">
                                        <c:forEach items="${apks}" var="item" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
                                            <td>${item.name}</td>
                                            <td>${item.path}</td>
                                            <td>
                                                <fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm"/>
                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-danger btn-sm btn-remove" data-id="${item.id}">删除</button>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${fn:length(apks) == 0}">
                                        <tr>
                                            <td class="text-center" colspan="5">
                                                暂无数据
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
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
	<div class="modal fade" id="remove-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">删除警告</h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger m-b-0">
                        <h4><i class="fa fa-info-circle"></i> 您确定要删除该APK吗？删除后不可恢复！</h4>
                        <input type="hidden" id="remove-id" value="">
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="javascript:;" class="btn btn-sm btn-white" data-dismiss="modal">取消</a>
                    <a href="javascript:;" id="btn-remove" class="btn btn-sm btn-danger">确定</a>
                </div>
            </div>
        </div>
    </div>
</body>
<hg:PageFooter />
<script src="${ctx}/js/jquery.gritter.js"></script>
<script>
    $(document).ready(function() {
        App.init();
        $('.btn-remove').click(function() {
            var id = $(this).attr('data-id');
            $('#remove-modal').modal();
            $('#remove-id').val(id);
        });
        $('#btn-remove').click(function() {
            var id = $('#remove-id').val();
            var url = '${ctx}/front/apk/' + id + '/remove';
            $.post(url).done(function(resp) {
                $('#remove-modal').modal('hide');
                if (resp.code == 1000) {
                    $.gritter.add({
                        title: '成功提示~',
                        text: resp.msg,
                        class_name: 'gritter-light',
                        time: 2000,
                        after_close: function() {
                            window.location.href = '${ctx}/front/apk/index';
                        }
                    });
                } else {
                    $.gritter.add({
                        title: '删除失败',
                    	text: resp.msg,
                    	time: 2000
                   	});
                }
            }).fail(function(){
                $('#remove-modal').modal('hide');
                $.gritter.add({
                    title: '删除失败',
                    text: '系统繁忙，请稍后再试吧~~',
                    time: 2000
                });
            });
        });
    });
</script>
</html>