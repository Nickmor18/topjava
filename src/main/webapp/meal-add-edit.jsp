<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <form action="meals" method="post">
        <table>
            <tr>
                <td>Описание:</td>
                <td><input style="width: 100%" type="text" value="${meal.description}" name="description" required></td>
            </tr>
            <tr>
                <td>Кол-во кал.:</td>
                <td><input style="width: 100%" type="number" value="${meal.calories}" name="calories" required></td>
            </tr>
            <tr>
                <td>Дата:</td>
                <td><input type="datetime-local" value="${meal.dateTime}" name="datetime" required></td>
            </tr>
            <input type="hidden" value="${meal.id}" name="id" required>
            <tr>
                <td></td>
                <td style="text-align: right"><input type="submit" value="OK" name="Ok"></td>
            </tr>
        </table>
    </form>

</body>
</html>
