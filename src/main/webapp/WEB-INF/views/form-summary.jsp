<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
<header class="header--form-page">
    <nav class="container container--70">
        <ul class="nav--actions">
            <li class="logged-user">
                Witaj ${appuser.name}
                <ul class="dropdown">
                    <li><a href="/user/profile">Profil</a></li>
                    <li><a href="/user/home">Moje zbiórki</a></li>
                    <li><a href="/logout">Wyloguj</a></li>
                </ul>
            </li>
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
        <div class="slogan--item">
            <h1>
                Oddaj rzeczy, których już nie chcesz<br/>
                <span class="uppercase">potrzebującym</span>
            </h1>

            <div class="slogan--steps">
                <div class="slogan--steps-title">Wystarczą 4 proste kroki:</div>
                <ul class="slogan--steps-boxes">
                    <li>
                        <div><em>1</em><span>Wybierz rzeczy</span></div>
                    </li>
                    <li>
                        <div><em>2</em><span>Spakuj je w worki</span></div>
                    </li>
                    <li>
                        <div><em>3</em><span>Wybierz fundację</span></div>
                    </li>
                    <li>
                        <div><em>4</em><span>Zamów kuriera</span></div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</header>
<section class="form--steps">
    <div class="form--steps-container">
        <div class="form--steps-counter" style="visibility: hidden"><span></span></div>

        <form:form action="/donateAdd" modelAttribute="donation" method="post">

            <div data-step="1" class="active">
                <h3>Podsumowanie Twojej darowizny</h3>

                <div class="summary">
                    <div class="form-section">
                        <h4>Oddajesz:</h4>
                        <ul>
                            <li>
                                <span class="icon icon-bag"></span>
                                <span class="summary--text">${donation.quantity} worki, w których znajdują
                                    się ${categoriesString}</span>
                            </li>
                            <form:input path="quantity" type="hidden"/>
                            <li>
                                <span class="icon icon-hand"></span>
                                <span class="summary--text">Dla fundacji "${donation.organization.name}"</span>
                            </li>
                            <form:input path="organization" type="hidden"/>
                        </ul>
                    </div>

                    <div class="form-section form-section--columns">
                        <div class="form-section--column">
                            <h4>Adres odbioru:</h4>
                            <ul>
                                <li>${donation.street}</li>
                                <li>${donation.city}</li>
                                <li>${donation.zipCode}</li>
                                <li>${donation.telephoneNumber}</li>
                                <form:input path="street" type="hidden"/>
                                <form:input path="city" type="hidden"/>
                                <form:input path="zipCode" type="hidden"/>
                                <form:input path="telephoneNumber" type="hidden"/>
                            </ul>
                        </div>

                        <div class="form-section--column">
                            <h4>Termin odbioru:</h4>
                            <ul>
                                <li>${donation.pickUpDate}</li>
                                <li>${donation.pickUpTime}</li>
                                <li>${donation.pickUpComment}</li>
                                <form:input path="pickUpDate" type="hidden"/>
                                <form:input path="pickUpTime" type="hidden"/>
                                <form:input path="pickUpComment" type="hidden"/>
                                <form:input path="categories" type="hidden"/>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="form-group form-group--buttons" style="justify-content: space-between">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <input type="submit" class="btn" value="Potwierdzam">
                </div>
            </div>
        </form:form>
    </div>
</section>
<%@include file="/WEB-INF/views/includes/footer.jsp" %>


