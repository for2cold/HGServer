<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/common/taglibs.jspf" %>
<hg:PageHeader />
<link href="${ctx}/css/jquery.gritter.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-select.min.css">
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
    <c:if test="${not empty type and type == 1}">
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
            <a href="javascript:;" id="btn-update" class="btn btn-sm btn-success"><i class="fa m-r-5 fa-exchange"></i>更新状态</a>
            <a href="javascript:;" id="btn-query" class="btn btn-sm btn-success"><i class="fa fa-refresh m-r-5"></i> 刷新</a>
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
                        <form class="form-inline" id="searchForm" action="" method="POST">
                            <label class="control-label">平台：</label>
                            <div class="form-group m-r-4">
                                <select class="selectpicker" name="platform" id="platform" data-size="10" data-title="" data-style="btn-white" data-width="90px" data-title="">
                                    <option value=""></option>
                                    <c:if test="${fn:length(platforms) > 0}">
                                        <c:forEach items="${platforms}" var="item">
                                            <option value="${item}"<c:if test="${item eq platform}"> selected="selected"</c:if>>${item}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                        </form>
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>
                                    <input type="checkbox" id="ids">
                                </th>
                                <th>序号</th>
                                <th>平台</th>
                                <th>链接</th>
                                <th>状态</th>
                                <th>微信号</th>
                                <th>最大访问次数</th>
                                <th>已访问次数</th>
                                <th>添加时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="articleTable">
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
<script type="text/html" id="notDataTpl">
    <tr><td class="text-center" colspan="10">暂无数据</td></tr>
</script>
<script type="text/html" id="loadDataTpl">
    <tr><td class="text-center" colspan="10">查询中，请稍后</td></tr>
</script>
<script type="text/html" id="rowTpl">
    <tr>
    <tr>
        <td>
            <input type="checkbox" class="ids" name="ids" value="">
        </td>
        <td class="index"></td>
        <td class="platform"></td>
        <td class="url">
            <span title=""></span>
        </td>
        <td class="status"></td>
        <td class="wechat"></td>
        <td class="times"></td>
        <td class="visitCount"></td>
        <td class="createDate"></td>
        <td>
            <a href="${ctx}/front/article/{id}/update" class="btn btn-sm btn-success btn-update">
                修改
            </a>
        </td>
    </tr>
    </tr>
</script>
<script src="${ctx}/js/jquery.gritter.js"></script>
<script src="${ctx}/js/bootstrap-select.min.js"></script>
<script>
    var loadData = $('#loadDataTpl').html();
    var notData = $('#notDataTpl').html();
    $(document).ready(function() {
        App.init();
        $('#platform').find('option[value="瞎转"]').attr('selected', true);
        query();
        $('#platform').change(function() {
            query();
        });
        $('#btn-query').click(function() {
            query();
        });
        $('#ids').click(function() {
            var checked = $(this).prop('checked');
            $('input[name="ids"]').prop('checked', checked);
        });
        $('body').on('click', 'table tbody > tr', function() {
            var $target = $(this).find('input[type="checkbox"]');
            var checked = $target.attr('checked');
            $target.prop('checked', !checked);
            $(this).toggleClass('tr-active');
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
                            query();
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
        $('#btn-update').click(function() {
            var ids = $('input[name="ids"]:checked').serialize();
            if (!ids || ids.length == 0) {
                alert('请先勾选要删除的数据');
                return false;
            }
            var url = '${ctx}/front/article/update?' + ids;
            $.post(url).done(function(resp) {
                $('#remove-modal').modal('hide');
                if (resp.code == 1000) {
                    $.gritter.add({
                        title: '成功提示~',
                        text: resp.msg,
                        class_name: 'gritter-light',
                        time: 2000,
                        after_close: function() {
                            query();
                        }
                    });
                } else {
                    $.gritter.add({
                        title: '更新失败',
                        text: resp.msg,
                        time: 2000
                    });
                }
            }).fail(function(){
                $('#remove-modal').modal('hide');
                $.gritter.add({
                    title: '更新失败',
                    text: '系统繁忙，请稍后再试吧~~',
                    time: 2000
                });
            });
        });
    });

    function query() {
        $('#articleTable').html(loadData);
        // 获取所有链接
        var platform = $('#platform').val() || '瞎转';
        var type = '${type}';

        $.get('${ctx}/front/article/list', {
            'platform': platform,
            'type': type
        }).done(function(resp) {
            if (resp.code == 1000) {
                $('#articleTable').html('');
                var articles = resp.data;
                if (articles.length == 0) {
                    $('#articleTable').html(notData);
                }
                for (var i = 0; i < articles.length; i++) {
                    var article = articles[i];
                    var row = $('#rowTpl').html();
                    var $row = $(row);
                    $row.find('.ids').val(article.id);
                    $row.find('.index').text(i + 1);
                    $row.find('.platform').text(article.platform);
                    $row.find('.url').find('span').attr('title', article.url).text(article.shortUrl);
                    if (article.active) {
                        $row.find('.status').html('<span class="label label-success">运行中</span>');
                    } else {
                        $row.find('.status').html('<span class="label label-danger">已暂停</span>');
                    }
                    $row.find('.wechat').text(article.wechat);
                    $row.find('.times').text(article.times);
                    $row.find('.visitCount').text(article.visitCount);
                    var time = article.createDate;
                    var date = new Date();
                    date.setTime(time);
                    $row.find('.createDate').text(date.toLocaleDateString());
                    var href = $row.find('.btn-update').attr('href');
                    href = href.replace(/{id}/, article.id);
                    $row.find('.btn-update').attr('href', href);
                    $('#articleTable').append($row);
                }
            } else {
                alert(resp.msg);
            }
        });
    }

</script>
</html>