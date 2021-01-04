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
                <div class="slogan--steps-title">
                    <table class="responsive-table">
                        <thead class="table-header" style="font-size: 20px;">
                        <td>Kategorie przedmiotów</td>
                        <td>Fundacja</td>
                        <td>Liczba przekazanych worków</td>
                        <td>Adres odbioru darów</td>
                        <td>Data i czas odbioru</td>
                        <td>Informacja dla kuriera</td>
                        <td>Nr telefonu</td>
                        </thead>
                        <tbody class="table-row">
                        <tr style="font-size: 16px">
                            <td>
                                <c:forEach items="${donation.categories}" var="category">
                                    ${category.name}<br>
                                </c:forEach>
                            </td>
                            <td>"${donation.organization.name}"</td>
                            <td>${donation.quantity}</td>
                            <td>
                                ${donation.street}<br>
                                ${donation.zipCode}
                                ${donation.city}
                            </td>
                            <td>
                                ${donation.pickUpDate}
                                ${donation.pickUpTime}
                            </td>
                            <td>${donation.pickUpComment}</td>
                            <td>${donation.telephoneNumber}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <a href="/user/home" class="myButton" style="position: relative; top: 30px;">Wróć</a>
        </div>
    </div>
</header>

<%@include file="/WEB-INF/views/includes/footer.jsp" %>
