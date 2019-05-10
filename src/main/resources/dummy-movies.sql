DROP TABLE if exists movie;
DROP TABLE if exists pricecategory;
DROP TABLE if exists presentation;
DROP TABLE if exists cinemahall;

-- PRICECATEGORY
INSERT INTO `pricecategory` (`id`, `name`, `reducedPrice`, `regularPrice`)
VALUES ('1', '2D', '7.5', '8.5'),
       ('2', '3D', '9.5', '11'),
       ('3', '2D Überlänge', '9', '10'),
       ('4', '3D Überlänge', '11', '12.5');

-- CINEMAHALL
INSERT INTO `cinemahall` (`id`, `name`)
VALUES ('1', 'Saal 1'),
       ('2', 'Saal 2'),
       ('3', 'Saal 3'),
       ('4', 'Europasaal');

-- MOVIE
INSERT INTO `movie` (`id`, `ageRating`, `duration`, `endDate`, `name`, `startDate`, `PRICECATEGORY_ID`)
VALUES
-- Captain Marvel, FSK 12, läuft den ganzen Mai
('1', '12', '2.0', '2019-05-31 00:00:00', 'Captain Marvel', '2019-05-01 00:00:00', '3'),
-- Brightburn, FSK 18, läuft ab 26.05. bis Mitte Juni
('2', '18', '1.5', '2019-06-16 00:00:00', 'Brightburn', '2019-05-26 00:00:00', '2'),
-- Frozen 2, FSK 0, lief nur bis 09.05.
('3', '0', '1.75', '2019-05-09 00:00:00', 'Frozen 2', '2019-04-17 00:00:00', '1');

-- PRESENTATION
INSERT INTO `presentation` (`id`, `date`, `CINEMAHALL_ID`, `MOVIE_ID`)
VALUES ('1', '2019-05-12 16:00:00', '1', '1'), -- Captain Marvel, Saal 1
       ('2', '2019-05-12 16:00:00', '2', '2'), -- Brightburn, Saal 2
       ('3', '2019-05-14 18:00:00', '1', '1'), -- Captain Marvel, Saal 1
       ('4', '2019-05-18 20:00:00', '1', '2'); -- Brightburn, Saal 1
