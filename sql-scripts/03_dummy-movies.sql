USE kino;
-- PRICECATEGORY
INSERT INTO `PriceCategory` (`id`, `name`, `reducedPrice`, `regularPrice`)
VALUES ('1', '2D', '7.5', '8.5'),
       ('2', '3D', '9.5', '11'),
       ('3', '2D Überlänge', '9', '10'),
       ('4', '3D Überlänge', '11', '12.5');

-- CINEMAHALL
INSERT INTO `CinemaHall` (`id`, `name`)
VALUES ('1', 'Luxemburg-Saal'),
       ('2', 'Saal 2'),
       ('3', 'Saal 3'),
       ('4', 'Europasaal');

-- SEAT
-- Sitze für Luxemburg
INSERT INTO `Seat` (`id`, `row`, `seatNumber`, `CINEMAHALL_ID`)
VALUES ('1', 'A', '1', '1'),
       ('2', 'A', '2', '1'),
       ('3', 'B', '1', '1'),
       ('4', 'B', '2', '1');

-- Sitze für Saal 2
INSERT INTO `Seat` (`id`, `row`, `seatNumber`, `CINEMAHALL_ID`)
VALUES ('5', 'A', '1', '2'),
       ('6', 'A', '2', '2'),
       ('7', 'A', '3', '2'),
       ('8', 'A', '4', '2'),
       ('9', 'A', '5', '2'),
       ('10', 'A', '6', '2'),
       ('11', 'B', '1', '2'),
       ('12', 'B', '2', '2'),
       ('13', 'B', '3', '2'),
       ('14', 'B', '4', '2'),
       ('15', 'B', '5', '2'),
       ('16', 'B', '6', '2'),
       ('17', 'C', '1', '2'),
       ('18', 'C', '2', '2'),
       ('19', 'C', '3', '2'),
       ('20', 'C', '4', '2'),
       ('21', 'C', '5', '2'),
       ('22', 'C', '6', '2');

-- Sitze für Saal 3
INSERT INTO `Seat` (`id`, `row`, `seatNumber`, `CINEMAHALL_ID`)
VALUES ('23', 'A', '1', '3'),
       ('24', 'A', '2', '3'),
       ('25', 'A', '3', '3'),
       ('26', 'A', '4', '3'),
       ('27', 'B', '1', '3'),
       ('28', 'B', '2', '3'),
       ('29', 'B', '3', '3'),
       ('30', 'B', '4', '3'),
       ('31', 'C', '1', '3'),
       ('32', 'C', '2', '3'),
       ('33', 'C', '3', '3'),
       ('34', 'C', '4', '3');

-- Sitze für Europasaal
INSERT INTO `Seat` (`id`, `row`, `seatNumber`, `CINEMAHALL_ID`)
VALUES ('35', 'A', '1', '4'),
       ('36', 'A', '2', '4'),
       ('37', 'A', '3', '4'),
       ('38', 'B', '1', '4'),
       ('39', 'B', '2', '4'),
       ('40', 'B', '3', '4'),
       ('41', 'C', '1', '4'),
       ('42', 'C', '2', '4'),
       ('43', 'C', '3', '4');

-- MOVIE
INSERT INTO `Movie` (`id`, `ageRating`, `duration`, `name`, `imageURL`, `PRICECATEGORY_ID`)
VALUES
-- Captain Marvel, FSK 12
('1', '12', '2.0', 'Captain Marvel',
 'https://media.services.cinergy.ch/media/box1600/7ff00589a6ac72d438fff3fe550a0b81159159ad.jpg',
 '3'),
-- Brightburn, FSK 18
('2', '18', '1.5', 'Brightburn',
 'https://pbs.twimg.com/media/D5_XG4kU0AA4Jrt.jpg',
 '2'),
-- Frozen 2, FSK 0
('3', '0', '1.75', 'Frozen 2',
 'https://indac.org/wp-content/uploads/2019/02/33796-1288x724.jpg',
 '1'),
-- Jonny English 3, FSK 0
('4', '0', '1.75', 'Jonny English 3',
 'https://images-na.ssl-images-amazon.com/images/I/81ZsP3ZkrVL._RI_SX300_.jpg',
 '2'),
-- Bohemian Rhapsody, FSK 18
('5', '18', '2.25', 'Bohemian Rhapsody',
 'https://image.film.at/images/cfs_poster_240w_339h/3014298/bohemian-rhapsody-plakat.jpg',
 '4'),
-- Sharknado 4 - The 4th Awakens, FSK 13
('6', '16', '1.5', 'Sharknado 4 - The 4th Awakens',
 'https://images-na.ssl-images-amazon.com/images/I/81dePEv10hL._SL1500_.jpg',
 '4');

-- PRESENTATION
INSERT INTO `Presentation` (`id`, `date`, `CINEMAHALL_ID`, `MOVIE_ID`)
VALUES ('1', '2019-07-01 16:00:00', '4', '1'),  -- Captain Marvel, Luxemburg-Saal
       ('2', '2019-06-02 16:00:00', '2', '2'),  -- Brightburn, Saal 2
       ('3', '2019-07-03 18:00:00', '1', '2'),  -- Brightburn, Luxemburg-Saal
       ('4', '2019-07-04 20:00:00', '1', '2'),  -- Brightburn, Luxemburg-Saal
       ('5', '2019-07-04 20:00:00', '2', '2'),  -- Brightburn, Luxemburg-Saal
       ('6', '2019-07-04 20:00:00', '1', '4'),  -- Jonny English 3, Luxemburg-Saal
       ('7', '2019-07-04 22:00:00', '3', '4'),  -- Jonny English 3, Saal 3
       ('8', '2019-07-06 20:00:00', '1', '4'),  -- Jonny English 3, Luxemburg-Saal
       ('9', '2019-07-12 22:00:00', '3', '4'),  -- Jonny English 3, Saal 3
       ('10', '2019-08-12 20:00:00', '1', '4'), -- Jonny English 3, Luxemburg-Saal
       ('11', '2019-08-13 22:00:00', '3', '4'), -- Jonny English 3, Saal 3
       ('12', '2019-07-12 16:00:00', '2', '5'), -- Bohemian Rhapsody, Saal 2
       ('13', '2019-07-12 18:00:00', '3', '5'), -- Bohemian Rhapsody, Saal 3
       ('14', '2019-07-12 20:00:00', '4', '5'), -- Bohemian Rhapsody, Europasaal
       ('15', '2019-07-13 14:00:00', '1', '5'), -- Bohemian Rhapsody, Luxemburg-Saal
       ('16', '2019-07-13 16:00:00', '3', '5'), -- Bohemian Rhapsody, Saal 3
       ('17', '2019-07-13 18:00:00', '2', '5'), -- Bohemian Rhapsody, Saal 2
       ('18', '2019-07-13 20:00:00', '4', '5'), -- Bohemian Rhapsody, Europasaal
       ('19', '2019-07-17 20:15:00', '2', '6'), -- Sharknado 4 - The 4th Awakens, Saal 2
       ('20', '2019-07-12 20:15:00', '2', '3'); -- Frozen 2, Saal 2
