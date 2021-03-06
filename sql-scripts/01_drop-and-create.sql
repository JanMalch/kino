DROP DATABASE if exists kino;


CREATE DATABASE kino CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL ON `kino`.* TO `jpa`@localhost IDENTIFIED BY 'jpa';
use kino;

DROP TABLE if exists Movie;
DROP TABLE if exists PriceCategory;
DROP TABLE if exists Presentation;
DROP TABLE if exists CinemaHall;
DROP TABLE if exists Seat;

create table Account
(
    id             bigint not null AUTO_INCREMENT,
    birthday       date,
    email          varchar(255),
    firstName      varchar(255),
    hashedPassword varchar(255),
    lastName       varchar(255),
    role           varchar(255),
    salt           tinyblob,
    primary key (id)
);

create table CinemaHall
(
    id   bigint not null AUTO_INCREMENT,
    name varchar(255),
    primary key (id)
);

create table Movie
(
    id               bigint  not null AUTO_INCREMENT,
    ageRating        integer not null,
    duration         float   not null,
    endDate          datetime,
    name             varchar(255),
    startDate        datetime,
    imageURL         varchar(500),
    PRICECATEGORY_ID bigint,
    primary key (id)
);

create table Presentation
(
    id            bigint not null AUTO_INCREMENT,
    date          datetime,
    CINEMAHALL_ID bigint,
    MOVIE_ID      bigint,
    primary key (id)
);

create table PriceCategory
(
    id           bigint not null AUTO_INCREMENT,
    name         varchar(255),
    reducedPrice float  not null,
    regularPrice float  not null,
    primary key (id)
);

create table Reservation
(
    id              bigint not null AUTO_INCREMENT,
    reservationDate datetime,
    ACCOUNT_ID      bigint,
    PRESENTATION_ID bigint,
    primary key (id)
);

create table RESERVATION_SEAT
(
    RESERVATION_ID bigint not null,
    SEAT_ID        bigint not null,
    primary key (RESERVATION_ID, SEAT_ID)
);

create table Seat
(
    id            bigint  not null AUTO_INCREMENT,
    row           varchar(255),
    seatNumber    integer not null,
    CINEMAHALL_ID bigint,
    primary key (id)
);
