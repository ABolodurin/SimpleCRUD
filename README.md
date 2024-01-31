# Инструкция по запуску
### Для запуска нужны JDK17, Maven, Docker, Git, локальный PostgreSQL
___

- Создать в PostgreSQL 2 БД ```crud``` и ```crud_test```
- Проверить в ```src/main/resources/application.yml``` путь и логин/пароль к БД ```crud``` 
- Проверить в ```src/test/resources/application-test.yml``` путь и логин/пароль к БД ```crud_test```
- Накатить скрипт liquibase ```mvn liquibase:update -Dliquibase.updateToTag=1```
- Запустить приложение
- Интерфейс Swagger доступен по адресу <a href="localhost:8080/swagger-ui.html">localhost:8080/swagger-ui.html</a>

Скрипты liquibase
- создание таблицы и заполнение данных ```mvn liquibase:update -Dliquibase.updateToTag=1```
- роллбэк(удаление таблицы)```mvn liquibase:rollback -Dliquibase.rollbackCount=1```