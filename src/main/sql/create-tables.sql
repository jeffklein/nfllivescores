CREATE TABLE `GAME` (
  `GAME_ID` int(11) NOT NULL,
  `DAY` varchar(3) NOT NULL,
  `TIME` varchar(8) NOT NULL,
  `STATUS` varchar(20) NOT NULL,
  `AWAY_TEAM` varchar(3) NOT NULL,
  `AWAY_SCORE` int(11) DEFAULT NULL,
  `HOME_TEAM` varchar(3) NOT NULL,
  `HOME_SCORE` int(11) DEFAULT NULL,
  `WEEK` varchar(20) NOT NULL,
  `YEAR` int(11) NOT NULL,
  PRIMARY KEY (`GAME_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE GAME
 ADD TIME_REMAINING VARCHAR(20) AFTER STATUS,
 ADD TEAM_IN_POSSESSION VARCHAR(3) AFTER HOME_SCORE;