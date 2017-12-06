<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="by.training.nc.dev5.clinic.services.IDiagnosisService" %>
<%@ page import="by.training.nc.dev5.clinic.entities.PatientDiagnosis" %>
<%@ page import="by.training.nc.dev5.clinic.services.IDrugService" %>
<%@ page import="by.training.nc.dev5.clinic.services.IMedProcedureService" %>
<%@ page import="by.training.nc.dev5.clinic.entities.PatientDrug" %>
<%@ page import="by.training.nc.dev5.clinic.entities.PatientMedProcedure" %>
<%@ page import="by.training.nc.dev5.clinic.entities.Patient" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<html>
<head>
    <title>Report</title>
    <link href="${pageContext.request.contextPath}/resources/css/page_style.css" rel="stylesheet">
</head>
<body>
<%
    ApplicationContext aC = RequestContextUtils.getWebApplicationContext(request);
    IDiagnosisService diagnosisService = (IDiagnosisService) aC.getBean("diagS");
    IDrugService drugService = (IDrugService) aC.getBean("drugS");
    IMedProcedureService medProcedureService = (IMedProcedureService) aC.getBean("procS");

%>
<div class="header" align="left">
    <%@include file="elements/header.jsp" %>
</div>
<div class="patient-report">
    <div class="text-large">Amount of patients: ${patientsList.size()}</div>
</div>
<c:forEach items="${patientsList}" var="patient">
    <div class="patient-report">
        <div class="text-large">${patient.firstName} ${patient.middleName} ${patient.lastName}</div>
        <div class="patient-info-report">
            <div class="button-row"><div class="text-large">Gender: </div>${patient.gender}</div>
            <div class="button-row">  <div class="text-large">Address: </div>${patient.address}</div>
            <div class="button-row"><div class="text-large">Phone: </div>${patient.phone}</div>

            <div class="report-block">
                <div class="text-large">Diagnosises</div>
                <div class="report-row">
                    <div class="report-item"><u>Name</u></div>
                    <div class="report-item"><u>Description</u></div>
                    <div class="report-item"><u>Start Date</u></div>
                    <div class="report-item"><u>End Date</u></div>
                </div>
                <c:forEach var="patientDiagnosis" items="${patient.patientDiagnoses}">
                    <%PatientDiagnosis patientDiagnosis = (PatientDiagnosis) pageContext.getAttribute("patientDiagnosis");%>
                    <div class="report-row">
                        <div class="report-item">
                            <c:out value="<%=diagnosisService.getById(patientDiagnosis.getDiagnosis().getDiagnosisId()).getName()%>"/>
                        </div>
                        <div class="report-item">
                            <c:out value="<%=diagnosisService.getById(patientDiagnosis.getDiagnosis().getDiagnosisId()).getDescription()%>"/>
                        </div>
                        <div class="report-item">
                            <c:out value="${ patientDiagnosis.startDate }"/>
                        </div>
                        <div class="report-item">
                            <c:out value="${ patientDiagnosis.endDate }"/>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="report-block">
                <div class="text-large">Drugs</div>
                <div class="report-row">
                    <div class="report-item"><u>Name</u></div>
                    <div class="report-item"><u>Description</u></div>
                    <div class="report-item"><u>Start Date</u></div>
                    <div class="report-item"><u>Days</u></div>
                </div>
                <c:forEach var="patientDrug" items="${patient.patientDrugs}">
                    <%PatientDrug patientDrug = (PatientDrug) pageContext.getAttribute("patientDrug");%>
                    <div class="report-row">
                        <div class="report-item">
                            <c:out value="<%=drugService.getById(patientDrug.getDrug().getDrugId()).getName()%>"/>
                        </div>
                        <div class="report-item">
                            <c:out value="<%=drugService.getById(patientDrug.getDrug().getDrugId()).getDescription()%>"/>
                        </div>
                        <div class="report-item">
                            <c:out value="${ patientDrug.startDate }"/>
                        </div>
                        <div class="report-item">
                            <c:out value="${ patientDrug.days }"/>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="report-block">
                <div class="text-large">Procedures</div>
                <div class="report-row">
                    <div class="report-item"><u>Name</u></div>
                    <div class="report-item"><u>Description</u></div>
                    <div class="report-item"><u>Start Date</u></div>
                    <div class="report-item"><u>Count</u></div>
                </div>
                <c:forEach var="patientMedProcedure" items="${patient.patientMedProcedures}">
                    <%PatientMedProcedure patientMedProcedure = (PatientMedProcedure) pageContext.getAttribute("patientMedProcedure");%>
                    <div class="report-row">
                        <div class="report-item">
                            <c:out value="<%=medProcedureService.getById(patientMedProcedure.getMedProcedure().getMedProcedureId()).getName()%>"/>
                        </div>
                        <div class="report-item">
                            <c:out value="<%=medProcedureService.getById(patientMedProcedure.getMedProcedure().getMedProcedureId()).getDescription()%>"/>
                        </div>
                        <div class="report-item">
                            <c:out value="${ patientMedProcedure.startDate }"/>
                        </div>
                        <div class="report-item">
                            <c:out value="${ patientMedProcedure.count }"/>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</c:forEach>

<div class="footer" align="center">
    <%@include file="elements/footer.jsp" %>
</div>

</body>
</html>
