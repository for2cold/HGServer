<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="显示的值" %>
<%@ attribute name="replace" type="java.lang.String" required="false" description="替代值，优先于先截取的值" %>
<%@ attribute name="length" type="java.lang.Integer" required="true" description="显示长度" %>
<%
String showText = value;
if (StringUtils.isNotEmpty(replace)) {
	showText = "";
	for (int i = 0; i < length; ++i) {
		showText += replace;
	}
} else {
	if (value != null && value.length() > length) {
		showText = value.substring(0, length) + "...";
	}
}
String showHtml = "<span data-rel=\"tooltip\" title=\"" + value + "\">" + showText + "</span>";
out.write(showHtml);
%>