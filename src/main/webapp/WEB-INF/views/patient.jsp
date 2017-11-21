<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:message code="menu.title"/></title>
    <link href="${pageContext.request.contextPath}/resources/css/page_style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/datatables.css">
    <script type="text/javascript" charset="utf8"
            src="${pageContext.request.contextPath}/resources/js/datatables.js"></script>
</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
        $('#patientTable').DataTable();
        $('#diagnosisTable').DataTable();
        $('#drugTable').DataTable();
        $('#medProcedureTable').DataTable();
        $('#surgeryTable').DataTable();
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
<div class="header" align="left">
    <%@include file="elements/header_with_logout.jsp" %>
</div>

<div class="tabs">
    <ul>
        <li><s:message code="patients.title"/></li>
        <sec:authorize access="hasRole('ROLE_DOCTOR')">
            <li><s:message code="menu.diagonisises"/></li>
            <li><s:message code="menu.drugs"/></li>
            <li><s:message code="menu.medprocedures"/></li>
            <li><s:message code="menu.surgeries"/></li>
        </sec:authorize>
    </ul>

    <div class="container">
        <div class="container-item">
            <form name="choosePatientForm" method="POST" action="/delpatient">
                <table id="patientTable" class="display">
                    <thead>
                    <tr>
                        <th><s:message code="patients.fullname"/></th>
                        <th><s:message code="patients.gender"/></th>
                        <th><s:message code="patients.address"/></th>
                        <th><s:message code="patients.email"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="patient" items="${patientsList}">
                        <tr>
                            <td>
                                <label>
                                    <input type="radio" name="patientId" value="${ patient.id }"/>
                                    <c:out value="${ patient.name }"/>
                                </label>
                            </td>
                            <td>
                                <c:out value="${ patient.gender }"/>
                            </td>
                            <td>
                                <c:out value="${ patient.address }"/>
                            </td>
                            <td>
                                <c:out value="${ patient.email }"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                       value="<c:out value="${_csrf.token}"/>"/>
                <div class="button-row">
                    <sec:authorize access="hasRole('ROLE_DOCTOR')">
                        <div class="button-item">
                            <a href="/addpatient"><s:message code="add.title"/></a>
                        </div>
                        <%--<div class="button-item">--%>
                        <%--<a href="/editpatient"><s:message code="edit.title"/></a>--%>
                        <%--</div>--%>
                        <div class="button-item">
                            <s:message var="button" code="del.title"/>
                            <input type="submit" value="${button}"/>
                        </div>
                    </sec:authorize>
                </div>
            </form>
        </div>

        <sec:authorize access="hasRole('ROLE_DOCTOR')">
        <div class="container-item">
            <form name="diagnosisListForm" method="POST" action="/deldiagnosis">
                <table id="diagnosisTable" class="display">
                    <thead>
                    <tr>
                        <th><s:message code="menu.diagonisises"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="diagnosis" items="${allDiagnosises}">
                        <tr>
                            <td>
                                <label>
                                    <input type="radio" name="diagnosisId" value="${ diagnosis.id }"/>
                                    <c:out value="${ diagnosis.name }"/>
                                </label>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                       value="<c:out value="${_csrf.token}"/>"/>
                <div class="button-row">
                    <div class="button-item">
                        <a href="/adddiagnosis"><s:message code="add.title"/></a>
                    </div>

                    <div class="button-item">
                        <s:message var="button" code="del.title"/>
                        <input type="submit" value="${button}"/>
                    </div>
                </div>
            </form>
        </div>

        <div class="container-item">
            <form name="drugListForm" method="POST" action="/deldrug">
                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                       value="<c:out value="${_csrf.token}"/>"/>
                <table id="drugTable" class="display">
                    <thead>
                    <tr>
                        <th><s:message code="menu.drugs"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="drug" items="${allDrugs}">
                        <tr>
                            <td>
                                <label>
                                    <input type="radio" name="drugId" value="${ drug.id }"/>
                                    <c:out value="${ drug.name }"/>
                                </label>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="button-row">
                    <div class="button-item">
                        <a href="/adddrug"><s:message code="add.title"/></a>
                    </div>
                    <div class="button-item">
                        <s:message var="button" code="del.title"/>
                        <input type="submit" value="${button}"/>
                    </div>
                </div>
            </form>
        </div>

        <div class="container-item">
            <form name="medProcedureListForm" method="POST" action="/delmedprocedure">
                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                       value="<c:out value="${_csrf.token}"/>"/>
                <table id="medProcedureTable" class="display">
                    <thead>
                    <tr>
                        <th><s:message code="menu.medprocedures"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="medProcedure" items="${allMedProcedures}">
                        <tr>
                            <td>
                                <label>
                                    <input type="radio" name="medProcedureId" value="${ medProcedure.id }"/>
                                    <c:out value="${ medProcedure.name }"/>
                                </label>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="button-row">
                    <div class="button-item">
                        <a href="/addmedprocedure"><s:message code="add.title"/></a>
                    </div>
                    <div class="button-item">
                        <s:message var="button" code="del.title"/>
                        <input type="submit" value="${button}"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
    </sec:authorize>
</div>

${operationMessage}<br/>
</div>

<div class="footer" align="center">
    <%@include file="elements/footer.jsp" %>
</div>
</body>
</html>
