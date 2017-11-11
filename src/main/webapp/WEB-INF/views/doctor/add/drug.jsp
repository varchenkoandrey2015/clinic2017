<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<html>
<head>
    <title><s:message code="add.drug.title"/></title>
    <link href="${pageContext.request.contextPath}/resources/css/page_style.css" rel="stylesheet" >
    <link href="${pageContext.request.contextPath}/resources/css/logo_style.css" rel="stylesheet" >
</head>
<body>
    <div class="container">
        <div class="header" align="left">
            <%@include file="../../../views/elements/header_with_logout.jsp" %>
        </div>
        <a href="/choosepatient"><s:message code="patients.title"/></a>>
        <a href="/doctormenu"><s:message code="menu.title"/></a>><s:message code="add.drug.title"/><br/>
        <div align="center">
            <form name="addDrugForm" method="POST" action="/adddrug">
                <s:message code="add.prescribing.entername"/><br />
                <input type="text" name="drugName" value="" />
                <s:message var="button" code="common.submit"/>
                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
                <input type="submit" value="${button}" /> <br />
                ${operationMessage}  <br />
            </form>
        </div>
        <div class="footer" align="center">
            <%@include file="../../../views/elements/footer.jsp" %>
        </div>
    </div>
</body>
</html>
