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
    <c:if test="${empty type}">
        <hg:Sidebar id="article"/>
    </c:if>
    <c:if test="${not empty type}">
        <hg:Sidebar id="article-1"/>
    </c:if>
    <!-- begin #content -->
    <div id="content" class="content">
        <div id="data-loader" class="fade in hide"><span class="spinner" style="top: 40%;z-index:9999"></span></div>
        <div class="email-btn-row hidden-xs">
            <c:if test="${empty type}">
                <a href="${ctx}/front/article/add" class="btn btn-sm btn-success"><i class="fa fa-plus m-r-5"></i> 添加链接</a>
            </c:if>
            <c:if test="${not empty type}">
                <a href="${ctx}/front/article/add?type=${type}" class="btn btn-sm btn-success"><i class="fa fa-plus m-r-5"></i> 添加链接</a>
            </c:if>
            <a href="javascript:;" id="btn-remove" class="btn btn-sm btn-danger"><i class="fa m-r-5 fa-trash"></i>删除</a>
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
                        <h4 class="panel-title">链接列表</h4>
                    </div>
                    <div class="panel-body">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>
                                    <input type="checkbox" id="ids">
                                </th>
                                <th>序号</th>
                                <th>平台</th>
                                <th>链接</th>
                                <th>访问次数</th>
                                <th>创建时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${fn:length(articles) > 0}">
                                <c:forEach items="${articles}" var="item" varStatus="status">
                                    <tr>
                                        <td>
                                            <input type="checkbox" name="ids" value="${item.id}">
                                        </td>
                                        <td>${status.index + 1}</td>
                                        <td>${item.platform}</td>
                                        <td>
                                            <span title="${item.url}">
                                                ${item.shortUrl}
                                            </span>
                                        </td>
                                        <td>${item.visitCount}</td>
                                        <td>
                                            <fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${fn:length(articles) == 0}">
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
</body>
<hg:PageFooter />
<script src="${ctx}/js/jquery.gritter.js"></script>
<script>
    $(document).ready(function() {
        App.init();
        $('#ids').click(function() {
            var checked = $(this).prop('checked');
            $('input[name="ids"]').prop('checked', checked);
        });
        $('#btn-remove').click(function() {
            var ids = $('input[name="ids"]:checked').serialize();
            if (!ids || ids.length == 0) {
                alert('请先勾选要删除的数据');
                return false;
            }
            var url = '${ctx}/front/article/remove?' + ids;
            $.post(url).done(function(resp) {
                $('#remove-modal').modal('hide');
                if (resp.code == 1000) {
                    $.gritter.add({
                        title: '成功提示~',
                        text: resp.msg,
                        class_name: 'gritter-light',
                        time: 2000,
                        after_close: function() {
                            window.location.reload();
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