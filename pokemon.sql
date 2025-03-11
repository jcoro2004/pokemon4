-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.30 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Volcando datos para la tabla pokemon.preguntes: ~10 rows (aproximadamente)
DELETE FROM `preguntes`;
INSERT INTO `preguntes` (`id_pregunta`, `text_pregunta`) VALUES
	(1, 'Quin és el Pokémon en el qual evoluciona Charmander?'),
	(2, 'Quin és el Pokémon en el qual evoluciona Squirtle?'),
	(3, 'En què evoluciona Pikachu?'),
	(4, 'Quin és el Pokémon inicial del qual evoluciona Ivysaur?'),
	(5, 'Quin és el Pokémon que evoluciona a Gyarados?'),
	(6, 'Quin és el Pokémon que evoluciona a Butterfree?'),
	(7, 'Quin és el Pokémon que evoluciona a Dugtrio?'),
	(8, 'Quin és el Pokémon inicial del qual evoluciona Charizard?'),
	(9, 'Quin és el Pokémon que evoluciona a Pidgeot?'),
	(10, 'Quin és el Pokémon que evoluciona a Machamp?');

-- Volcando datos para la tabla pokemon.respostes: ~40 rows (aproximadamente)
DELETE FROM `respostes`;
INSERT INTO respostes (id_resposta, id_pregunta, text_resposta, es_correcta) VALUES
    (1, 1, 'Charizard', 0),
    (2, 1, 'Charmeleon', 1),
    (3, 1, 'Squirtle', 0),
    (4, 1, 'Bulbasaur', 0),
    (5, 2, 'Blastoise', 0),
    (6, 2, 'Wartortle', 1),
    (7, 2, 'Seel', 0),
    (8, 2, 'Poliwhirl', 0),
    (9, 3, 'Electrode', 0),
    (10, 3, 'Raichu', 1),
    (11, 3, 'Magnemite', 0),
    (12, 3, 'Jolteon', 0),
    (13, 4, 'Bellsprout', 0),
    (14, 4, 'Oddish', 0),
    (15, 4, 'Venusaur', 0),
    (16, 4, 'Bulbasaur', 1),
    (17, 5, 'Horsea', 0),
    (18, 5, 'Seadra', 0),
    (19, 5, 'Magikarp', 1),
    (20, 5, 'Tentacool', 0),
    (21, 6, 'Kakuna', 0),
    (22, 6, 'Metapod', 1),
    (23, 6, 'Caterpie', 0),
    (24, 6, 'Weedle', 0),
    (25, 7, 'Diglett', 1),
    (26, 7, 'Geodude', 0),
    (27, 7, 'Onix', 0),
    (28, 7, 'Sandshrew', 0),
    (29, 8, 'Charmander', 0),
    (30, 8, 'Charmeleon', 1),
    (31, 8, 'Arcanine', 0),
    (32, 8, 'Flareon', 0),
    (33, 9, 'Pidgeotto', 1),
    (34, 9, 'Pidgey', 0),
    (35, 9, 'Fearow', 0),
    (36, 9, 'Spearow', 0),
    (37, 10, 'Machoke', 1),
    (38, 10, 'Machop', 0),
    (39, 10, 'Hitmonlee', 0),
    (40, 10, 'Hitmonchan', 0);

-- Volcando datos para la tabla pokemon.usuaris: ~2 rows (aproximadamente)
DELETE FROM `usuaris`;
INSERT INTO `usuaris` (`id_usuari`, `nom_usuari`, `puntuacio`) VALUES
	(1, 'test1', 5),
	(2, 'test2', 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
