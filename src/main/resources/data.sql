INSERT INTO Character (alias, gender, name, picture) VALUES ('Rocky','male','Sylvester Stallone','https://www.imdb.com/name/nm0000230/mediaviewer/rm1833012480/');
INSERT INTO Character (alias, gender, name, picture) VALUES ('Harry Potter','male','Danial Radcliffe','https://www.imdb.com/name/nm0705356/mediaviewer/rm16572161/');
INSERT INTO Character (alias, gender, name, picture) VALUES ('Daywalker','male','Wesley Snipes','https://www.imdb.com/name/nm0000648/mediaviewer/rm1255119616/');
INSERT INTO Character (alias, gender, name, picture) VALUES ('Nightwalker','male','James Snipes','https://www.imdb.com/name/nm0000648/mediaviewer/rm1255119616/');
INSERT INTO Character (alias, gender, name, picture) VALUES ('KitchenWalker','female','Jennifer Snipes','https://www.imdb.com/name/nm0000648/mediaviewer/rm1255119616/');

INSERT INTO Franchise (description, name) VALUES ('The franchise contains 9 movies...','Rocky');
INSERT INTO Franchise (description, name) VALUES ('The franchise contains 8 movies based on the Harry Potter books','Harry Potter');
INSERT INTO Franchise (description, name) VALUES ('The franchise contains 3 movies...','Blade');

INSERT INTO Movie (director, picture, release_year, title, trailer) VALUES ('John G. Avildsen','https://www.imdb.com/title/tt0075148/mediaviewer/rm960529408/','1976','Rocky','https://www.youtube.com/watch?v=7RYpJAUMo2M');
INSERT INTO Movie (director, picture, release_year, title, trailer) VALUES ('Chris Columbus','https://www.imdb.com/title/tt0241527/mediaviewer/rm683213568/','2001','Harry Potter and the Sorcerers Stone','https://www.youtube.com/watch?v=VyHV0BRtdxo');
INSERT INTO Movie (director, picture, release_year, title, trailer) VALUES ('Stephen Norrington','https://www.imdb.com/title/tt0120611/mediaviewer/rm2498662657/','1998','Blade','https://www.youtube.com/watch?v=kaU2A7KyOu4');

INSERT INTO characters_movies (movie_id, character_id ) VALUES('1','1');
INSERT INTO characters_movies (movie_id, character_id ) VALUES('2','2');
INSERT INTO characters_movies (movie_id, character_id ) VALUES('3','3');
INSERT INTO characters_movies (movie_id, character_id ) VALUES('3','4');
INSERT INTO characters_movies (movie_id, character_id ) VALUES('3','5');

INSERT INTO franchise_movies (franchise_id, movies_id) VALUES('1','1');
INSERT INTO franchise_movies (franchise_id, movies_id) VALUES('2','2');
INSERT INTO franchise_movies (franchise_id, movies_id) VALUES('3','3');

