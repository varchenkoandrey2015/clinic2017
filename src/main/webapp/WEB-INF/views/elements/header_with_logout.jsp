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
        </tr>
    </table>
</div>

<br/>
<a href="/menu">
    <img src="${pageContext.request.contextPath}/resources/img/logo.png" class="logoStyle"/>
</a>
<br>
