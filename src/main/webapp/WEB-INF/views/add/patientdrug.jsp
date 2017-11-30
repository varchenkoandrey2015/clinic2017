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
        $('#drugId').select2();
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
    <form name="addPatientDrugForm" method="POST" action="/addpatientdrug">
        <div class="fields-container">
            <input type="hidden" name="patientDrugId" value="${patientDrugId}"/>
            <div class="field">
                <s:message code="prescribings.name"/>
                <select class="input-field" name="drugId" id="drugId">
                    <c:forEach items="${allDrugs}" var="drug">
                        <c:choose>
                            <c:when test="${drug.drugId==patientDrugDrug.drugId}">
                                <option selected value="${drug.drugId}"> ${drug.name} </option>
                            </c:when>
                            <c:otherwise>
                                <option value="${drug.drugId}"> ${drug.name} </option>
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
                <s:message code="prescribings.days"/>
                <input class="input-field" type="text" name="days" value="${days}"/>
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

