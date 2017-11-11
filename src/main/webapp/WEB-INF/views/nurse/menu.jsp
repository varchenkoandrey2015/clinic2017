<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title><s:message code="menu.title"/></title>
    <link href="${pageContext.request.contextPath}/resources/css/page_style.css" rel="stylesheet" >
    <link href="${pageContext.request.contextPath}/resources/css/logo_style.css" rel="stylesheet" >
    <link href="${pageContext.request.contextPath}/resources/css/table_style.css" rel="stylesheet" >
</head>
<body>
    <div class="container">
        <div class="header" align="left">
            <%@include file="../../views/elements/header_with_logout.jsp" %>
        </div>
        <a href="/choosepatient"><s:message code="patients.title"/></a>>
        <s:message code="menu.title"/><br/>
        <div align="center">
            <strong>"${patientName}"</strong>
            <table align="center">
                <tr>
                    <td valign="top" style="text-align: center">
                        <form name="drugListForm" method="POST" action="/deldrug">
                            <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
                            <s:message var="button" code="menu.deldrug"/>
                            <input type="submit" value="${button}" align="center"/>  <br/>
                            <table class="table">
                                <tr class="tr">
                                    <th class="th"><strong><s:message code="menu.drugs"/></strong></th>
                                </tr>
                                <c:if test="${empty drugsList}">
                                    <tr class="tr">
                                        <td class="td"><s:message code="common.emptylist"/></td>
                                    </tr>
                                </c:if>
                                <c:forEach var="drug" items="${drugsList}">
                                    <tr class="tr">
                                        <td class="td">
                                            <label>
                                                <input type="radio" name="drugId" value="${ drug.id }"/>
                                                <c:out value="${ drug.name }" />
                                            </label>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </form>
                    </td>
                    <td valign="top" style="text-align: center">
                        <form name="medProcedureListForm" method="POST" action="/delmedprocedure">
                            <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
                            <s:message var="button" code="menu.delmedprocedure"/>
                            <input type="submit" value="${button}" align="center"/>  <br/>
                            <table class="table">
                                <tr class="tr">
                                    <th class="th"><strong><s:message code="menu.medprocedures"/></strong></th>
                                </tr>
                                <c:if test="${empty medProceduresList}">
                                    <tr class="tr">
                                        <td class="td"><s:message code="common.emptylist"/></td>
                                    </tr>
                                </c:if>
                                <c:forEach var="medProcedure" items="${medProceduresList}">
                                    <tr class="tr">
                                        <td class="td">
                                            <label>
                                                <input type="radio" name="medProcedureId" value="${ medProcedure.id }"/>
                                                <c:out value="${ medProcedure.name }" />
                                            </label>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </form>
                    </td>
                </tr>
            </table>
            ${operationMessage}  <br />
        </div>
        <div class="footer" align="center">
            <%@include file="../../views/elements/footer.jsp" %>
        </div>
    </div>
</body>
</html>



