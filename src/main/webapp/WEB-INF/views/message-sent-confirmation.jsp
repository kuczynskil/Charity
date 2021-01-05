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
<%@include file="/WEB-INF/views/includes/header.jsp" %>

    <div class="slogan container container--90">
        <h2>
            Dziękujemy za przesłaną wiadomość. Skontaktujemy się z Tobą jak najszybciej.
        </h2>
    </div>
</header>

<%@include file="/WEB-INF/views/includes/footer.jsp" %>


