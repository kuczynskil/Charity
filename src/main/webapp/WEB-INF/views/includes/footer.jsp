<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer>
    <div id="contact" class="contact">
        <h2>Skontaktuj się z nami</h2>
        <h3>Formularz kontaktowy</h3>
        <form action="/sendMessage" method="post" class="form--contact">
            <div class="form-group form-group--50"><input type="text" name="name" placeholder="Imię"/></div>
            <div class="form-group form-group--50"><input type="text" name="surname" placeholder="Nazwisko"/></div>
            <sec:authorize access="!isAuthenticated()">
                <div class="form-group"><input name="email" placeholder="Adres email"></div>
            </sec:authorize>
            <div class="form-group"><textarea name="message" placeholder="Wiadomość" rows="1"></textarea></div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn" type="submit">Wyślij</button>
        </form>
    </div>
    <div class="bottom-line">
        <div class="bottom-line--icons">
            <a href="#" class="btn btn--small"><img src="${pageContext.request.contextPath}/images/icon-facebook.svg"
                                                    alt="facebook"/></a>
            <a href="#"
               class="btn btn--small"><img src="${pageContext.request.contextPath}/images/icon-instagram.svg"
                                           alt="instagram"/></a>
        </div>
    </div>
</footer>

<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
