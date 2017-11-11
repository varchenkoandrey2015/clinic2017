<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sform" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<html>
<head>
    <title><s:message code="registration.title"/></title>
    <link href="${pageContext.request.contextPath}/resources/css/page_style.css" rel="stylesheet" >
    <link href="${pageContext.request.contextPath}/resources/css/logo_style.css" rel="stylesheet" >
</head>
<body>
    <div class="container">
        <div class="header" align="left">
            <%@include file="../views/elements/header.jsp" %>
        </div>
        <a href="/index"><s:message code="index.title"/></a>><s:message code="registration.title"/><br/>
        <div align="center">
            <sform:form method="post" modelAttribute="user" action="/registration">
                <table>
                    <tr>
                        <td><s:message code="common.login"/></td>
                        <td><sform:input type="text" path="login" value="" size="20"/></td>
                    </tr>
                    <tr>
                        <td><s:message code="common.password"/></td>
                        <td><sform:input type="password" path="password" value="" size="20" /></td>
                    </tr>
                    <tr>
                        <td><s:message code="registration.accesslevel"/></td>
                        <td>
                            <sform:radiobutton path="accessLevel" value="DOCTOR"/><s:message code="registration.doctor"/><br/>
                            <sform:radiobutton path="accessLevel" value="NURSE"/><s:message code="registration.nurse"/>
                        </td>
                    </tr>
                </table>
                <sform:button><s:message code="common.submit"/></sform:button><br />
                ${operationMessage}<br />
            </sform:form>
        </div>
        <div class="footer" align="center">
            <%@include file="../views/elements/footer.jsp" %>
        </div>
    </div>
</body>
</html>