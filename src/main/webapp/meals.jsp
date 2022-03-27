<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        table {
            border-collapse: collapse;
        }
        table, th, td, tr {
            border:1px solid black;
        }
    </style>
</head>
<body>

<a href="meals?add">Добавить</a>

    <table style="width:50%">
        <tr>
            <th>Время</th>
            <th>Описание</th>
            <th>Кол-во кал.</th>
        </tr>
        <c:forEach items="${meals}" var="item">
                <tr>
                    <td style="text-align: center;color: ${item.excess ? "red" : "green"}">${item.date} ${item.time}</td>
                    <td style="text-align: center;color: ${item.excess ? "red" : "green"}">${item.description}</td>
                    <td style="text-align: center;color: ${item.excess ? "red" : "green"}">${item.calories}</td>
                    <td style="text-align: center"><a href="meals?edit&id=${item.id}">Изменить</a></td>
                    <td style="text-align: center">
                        <form action="meals" method="post">
                            <input type="hidden" name="id" value="${item.id}">
                            <input type="submit" value="Удалить" name="delete">
                        </form>
                    </td>
                </tr>
        </c:forEach>
    </table>
</body>
</html>
