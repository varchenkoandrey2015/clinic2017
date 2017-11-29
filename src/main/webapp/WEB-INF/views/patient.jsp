<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="by.training.nc.dev5.clinic.services.IDiagnosisService" %>
<%@ page import="by.training.nc.dev5.clinic.entities.PatientDiagnosis" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<html>
<head>
    <title><s:message code="patient.prescribings"/></title>
    <link href="${pageContext.request.contextPath}/resources/css/page_style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/datatables.css">
    <script type="text/javascript" charset="utf8"
            src="${pageContext.request.contextPath}/resources/js/datatables.js"></script>
</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
        $('#diagnosisTable').DataTable();
        $('#drugTable').DataTable();
        $('#medProcedureTable').DataTable();
        $(".tabs").lightTabs();
    });
    (function ($) {
        jQuery.fn.lightTabs = function (options) {

            var createTabs = function () {
                tabs = this;
                i = 0;

                showPage = function (i) {
                    $(tabs).children("div").children("div").hide();
                    $(tabs).children("div").children("div").eq(i).show();
                    $(tabs).children("ul").children("li").removeClass("active");
                    $(tabs).children("ul").children("li").eq(i).addClass("active");
                }

                showPage(0);

                $(tabs).children("ul").children("li").each(function (index, element) {
                    $(element).attr("data-page", i);
                    i++;
                });

                $(tabs).children("ul").children("li").click(function () {
                    showPage(parseInt($(this).attr("data-page")));
                });
            };
            return this.each(createTabs);
        };
    })(jQuery);
</script>
<%
    ApplicationContext aC = RequestContextUtils.getWebApplicationContext(request);
    IDiagnosisService diagnosisService = (IDiagnosisService) aC.getBean("diagS");
%>
<div class="header" align="left">
    <%@include file="elements/header_with_logout.jsp" %>
</div>

<div class="text-large">
    <c:out value="${patientFirstName} ${patientMiddleName} ${patientLastName}"/>
</div>
<div class="tabs">
    <ul>
        <li><s:message code="menu.diagonisises"/></li>
        <li><s:message code="menu.drugs"/></li>
        <li><s:message code="menu.medprocedures"/></li>
    </ul>

    <div class="container">
        <div class="container-item">
            <form name="diagnosisListForm" method="POST">
                <table id="diagnosisTable" class="display">
                    <thead>
                    <tr>
                        <th><s:message code="prescribings.name"/></th>
                        <th><s:message code="prescribings.description"/></th>
                        <th><s:message code="prescribings.startdate"/></th>
                        <th><s:message code="prescribings.enddate"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="patientDiagnosis" items="${patientDiagnosises}">
                        <%PatientDiagnosis patientDiagnosis = (PatientDiagnosis) pageContext.getAttribute("patientDiagnosis");%>
                        <tr>
                            <td>
                                <label>
                                    <input type="radio" name="patientDiagnosisId"
                                           value="${ patientDiagnosis.patientDiagnosisId }"/>
                                    <c:out value="<%=diagnosisService.getById(patientDiagnosis.getDiagnosis().getDiagnosisId()).getName()%>"/>
                                </label>
                            </td>
                            <td>
                                <c:out value="<%=diagnosisService.getById(patientDiagnosis.getDiagnosis().getDiagnosisId()).getDescription()%>"/>
                            </td>
                            <td>
                                <c:out value="${ patientDiagnosis.startDate }"/>
                            </td>
                            <td>
                                <c:out value="${ patientDiagnosis.endDate }"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                       value="<c:out value="${_csrf.token}"/>"/>
                <div class="button-row">
                    <div class="button-item">
                        <a class="button" href="/addpatientdiagnosis"><s:message code="add.title"/></a>
                    </div>
                    <div class="button-item">
                        <s:message var="button" code="edit.title"/>
                        <input class="button" type="submit" value="${button}"
                               onclick= "document.diagnosisListForm.action = '/editpatientdiagnosis';
                               document.diagnosisListForm.method = 'GET';
                               document.diagnosisListForm.submit()"/>
                    </div>

                    <%--<div class="button-item">--%>
                    <%--<s:message var="button" code="del.title"/>--%>
                    <%--<input class="button" type="submit" value="${button}"--%>
                    <%--onclick="document.diagnosisListForm.action = '/deldiagnosis';document.diagnosisListForm.submit()"/>--%>
                    <%--</div>--%>
                </div>
            </form>
        </div>

        <div class="container-item">
            <form name="drugListForm" method="POST">
                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                       value="<c:out value="${_csrf.token}"/>"/>
                <%--<table id="drugTable" class="display">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                <%--<th><s:message code="prescribings.name"/></th>--%>
                <%--<th><s:message code="prescribings.startdate"/></th>--%>
                <%--<th><s:message code="prescribings.days"/></th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
                <%--<tbody>--%>
                <%--<c:forEach var="drug" items="${patientDrugs}">--%>
                <%--<tr>--%>
                <%--<td>--%>
                <%--<label>--%>
                <%--<input type="radio" name="drugId" value="${ drug.drugId }"/>--%>
                <%--<c:out value="${ drug.name }"/>--%>
                <%--</label>--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--<c:out value="${ drug.description }"/>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--</c:forEach>--%>
                <%--</tbody>--%>
                <%--</table>--%>
                <%--<div class="button-row">--%>
                <%--<div class="button-item">--%>
                <%--<a class="button" href="/adddrug"><s:message code="add.title"/></a>--%>
                <%--</div>--%>
                <%--<div class="button-item">--%>
                <%--<s:message var="button" code="edit.title"/>--%>
                <%--<input class="button" type="submit" value="${button}"--%>
                <%--onclick="document.drugListForm.action = '/editdrug';document.drugListForm.submit()"/>--%>
                <%--</div>--%>
                <%--<div class="button-item">--%>
                <%--<s:message var="button" code="del.title"/>--%>
                <%--<input class="button" type="submit" value="${button}"--%>
                <%--onclick="document.drugListForm.action = '/deldrug';document.drugListForm.submit()"/>--%>
                <%--</div>--%>
                <%--</div>--%>
            </form>
        </div>

        <div class="container-item">
            <form name="medProcedureListForm" method="POST">
                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                       value="<c:out value="${_csrf.token}"/>"/>
                <%--<table id="medProcedureTable" class="display">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                <%--<th><s:message code="prescribings.name"/></th>--%>
                <%--<th><s:message code="prescribings.startdate"/></th>--%>
                <%--<th><s:message code="prescribings.count"/></th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
                <%--<tbody>--%>
                <%--<c:forEach var="medProcedure" items="${patientMedProcedures}">--%>
                <%--<tr>--%>
                <%--<td>--%>
                <%--<label>--%>
                <%--<input type="radio" name="medProcedureId" value="${ medProcedure.medProcedureId }"/>--%>
                <%--<c:out value="${ medProcedure.name }"/>--%>
                <%--</label>--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--<c:out value="${ medProcedure.description }"/>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--</c:forEach>--%>
                <%--</tbody>--%>
                <%--</table>--%>
                <%--<div class="button-row">--%>
                <%--<div class="button-item">--%>
                <%--<a class="button" href="/addmedprocedure"><s:message code="add.title"/></a>--%>
                <%--</div>--%>
                <%--<div class="button-item">--%>
                <%--<s:message var="button" code="edit.title"/>--%>
                <%--<input class="button" type="submit" value="${button}"--%>
                <%--onclick="document.medProcedureListForm.action = '/editmedprocedure';document.medProcedureListForm.submit()"/>--%>
                <%--</div>--%>
                <%--<div class="button-item">--%>
                <%--<s:message var="button" code="del.title"/>--%>
                <%--<input class="button" type="submit" value="${button}"--%>
                <%--onclick="document.medProcedureListForm.action = '/delmedprocedure';document.medProcedureListForm.submit()"/>--%>
                <%--</div>--%>
                <%--</div>--%>
            </form>
        </div>
    </div>
</div>

${operationMessage}<br/>
</div>

<div class="footer" align="center">
    <%@include file="elements/footer.jsp" %>
</div>

</body>
</html>
