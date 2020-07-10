-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 11, 2020 at 05:32 AM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chat_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblloginauth`
--

CREATE TABLE `tblloginauth` (
  `authID` int(11) NOT NULL,
  `authEmail` varchar(50) NOT NULL,
  `authPassword` varchar(50) NOT NULL,
  `authStatus` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblloginauth`
--

INSERT INTO `tblloginauth` (`authID`, `authEmail`, `authPassword`, `authStatus`) VALUES
(2, 'fareed@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', 0),
(5, 'raja@gmail.com', '202cb962ac59075b964b07152d234b70', 0),
(6, 'em@gmail.com', '202cb962ac59075b964b07152d234b70', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tblnodes`
--

CREATE TABLE `tblnodes` (
  `node_id` int(11) NOT NULL,
  `node_name` varchar(150) NOT NULL,
  `node_by` varchar(50) NOT NULL,
  `node_to` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblnodes`
--

INSERT INTO `tblnodes` (`node_id`, `node_name`, `node_by`, `node_to`) VALUES
(1020, 'raja_em', 'raja@gmail.com', 'em@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `tblnotifications`
--

CREATE TABLE `tblnotifications` (
  `notify_id` int(11) NOT NULL,
  `notify_by` varchar(50) NOT NULL,
  `notify_to` varchar(50) NOT NULL,
  `notify_title` varchar(50) NOT NULL,
  `notify_message` varchar(50) NOT NULL,
  `notify_status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblnotifications`
--

INSERT INTO `tblnotifications` (`notify_id`, `notify_by`, `notify_to`, `notify_title`, `notify_message`, `notify_status`) VALUES
(4, 'raja@gmail.com', 'em@gmail.com', 'Chat App', 'New Message From raja@gmail.com ', 1),
(5, 'em@gmail.com', 'fareed@gmail.com', 'Chat App', 'New Message From em@gmail.com ', 2),
(6, 'em@gmail.com', 'raja@gmail.com', 'Chat App', 'New Message From em@gmail.com ', 2),
(7, 'em@gmail.com', 'fareed@gmail.com', 'Chat App', 'New Message From em@gmail.com ', 2),
(8, 'em@gmail.com', 'raja@gmail.com', 'Chat App', 'New Message From em@gmail.com ', 2);

-- --------------------------------------------------------

--
-- Table structure for table `tblpost`
--

CREATE TABLE `tblpost` (
  `postID` int(11) NOT NULL,
  `postBy` varchar(50) NOT NULL,
  `postTitle` varchar(100) NOT NULL,
  `postLocation` varchar(200) NOT NULL,
  `postGender` varchar(10) NOT NULL,
  `postAge` varchar(10) NOT NULL,
  `postTime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblpost`
--

INSERT INTO `tblpost` (`postID`, `postBy`, `postTitle`, `postLocation`, `postGender`, `postAge`, `postTime`) VALUES
(4, 'raja@gmail.com', 'testing', 'Londonderry Drive, Killarney Heights NSW, Australia', 'Female', '30-35', '2020-03-05 08:39:52'),
(5, 'fareed@gmail.com', 'Demo', 'Karsaz Road, Karachi, Pakistan', 'Male', '36-41', '2020-03-06 04:07:39'),
(6, 'em@gmail.com', 'Notification Testing', 'Karalta Crescent, Belrose NSW, Australia', 'Male', '18-23', '2020-03-08 09:22:28'),
(7, 'em@gmail.com', 'Notification 2', 'Karachi - Hyderabad Motorway, Karachi, Pakistan', 'Male', '18-23', '2020-03-08 09:32:27'),
(8, 'em@gmail.com', 'Notification 3', 'Karachi Street, Riverstone NSW, Australia', 'Male', '18-23', '2020-03-08 09:33:26'),
(9, 'em@gmail.com', 'Notification 2', 'Karachi Street, Riverstone NSW, Australia', 'Male', '18-23', '2020-03-08 09:34:05'),
(10, 'em@gmail.com', 'demo notification', 'Islamabad Expressway, Islamabad, Pakistan', 'Male', '18-23', '2020-03-08 09:37:45'),
(11, 'em@gmail.com', 'demo3', 'Help Street, Chatswood NSW, Australia', 'Male', '18-23', '2020-03-08 09:38:51');

-- --------------------------------------------------------

--
-- Table structure for table `tblusers`
--

CREATE TABLE `tblusers` (
  `userID` int(11) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `userEmail` varchar(50) NOT NULL,
  `userPassword` varchar(50) NOT NULL,
  `userAge` int(11) NOT NULL,
  `userGender` varchar(10) NOT NULL,
  `userImage` varchar(100) NOT NULL,
  `userDeviceToken` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblusers`
--

INSERT INTO `tblusers` (`userID`, `userName`, `userEmail`, `userPassword`, `userAge`, `userGender`, `userImage`, `userDeviceToken`) VALUES
(1, 'Fareed', 'fareed@gmail.com', '26c8b044ee0b58dbd63f0162b5a6d52d', 21, 'Male', 'uploads/userImage/1583344994.jpg', 'c1KGITPGYgE:APA91bEx715-0hV3Xs_YptGq-aALs3GzyykQiRd17c2HDz95zl3nJTJWvKFREBNO3V3ybVN-CS1dwRoTHgXMVu9P'),
(4, 'raja', 'raja@gmail.com', '202cb962ac59075b964b07152d234b70', 22, 'Male', 'uploads/userImage/1583576513.jpg', 'c1KGITPGYgE:APA91bEx715-0hV3Xs_YptGq-aALs3GzyykQiRd17c2HDz95zl3nJTJWvKFREBNO3V3ybVN-CS1dwRoTHgXMVu9P'),
(5, 'emulator', 'em@gmail.com', '202cb962ac59075b964b07152d234b70', 23, 'Male', 'uploads/userImage/1583581375.jpg', 'cixBYOHh47-KojhOmeKaaN:APA91bE6La5eIbrAr4UeQmZEmPF25tYLOf7j8tqwG7uILBpPDjE12dJugPyl5gYUY5lyUtp3BnMFk');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblloginauth`
--
ALTER TABLE `tblloginauth`
  ADD PRIMARY KEY (`authID`),
  ADD UNIQUE KEY `authEmail` (`authEmail`);

--
-- Indexes for table `tblnodes`
--
ALTER TABLE `tblnodes`
  ADD PRIMARY KEY (`node_id`);

--
-- Indexes for table `tblnotifications`
--
ALTER TABLE `tblnotifications`
  ADD PRIMARY KEY (`notify_id`);

--
-- Indexes for table `tblpost`
--
ALTER TABLE `tblpost`
  ADD PRIMARY KEY (`postID`);

--
-- Indexes for table `tblusers`
--
ALTER TABLE `tblusers`
  ADD PRIMARY KEY (`userID`),
  ADD UNIQUE KEY `userEmail` (`userEmail`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblloginauth`
--
ALTER TABLE `tblloginauth`
  MODIFY `authID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tblnodes`
--
ALTER TABLE `tblnodes`
  MODIFY `node_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1021;

--
-- AUTO_INCREMENT for table `tblnotifications`
--
ALTER TABLE `tblnotifications`
  MODIFY `notify_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tblpost`
--
ALTER TABLE `tblpost`
  MODIFY `postID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `tblusers`
--
ALTER TABLE `tblusers`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
