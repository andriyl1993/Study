<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<p>Оберіть тип операції</p>

<form method="post" action="./">
    <select name="opt">
        <option value="1">середньомісячної температури повітря</option>
        <option value="2">кількість днів, коли температура опускалась нижче 0°С</option>
        <option value="3">три самих теплих дня</option>
    </select>
    <button>Надіслати</button>
</form>

</html>