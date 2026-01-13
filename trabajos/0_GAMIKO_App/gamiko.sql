-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-11-2025 a las 12:51:53
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gamiko`
--
CREATE DATABASE IF NOT EXISTS `gamiko` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gamiko`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno_curso`
--

CREATE TABLE `alumno_curso` (
  `id_alumno` int(11) NOT NULL,
  `id_curso` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `alumno_curso`
--

INSERT INTO `alumno_curso` (`id_alumno`, `id_curso`) VALUES
(1, 1),
(6, 1),
(28, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asignatura`
--

CREATE TABLE `asignatura` (
  `id_asignatura` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  `id_profesor` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `asignatura`
--

INSERT INTO `asignatura` (`id_asignatura`, `nombre`, `descripcion`, `id_profesor`) VALUES
(2, 'Matemáticas', 'Matemáticas de pruebas', 15),
(3, 'Inglés', 'Clase de inglés', 18);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `curso`
--

CREATE TABLE `curso` (
  `id_curso` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `curso`
--

INSERT INTO `curso` (`id_curso`, `nombre`) VALUES
(1, '1º'),
(2, '2º'),
(3, '3º'),
(4, '4º');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pregunta`
--

CREATE TABLE `pregunta` (
  `id_pregunta` int(11) NOT NULL,
  `id_test` int(11) DEFAULT NULL,
  `enunciado` varchar(300) NOT NULL,
  `opcion_a` varchar(255) NOT NULL,
  `opcion_b` varchar(255) NOT NULL,
  `opcion_c` varchar(255) NOT NULL,
  `opcion_d` varchar(255) NOT NULL,
  `correcta` enum('a','b','c','d') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor_curso`
--

CREATE TABLE `profesor_curso` (
  `id_profesor` int(11) NOT NULL,
  `id_curso` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `profesor_curso`
--

INSERT INTO `profesor_curso` (`id_profesor`, `id_curso`) VALUES
(15, 1),
(18, 1),
(18, 2),
(18, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `progreso`
--

CREATE TABLE `progreso` (
  `id_progreso` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_tarea` int(11) DEFAULT NULL,
  `tiempo_estudio` float NOT NULL DEFAULT 0,
  `nivel` int(11) NOT NULL DEFAULT 0,
  `puntos_obtenidos` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recompensa`
--

CREATE TABLE `recompensa` (
  `id_recompensa` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `puntos_necesarios` int(11) NOT NULL,
  `icono` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `resultado_test`
--

CREATE TABLE `resultado_test` (
  `id_resultado` int(11) NOT NULL,
  `id_test` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `puntuacion` float NOT NULL,
  `fecha_realizacion` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarea`
--

CREATE TABLE `tarea` (
  `id_tarea` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  `fecha_entrega` date DEFAULT NULL,
  `id_asignatura` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `test`
--

CREATE TABLE `test` (
  `id_test` int(11) NOT NULL,
  `titulo` varchar(50) NOT NULL,
  `id_asignatura` int(11) DEFAULT NULL,
  `id_profesor` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellidos` varchar(200) DEFAULT NULL,
  `correo` varchar(100) NOT NULL,
  `contrasenha` varchar(255) NOT NULL,
  `rol` enum('alumno','profesor','admin') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombre`, `apellidos`, `correo`, `contrasenha`, `rol`) VALUES
(1, 'Alumno', 'Prueba Prueba', 'alumno@prueba.com', '$2y$10$Zsw5SBth90DVjYWWSeB1DuL.qyDKte0eBVbnVrJM7B.gvdedSKuJK', 'alumno'),
(5, 'Admin', 'Prueba Prueba', 'admin@prueba.com', '$2y$10$RfEv2eRYLDs8aSOFXB6mfuN/tdiMzb7nXjA5Lt2gECjYyy3JAjceG', 'admin'),
(6, 'Prueba 1', 'Ingreso', 'prueba@ingreso.com', '$2y$10$dAs65DAdoGvTotFrVsZ3ZOlIUNrOL7EshGvzr.rbxP4nMpuNBrwKW', 'alumno'),
(15, 'Manolo', 'Prueba Prueba', 'manolo@prueba.com', '$2y$10$IUcdoeQoVCLRw3CO/Uy5kuznE3atDYjMikKt4bNV5RjWc/TXumSKW', 'profesor'),
(18, 'Marco', 'Coello Algo', 'marcocoello@gmail.com', '$2y$10$Lh2PnpK28EVsfUhQnH/FeuU3fFKY3gwoUtyFASoejuC.vVpQkeP9u', 'profesor'),
(24, 'Admin2', 'Prueba', 'admin2@prueba.com', '$2y$10$xMgKJiLz49wFPy5wgTDSoO2OcM8gOjMiK6AblBhoYov9LKOyptA/S', 'admin'),
(28, 'Alumno2', 'Prueba Alumno', 'alumno2@prueba.com', '$2y$10$NdCyAzGbdaJ1BavxADcoseF5OIZM3jkM0.UV7u9lKHaZ3luZgSnqq', 'alumno');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_recompensa`
--

CREATE TABLE `usuario_recompensa` (
  `id_usuario` int(11) DEFAULT NULL,
  `id_recompensa` int(11) DEFAULT NULL,
  `fecha_obtenida` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_tarea`
--

CREATE TABLE `usuario_tarea` (
  `id_alumno` int(11) DEFAULT NULL,
  `id_tarea` int(11) DEFAULT NULL,
  `estado` enum('pendiente','completada') DEFAULT 'pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno_curso`
--
ALTER TABLE `alumno_curso`
  ADD PRIMARY KEY (`id_alumno`),
  ADD KEY `id_curso` (`id_curso`);

--
-- Indices de la tabla `asignatura`
--
ALTER TABLE `asignatura`
  ADD PRIMARY KEY (`id_asignatura`),
  ADD KEY `id_profesor` (`id_profesor`);

--
-- Indices de la tabla `curso`
--
ALTER TABLE `curso`
  ADD PRIMARY KEY (`id_curso`);

--
-- Indices de la tabla `pregunta`
--
ALTER TABLE `pregunta`
  ADD PRIMARY KEY (`id_pregunta`),
  ADD KEY `id_test` (`id_test`);

--
-- Indices de la tabla `profesor_curso`
--
ALTER TABLE `profesor_curso`
  ADD PRIMARY KEY (`id_profesor`,`id_curso`),
  ADD KEY `id_curso` (`id_curso`);

--
-- Indices de la tabla `progreso`
--
ALTER TABLE `progreso`
  ADD PRIMARY KEY (`id_progreso`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_tarea` (`id_tarea`);

--
-- Indices de la tabla `recompensa`
--
ALTER TABLE `recompensa`
  ADD PRIMARY KEY (`id_recompensa`);

--
-- Indices de la tabla `resultado_test`
--
ALTER TABLE `resultado_test`
  ADD PRIMARY KEY (`id_resultado`),
  ADD KEY `id_test` (`id_test`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `tarea`
--
ALTER TABLE `tarea`
  ADD PRIMARY KEY (`id_tarea`),
  ADD KEY `id_asignatura` (`id_asignatura`);

--
-- Indices de la tabla `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`id_test`),
  ADD KEY `id_asignatura` (`id_asignatura`),
  ADD KEY `id_profesor` (`id_profesor`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `correo` (`correo`);

--
-- Indices de la tabla `usuario_recompensa`
--
ALTER TABLE `usuario_recompensa`
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_recompensa` (`id_recompensa`);

--
-- Indices de la tabla `usuario_tarea`
--
ALTER TABLE `usuario_tarea`
  ADD KEY `id_alumno` (`id_alumno`),
  ADD KEY `id_tarea` (`id_tarea`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `asignatura`
--
ALTER TABLE `asignatura`
  MODIFY `id_asignatura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `curso`
--
ALTER TABLE `curso`
  MODIFY `id_curso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `pregunta`
--
ALTER TABLE `pregunta`
  MODIFY `id_pregunta` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `progreso`
--
ALTER TABLE `progreso`
  MODIFY `id_progreso` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `recompensa`
--
ALTER TABLE `recompensa`
  MODIFY `id_recompensa` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `resultado_test`
--
ALTER TABLE `resultado_test`
  MODIFY `id_resultado` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tarea`
--
ALTER TABLE `tarea`
  MODIFY `id_tarea` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `test`
--
ALTER TABLE `test`
  MODIFY `id_test` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alumno_curso`
--
ALTER TABLE `alumno_curso`
  ADD CONSTRAINT `alumno_curso_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE,
  ADD CONSTRAINT `alumno_curso_ibfk_2` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id_curso`) ON DELETE CASCADE;

--
-- Filtros para la tabla `asignatura`
--
ALTER TABLE `asignatura`
  ADD CONSTRAINT `asignatura_ibfk_1` FOREIGN KEY (`id_profesor`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `pregunta`
--
ALTER TABLE `pregunta`
  ADD CONSTRAINT `pregunta_ibfk_1` FOREIGN KEY (`id_test`) REFERENCES `test` (`id_test`);

--
-- Filtros para la tabla `profesor_curso`
--
ALTER TABLE `profesor_curso`
  ADD CONSTRAINT `profesor_curso_ibfk_1` FOREIGN KEY (`id_profesor`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE,
  ADD CONSTRAINT `profesor_curso_ibfk_2` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id_curso`) ON DELETE CASCADE;

--
-- Filtros para la tabla `progreso`
--
ALTER TABLE `progreso`
  ADD CONSTRAINT `progreso_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `progreso_ibfk_2` FOREIGN KEY (`id_tarea`) REFERENCES `tarea` (`id_tarea`);

--
-- Filtros para la tabla `resultado_test`
--
ALTER TABLE `resultado_test`
  ADD CONSTRAINT `resultado_test_ibfk_1` FOREIGN KEY (`id_test`) REFERENCES `test` (`id_test`),
  ADD CONSTRAINT `resultado_test_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `tarea`
--
ALTER TABLE `tarea`
  ADD CONSTRAINT `tarea_ibfk_1` FOREIGN KEY (`id_asignatura`) REFERENCES `asignatura` (`id_asignatura`);

--
-- Filtros para la tabla `test`
--
ALTER TABLE `test`
  ADD CONSTRAINT `test_ibfk_1` FOREIGN KEY (`id_asignatura`) REFERENCES `asignatura` (`id_asignatura`),
  ADD CONSTRAINT `test_ibfk_2` FOREIGN KEY (`id_profesor`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `usuario_recompensa`
--
ALTER TABLE `usuario_recompensa`
  ADD CONSTRAINT `usuario_recompensa_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `usuario_recompensa_ibfk_2` FOREIGN KEY (`id_recompensa`) REFERENCES `recompensa` (`id_recompensa`);

--
-- Filtros para la tabla `usuario_tarea`
--
ALTER TABLE `usuario_tarea`
  ADD CONSTRAINT `usuario_tarea_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `usuario_tarea_ibfk_2` FOREIGN KEY (`id_tarea`) REFERENCES `tarea` (`id_tarea`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
