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
<script type="text/javascript">
    $(document).ready(function () {
        $('.diagnosisesSelect').select2();
    });
</script>
<div class="container">
    <div class="header" align="left">
        <%@include file="../elements/header_with_logout.jsp" %>
    </div>

    <div align="center">
        <form name="addPatientForm" method="POST" action="/addpatient">
            <table>
                <tr>
                    <td><s:message code="patients.fullname"/></td>
                    <td><input type="text" name="patientName" value=""/></td>
                </tr>
                <tr>
                    <td><s:message code="patients.gender"/></td>
                    <td><input type="text" name="patientGender" value=""/></td>
                </tr>
                <tr>
                    <td><s:message code="patients.address"/></td>
                    <td><input type="text" name="patientAddress" value=""/></td>
                </tr>
                <tr>
                    <td><s:message code="patients.email"/></td>
                    <td><input type="text" name="patientEmail" value=""/></td>
                </tr>
                <tr>
                    <td><s:message code="menu.diagonisises"/></td>
                    <td>
                        <select class="diagnosisesSelect" style="width: 100%" name="patientDiagnosises" multiple="multiple">
                            <c:forEach var="diagnosis" items="${allDiagnosises}">
                                <option value="${diagnosis.id}">${diagnosis.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>

            <%--<div class="patient-field">--%>
                <%--<s:message code="menu.drugs"/>--%>
                <%--<select class="diagnosisesSelect" multiple="multiple">--%>
                    <%--<c:forEach var="diagnosis" items="${patientDiagnosises}">--%>
                        <%--<option value="${diagnosis.id}">${diagnosis.name}</option>--%>
                    <%--</c:forEach>--%>
                <%--</select>--%>
            <%--</div>--%>
            <%--<div class="patient-field">--%>
                <%--<s:message code="menu.medprocedures"/>--%>
                <%--<input type="text" name="medprocedures" value=""/>--%>
            <%--</div>--%>
            <%--<div class="patient-field">--%>
                <%--<s:message code="menu.surgeries"/>--%>
                <%--<input type="text" name="surgeries" value=""/>--%>
            <%--</div>--%>

            <s:message var="button" code="common.submit"/>
            <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                   value="<c:out value="${_csrf.token}"/>"/>
            <input type="submit" value="${button}"/>
            ${operationMessage}
        </form>
    </div>
    <div class="footer" align="center">
        <%@include file="../elements/footer.jsp" %>
    </div>
</div>
</body>
</html>
