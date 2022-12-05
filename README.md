# ExploreWithMe

Платформа ExploreWithMe (англ. «исследуй со мной») предназначена для пользователей, которые хотят быть в курсе интересных событий и помогает объединяться в группы по тематическим интересам, находить новых друзей.

**Платформа реализована по микросервисной архитектуре:**

    * ewm-main-service - публичный API
    * ewm-stats-service - сервис статистики

**Использован стек технологий:** Spring Boot, Spring Data, Spring Rest Template, PostgreSQL, Lombok.

**Документация Swagger:**
ewm-main-service-spec.json, ewm-stats-service-spec.json.

### Функциональность "Комментарии"

#### Private API
Авторизированные пользователи могут создавать комментарии к событиям, которые ранее были опубликованы, просматривать, редактировать и удалять их.

* POST /users/{userId}/comments/events/{eventId} - добавление комментария
* PATCH /users/{userId}/comments/{commentId} - обновление комментария
* DELETE PATCH /users/{userId}/comments/{commentId} - удаление
* GET /users/{userId}/comments - получение всех  комментариев пользователя
* GET  /users/{userId}/comments/{commentId} - получение комментария пользователя по его id
* GET  /users/{userId}/comments/events/{eventId} - получение комментария пользователя к событию

#### Admin API
Администратор платформы имеет расширенный функционал для управления платформой.

* GET /admin/comments/{eventId}/count - получение количества сообщений к событию
* GET /admin/comments/users/{userId} - получение комментариев конкретного пользователя
* GET /admin/comments/{commentId} - получение комментария по его id 
* GET /admin/comments/events/{eventId} - получение всех комментариев к событию
* GET /admin/comments/events/{eventId}/users/{userId} - получение всех комментариев пользователя к конкретному событию
* PATCH /admin/comments/{commentId} - модерация комментария (после модерации пользователь не может редактировать комментарий)
* DELETE /admin/comments/{commentId} - удаление комментария
* PATCH /admin/comments/users/{userId}/ban - блокировка (бан) создания комментариев пользователем на заданный или неопределенный период времени при нарушении пользователем Правил платформы
* PATCH /admin/comments/users/{userId}/unban - разблокировка (разбан) пользователя для возможности вновь создавать комментарии 


**Тесты Postman:** 
- ewm-main-service.json 
- ewm-stat-service.json 
- ewm-main-service-comments.json

Pull Request: https://github.com/QuickHamster/java-explore-with-me/pull/2
