<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<br>
<div style="float: right;">
    <table>
        <tr>
            <td style="padding: 5px;">
                <form name="logoutForm" method="POST" action="/logout" style=" margin-bottom: 0px;">
                    <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
                    <s:message code="common.username"/> ${pageContext.request.userPrincipal.name}
                    <s:message var="button" code="common.logout"/>
                    <input type="submit" value="${button}" align="center"/>  <br/>
                </form>
            </td>
            <td style="padding: 5px;">
                <a href="?locale=ru">
                    <img src="${pageContext.request.contextPath}/resources/img/ru_locale_64.png"
                         height="20"
                         width="20"
                         style="display:block;"
                    />
                </a>
            </td>
            <td style="padding: 5px;">
                <a href="?locale=en">
                    <img src="${pageContext.request.contextPath}/resources/img/en_locale_64.png"
                         height="20"
                         width="20"
                         style="display:block;"
                    />
                </a>
            </td>
        </tr>
    </table>
</div>

<br/>
<a href="/choosepatient">
    <img src="${pageContext.request.contextPath}/resources/img/logo.png" class="logoStyle"/>
</a>
<br>
