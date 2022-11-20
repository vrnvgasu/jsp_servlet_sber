<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page isELIgnored="false" %>

<html>
<body>

<c:choose>
    <c:when test="${usersList.size() != 0}">
        <h1>All users:</h1>
        <ul>
            <c:forEach items="${usersList}" var="user">
                <li>name=${user.name} login=${user.login}</li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <h1>Users list is empty</h1>
    </c:otherwise>
</c:choose>


</body>
</html>