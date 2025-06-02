-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-11-2024 a las 18:06:54
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
-- Base de datos: `pedidos`
--
CREATE DATABASE IF NOT EXISTS `pedidos` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `pedidos`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

DROP TABLE IF EXISTS `categorias`;
CREATE TABLE `categorias` (
  `CodCat` int(11) NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Descripcion` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`CodCat`, `Nombre`, `Descripcion`) VALUES
(1, 'Comida', 'Platos e ingredientes'),
(2, 'Bedidas sin', 'Bebidas sin alcohol'),
(3, 'Bebidas con', 'Bebidas con alcohol');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
CREATE TABLE `pedidos` (
  `CodPed` int(11) NOT NULL,
  `Fecha` datetime NOT NULL,
  `Enviado` int(11) NOT NULL,
  `Restaurante` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidosproductos`
--

DROP TABLE IF EXISTS `pedidosproductos`;
CREATE TABLE `pedidosproductos` (
  `CodPredProd` int(11) NOT NULL,
  `CodPed` int(11) NOT NULL,
  `CodProd` int(11) NOT NULL,
  `Unidades` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

DROP TABLE IF EXISTS `productos`;
CREATE TABLE `productos` (
  `CodProd` int(11) NOT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `Descripcion` varchar(90) NOT NULL,
  `Peso` float NOT NULL,
  `Stock` int(11) NOT NULL,
  `CodCat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`CodProd`, `Nombre`, `Descripcion`, `Peso`, `Stock`, `CodCat`) VALUES
(1, 'Harina', '8 paquetes de 1kg de harina cada uno', 8, 100, 1),
(2, 'Azúcar', '20 paquetes de 1kg cada uno', 20, 3, 1),
(3, 'Agua 0.5', '100 botellas de 0.5 litros cada una', 51, 100, 2),
(4, 'Agua 1.5', '20 botellas de 1.5 litros cada una', 31, 50, 2),
(5, 'Cerveza Alhambra tercio', '24 botellas de 33cl', 10, 0, 3),
(6, 'Vino tinto Rioja 0.75', '6 botellas de 0.75 ', 5.5, 10, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `restaurantes`
--

DROP TABLE IF EXISTS `restaurantes`;
CREATE TABLE `restaurantes` (
  `CodRes` int(11) NOT NULL,
  `Correo` varchar(90) NOT NULL,
  `Clave` varchar(45) NOT NULL,
  `Pais` varchar(45) NOT NULL,
  `CP` int(5) DEFAULT NULL,
  `Ciudad` varchar(45) NOT NULL,
  `Direccion` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `restaurantes`
--

INSERT INTO `restaurantes` (`CodRes`, `Correo`, `Clave`, `Pais`, `CP`, `Ciudad`, `Direccion`) VALUES
(1, 'madrid1@empresa.com', '1234', 'España', 28002, 'Madrid', 'C/ Padre  Claret, 8'),
(2, 'cadiz1@empresa.com', '1234', 'España', 11001, 'Cádiz', 'C/ Portales, 2 '),
(3, 'dwes@afleal.es', '1234', 'España', 15001, 'A Coruña', 'Plaza de María Pita, 1');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`CodCat`),
  ADD UNIQUE KEY `UN_NOM_CAT` (`Nombre`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`CodPed`),
  ADD KEY `Restaurante` (`Restaurante`);

--
-- Indices de la tabla `pedidosproductos`
--
ALTER TABLE `pedidosproductos`
  ADD PRIMARY KEY (`CodPredProd`),
  ADD KEY `CodPed` (`CodPed`),
  ADD KEY `CodProd` (`CodProd`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`CodProd`);

--
-- Indices de la tabla `restaurantes`
--
ALTER TABLE `restaurantes`
  ADD PRIMARY KEY (`CodRes`),
  ADD UNIQUE KEY `UN_RES_COR` (`Correo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `CodCat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `CodPed` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pedidosproductos`
--
ALTER TABLE `pedidosproductos`
  MODIFY `CodPredProd` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `CodProd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `restaurantes`
--
ALTER TABLE `restaurantes`
  MODIFY `CodRes` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`Restaurante`) REFERENCES `restaurantes` (`CodRes`);

--
-- Filtros para la tabla `pedidosproductos`
--
ALTER TABLE `pedidosproductos`
  ADD CONSTRAINT `pedidosproductos_ibfk_1` FOREIGN KEY (`CodPed`) REFERENCES `pedidos` (`CodPed`),
  ADD CONSTRAINT `pedidosproductos_ibfk_2` FOREIGN KEY (`CodProd`) REFERENCES `productos` (`CodProd`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
