USE kino;
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

-- SEAT
-- Sitze für Saal 1
INSERT INTO `seat` (`id`, `row`, `seatNumber`, `CINEMAHALL_ID`)
VALUES ('1', 'A', '1', '1'),
       ('2', 'A', '2', '1'),
       ('3', 'B', '1', '1'),
       ('4', 'B', '2', '1');

-- Sitze für Saal 2
INSERT INTO `seat` (`id`, `row`, `seatNumber`, `CINEMAHALL_ID`)
VALUES ('5', 'A', '1', '2'),
       ('6', 'A', '2', '2'),
       ('7', 'B', '1', '2'),
       ('8', 'B', '2', '2');

-- Sitze für Saal 3
INSERT INTO `seat` (`id`, `row`, `seatNumber`, `CINEMAHALL_ID`)
VALUES ('9', 'A', '1', '3'),
       ('10', 'A', '2', '3'),
       ('11', 'B', '1', '3'),
       ('12', 'B', '2', '3');

-- Sitze für Europasaal
INSERT INTO `seat` (`id`, `row`, `seatNumber`, `CINEMAHALL_ID`)
VALUES ('13', 'A', '1', '4'),
       ('14', 'A', '2', '4'),
       ('15', 'A', '3', '4'),
       ('16', 'B', '1', '4'),
       ('17', 'B', '2', '4'),
       ('18', 'B', '3', '4'),
       ('19', 'C', '1', '4'),
       ('20', 'C', '2', '4'),
       ('21', 'C', '3', '4');

-- MOVIE
INSERT INTO `movie` (`id`, `ageRating`, `duration`, `endDate`, `name`, `startDate`,`imageURL`,`PRICECATEGORY_ID`)
VALUES
-- Captain Marvel, FSK 12, läuft den ganzen Mai
('1', '12', '2.0', '2019-05-31 00:00:00', 'Captain Marvel', '2019-05-01 00:00:00','https://akns-images.eonline.com/eol_images/Entire_Site/2017126/rs_634x1024-170226172707-634.Alicia-Vikander-Oscars-2017-Beauty.jpg' ,'3'),
-- Brightburn, FSK 18, läuft ab 26.05. bis Mitte Juni
('2', '18', '1.5', '2019-06-16 00:00:00', 'Brightburn', '2019-05-26 00:00:00','https://image.kurier.at/images/cfs_landscape_616w_347h/1454025/Alicia-Vikander-as-Lara-Croft-Tomb-Raider-02.jpg' , '2'),
-- Frozen 2, FSK 0, lief nur bis 09.05.
('3', '0', '1.75', '2019-05-09 00:00:00', 'Frozen 2', '2019-04-17 00:00:00','https://www.telegraph.co.uk/content/dam/films/2018/03/08/TR-ISS-08A-068-01_trans_NvBQzQNjv4BqEC90nADzAHqz_X14LCxiKfr7sARQy7EgBjwPUzvqL_M.jpg' , '1');

-- PRESENTATION
INSERT INTO `presentation` (`id`, `date`, `CINEMAHALL_ID`, `MOVIE_ID`)
VALUES ('1', '2019-05-12 16:00:00', '1', '1'), -- Captain Marvel, Saal 1
       ('2', '2019-05-12 16:00:00', '2', '2'), -- Brightburn, Saal 2
       ('3', '2019-05-14 18:00:00', '1', '1'), -- Captain Marvel, Saal 1
       ('4', '2019-05-18 20:00:00', '1', '2');
-- Brightburn, Saal 1
