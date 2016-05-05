use moviedb;
DELIMITER //
CREATE PROCEDURE add_movie(
IN movieTitle VARCHAR(50),
IN movieDirector VARCHAR(50),
IN movieYear INT,
IN starFirst VARCHAR(50),
IN starLast VARCHAR(50),
IN genreName VARCHAR(50))

proc_label:BEGIN
DECLARE movieId INT;

DECLARE starId INT;

DECLARE genreId INT;

SELECT id INTO movieId
from movies
where title = movieTitle
and director = movieDirector
and year = movieYear;

if movieId is null then

insert into movies(title, director, year) values (movieTitle, movieDirector, movieYear);

SELECT id INTO movieId
from movies
where title = movieTitle
and director = movieDirector
and year = movieYear;

if(movieId is null) then
select 'Error: could not insert movie, now exiting stored procedure' as '';
LEAVE proc_label;
else
select 'No such movie exists, inserting new movie into database' as '';
end if;
end if;


SELECT id INTO starId
from stars
where first = starFirst
and last = starLast;

if starId is null then
insert into stars(first, last) values (starFirst, starLast);

SELECT id INTO starId
from stars
where first = starFirst
and last = starLast;
end if;


SELECT id INTO genreId
from genres where name = genreName;

if(genreId is null) then

insert into genres(name) values (genreName);

SELECT id INTO genreId
from genres where name = genreName;

end if;

if starId is not null then
insert into stars_in_movies(star_id, movie_id) values (starId, movieId);
select 'inserting star_in_movies' as '';
end if;

if genreId is not null then
insert into genres_in_movies(genre_id, movie_id) values (genreId, movieId);
select 'inserting genres_in_movies' as '';
end if;

select movieId as 'movieId';
select starId as 'starId';
select genreId as 'genreId';







END //

DELIMITER ;