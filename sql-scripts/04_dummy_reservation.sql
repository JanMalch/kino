use kino;
-- create reservation dummy

INSERT INTO `reservation` (`id`, `reservationDate`, `ACCOUNT_ID`, `PRESENTATION_ID`)
VALUES ('1', '2019-06-09 15:15:07', '3', '19'); -- Sharknado 4
INSERT INTO `reservation_seat` (`RESERVATION_ID`, `SEAT_ID`)
VALUES ('1', '19'), ('1', '20'), ('1', '21');-- with 3 Seats

