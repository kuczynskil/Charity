<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/includes/header.jsp" %>

</header>

<section class="login-page">
    <h2>Zmień hasło do konta</h2>
    <form action="${pageContext.request.contextPath}/resetPassword" method="post">
        <div class="form-group">
<%--            <form:input path="password" type="password" placeholder="Nowe hasło"/><br>--%>
<%--            <form:errors path="password"/>--%>
            <label>
                <input type="password" name="password" placeholder="Nowe hasło"/><br>
            </label>
        </div>
        <div class="form-group">
            <label>
                <input type="password" name="password2" placeholder="Powtórz nowe hasło"/><br>
            </label>
            <div style="color: red; font-size: 20px">${diffpasswordsMessage}</div>
        </div>

        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">Zmień hasło</button>
        </div>
        <input name="email" type="hidden" value="${appuser.email}"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</section>

<%@include file="/WEB-INF/views/includes/footer.jsp" %>
