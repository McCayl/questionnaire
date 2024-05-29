<#import "/spring.ftl" as spring />

<html lang="en">
<head>
    <meta charset="utf-8">
    <div>
        <link rel="stylesheet"
              href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
        <link rel="stylesheet"
              href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link rel="stylesheet" href="../../css/main.css">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </div>
    <title>Update Question</title>
</head>
<body>
<nav class="navbar navbar-expand-md bg-light navbar-light">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Q&A</a>
        </div>
        <ul class="navbar-nav">
            <li><a class="nav-link" href="/theme">Темы</a></li>
            <li><a class="nav-link" href="/user/profile">Профиль</a></li>
            <li><a class="nav-link" href="/user/admin/usertest">Админ</a></li>
            <li><a class="nav-link" href="/auth/signin">Авторизация</a></li>
        </ul>
        <span>
            <a href="/auth/logout">Выйти из аккаунта</a>
        </span>
    </div>
</nav>
    <div class="container-fluid text-center">
        <form action="/test/update/question"
            method="post" 
            style="max-width: 350px; margin: 0 auto;">
            <div class="border border-secondary p-3 rounded">
                <#if question.id??>
                        <p>Изменение вопроса</p>
                        <p><input type="hidden" name="id" value="${question.id}"/></p>
                    <#else>
                        <p>Сохранение вопроса</p>
                </#if>
                <p>
                    <@spring.formInput "question.text" "required class=\"form-control\" placeholder=\"Текст вопроса\""/>
                </p>
                <div style="width: 100%">
                    <select type="number" name="areaId" required>
                        <option value=1>Программирование</option>
                        <option value=2>Бизнес-анализ</option>
                        <option value=3>Тестирование ПО</option>
                        <option value=4>Стрессоустойчивость</option>
                        <option value=5>Доброжелательность</option>
                    </select>
                </div>
                <br>
                <p>
                    <input type="submit" value="Отправить" class="btn btn-outline-dark"/>
                </p>
            </div>
        </form>
    </div>
</body>
</html>