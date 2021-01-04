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
                        <thead class="table-header" style="font-size: 32px;">
                        <td>Id</td>
                        <td>Fundacja</td>
                        <td>Data i czas odbioru</td>
                        <td>Odebrane</td>
                        <td>Szczegóły</td>
                        </thead>
                        <tbody class="table-row">
                        <c:forEach items="${donations}" var="donation" varStatus="indexNumber">
                            <tr style="font-size: 24px">
                                <td>
                                    <c:out value="${indexNumber.index + 1}"/>
                                </td>
                                <td>"${donation.organization.name}"</td>
                                <td>
                                        ${donation.pickUpDate}
                                        ${donation.pickUpTime}
                                </td>
                                <td>
                                    <form action="/user/home" method="post" class="myForm center">
                                        <input type="checkbox" id="pickedUp${donation.id}" name="pickedUp"
                                               style="display: none"
                                               onchange="this.form.submit()"
                                               <c:if test="${donation.pickedUp}">checked</c:if>>
                                        <label for="pickedUp${donation.id}" class="toggle"><span></span></label>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="id" value="${donation.id}"/>
                                    </form>
                                </td>
                                <td>
                                    <a href="/user/donation/${donation.id}">
                                        <img src="${pageContext.request.contextPath}/images/info-solid.svg"
                                             height="16" width="40 px"
                                             alt="szczegóły"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</header>

<%@include file="/WEB-INF/views/includes/footer.jsp" %>
