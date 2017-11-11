<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title><s:message code="error.title"/></title>
    <link href="${pageContext.request.contextPath}/resources/css/page_style.css" rel="stylesheet" >
    <link href="${pageContext.request.contextPath}/resources/css/logo_style.css" rel="stylesheet" >
</head>
<body>
    <div class="container">
        <div class="header" align="left">
            <%@include file="../../views/elements/header.jsp" %>
        </div>
        <a href="/index"><s:message code="index.title"/></a>><s:message code="error.title"/><br/>
        <div>
            <s:message code="error.text"/><br/>
        </div>
        <div class="footer" align="center">
            <%@include file="../../views/elements/footer.jsp" %>
        </div>
    </div>
</body>
</html>