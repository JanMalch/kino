DROP DATABASE kino;
CREATE DATABASE `kino` CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL ON `kino`.* TO `jpa`@localhost IDENTIFIED BY 'jpa';
CREATE USER 'jpa'@'localhost' IDENTIFIED BY PASSWORD 'jpa';
