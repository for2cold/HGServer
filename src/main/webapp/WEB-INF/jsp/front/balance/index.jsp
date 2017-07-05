<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/common/taglibs.jspf" %>
<hg:PageHeader />
<link href="${ctx}/css/jquery.gritter.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-select.min.css">
<link rel="stylesheet" href="${ctx}/css/datetimepicker.css">
</head>
<body>
<!-- begin #page-loader -->
<div id="page-loader" class="fade in"><span class="spinner"></span></div>
<!-- end #page-loader -->
<!-- begin #page-container -->
<div id="page-container" class="fade page-sidebar-fixed page-header-fixed">
    <hg:Header />
    <c:if test="${empty type}">
        <hg:Sidebar id="balance"/>
    </c:if>
    <c:if test="${not empty type}">
        <hg:Sidebar id="balance-1"/>
    </c:if>
    <!-- begin #content -->
    <div id="content" class="content">
        <div id="data-loader" class="fade in hide"><span class="spinner" style="top: 40%;z-index:9999"></span></div>
        <div class="email-btn-row">
            <a href="javascript:;" id="btn-remove" class="btn btn-sm btn-danger"><i class="fa m-r-5 fa-trash"></i>删除</a>
            <a href="javascript:;" id="btn-exchange" class="btn btn-sm btn-success"><i class="fa fa-exchange m-r-5"></i> 存档</a>
            <a href="javascript:;" id="btn-import" class="btn btn-sm btn-success"><i class="fa fa-plus m-r-5"></i> 批量导入账号</a>
            <a href="javascript:;" id="btn-refresh" class="btn btn-sm btn-success"><i class="fa fa-plus m-r-5"></i> 批量更新链接</a>
            <a href="javascript:;" id="btn-update" class="btn btn-sm btn-success"><i class="fa fa-plus m-r-5"></i> 批量更新状态</a>
            <a href="javascript:;" id="btn-search" class="btn btn-sm btn-primary"><i class="fa fa-search m-r-5"></i> 筛选</a>
            <a href="javascript:;" id="btn-query" class="btn btn-sm btn-info"><i class="fa fa-refresh m-r-5"></i> 刷新</a>
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
                        <h2 class="panel-title">余额查询 <span id="refreshDate"></span></h2>
                    </div>
                    <div class="panel-body">
                        <form class="form-inline" id="searchForm" action="" method="POST">
                            <label class="control-label">平台：</label>
                            <div class="form-group m-r-4">
                                <select class="selectpicker" name="platform" id="platform" data-size="10" data-title="" data-style="btn-white" data-width="90px" data-title="">
                                    <c:if test="${fn:length(platforms) > 0}">
                                        <c:forEach items="${platforms}" var="item">
                                            <option value="${item}"<c:if test="${item eq platform}"> selected="selected"</c:if>>${item}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <label class="control-label">登记日期：</label>
                            <div class="form-group m-r-4">
                                <input type="text" class="form-control" id="period" name="periodDate" value="" />
                            </div>
                            <label class="control-label">今日总收入：<span id="todayIncome" class="text-success">0.00</span></label>
                        </form>
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>
                                    <input type="checkbox" id="ids">
                                </th>
                                <th>序号</th>
                                <th>账号</th>
                                <th>余额</th>
                                <th>今日收入</th>
                                <th>登记日期</th>
                                <th>状态</th>
                                <th>链接状态</th>
                                <th>提现</th>
                            </tr>
                            </thead>
                            <tbody id="balanceTable">

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
<div class="modal fade" id="record-modal">
    <div class="modal-dialog" style="width: 800px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">提现记录</h4>
            </div>
            <div class="modal-body">
                <div class="table-responsive" style="max-height: 500px">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>标题</th>
                            <th>状态</th>
                            <th>描述</th>
                            <th>日期</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <a href="javascript:;" class="btn btn-sm btn-white" data-dismiss="modal">关闭</a>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="import-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">批量导入账号</h4>
            </div>
            <div class="modal-body">
                <form id="importForm" method="post" class="form-horizontal form-bordered" data-parsley-validate="true" enctype="multipart/form-data">
                    <input type="hidden" name="type" value="${type}">
                    <div class="form-group">
                        <a href="${ctx}/dl/tpl/import_tpl.xls" target="_blank">下载批量导入账号模板</a>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-4 col-sm-4 required" for="file">文件</label>
                        <div class="col-md-6 col-sm-6">
                            <input class="form-control" type="file" id="file" name="file" required />
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="javascript:;" class="btn btn-sm btn-primary" id="btnImport">确定</a>
                <a href="javascript:;" class="btn btn-sm btn-white" data-dismiss="modal">关闭</a>
            </div>
        </div>
    </div>
</div>
<hg:PageFooter />
<script type="text/html" id="notDataTpl">
    <tr><td class="text-center" colspan="9">暂无数据</td></tr>
</script>
<script type="text/html" id="loadDataTpl">
    <tr><td class="text-center" colspan="9">查询中，请稍后</td></tr>
</script>
<script type="text/html" id="rowTpl">
    <tr>
        <td>
            <input type="checkbox" class="ids" name="ids" value="">
        </td>
        <td class="index"></td>
        <td class="username"></td>
        <td class="amount"></td>
        <td class="today"></td>
        <td class="date"></td>
        <td class="status"></td>
        <td class="artcleStatus"></td>
        <td>
            <a href="${ctx}/front/balance/{id}/withdraw" target="_blank" class="btn btn-sm btn-primary btn-withdraw" style="display: none;">提现</a>
            <a href="javascript:void(0);" class="btn btn-sm btn-info btn-update" data-id="">更新链接状态</a>
            <a style="display: none;" href="javascript:void(0);" class="btn btn-sm btn-info btn-record" data-id="">提现记录</a>
        </td>
    </tr>
</script>
<script src="${ctx}/js/jquery.gritter.js"></script>
<script src="${ctx}/js/bootstrap-select.min.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.min.js"></script>
<script>
    var loadData = $('#loadDataTpl').html();
    var notData = $('#notDataTpl').html();
    var datas;
    var platform;
    var todayIncome;
    $(document).ready(function() {
        App.init();
        $('#period').datetimepicker({
            format: 'yyyy-mm-dd',
            minView: 2,
            startView: 2,
            autoclose: true,
            endDate: new Date()
        });
        $('#platform').find('option[value="瞎转"]').attr('selected', true);
        query();
        $('#btn-query').click(function() {
            query();
        });
        $('#platform').change(function() {
            query();
        });
        // 导入
        $('#btn-import').click(function() {
            $('#import-modal').modal();
        });
        $('#btnImport').click(function() {
            var $btn = $(this);
            if ($btn.attr('disabled')) {
                return false;
            }
            $btn.attr('disabled', true).text('处理中，请稍后');
            var form = new FormData($('#importForm')[0]);
            $.ajax({
                url: '${ctx}/front/balance/import',
                type: 'post',
                data: form,
                processData:false,
                contentType:false,
                success: function(resp) {
                    $btn.attr('disabled', false).text('确定');
                    $('#import-modal').modal('hide');
                    if (resp.code == 1000) {
                        query();
                    } else {
                        alert(resp.msg);
                    }
                }
            });
        });
        $('#ids').click(function() {
            var checked = $(this).prop('checked');
            $('input[name="ids"]').prop('checked', checked);
        });
        $('body').on('click', '.ids', function() {
            return false;
        });
        $('body').on('click', 'table tbody > tr', function() {
            var $target = $(this).find('input[type="checkbox"]');
            var checked = $target.attr('checked');
            $target.prop('checked', !checked);
            $(this).toggleClass('tr-active');
        });
        $('body').on('click', '.btn-record', function() {
            var id = $(this).attr('data-id');
            var url = ctx + '/front/balance/' + id + '/record';
            $.get(url).done(function(resp) {
                if (resp.code == 1000) {
                    $('#record-modal').modal();
                    var datas = resp.data;
                    $('#record-modal table tbody').html('');
                    if (datas && datas.length > 0) {
                        for (var i = 0; i < datas.length; ++i) {
                            var record = datas[i];
                            var $tr = $('<tr><td class="index"></td><td class="note"></td><td class="status"></td><td class="desc"></td><td class="date"></td></tr>');
                            $tr.find('.index').text((i + 1));
                            $tr.find('.note').text(record.note);
                            $tr.find('.status').html(record.remark);
                            $tr.find('.desc').text(record.description);
                            $tr.find('.date').text(record.tixianDate);
                            $('#record-modal table tbody').append($tr);
                        }
                    } else {
                        $('#record-modal table tbody').html('<tr><td class="text-center" colspan="5">暂无记录</td></tr>');
                    }
                }
            });
        });
        $('#btn-update').click(function() {
            var ids = $('input[name="ids"]:checked').serialize();
            if (!ids || ids.length == 0) {
                alert('请先勾选要删除的数据');
                return false;
            }
            var url = '${ctx}/front/balance/updateStatus?' + ids;
            $.post(url).done(function(resp) {
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
        $('body').on('click', '.btn-update', function() {
            var id = $(this).attr('data-id');
            var $row = $(this).parents('tr');
            var url = '${ctx}/front/balance/' + id + '/update';
            $.post(url).done(function(resp) {
                if (resp.code == 1000) {
                    var status = $row.find('.artcleStatus').attr('data-status');
                    if (status == 0) {
                        $row.find('.artcleStatus').html('<span class="label label-success">运行中</span>');
                    } else if (status == 1) {
                        $row.find('.artcleStatus').html('<span class="label label-danger">已停止</span>');
                    }
                }
            });
        });
        // 删除
        $('#btn-remove').click(function() {
            var ids = $('input[name="ids"]:checked').serialize();
            if (!ids || ids.length == 0) {
                alert('请先勾选要删除的数据');
                return false;
            }
            if (confirm("您确定要删除已选择的数据？删除后不可恢复！")) {
                var url = '${ctx}/front/balance/remove?' + ids;
                $.post(url).done(function(resp) {
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
            }
        });
        // 存档
        $('#btn-exchange').click(function() {
            var ids = $('input[name="ids"]:checked').serialize();
            if (!ids || ids.length == 0) {
                alert('请先勾选要存档的数据');
                return false;
            }
            var url = '${ctx}/front/balance/update/date?' + ids;
            $.post(url).done(function(resp) {
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
                        title: '存档更新失败',
                        text: resp.msg,
                        time: 2000
                    });
                }
            }).fail(function(){
                $('#remove-modal').modal('hide');
                $.gritter.add({
                    title: '存档更新失败',
                    text: '系统繁忙，请稍后再试吧~~',
                    time: 2000
                });
            });
        });
        // 批量更新链接
        $('#btn-refresh').click(function() {
            var ids = $('input[name="ids"]:checked').serialize();
            if (!ids || ids.length == 0) {
                alert('请先勾选要更新链接的数据');
                return false;
            }
            var url = '${ctx}/front/balance/update/link?' + ids;
            $.post(url).done(function(resp) {
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
                        title: '链接更新失败',
                        text: resp.msg,
                        time: 2000
                    });
                }
            }).fail(function(){
                $('#remove-modal').modal('hide');
                $.gritter.add({
                    title: '链接更新失败',
                    text: '系统繁忙，请稍后再试吧~~',
                    time: 2000
                });
            });
        });
        // 筛选没跑满的数据
        $('#btn-search').click(function() {
            if (datas) {
                var results = [];
                for (var i = 0; i < datas.length; i++) {
                    var data = datas[i];
                    var today = parseFloat(data.today);
                    if (today < 1 && !data.date && data.artcleStatus == 0) {
                        results.push(data);
                    }
                }
                callback(results);
            }
        });
    });

    function query() {
        $('#balanceTable').html(loadData);

        // 获取所有账号余额
        var _platform = $('#platform').val() || '瞎转';
        var type = '${type}';
        var date = $('#period').val();

        $.get('${ctx}/front/balance/list', {
            'platform': _platform,
            'type': type,
            'date': date
        }).done(function(resp) {
            var date = new Date();
            date.setTime(resp.time);
            $('#refreshDate').text(date.toLocaleString());
            if (resp.code == 1000) {
                datas = resp.data;
                platform = _platform;
                callback(resp.data, platform);
            } else {
                alert(resp.msg);
            }
        });
    }

    function callback(datas, platform) {
        $('#balanceTable').html('');
        var balances = datas;
        if (balances.length == 0) {
            $('#balanceTable').html(notData);
        }
        todayIncome = 0;
        for (var i = 0; i < balances.length; i++) {
            var balance = balances[i];
            var row = $('#rowTpl').html();
            var $row = $(row);
            $row.find('.ids').val(balance.id);
            $row.find('.index').text(i + 1);
            $row.find('.username').text(balance.username);
            var amount = balance.amount;
            try {
                amount = parseFloat(amount);
                if (isNaN(amount)) {
                    amount = balance.amount;
                } else {
                    if (amount >= 20) {
                        amount = '<span class="text-success">' + amount + '</span>';
                    } else if (amount < 20 && amount >= 10) {
                        amount = '<span class="text-primary">' + amount + '</span>';
                    } else if (amount < 10 && amount >= 5) {
                        amount = '<span class="text-info">' + amount + '</span>';
                    } else if (amount == -1) {
                        amount = '<span class="text-danger">查询失败</span>';
                    }
                }
            }catch(e) {}
            today = parseFloat(balance.today);
            todayIncome += today;
            $row.find('.amount').html(amount);
            $row.find('.today').html(balance.today);
            $row.find('.date').html(balance.date);
            if (balance.block) {
                $row.find('.status').html('<span class="label label-danger">' + balance.block + '</span>');
            } else {
                $row.find('.status').html('<span class="label label-success">正常</span>');
            }
            var href = $row.find('.btn-withdraw').attr('href');
            href = href.replace(/{id}/, balance.id);
            $row.find('.btn-withdraw').attr('href', href);
            $row.find('.btn-record').attr('data-id', balance.id);
            $row.find('.btn-update').attr('data-id', balance.id);
            if ("瞎转" == platform || "无敌赚" == platform || "牛逼赚" == platform || "超人赚" == platform || "爱传" == platform) {
                $row.find('.btn-withdraw').show();
            }
            if (platform == '无敌赚') {
                $row.find('.btn-record').show();
            } else {
                $row.find('.btn-record').hide();
            }
            var artcleStatus = balance.artcleStatus;
            if (artcleStatus == 0) {
                $row.find('.artcleStatus').html('<span class="label label-danger">已暂停</span>');
            } else if (artcleStatus == 1) {
                $row.find('.artcleStatus').html('<span class="label label-success">运行中</span>');
            } else {
                $row.find('.artcleStatus').html('<span class="label label-warning">无链接</span>');
            }
            $row.find('.artcleStatus').attr('data-status', artcleStatus)
            $('#balanceTable').append($row);
        }
        if (platform) {
            $('#todayIncome').text(todayIncome.toFixed(2));
        }
    }
</script>
</html>