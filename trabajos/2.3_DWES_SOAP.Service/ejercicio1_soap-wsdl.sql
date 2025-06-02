-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-05-2025 a las 15:47:19
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
-- Base de datos: `ejercicio1_soap-wsdl`
--
CREATE DATABASE IF NOT EXISTS `ejercicio1_soap-wsdl` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE `ejercicio1_soap-wsdl`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tm_usuario`
--

CREATE TABLE `tm_usuario` (
  `usu_id` int(11) NOT NULL,
  `usu_nom` varchar(150) NOT NULL,
  `usu_ape` varchar(150) NOT NULL,
  `usu_correo` varchar(250) NOT NULL,
  `est` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tm_usuario`
--

INSERT INTO `tm_usuario` (`usu_id`, `usu_nom`, `usu_ape`, `usu_correo`, `est`) VALUES
(1, 'TEST', 'TEST APELLIDO', 'TEST@CORREO.COM', 1),
(3, 'TEST', 'TEST2', 'TEST2@correo.com', 1),
(4, 'SOAPUI', 'SOAPUI API', 'correo@ui.com', 1),
(5, 'SOAPUIx', 'SOAPUIx API', 'correox@ui.com', 1),
(6, 'Consumo test', 'Consumo test ape', 'consumo_test@ui.com', 1),
(7, 'Alexandra', 'Carro Valledor', 'alexandra_cv@correo.com', 1),
(10, 'Prueba', 'Formulario', 'pruebaformulario@correo.com', 1),
(11, 'Ana', 'Mantequilla', 'ana@mantequilla.com', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tm_usuario`
--
ALTER TABLE `tm_usuario`
  ADD PRIMARY KEY (`usu_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tm_usuario`
--
ALTER TABLE `tm_usuario`
  MODIFY `usu_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
