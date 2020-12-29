<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<header class="header--form-page">
    <nav class="container container--70">
        <ul class="nav--actions">
            <sec:authorize access="isAuthenticated()">
                <li class="logged-user">
                    Witaj ${appuser.name}
                    <ul class="dropdown">
                        <li><a href="/user/profile">Profil</a></li>
                        <li><a href="/user/home">Moje zbiórki</a></li>
                        <sec:authorize access="hasRole('ADMIN')">
                            <li><a href="/admin/appusers">Panel admina</a></li>
                        </sec:authorize>
                        <li><form class="nav-link" action="<c:url value="/logout"/>" method="post">
                            <input class="nav-link" type="submit" value="Wyloguj">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form></li>
                    </ul>
                </li>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                <li><a href="/login" class="btn btn--small btn--without-border">Zaloguj</a></li>
                <li><a href="/register" class="btn btn--small btn--highlighted">Załóż konto</a></li>
            </sec:authorize>
        </ul>

        <ul>
            <li><a href="/" class="btn btn--without-border active">Start</a></li>
            <li><a href="/#steps" class="btn btn--without-border">O co chodzi?</a></li>
            <li><a href="/#about-us" class="btn btn--without-border">O nas</a></li>
            <li><a href="/#help" class="btn btn--without-border">Fundacje i organizacje</a></li>
            <li><a href="/#contact" class="btn btn--without-border">Kontakt</a></li>
        </ul>
    </nav>

    <div class="slogan container container--90">
        <h2>
            Dziękujemy za przesłaną wiadomość. Skontaktujemy się z Tobą jak najszybciej.
        </h2>
    </div>
</header>

<%@include file="/WEB-INF/views/includes/footer.jsp" %>


