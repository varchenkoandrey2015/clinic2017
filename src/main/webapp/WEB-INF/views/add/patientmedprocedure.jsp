<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<html>
<head>
    <title><s:message code="add.title"/></title>
    <link href="${pageContext.request.contextPath}/resources/css/page_style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/select2.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/select2.min.css">
</head>
<body>
<div class="container">
    <div class="header" align="left">
        <%@include file="../elements/header_with_logout.jsp" %>
    </div>

    <form name="addMedProcedureForm" method="POST" action="/addmedprocedure">
        <div class="fields-container">
            <input type="hidden" name="medProcedureId" value="${medProcedureId}"/>
            <div class="field">
                <s:message code="prescribings.name"/>
                <input class="input-field" type="text" name="medProcedureName" value="${medProcedureName}"/>
            </div>
            <div class="field">
                <s:message code="prescribings.description"/>
                <input class="input-field" type="text" name="medProcedureDescription" value="${medProcedureDescription}"/>
            </div>

            <s:message var="button" code="common.submit"/>
            <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                   value="<c:out value="${_csrf.token}"/>"/>
            <input type="submit" value="${button}"/>
            ${operationMessage}
        </div>
    </form>

    <div class="footer" align="center">
        <%@include file="../elements/footer.jsp" %>
    </div>
</div>
</body>
</html>
