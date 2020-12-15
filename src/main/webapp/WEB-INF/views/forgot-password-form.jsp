<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/includes/header.jsp" %>

</header>

<section class="login-page">
    <h2>Wprowadź email, na który zarejestrowane jest konto:</h2>
    <form action="${pageContext.request.contextPath}/recoverPassword" method="post">
        <div class="form-group">
            <label>
                <input name="email" type="email" placeholder="Email"/>
            </label><br>
            <div style="color: red; font-size: 20px">${message}</div>
        </div>
        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">Zresetuj hasło</button>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</section>

<%@include file="/WEB-INF/views/includes/footer.jsp" %>
