<html>
<body>
<%--request - Встроенный в jsp объект. Засунули в него в сервлете параметр--%>
<h1>Hello, <%=request.getAttribute("name")%></h1>
LOGIN: <%=request.getAttribute("login")%><br>
NAME: <%=request.getAttribute("name")%><br>
<hr>
<form method="POST" action="">
    <input name="shouldDelete" value="true" hidden><br>
    <input type="submit" value="DELETE"><br>
</form>
</body>
</html>