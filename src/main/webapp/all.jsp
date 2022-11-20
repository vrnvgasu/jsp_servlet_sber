<%@ page import="java.util.List" %>
<%@ page import="ru.edu.UserInfo" %>
<html>
<body>
<h1>All users:</h1>
<ul>
    <%
        List<UserInfo> users = (List<UserInfo>) request.getAttribute("usersList");
        for (int i = 0; i < users.size(); i++) {
            String login = users.get(i).getLogin();
            String name = users.get(i).getName();
            out.write("<li>name=" + name + " login=" + login + "</li>");
        }
    %>
</ul>
</body>
</html>