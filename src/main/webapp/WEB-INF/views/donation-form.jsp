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
    <div class="form--steps-instructions">
        <div class="form--steps-container">
            <h3>Ważne!</h3>
            <p data-step="1" class="active">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="2">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="3">
                Wybierz jedną, do
                której trafi Twoja przesyłka.
            </p>
            <p data-step="4">Podaj adres oraz termin odbioru rzeczy.</p>
        </div>
    </div>

    <div class="form--steps-container">
        <div class="form--steps-counter">Krok <span>1</span>/4</div>

        <form:form modelAttribute="donation" method="post">
            <!-- STEP 1: class .active is switching steps -->
            <div data-step="1" class="active">
                <h3>Zaznacz co chcesz oddać:</h3>

                <c:forEach items="${categories}" var="category">
                    <div class="form-group form-group--checkbox">
                        <label>
                            <input type="hidden" name="_categories" value="on">
                            <input id="categories6" name="categories" type="checkbox" value="${category.id}">
                            <span class="checkbox"></span>
                            <span class="description">${category.name}</span>
                        </label>
                    </div>
                </c:forEach>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 2 -->
            <div data-step="2">
                <h3>Podaj liczbę 60l worków, w które spakowałeś/aś rzeczy:</h3>

                <div class="form-group form-group--inline">
                    <label>
                        Liczba 60l worków:
                        <form:input path="quantity" type="number" value="1"/>
                    </label>
                </div>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 3 -->
            <div data-step="3">
                <h3>Wybierz organizacje, której chcesz pomóc:</h3>

                <c:forEach items="${organizations}" var="organization">
                    <div class="form-group form-group--checkbox">
                        <label>
                            <input type="radio" name="organization" value="${organization.id}"/>
                            <span class="checkbox radio"></span>
                            <span class="description">
                            <div class="title">Fundacja “${organization.name}”</div>
                            <div class="subtitle">
                                Cel i misja: ${organization.description}
                            </div>
                        </span>
                        </label>
                    </div>
                </c:forEach>


                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 4 -->
            <div data-step="4">
                <h3>Podaj adres oraz termin odbioru rzecz przez kuriera:</h3>

                <div class="form-section form-section--columns">
                    <div class="form-section--column">
                        <h4>Adres odbioru</h4>
                        <div class="form-group form-group--inline">
                            <label>Ulica i nr
                                <form:input path="street"/></label>
                            <label style="color:red;"><form:errors path="street"/></label>
                        </div>

                        <div class="form-group form-group--inline">
                            <label>Miasto <form:input path="city"/></label>
                            <label style="color:red;"><form:errors path="city"/></label>

                        </div>

                        <div class="form-group form-group--inline">
                            <label>Kod pocztowy<form:input path="zipCode"/></label>
                            <label style="color:red;"><form:errors path="zipCode"/></label>

                        </div>

                        <div class="form-group form-group--inline">
                            <label>Numer telefonu <form:input path="telephoneNumber" type="tel"/></label>
                            <label style="color:red;"><form:errors path="telephoneNumber"/></label>

                        </div>
                    </div>

                    <div class="form-section--column">
                        <h4>Termin odbioru</h4>
                        <div class="form-group form-group--inline">
                            <label> Data <form:input path="pickUpDate" type="date"/></label>
                            <label style="color:red;"><form:errors path="pickUpDate"/></label>

                        </div>

                        <div class="form-group form-group--inline">
                            <label> Godzina <form:input path="pickUpTime" type="time"/></label>
                            <label style="color:red;"><form:errors path="pickUpTime"/></label>

                        </div>

                        <div class="form-group form-group--inline">
                            <label> Uwagi dla kuriera <form:textarea path="pickUpComment" rows="5"/> </label>
                            <label style="color:red;"><form:errors path="pickUpComment"/></label>

                        </div>
                    </div>
                </div>
                <div class="form-group form-group--buttons" style="justify-content: space-between">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <input type="submit" class="btn" value="Zatwierdź">
                </div>
            </div>
        </form:form>
    </div>
</section>

<%@include file="/WEB-INF/views/includes/footer.jsp" %>
