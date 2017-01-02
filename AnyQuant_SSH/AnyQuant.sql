-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Jan 02, 2017 at 07:30 PM
-- Server version: 5.5.42
-- PHP Version: 5.6.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `AnyQuant`
--
CREATE DATABASE IF NOT EXISTS `AnyQuant` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `AnyQuant`;

-- --------------------------------------------------------

--
-- Table structure for table `benchmark`
--

DROP TABLE IF EXISTS `benchmark`;
CREATE TABLE `benchmark` (
  `code` varchar(255) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `benchmarkdata`
--

DROP TABLE IF EXISTS `benchmarkdata`;
CREATE TABLE `benchmarkdata` (
  `date` date NOT NULL,
  `code` varchar(255) COLLATE utf8_bin NOT NULL,
  `changePct` double DEFAULT NULL,
  `changeValue` double DEFAULT NULL,
  `close` double DEFAULT NULL,
  `high` double DEFAULT NULL,
  `low` double DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `open` double DEFAULT NULL,
  `preClose` double DEFAULT NULL,
  `turnoverValue` double DEFAULT NULL,
  `turnoverVol` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `factor`
--

DROP TABLE IF EXISTS `factor`;
CREATE TABLE `factor` (
  `date` date NOT NULL,
  `code` varchar(255) COLLATE utf8_bin NOT NULL,
  `darec` double DEFAULT NULL,
  `ma10` double DEFAULT NULL,
  `ma120` double DEFAULT NULL,
  `ma5` double DEFAULT NULL,
  `ma60` double DEFAULT NULL,
  `pb` double DEFAULT NULL,
  `pcf` double DEFAULT NULL,
  `pe` double DEFAULT NULL,
  `ps` double DEFAULT NULL,
  `psy` double DEFAULT NULL,
  `rec` double DEFAULT NULL,
  `vol10` double DEFAULT NULL,
  `vol120` double DEFAULT NULL,
  `vol5` double DEFAULT NULL,
  `vol60` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `optionalstock`
--

DROP TABLE IF EXISTS `optionalstock`;
CREATE TABLE `optionalstock` (
  `userID` int(11) NOT NULL,
  `code` varchar(255) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `id` int(11) NOT NULL,
  `report` tinyblob,
  `userid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `code` varchar(255) COLLATE utf8_bin NOT NULL,
  `board` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `listDate` date DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `officeAddr` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `primeOperating` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `region` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `totalShares` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `stockdata`
--

DROP TABLE IF EXISTS `stockdata`;
CREATE TABLE `stockdata` (
  `date` date NOT NULL,
  `code` varchar(255) COLLATE utf8_bin NOT NULL,
  `accAdjFactor` double DEFAULT NULL,
  `amplitude` double DEFAULT NULL,
  `changeRate` double DEFAULT NULL,
  `cirMarketValue` double DEFAULT NULL,
  `close` double DEFAULT NULL,
  `high` double DEFAULT NULL,
  `low` double DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `open` double DEFAULT NULL,
  `pb` double DEFAULT NULL,
  `pe` double DEFAULT NULL,
  `preClose` double DEFAULT NULL,
  `totalMarketValue` double DEFAULT NULL,
  `turnoverRate` double DEFAULT NULL,
  `turnoverValue` double DEFAULT NULL,
  `turnoverVol` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `password`) VALUES
(1, 'qiang', '123456');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `benchmark`
--
ALTER TABLE `benchmark`
  ADD PRIMARY KEY (`code`);

--
-- Indexes for table `benchmarkdata`
--
ALTER TABLE `benchmarkdata`
  ADD PRIMARY KEY (`date`,`code`);

--
-- Indexes for table `factor`
--
ALTER TABLE `factor`
  ADD PRIMARY KEY (`date`,`code`);

--
-- Indexes for table `optionalstock`
--
ALTER TABLE `optionalstock`
  ADD PRIMARY KEY (`userID`,`code`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`code`);

--
-- Indexes for table `stockdata`
--
ALTER TABLE `stockdata`
  ADD PRIMARY KEY (`date`,`code`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
