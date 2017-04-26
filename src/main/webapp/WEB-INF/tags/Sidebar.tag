<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="菜单节点" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- begin #sidebar -->
<div id="sidebar" class="sidebar">
    <!-- begin sidebar scrollbar -->
    <div data-scrollbar="true" data-height="100%">
        <!-- begin sidebar nav -->
        <ul class="nav">
            <li class="nav-header">功能菜单</li>
            <li class="${id eq 'phonedata' ? 'active' : ''}"><a href="${ctx}/front/phonedata/index"><i class="fa fa-database"></i> <span>手机数据</span></a></li>
            <li class="${id eq 'apk' ? 'active' : ''}"><a href="${ctx}/front/apk/index"><i class="fa fa-android"></i> <span>APK管理</span></a></li>
			<li class="${id eq 'script' ? 'active' : ''}"><a href="${ctx}/front/script/index"><i class="fa fa-terminal"></i> <span>脚本维护</span></a></li>
            <c:if test="${currentUser.id == 3}">
            <li class="${id eq 'article' ? 'active' : ''}"><a href="${ctx}/front/article/index"><i class="fa fa-rocket"></i> <span>链接管理</span></a></li>
            <li class="${id eq 'article-1' ? 'active' : ''}"><a href="${ctx}/front/article/index?type=1"><i class="fa fa-rocket"></i> <span>链接管理2</span></a></li>
            <li class="${id eq 'vpn' ? 'active' : ''}"><a href="${ctx}/front/vpn/index"><i class="fa fa-pied-piper"></i> <span>VPN管理</span></a></li>
            <li class="${id eq 'account' ? 'active' : ''}"><a href="${ctx}/front/account/index?periodDate=${nowMonth}"><i class="fa fa-user"></i> <span>账号管理</span></a></li>
            <li class="${id eq 'withdraw' ? 'active' : ''}"><a href="${ctx}/front/withdraw/index"><i class="fa fa-cny"></i> <span>提现记录</span></a></li>
            <li class="${id eq 'report' ? 'active' : ''}"><a href="${ctx}/front/report/index"><i class="fa fa-line-chart"></i> <span>数据报表</span></a></li>
            <li class="${id eq 'user' ? 'active' : ''}"><a href="${ctx}/front/user/index"><i class="fa fa-angellist"></i> <span>管理员管理</span></a></li>
            <!-- begin sidebar minify button -->
            <li><a href="javascript:;" class="sidebar-minify-btn" data-click="sidebar-minify"><i class="fa fa-angle-double-left"></i></a></li>
            </c:if>
            <!-- end sidebar minify button -->
        </ul>
        <!-- end sidebar nav -->
    </div>
</div>
    <!-- end sidebar scrollbar -->
<div class="sidebar-bg"></div>
<!-- end #sidebar -->