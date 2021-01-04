<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<header class="header--form-page">
    <%@include file="/WEB-INF/views/includes/header.jsp" %>
    <div class="slogan container container--90">
        <div class="slogan--item">
            <div class="slogan--steps">
                <form:form method="post" modelAttribute="loggedInAppUser" class="login-page">
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
                        <button class="btn btn--without-border" type="submit">Zapisz nowe hasło</button>
                    </div>
                    <input type="hidden" name="id" value="${loggedInAppUser.id}">
                </form:form>
            </div>
        </div>
    </div>
</header>


<%@include file="/WEB-INF/views/includes/footer.jsp" %>
