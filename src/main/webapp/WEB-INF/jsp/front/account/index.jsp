<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/common/taglibs.jspf" %>
<hg:PageHeader />
<link rel="stylesheet" href="${ctx}/css/jquery.edittable.min.css">
<link rel="stylesheet" href="${ctx}/css/bootstrap-select.min.css">
<link rel="stylesheet" href="${ctx}/css/datetimepicker.css">
<link href="${ctx}/css/jquery.gritter.css" rel="stylesheet" />
<style>
    .bootstrap-select{margin-bottom:0!important}
    .mobile {width: 120px!important}
    .withdraw_balance,.balance {width: 100px!important}
</style>
</head>
<body>
	<!-- begin #page-loader -->
	<div id="page-loader" class="fade in"><span class="spinner"></span></div>
	<!-- end #page-loader -->
	<!-- begin #page-container -->
	<div id="page-container" class="fade page-sidebar-fixed page-header-fixed">
        <hg:Header />
        <hg:Sidebar id="account"/>
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
                            <h4 class="panel-title">账号管理</h4>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline" id="searchForm" action="${ctx}/front/account/index" method="POST">
                                <label class="control-label">月份：</label>
                                <div class="form-group m-r-4">
                                    <input type="text" class="form-control" id="period" name="periodDate" value="${searchView.period}" />
                                </div>
                                <label class="control-label">状态：</label>
                                <div class="form-group m-r-4">
                                    <c:choose>
                                        <c:when test="${searchView.status == 'IDLE'}">
                                            <c:set var="idle" value="selected='selected'"/>
                                        </c:when>
                                        <c:when test="${searchView.status == 'RUNNING'}">
                                            <c:set var="running" value="selected='selected'"/>
                                        </c:when>
                                        <c:when test="${searchView.status == 'WITHDRAW'}">
                                            <c:set var="withdraw" value="selected='selected'"/>
                                        </c:when>
                                        <c:when test="${searchView.status == 'SIGNING'}">
                                            <c:set var="signing" value="selected='selected'"/>
                                        </c:when>
                                        <c:when test="${searchView.status == 'AUDITING'}">
                                            <c:set var="auditing" value="selected='selected'"/>
                                        </c:when>
                                        <c:when test="${searchView.status == 'RECEIVED'}">
                                            <c:set var="received" value="selected='selected'"/>
                                        </c:when>
                                    </c:choose>
                                    <select class="selectpicker statusVal" name="status" id="statusVal" data-size="10" data-style="btn-white" data-title="" data-width="110px">
                                        <option value="" data-class="btn-default"></option>
                                        <option value="IDLE" ${idle} data-class="btn-white">空闲中</option>
                                        <option value="RUNNING" ${running} data-class="btn-warning">运行中</option>
                                        <option value="WITHDRAW" ${withdraw} data-class="btn-primary">待提现</option>
                                        <option value="SIGNING" ${signing} data-class="btn-success">签到中</option>
                                        <option value="AUDITING" ${auditing} data-class="btn-danger">审核中</option>
                                        <option value="RECEIVED" ${received} data-class="btn-info">已到账</option>
                                    </select>
                                </div>
                                <label class="control-label">机器：</label>
                                <div class="form-group m-r-4">
                                    <select class="selectpicker" name="phone" id="phoneVal" data-size="10" data-style="btn-white" data-width="90px" data-title="">
                                         <option value=""></option>
                                         <option value="K1" <c:if test="${searchView.phone == 'K1'}">selected='selected'</c:if>>K1</option>
                                         <option value="K2" <c:if test="${searchView.phone == 'K2'}">selected='selected'</c:if>>K2</option>
                                         <option value="K3" <c:if test="${searchView.phone == 'K3'}">selected='selected'</c:if>>K3</option>
                                         <option value="K4" <c:if test="${searchView.phone == 'K4'}">selected='selected'</c:if>>K4</option>
                                         <option value="K5" <c:if test="${searchView.phone == 'K5'}">selected='selected'</c:if>>K5</option>
                                         <option value="K6" <c:if test="${searchView.phone == 'K6'}">selected='selected'</c:if>>K6</option>
                                         <option value="K7" <c:if test="${searchView.phone == 'K7'}">selected='selected'</c:if>>K7</option>
                                         <option value="K8" <c:if test="${searchView.phone == 'K8'}">selected='selected'</c:if>>K8</option>
                                         <option value="K9" <c:if test="${searchView.phone == 'K9'}">selected='selected'</c:if>>K9</option>
                                         <option value="K10" <c:if test="${searchView.phone == 'K10'}">selected='selected'</c:if>>K10</option>
                                         <option value="K11" <c:if test="${searchView.phone == 'K11'}">selected='selected'</c:if>>K11</option>
                                     </select>
                                </div>
                                <label class="control-label">支付宝：</label>
                                <div class="form-group m-r-4">
                                    <select class="selectpicker" name="alipay" id="alipayVal" data-size="10" data-title="" data-style="btn-white" data-width="90px" data-title="">
                                         <option value=""></option>
                                         <option value="黄伟钢" <c:if test="${searchView.alipay == '黄伟钢'}">selected='selected'</c:if>>黄伟钢</option>
                                         <option value="张少芳" <c:if test="${searchView.alipay == '张少芳'}">selected='selected'</c:if>>张少芳</option>
                                         <option value="张超" <c:if test="${searchView.alipay == '张超'}">selected='selected'</c:if>>张超</option>
                                         <option value="张木坤" <c:if test="${searchView.alipay == '张木坤'}">selected='selected'</c:if>>张木坤</option>
                                         <option value="李俊峰" <c:if test="${searchView.alipay == '李俊峰'}">selected='selected'</c:if>>李俊峰</option>
                                         <option value="郑浩勇" <c:if test="${searchView.alipay == '郑浩勇'}">selected='selected'</c:if>>郑浩勇</option>
                                         <option value="余清增" <c:if test="${searchView.alipay == '余清增'}">selected='selected'</c:if>>余清增</option>
                                         <option value="张少尔" <c:if test="${searchView.alipay == '张少尔'}">selected='selected'</c:if>>张少尔</option>
                                         <option value="邱锦放" <c:if test="${searchView.alipay == '邱锦放'}">selected='selected'</c:if>>邱锦放</option>
                                     </select>
                                </div>
                            </form>
                            <h3>
                                <c:if test="${not empty searchView.periodDate}">
                                    <fmt:formatDate value="${searchView.periodDate}" pattern="yyyy年MM月"/>
                                </c:if>
                                <c:if test="${empty searchView.periodDate}">
                                    空闲
                                </c:if>账号
                                <c:if test="${not empty withdrawResult}">
                                    <span style="font-size: 18px;color:#00acac" class="text-primary">
                                    <span>总收入：<fmt:formatNumber value="${withdrawResult.amount}" pattern="#,#00.0#"/>，</span>
                                    <span>${withdrawResult.days}天，</span>
                                    <span><fmt:formatNumber value="${withdrawResult.averageAmount}" pattern="#,#00.0#"/>/天</span>
                                    </span>
                                </c:if>
                                <a class="btn btn-success" id="reportBtn" href="javascript:void(0);">收入报表</a>
                            </h3>
                            <div id="accountTable"></div>
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
	<script type="text/html" id="phoneTpl">
        <div>
            <select class="selectpicker phoneVal" data-size="10" data-style="btn-white" data-width="90px">
                <option value="K1">K1</option>
                <option value="K2">K2</option>
                <option value="K3">K3</option>
                <option value="K4">K4</option>
                <option value="K5">K5</option>
                <option value="K6">K6</option>
                <option value="K7">K7</option>
                <option value="K8">K8</option>
                <option value="K9">K9</option>
                <option value="K10">K10</option>
                <option value="K11">K11</option>
            </select>
        </div>
	</script>
	<script type="text/html" id="taskTpl">
        <div>
            <select class="selectpicker taskVal" data-size="10" data-title="" data-style="btn-white" data-width="110px">
                <option value="NONE"></option>
                <option value="NORMAL_TASK">普通任务</option>
                <option value="BAIDU_TASK">百度任务</option>
            </select>
        </div>
    </script>
	<script type="text/html" id="alipayTpl">
        <div>
            <select class="selectpicker alipayVal" data-size="10" data-style="btn-white" title="" data-width="110px">
                <option value=""></option>
                <option value="黄伟钢">黄伟钢</option>
                <option value="张少芳">张少芳</option>
                <option value="张超">张超</option>
                <option value="张木坤">张木坤</option>
                <option value="李俊峰">李俊峰</option>
                <option value="郑浩勇">郑浩勇</option>
                <option value="余清增">余清增</option>
                <option value="张少尔">张少尔</option>
                <option value="邱锦放">邱锦放</option>
            </select>
        </div>
    </script>
    <script type="text/html" id="statusTpl">
        <div>
            <select class="selectpicker statusVal" data-size="10" data-style="btn-white" data-width="110px">
                <option value="IDLE" data-class="btn-white">空闲中</option>
                <option value="RUNNING" data-class="btn-warning">运行中</option>
                <option value="WITHDRAW" data-class="btn-primary">待提现</option>
                <option value="SIGNING" data-class="btn-success">签到中</option>
                <option value="AUDITING" data-class="btn-danger">审核中</option>
                <option value="RECEIVED" data-class="btn-info">已到账</option>
            </select>
        </div>
    </script>
    <div class="modal fade" id="remove-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">删除警告</h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger m-b-0">
                        <h4><i class="fa fa-info-circle"></i> 您确定要删除该账号吗？删除后可找管理员进行恢复！</h4>
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
    <div class="modal fade" id="report-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">收入报表</h4>
                </div>
                <div class="modal-body">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>日期</th>
                                    <th>金额</th>
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
</body>
<hg:PageFooter />
<script src="${ctx}/js/jquery.edittable.js"></script>
<script src="${ctx}/js/bootstrap-select.min.js"></script>
<script src="${ctx}/js/jquery.gritter.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.min.js"></script>
<script>
    $(document).ready(function() {
        App.init();
    });
    window.onbeforeunload = function(){
        return "离开前，请确认数据已保存！";
    }
</script>
<script type="text/javascript">
var data = ${accounts};var ctx = '${ctx}';
</script>
<script src="${ctx}/js/modules/account.js"></script>
</html>