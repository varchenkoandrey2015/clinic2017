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
    <form name="addPatientForm" method="POST" action="/addpatient">
        <div class="fields-container">
            <input type="hidden" name="patientId" value="${patientId}"/>
            <div class="field">
                <s:message code="patients.firstName"/>
                <input class="input-field" type="text" name="patientFirstName" value="${patientFirstName}"/>
            </div>
            <div class="field">
                <s:message code="patients.middleName"/>
                <input class="input-field" type="text" name="patientMiddleName" value="${patientMiddleName}"/>
            </div>
            <div class="field">
                <s:message code="patients.lastName"/>
                <input class="input-field" type="text" name="patientLastName" value="${patientLastName}"/>
            </div>

            <div class="field">
                <s:message code="patients.gender"/>
                <input class="input-field" type="text" name="patientGender" value="${patientGender}"/>
            </div>
            <div class="field">
                <s:message code="patients.address"/>
                <input class="input-field" type="text" name="patientAddress" value="${patientAddress}"/>
            </div>
            <div class="field">
                <s:message code="patients.phone"/> (Example:+37529XXXXXXX)
                <input class="input-field" type="text" name="patientPhone" value="${patientPhone}"/>
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
