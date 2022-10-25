CREATE TABLE IF NOT EXISTS USERS (
                                     user_id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     email VARCHAR(50) NOT NULL,
                                     login VARCHAR(50) NOT NULL,
                                     name VARCHAR(50),
                                     birthday DATE
);

CREATE TABLE IF NOT EXISTS FRIENDS (
                                       id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                       friend_id INTEGER REFERENCES USERS (user_id),
                                       user_id INTEGER REFERENCES USERS (user_id),
                                       confirm_friend VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS MPA (
                                   rating_id INTEGER PRIMARY KEY,
                                   rating_name VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS GENRES (
                                      genre_id INTEGER PRIMARY KEY,
                                      category VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS FILMS (
                                     film_id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     name VARCHAR(50) NOT NULL,
                                     description VARCHAR(200),
                                     genre INTEGER REFERENCES GENRES (genre_id),
                                     release_date DATE,
                                     duration INTEGER,
                                     rating INTEGER REFERENCES MPA (rating_id)
);

CREATE TABLE IF NOT EXISTS LIKES (
                                     id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     film_id INTEGER REFERENCES FILMS (film_id),
                                     user_id INTEGER REFERENCES USERS (user_id)
);