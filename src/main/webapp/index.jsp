<html>
<body>
1 + 2 = <%=1 + 2%>

<%
    out.write("<h1>Digits</h1>");
    for (int i = 0; i < 10; i++) {
        String value = String.valueOf(i);
        out.write(value);
        out.write("<br>");
    }
%>

</body>
</html>
