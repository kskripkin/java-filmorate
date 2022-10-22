CREATE TABLE IF NOT EXISTS USERS (
                                     user_id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     email VARCHAR(50) NOT NULL UNIQUE,
                                     login VARCHAR(50) NOT NULL UNIQUE,
                                     name VARCHAR(50),
                                     birthday DATE
);

CREATE TABLE IF NOT EXISTS FRIENDS (
                                       id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                       friend_id INTEGER REFERENCES USERS (user_id),
                                       user_id INTEGER REFERENCES USERS (user_id)
);

CREATE TABLE IF NOT EXISTS FILMS (
                                     film_id INTEGER AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(50) NOT NULL,
                                     description VARCHAR(200),
                                     genre VARCHAR(50) NOT NULL,
                                     release_date DATE,
                                     duration INTEGER,
                                     rating VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS LIKES (
                                     id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     film_id INTEGER REFERENCES FILMS (film_id),
                                     user_id INTEGER REFERENCES USERS (user_id)
);

CREATE TABLE IF NOT EXISTS GENRES (
                                      id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                      film_id INTEGER REFERENCES FILMS (film_id),
                                      category VARCHAR(50)
);