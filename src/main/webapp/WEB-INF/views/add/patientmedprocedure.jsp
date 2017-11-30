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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.datetimepicker.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.datetimepicker.full.min.js"></script>
</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
        $('#medProcedureId').select2();
        $('#startDate').datetimepicker({
            timepicker:false,
            format:'d.m.Y'
        });
    });
</script>
<div class="container">
    <div class="header" align="left">
        <%@include file="../elements/header_with_logout.jsp" %>
    </div>
    <form name="addPatientMedProcedureForm" method="POST" action="/addpatientmedprocedure">
        <div class="fields-container">
            <input type="hidden" name="patientMedProcedureId" value="${patientMedProcedureId}"/>
            <div class="field">
                <s:message code="prescribings.name"/>
                <select class="input-field" name="medProcedureId" id="medProcedureId">
                    <c:forEach items="${allMedProcedures}" var="medProcedure">
                        <c:choose>
                            <c:when test="${medProcedure.medProcedureId==patientMedProcedureMedProcedure.medProcedureId}">
                                <option selected value="${medProcedure.medProcedureId}"> ${medProcedure.name} </option>
                            </c:when>
                            <c:otherwise>
                                <option value="${medProcedure.medProcedureId}"> ${medProcedure.name} </option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
            <div class="field">
                <s:message code="prescribings.startdate"/>
                <input class="input-field" name="startDate" id="startDate" type="text" value="${startDate}">
            </div>
            <div class="field">
                <s:message code="prescribings.count"/>
                <input class="input-field" type="text" name="count" value="${count}"/>
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

