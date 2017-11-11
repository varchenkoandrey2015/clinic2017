<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<html>
    <head>
        <title><s:message code="index.title"/></title>
        <link href="${pageContext.request.contextPath}/resources/css/page_style.css" rel="stylesheet" >
        <link href="${pageContext.request.contextPath}/resources/css/logo_style.css" rel="stylesheet" >
    </head>
    <body>
        <div class="container">
            <div class="header" align="left">
              <%@include file="elements/header.jsp" %>
            </div>
            <div align="center">
                <form name="loginForm" method="POST" action="/login">
                    <table>
                        <tr>
                            <td><s:message code="common.login"/></td>
                            <td><input type="text" name="login" value="" size="20"/></td>
                        </tr>
                        <tr>
                            <td><s:message code="common.password"/></td>
                            <td><input type="password" name="password" value="" size="20" /></td>
                        </tr>
                    </table>
                    <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
                    <s:message var="button" code="common.submit"/>
                    <input type="submit" value="${button}" />
                    <a href="/registration"><s:message code="registration.title"/></a><br />
                    ${operationMessage}<br />
                </form>
            </div>
            <div class="footer" align="center">
                <%@include file="elements/footer.jsp" %>
            </div>
        </div>
    </body>
</html>