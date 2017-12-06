<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

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
        $('#patientTable').DataTable(
            {
                initComplete: function () {
                    this.api().columns().every(function () {
                        var column = this;
                        var select = $('<select class="table-select"><option value=""></option></select>')
                            .appendTo($(column.footer()).empty())
                            .on('change', function () {
                                var val = $.fn.dataTable.util.escapeRegex(
                                    $(this).val()
                                );

                                column
                                    .search(val ? '^' + val + '$' : '', true, false)
                                    .draw();
                            });

                        column.data().unique().sort().each(function (d, j) {
                            select.append('<option value="' + d + '">' + d + '</option>')
                        });
                    });
                }
            }
        );
        $('#diagnosisTable').DataTable(
            {
                initComplete: function () {
                    this.api().columns().every(function () {
                        var column = this;
                        var select = $('<select><option value=""></option></select>')
                            .appendTo($(column.footer()).empty())
                            .on('change', function () {
                                var val = $.fn.dataTable.util.escapeRegex(
                                    $(this).val()
                                );

                                column
                                    .search(val ? '^' + val + '$' : '', true, false)
                                    .draw();
                            });

                        column.data().unique().sort().each(function (d, j) {
                            select.append('<option value="' + d + '">' + d + '</option>')
                        });
                    });
                }
            }
        );
        $('#drugTable').DataTable(
            {
                initComplete: function () {
                    this.api().columns().every(function () {
                        var column = this;
                        var select = $('<select><option value=""></option></select>')
                            .appendTo($(column.footer()).empty())
                            .on('change', function () {
                                var val = $.fn.dataTable.util.escapeRegex(
                                    $(this).val()
                                );

                                column
                                    .search(val ? '^' + val + '$' : '', true, false)
                                    .draw();
                            });

                        column.data().unique().sort().each(function (d, j) {
                            select.append('<option value="' + d + '">' + d + '</option>')
                        });
                    });
                }
            }
        );
        $('#medProcedureTable').DataTable({
            initComplete: function () {
                this.api().columns().every(function () {
                    var column = this;
                    var select = $('<select ><option value=""></option></select>')
                        .appendTo($(column.footer()).empty())
                        .on('change', function () {
                            var val = $.fn.dataTable.util.escapeRegex(
                                $(this).val()
                            );

                            column
                                .search(val ? '^' + val + '$' : '', true, false)
                                .draw();
                        });

                    column.data().unique().sort().each(function (d, j) {
                        select.append('<option value="' + d + '">' + d + '</option>')
                    });
                });
            }
        });
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
        <li><s:message code="menu.diagonisises"/></li>
        <li><s:message code="menu.drugs"/></li>
        <li><s:message code="menu.medprocedures"/></li>
    </ul>

    <div class="container">
        <div class="container-item">
            <form name="choosePatientForm" method="POST">
                <table id="patientTable" class="display">
                    <thead>
                    <tr>
                        <th></th>
                        <th><s:message code="patients.firstName"/></th>
                        <th><s:message code="patients.middleName"/></th>
                        <th><s:message code="patients.lastName"/></th>
                        <th><s:message code="patients.gender"/></th>
                        <th><s:message code="patients.address"/></th>
                        <th><s:message code="patients.phone"/></th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th></th>
                        <th><s:message code="patients.firstName"/></th>
                        <th><s:message code="patients.middleName"/></th>
                        <th><s:message code="patients.lastName"/></th>
                        <th><s:message code="patients.gender"/></th>
                        <th><s:message code="patients.address"/></th>
                        <th><s:message code="patients.phone"/></th>
                    </tr>
                    </tfoot>
                    <tbody>
                    <c:forEach var="patient" items="${patientsList}">
                        <tr>
                            <td><input type="radio" name="patientId" value="${ patient.patientId }"/></td>
                            <td><c:out value="${ patient.firstName }"/></td>
                            <td><c:out value="${ patient.middleName }"/></td>
                            <td><c:out value="${ patient.lastName }"/></td>
                            <td><c:out value="${ patient.gender }"/></td>
                            <td><c:out value="${ patient.address }"/></td>
                            <td><c:out value="${ patient.phone }"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                       value="<c:out value="${_csrf.token}"/>"/>
                <div class="button-row">
                    <div class="button-item">
                        <s:message var="button" code="open.title"/>
                        <input class="button" type="submit" value="${button}"
                               onclick="document.choosePatientForm.action = '/showpatient';
                                document.choosePatientForm.method = 'GET';
                                document.choosePatientForm.target = '_self';
                                document.choosePatientForm.submit()"/>
                    </div>
                    <div class="button-item">
                        <a class="button" href="/addpatient"><s:message code="add.title"/></a>
                    </div>
                    <div class="button-item">
                        <s:message var="button" code="edit.title"/>
                        <input class="button" type="submit" value="${button}"
                               onclick="document.choosePatientForm.action = '/editpatient';
                               document.choosePatientForm.method = 'GET';
                               document.choosePatientForm.target = '_self';
                               document.choosePatientForm.submit()"/>
                    </div>
                    <div class="button-item">
                        <s:message var="button" code="del.title"/>
                        <input class="button" type="submit" value="${button}"
                               onclick="document.choosePatientForm.action = '/delpatient';
                               document.choosePatientForm.target = '_self';
                               document.choosePatientForm.submit()"/>
                    </div>
                    <div class="button-item">
                        <input class="button" type="submit" value="Сreate a report"
                               onclick="document.choosePatientForm.action = '/report';
                               document.choosePatientForm.method = 'GET';
                               document.choosePatientForm.target = '_blank';
                               document.choosePatientForm.submit()"/>
                    </div>
                </div>
            </form>
        </div>

        <div class="container-item">
            <form name="diagnosisListForm" method="POST">
                <table id="diagnosisTable" class="display">
                    <thead>
                    <tr>
                        <th></th>
                        <th><s:message code="prescribings.name"/></th>
                        <th><s:message code="prescribings.description"/></th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th></th>
                        <th><s:message code="prescribings.name"/></th>
                        <th><s:message code="prescribings.description"/></th>
                    </tr>
                    </tfoot>
                    <tbody>
                    <c:forEach var="diagnosis" items="${allDiagnosises}">
                        <tr>
                            <td><input type="radio" name="diagnosisId" value="${ diagnosis.diagnosisId }"/></td>
                            <td><c:out value="${ diagnosis.name }"/></td>
                            <td><c:out value="${ diagnosis.description }"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                       value="<c:out value="${_csrf.token}"/>"/>
                <div class="button-row">
                    <div class="button-item">
                        <a class="button" href="/adddiagnosis"><s:message code="add.title"/></a>
                    </div>

                    <div class="button-item">
                        <s:message var="button" code="edit.title"/>
                        <input class="button" type="submit" value="${button}"
                               onclick="document.diagnosisListForm.action = '/editdiagnosis';document.diagnosisListForm.submit()"/>
                    </div>

                    <div class="button-item">
                        <s:message var="button" code="del.title"/>
                        <input class="button" type="submit" value="${button}"
                               onclick="document.diagnosisListForm.action = '/deldiagnosis';document.diagnosisListForm.submit()"/>
                    </div>
                </div>
            </form>
        </div>

        <div class="container-item">
            <form name="drugListForm" method="POST">
                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                       value="<c:out value="${_csrf.token}"/>"/>
                <table id="drugTable" class="display">
                    <thead>
                    <tr>
                        <th></th>
                        <th><s:message code="prescribings.name"/></th>
                        <th><s:message code="prescribings.description"/></th>
                    </tr>
                    <tfoot>
                    <tr>
                        <th></th>
                        <th><s:message code="prescribings.name"/></th>
                        <th><s:message code="prescribings.description"/></th>
                    </tr>
                    </tfoot>
                    <tbody>
                    <c:forEach var="drug" items="${allDrugs}">
                        <tr>
                            <td><input type="radio" name="drugId" value="${ drug.drugId }"/></td>
                            <td><c:out value="${ drug.name }"/></td>
                            <td><c:out value="${ drug.description }"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="button-row">
                    <div class="button-item">
                        <a class="button" href="/adddrug"><s:message code="add.title"/></a>
                    </div>
                    <div class="button-item">
                        <s:message var="button" code="edit.title"/>
                        <input class="button" type="submit" value="${button}"
                               onclick="document.drugListForm.action = '/editdrug';document.drugListForm.submit()"/>
                    </div>
                    <div class="button-item">
                        <s:message var="button" code="del.title"/>
                        <input class="button" type="submit" value="${button}"
                               onclick="document.drugListForm.action = '/deldrug';document.drugListForm.submit()"/>
                    </div>
                </div>
            </form>
        </div>

        <div class="container-item">
            <form name="medProcedureListForm" method="POST">
                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                       value="<c:out value="${_csrf.token}"/>"/>
                <table id="medProcedureTable" class="display">
                    <thead>
                    <tr>
                        <th></th>
                        <th><s:message code="prescribings.name"/></th>
                        <th><s:message code="prescribings.description"/></th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th></th>
                        <th><s:message code="prescribings.name"/></th>
                        <th><s:message code="prescribings.description"/></th>
                    </tr>
                    </tfoot>
                    <tbody>
                    <c:forEach var="medProcedure" items="${allMedProcedures}">
                        <tr>
                            <td><input type="radio" name="medProcedureId" value="${ medProcedure.medProcedureId }"/></td>
                            <td><c:out value="${ medProcedure.name }"/></td>
                            <td><c:out value="${ medProcedure.description }"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="button-row">
                    <div class="button-item">
                        <a class="button" href="/addmedprocedure"><s:message code="add.title"/></a>
                    </div>
                    <div class="button-item">
                        <s:message var="button" code="edit.title"/>
                        <input class="button" type="submit" value="${button}"
                               onclick="document.medProcedureListForm.action = '/editmedprocedure';document.medProcedureListForm.submit()"/>
                    </div>
                    <div class="button-item">
                        <s:message var="button" code="del.title"/>
                        <input class="button" type="submit" value="${button}"
                               onclick="document.medProcedureListForm.action = '/delmedprocedure';document.medProcedureListForm.submit()"/>
                    </div>
                </div>
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
