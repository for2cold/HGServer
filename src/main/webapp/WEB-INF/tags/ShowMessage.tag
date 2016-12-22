<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="msg" type="java.lang.String" required="true" description="信息内容" %>
<%@ attribute name="type" type="java.lang.String" required="true" description="信息类型" %>
<c:if test="${not empty msg}">
<c:set var="title" value="Success" />
<c:set var="className" value="alert-success" />
<c:choose>
<c:when test="${type eq 'error'}">
    <c:set var="title" value="Error" />
    <c:set var="className" value="alert-danger" />
</c:when>
<c:when test="${type eq 'success'}">
    <c:set var="title" value="Success" />
    <c:set var="className" value="alert-success" />
</c:when>
<c:when test="${type eq 'warning'}">
    <c:set var="title" value="Warning" />
    <c:set var="className" value="alert-warning" />
</c:when>
<c:when test="${type eq 'info'}">
    <c:set var="title" value="Info" />
    <c:set var="className" value="alert-info" />
</c:when>
</c:choose>
<div class="alert ${className} fade in">
    <strong>${title}!</strong>
    ${msg}
    <span class="close" data-dismiss="alert">&times;</span>
</div>
</c:if>