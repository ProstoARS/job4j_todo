# job4j_todo

### О проекте:
Данный проект представляет собой web-приложение по управлению списком задач.

Приложение позволяет:
- Добавлять задачи
- Удалять задачи
- Просматривать описание задачи
- Редактировать задачи
- Помечать "выполнена/невыполнена"
- Просматривать отдельными списками выполненные/невыполненные

#### При реализации сервиса использованы:

- Spring boot 2.7.3.
- Thymeleaf 3.0.15.
- Bootstrap 4.4.1.
- Hibernate 5.6.11.
- PostgreSql 42.2.16.
- Liquibase 4.15.0.
- Lombok 1.18.22

#### Требования к окружению:

- JDK 17(+),
- Maven,
- PostgreSql

#### Запуск приложения

1. Создать базу данных:
"create database todo"
2. Запуск через maven: переходим в командной строке в корень проекта и
набираем команды "mvn clean install" и 
   "mvn spring-boot:run"
3. Переходим в src\main\resources\hibernate.cfg.xml и меняем в строке
   hibernate.connection.username и hibernate.connection.password данные для 
подключения к своей БД.
4. Переходим в db/liquibase.properties и меняем значения полей
   username и password на значения для подключения к своей БД.

#### Главная страница

![image](https://user-images.githubusercontent.com/78408444/211489777-1c1fce5e-e938-4969-8eda-bd715cbc4083.png)

#### Страница добавления задачи

![image](https://user-images.githubusercontent.com/78408444/211490152-e7008ba5-0b60-43f5-8aca-64c861a45e9b.png)

#### Страница описания задачи

![image](https://user-images.githubusercontent.com/78408444/211490570-a39b464a-9dcb-4564-b42b-7551166cf81d.png)

#### Страница редактирования задачи

![image](https://user-images.githubusercontent.com/78408444/211490754-6f6869db-62de-45e4-bb19-3b246c9b3b2a.png)
