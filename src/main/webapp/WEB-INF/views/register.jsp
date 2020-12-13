<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/includes/header.jsp" %>

</header>

<section class="login-page">
    <h2>Załóż konto</h2>
    <form:form method="post" modelAttribute="appuser">
        <div class="form-group">
            <form:input path="email" type="email" placeholder="Email"/><br>
            <form:errors path="email"/>
        </div>
        <div class="form-group">
            <form:input path="password" type="password" placeholder="Hasło"/><br>
            <form:errors path="password"/>
        </div>
        <div class="form-group">
            <label>
                <input type="password" name="password2" placeholder="Powtórz hasło"/><br>
            </label>
        </div>

        <div class="form-group form-group--buttons">
            <a href="/login" class="btn btn--without-border">Zaloguj się</a>
            <button class="btn" type="submit">Załóż konto</button>
        </div>
    </form:form>
</section>

<%@include file="/WEB-INF/views/includes/footer.jsp" %>
