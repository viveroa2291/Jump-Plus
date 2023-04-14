DROP DATABASE IF EXISTS mr;

CREATE DATABASE mr;

USE mr;

CREATE TABLE user
(
	user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(15) NOT NULL,
    last_name VARCHAR(15),
    email VARCHAR(30),
    username VARCHAR(20) UNIQUE,
    password VARCHAR(20)

);

CREATE TABLE movies
(
	movie_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50),
	rating DOUBLE(1,1)

);

CREATE TABLE user_movie
(
	user_id int,
    movie_id int,
    status VARCHAR(5),
    primary key(user_id, movie_id),
    foreign key(user_id) references user(user_id),
    foreign key(movie_id) references movies(movie_id)

);