MERGE INTO MPA (rating_id, rating_name) VALUES (1, 'G');
MERGE INTO MPA (rating_id, rating_name) VALUES (2, 'PG');
MERGE INTO MPA (rating_id, rating_name) VALUES (3, 'PG-13');
MERGE INTO MPA (rating_id, rating_name) VALUES (4, 'R');
MERGE INTO MPA (rating_id, rating_name) VALUES (5, 'NC-17');

MERGE INTO GENRES (genre_id, category) VALUES (1, 'COMEDY');
MERGE INTO GENRES (genre_id, category) VALUES (2, 'DRAMA');
MERGE INTO GENRES (genre_id, category) VALUES (3, 'CARTOON');
MERGE INTO GENRES (genre_id, category) VALUES (4, 'THRILLER');
MERGE INTO GENRES (genre_id, category) VALUES (5, 'DOCUMENTARY');
MERGE INTO GENRES (genre_id, category) VALUES (6, 'ACTION_MOVIE');