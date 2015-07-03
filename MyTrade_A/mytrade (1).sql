-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 03. Jul 2015 um 16:03
-- Server Version: 5.6.21
-- PHP-Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `mytrade`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `aktie`
--

CREATE TABLE IF NOT EXISTS `aktie` (
`AktienID` int(11) NOT NULL,
  `Name` varchar(50) COLLATE utf8_bin NOT NULL,
  `Kuerzel` varchar(4) COLLATE utf8_bin NOT NULL,
  `Nominalpreis` int(11) NOT NULL,
  `Dividende` int(11) NOT NULL,
  `Fk_BenutzerID` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1652 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `aktie`
--

INSERT INTO `aktie` (`AktienID`, `Name`, `Kuerzel`, `Nominalpreis`, `Dividende`, `Fk_BenutzerID`) VALUES
(1, 'Google', 'goog', 50, 37, 1),
(2, 'Apple', 'appl', 80, 34, 7),
(3, 'Nikkei225', 'NIKK', 40, 31, 7),
(1617, 'test', 'test', 2, 23, 1),
(1619, 'Test', 'test', 1, 23, 3),
(1620, 'Test', 'test', 1, 23, 3),
(1621, 'Test', 'TEST', 1, 23, 3),
(1622, 'Test', 'TEST', 1, 23, 3),
(1623, 'Aktie', 'AKTI', 8, 29, 7),
(1624, 'Aktie', 'AKTI', 5, 29, 7),
(1625, 'Aktie', 'AKTI', 5, 29, 2),
(1626, 'ffdg', 'ffdg', 1, 38, 7),
(1627, 'Nike', 'NIKE', 8, 23, 7),
(1628, 'chd', 'vhhs', 1, 36, 7),
(1629, 'hfgh', 'gfhk', 1, 23, 7),
(1630, 'Hello', 'KITT', 1, 26, 7),
(1631, 'Jason', 'JASO', 44, 50, 7),
(1632, 'Jason', 'JASO', 44, 50, 7),
(1633, 'Google', 'goog', 10, 20, 2),
(1634, 'Google', 'goog', 10, 20, 7),
(1635, 'Apple', 'appl', 20, 30, 7),
(1636, 'test', 'test', 20, 30, 7),
(1637, 'Jason', 'JJJJ', 20, 20, 1),
(1638, 'Jason', 'JJJJ', 20, 20, 1),
(1639, 'Dennis', 'dsgh', 88, 50, 7),
(1640, 'Dennis', 'dsgh', 88, 50, 7),
(1641, 'Dennis', 'dsgh', 88, 50, 1),
(1642, 'Dennis', 'dsgh', 88, 50, 1),
(1643, 'Dennis', 'dsgh', 88, 50, 1),
(1644, 'Sven', 'sven', 50, 4, 7),
(1645, 'Sven', 'sven', 50, 4, 7),
(1646, 'Sven', 'sven', 50, 4, 1),
(1647, 'Sven', 'sven', 50, 4, 1),
(1648, 'Sven', 'sven', 50, 4, 1),
(1649, 'finaltest', 'ftes', 23, 50, 7),
(1650, 'finaltest', 'ftes', 23, 50, 1),
(1651, 'finaltest', 'ftes', 23, 50, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `auftrag`
--

CREATE TABLE IF NOT EXISTS `auftrag` (
`AuftragID` int(11) NOT NULL,
  `Preis` int(11) NOT NULL,
  `Fk_AktienID` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `auftrag`
--

INSERT INTO `auftrag` (`AuftragID`, `Preis`, `Fk_AktienID`) VALUES
(9, 30, 1626),
(38, 60, 1631),
(39, 60, 1632),
(42, 88, 1641),
(43, 88, 1642),
(44, 88, 1643),
(47, 50, 1646),
(48, 50, 1647),
(49, 50, 1648),
(51, 23, 1650),
(52, 23, 1651),
(53, 20, 1627);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer`
--

CREATE TABLE IF NOT EXISTS `benutzer` (
`BenutzerID` int(11) NOT NULL,
  `Name` varchar(50) COLLATE utf8_bin NOT NULL,
  `Vorname` varchar(50) COLLATE utf8_bin NOT NULL,
  `Login` varchar(50) COLLATE utf8_bin NOT NULL,
  `Passwort` char(40) COLLATE utf8_bin NOT NULL,
  `Fk_TypID` int(11) NOT NULL,
  `Kontostand` decimal(11,0) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `benutzer`
--

INSERT INTO `benutzer` (`BenutzerID`, `Name`, `Vorname`, `Login`, `Passwort`, `Fk_TypID`, `Kontostand`) VALUES
(1, 'Admintermeier', 'Adminius', 'Administrator', 'd033e22ae348aeb5660fc2140aec35850c4da997', 1, '361'),
(2, 'StandardUser', 'Tom', 'StandardUser', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, '328'),
(3, 'Hansmeier', 'Rudolph', 'rhans', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, '10118'),
(6, 'Weiersmeier', 'Sachalle', 'sweier', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, '0'),
(7, 'Nordmann', 'Christina', 'Cnord', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, '-168'),
(8, 'Wurst', 'Hans', 'hansWurst', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, '10000'),
(9, 'Nordmann', 'Christina', 'C', 'ffa6706ff2127a749973072756f83c532e43ed02', 1, '10000'),
(10, 'n', 'b', 'l', 'e9d71f5ee7c92d6dc9e92ffdad17b8bd49418f98', 1, '10000'),
(11, 'fdgdf', 'ffdg', 'dfgdg', '72f77e84ba0149b2af1051f1318128dccf60ab60', 1, '10000'),
(12, 'dfgdfg', 'dfgdfg', 'dfgdfg', 'bfc896e1ecd35d326871e60c70649a1e99571ffa', 1, '10000'),
(13, 'Maggion', 'Gweni', 'Gwee', 'ffa6706ff2127a749973072756f83c532e43ed02', 2, '10000'),
(14, 'Maggion', 'Christina', 'Chris', 'ffa6706ff2127a749973072756f83c532e43ed02', 1, '10000'),
(15, 'fhj', 'jnkjh', 'jhkjh', 'f855c87a3d6320302982f2bbed245941e4ed1456', 2, '10000'),
(16, 'ghfh', 'gfhfgh', 'fgh', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', 1, '10000'),
(17, 'Nordmann', 'Christina', '123', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', 1, '10000'),
(18, 'Nordmann', 'Christina', '1234', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 1, '10000'),
(19, 'Angst', 'Jason', '123123', '601f1889667efaebb33b8c12572835da3f027f78', 2, '10000'),
(20, 'Angst', 'lol', '456456', 'ce6cc32b02e9ab8dc1a8101d63093e5f1d4c01b0', 2, '10000'),
(21, 'Angst', 'lol', '789711', '601f1889667efaebb33b8c12572835da3f027f78', 2, '10000'),
(22, 'Angst', 'Jason', 'plplplpl', '5c59871d806990b6551ea19248420a2e0d5304d1', 2, '10000'),
(23, 'MiniMe', 'DeineMudda', 'was', '403926033d001b5279df37cbbe5287b7c7c267fa', 1, '10000'),
(24, '56454', '3541351', '3513513', 'da39a3ee5e6b4b0d3255bfef95601890afd80709', 1, '10000'),
(25, 'Nordmann', 'Patricia', 'Pati', '339e3fe47a4c758de119f132acd819f18ea58ca7', 1, '10000');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `typ`
--

CREATE TABLE IF NOT EXISTS `typ` (
`TypID` int(11) NOT NULL,
  `Typname` varchar(20) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `typ`
--

INSERT INTO `typ` (`TypID`, `Typname`) VALUES
(1, 'Administrator'),
(2, 'Aktienhändler');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `aktie`
--
ALTER TABLE `aktie`
 ADD PRIMARY KEY (`AktienID`), ADD KEY `Fk_BenutzerID` (`Fk_BenutzerID`);

--
-- Indizes für die Tabelle `auftrag`
--
ALTER TABLE `auftrag`
 ADD PRIMARY KEY (`AuftragID`), ADD KEY `Fk_AktienID` (`Fk_AktienID`);

--
-- Indizes für die Tabelle `benutzer`
--
ALTER TABLE `benutzer`
 ADD PRIMARY KEY (`BenutzerID`), ADD KEY `Fk_TypID` (`Fk_TypID`);

--
-- Indizes für die Tabelle `typ`
--
ALTER TABLE `typ`
 ADD PRIMARY KEY (`TypID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `aktie`
--
ALTER TABLE `aktie`
MODIFY `AktienID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1652;
--
-- AUTO_INCREMENT für Tabelle `auftrag`
--
ALTER TABLE `auftrag`
MODIFY `AuftragID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=54;
--
-- AUTO_INCREMENT für Tabelle `benutzer`
--
ALTER TABLE `benutzer`
MODIFY `BenutzerID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT für Tabelle `typ`
--
ALTER TABLE `typ`
MODIFY `TypID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `aktie`
--
ALTER TABLE `aktie`
ADD CONSTRAINT `aktie_ibfk_1` FOREIGN KEY (`Fk_BenutzerID`) REFERENCES `benutzer` (`BenutzerID`);

--
-- Constraints der Tabelle `auftrag`
--
ALTER TABLE `auftrag`
ADD CONSTRAINT `auftrag_ibfk_1` FOREIGN KEY (`Fk_AktienID`) REFERENCES `aktie` (`AktienID`);

--
-- Constraints der Tabelle `benutzer`
--
ALTER TABLE `benutzer`
ADD CONSTRAINT `benutzer_ibfk_1` FOREIGN KEY (`Fk_TypID`) REFERENCES `typ` (`TypID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

/* User erstellen und Rechte */
GRANT
	INSERT, SELECT, UPDATE, DELETE
    ON `mytrade`.*
    TO "MyTradeUser"@"%" IDENTIFIED BY "123456"
