<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Document</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<header class="header">
<%@include file="/WEB-INF/views/includes/header.jsp" %>
</header>
<section class="login-page">
    <h2>Zmień hasło do konta</h2>
    <form:form method="post" modelAttribute="appuser">
        <div class="form-group">
            <form:input path="password" type="password" placeholder="Nowe hasło"/><br>
            <form:errors path="password"/>
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
        <form:input name="email" type="hidden" path="email"/>
        <form:input path="id" type="hidden"/>
    </form:form>
</section>
<%@include file="/WEB-INF/views/includes/footer.jsp" %>
