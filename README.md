# java-filmorate

Добавлена схема базы данных

![Схема базы данных](./db_scheme.PNG)

#Примеры запросов:
##ТОП N наиболее популярных фильмов

####SELECT f.name AS name
####       COUNT(l.user_id) AS like
####FROM films AS f 
####JOIN likes AS l ON f.film_id = l.film_id
####GROUP BY name
####ORDER BY like
####LIMIT N;

## Cписок общих друзей с другим пользователем

####select * from users where user_id in (
####select friend_id from (
####select friend_id
####from friends
####where user_id = 1
####UNION ALL
####select friend_id
####from friends
####where user_id = 3
####)
####group by friend_id
####having count(friend_id)=2
####)
