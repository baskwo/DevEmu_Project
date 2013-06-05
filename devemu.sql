/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_3306
Source Server Version : 50524
Source Host           : 127.0.0.1:3306
Source Database       : devemu

Target Server Type    : MYSQL
Target Server Version : 50524
File Encoding         : 65001

Date: 2013-06-05 00:07:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `accounts`
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `pseudo` varchar(50) DEFAULT NULL,
  `question` varchar(50) DEFAULT NULL,
  `aboTime` bigint(11) DEFAULT NULL,
  `players` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of accounts
-- ----------------------------
INSERT INTO `accounts` VALUES ('1', 'test', 'test', 'Admin', 'Suis-je M.Test?', '0', '1:1;');

-- ----------------------------
-- Table structure for `ban`
-- ----------------------------
DROP TABLE IF EXISTS `ban`;
CREATE TABLE `ban` (
  `id` int(11) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `banTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ban
-- ----------------------------

-- ----------------------------
-- Table structure for `expsteps`
-- ----------------------------
DROP TABLE IF EXISTS `expsteps`;
CREATE TABLE `expsteps` (
  `level` int(11) NOT NULL,
  `player` bigint(20) DEFAULT NULL,
  `job` bigint(20) DEFAULT NULL,
  `drago` bigint(20) DEFAULT NULL,
  `alignement` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of expsteps
-- ----------------------------
INSERT INTO `expsteps` VALUES ('1', '0', '0', '0', '0');
INSERT INTO `expsteps` VALUES ('2', '110', '50', '600', '500');
INSERT INTO `expsteps` VALUES ('3', '650', '140', '1750', '1500');
INSERT INTO `expsteps` VALUES ('4', '1500', '271', '2750', '3000');
INSERT INTO `expsteps` VALUES ('5', '2800', '441', '4000', '5000');
INSERT INTO `expsteps` VALUES ('6', '4800', '653', '5500', '7500');
INSERT INTO `expsteps` VALUES ('7', '7300', '905', '7250', '10000');
INSERT INTO `expsteps` VALUES ('8', '10500', '1199', '9250', '12500');
INSERT INTO `expsteps` VALUES ('9', '14500', '1543', '11500', '15000');
INSERT INTO `expsteps` VALUES ('10', '19200', '1911', '14000', '17500');
INSERT INTO `expsteps` VALUES ('11', '25200', '2330', '16750', '-1');
INSERT INTO `expsteps` VALUES ('12', '32600', '2792', '19750', '-1');
INSERT INTO `expsteps` VALUES ('13', '41000', '3297', '23000', '-1');
INSERT INTO `expsteps` VALUES ('14', '50500', '3840', '26500', '-1');
INSERT INTO `expsteps` VALUES ('15', '61000', '4439', '30250', '-1');
INSERT INTO `expsteps` VALUES ('16', '75000', '5078', '34250', '-1');
INSERT INTO `expsteps` VALUES ('17', '91000', '5762', '38500', '-1');
INSERT INTO `expsteps` VALUES ('18', '115000', '6493', '43000', '-1');
INSERT INTO `expsteps` VALUES ('19', '142000', '7280', '47750', '-1');
INSERT INTO `expsteps` VALUES ('20', '171000', '8097', '52750', '-1');
INSERT INTO `expsteps` VALUES ('21', '202000', '8980', '58000', '-1');
INSERT INTO `expsteps` VALUES ('22', '235000', '9898', '63500', '-1');
INSERT INTO `expsteps` VALUES ('23', '270000', '10875', '69250', '-1');
INSERT INTO `expsteps` VALUES ('24', '310000', '11903', '75250', '-1');
INSERT INTO `expsteps` VALUES ('25', '353000', '12985', '81500', '-1');
INSERT INTO `expsteps` VALUES ('26', '398500', '14122', '88000', '-1');
INSERT INTO `expsteps` VALUES ('27', '448000', '15315', '94750', '-1');
INSERT INTO `expsteps` VALUES ('28', '503000', '16564', '101750', '-1');
INSERT INTO `expsteps` VALUES ('29', '561000', '17873', '109000', '-1');
INSERT INTO `expsteps` VALUES ('30', '621600', '19242', '116500', '-1');
INSERT INTO `expsteps` VALUES ('31', '687000', '20672', '124250', '-1');
INSERT INTO `expsteps` VALUES ('32', '755000', '22166', '132250', '-1');
INSERT INTO `expsteps` VALUES ('33', '829000', '23726', '140500', '-1');
INSERT INTO `expsteps` VALUES ('34', '910000', '25353', '149000', '-1');
INSERT INTO `expsteps` VALUES ('35', '1000000', '27048', '157750', '-1');
INSERT INTO `expsteps` VALUES ('36', '1100000', '28815', '166750', '-1');
INSERT INTO `expsteps` VALUES ('37', '1240000', '30656', '176000', '-1');
INSERT INTO `expsteps` VALUES ('38', '1400000', '32572', '185500', '-1');
INSERT INTO `expsteps` VALUES ('39', '1580000', '345660', '195250', '-1');
INSERT INTO `expsteps` VALUES ('40', '1780000', '36641', '205250', '-1');
INSERT INTO `expsteps` VALUES ('41', '2000000', '38800', '215500', '-1');
INSERT INTO `expsteps` VALUES ('42', '2250000', '41044', '226000', '-1');
INSERT INTO `expsteps` VALUES ('43', '2530000', '43378', '236750', '-1');
INSERT INTO `expsteps` VALUES ('44', '2850000', '45804', '247750', '-1');
INSERT INTO `expsteps` VALUES ('45', '3200000', '48325', '249000', '-1');
INSERT INTO `expsteps` VALUES ('46', '3570000', '50946', '270500', '-1');
INSERT INTO `expsteps` VALUES ('47', '3960000', '53669', '282250', '-1');
INSERT INTO `expsteps` VALUES ('48', '4400000', '56498', '294250', '-1');
INSERT INTO `expsteps` VALUES ('49', '4860000', '59437', '306500', '-1');
INSERT INTO `expsteps` VALUES ('50', '5350000', '62491', '319000', '-1');
INSERT INTO `expsteps` VALUES ('51', '5860000', '65664', '331750', '-1');
INSERT INTO `expsteps` VALUES ('52', '6390000', '68960', '344750', '-1');
INSERT INTO `expsteps` VALUES ('53', '6950000', '72385', '358000', '-1');
INSERT INTO `expsteps` VALUES ('54', '7530000', '75943', '371500', '-1');
INSERT INTO `expsteps` VALUES ('55', '8130000', '79640', '385250', '-1');
INSERT INTO `expsteps` VALUES ('56', '8765100', '83482', '399250', '-1');
INSERT INTO `expsteps` VALUES ('57', '9420000', '87475', '413500', '-1');
INSERT INTO `expsteps` VALUES ('58', '10150000', '91624', '428000', '-1');
INSERT INTO `expsteps` VALUES ('59', '10894000', '95937', '442750', '-1');
INSERT INTO `expsteps` VALUES ('60', '11650000', '100421', '457750', '-1');
INSERT INTO `expsteps` VALUES ('61', '12450000', '105082', '473000', '-1');
INSERT INTO `expsteps` VALUES ('62', '13280000', '109930', '488500', '-1');
INSERT INTO `expsteps` VALUES ('63', '14130000', '114971', '504250', '-1');
INSERT INTO `expsteps` VALUES ('64', '15170000', '120215', '520250', '-1');
INSERT INTO `expsteps` VALUES ('65', '16251000', '125671', '536500', '-1');
INSERT INTO `expsteps` VALUES ('66', '17377000', '131348', '553000', '-1');
INSERT INTO `expsteps` VALUES ('67', '18553000', '137256', '569750', '-1');
INSERT INTO `expsteps` VALUES ('68', '19778000', '143407', '586750', '-1');
INSERT INTO `expsteps` VALUES ('69', '21055000', '149811', '604000', '-1');
INSERT INTO `expsteps` VALUES ('70', '22385000', '156481', '621500', '-1');
INSERT INTO `expsteps` VALUES ('71', '23529000', '163429', '639250', '-1');
INSERT INTO `expsteps` VALUES ('72', '25209000', '170669', '657250', '-1');
INSERT INTO `expsteps` VALUES ('73', '26707000', '178214', '675500', '-1');
INSERT INTO `expsteps` VALUES ('74', '28264000', '186080', '694000', '-1');
INSERT INTO `expsteps` VALUES ('75', '29882000', '194283', '712750', '-1');
INSERT INTO `expsteps` VALUES ('76', '31563000', '202839', '731750', '-1');
INSERT INTO `expsteps` VALUES ('77', '33307000', '211765', '751000', '-1');
INSERT INTO `expsteps` VALUES ('78', '35118000', '221082', '770500', '-1');
INSERT INTO `expsteps` VALUES ('79', '36997000', '230808', '790250', '-1');
INSERT INTO `expsteps` VALUES ('80', '38945000', '240964', '810250', '-1');
INSERT INTO `expsteps` VALUES ('81', '40965000', '251574', '830500', '-1');
INSERT INTO `expsteps` VALUES ('82', '43059000', '262660', '851000', '-1');
INSERT INTO `expsteps` VALUES ('83', '45229000', '274248', '871750', '-1');
INSERT INTO `expsteps` VALUES ('84', '47476000', '286364', '892750', '-1');
INSERT INTO `expsteps` VALUES ('85', '49803000', '299037', '914000', '-1');
INSERT INTO `expsteps` VALUES ('86', '52211000', '312297', '935500', '-1');
INSERT INTO `expsteps` VALUES ('87', '54704000', '326175', '957250', '-1');
INSERT INTO `expsteps` VALUES ('88', '57284000', '340705', '979250', '-1');
INSERT INTO `expsteps` VALUES ('89', '59952000', '355924', '1001500', '-1');
INSERT INTO `expsteps` VALUES ('90', '62712000', '371870', '1024000', '-1');
INSERT INTO `expsteps` VALUES ('91', '65565000', '388582', '1046750', '-1');
INSERT INTO `expsteps` VALUES ('92', '68514000', '406106', '1069750', '-1');
INSERT INTO `expsteps` VALUES ('93', '71561000', '424486', '1093000', '-1');
INSERT INTO `expsteps` VALUES ('94', '74710000', '443772', '1116500', '-1');
INSERT INTO `expsteps` VALUES ('95', '77963000', '464016', '1140250', '-1');
INSERT INTO `expsteps` VALUES ('96', '81323000', '485274', '1164250', '-1');
INSERT INTO `expsteps` VALUES ('97', '84792000', '507604', '1188500', '-1');
INSERT INTO `expsteps` VALUES ('98', '88374000', '531071', '1213000', '-1');
INSERT INTO `expsteps` VALUES ('99', '92071000', '555541', '1237750', '-1');
INSERT INTO `expsteps` VALUES ('100', '95886000', '581687', '1262750', '-1');
INSERT INTO `expsteps` VALUES ('101', '99823000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('102', '103885000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('103', '108075000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('104', '112396000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('105', '116853000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('106', '121447000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('107', '126184000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('108', '131066000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('109', '136098000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('110', '141283000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('111', '146626000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('112', '152130000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('113', '157800000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('114', '163640000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('115', '169655000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('116', '175848000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('117', '182225000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('118', '188791000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('119', '195550000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('120', '202507000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('121', '209667000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('122', '217037000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('123', '224620000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('124', '232424000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('125', '240452000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('126', '248712000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('127', '257209000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('128', '265949000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('129', '274939000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('130', '284186000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('131', '293694000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('132', '303473000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('133', '313527000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('134', '323866000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('135', '334495000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('136', '345423000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('137', '356657000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('138', '368206000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('139', '380076000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('140', '392278000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('141', '404818000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('142', '417706000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('143', '430952000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('144', '444564000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('145', '458551000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('146', '472924000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('147', '487693000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('148', '502867000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('149', '518458000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('150', '534476000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('151', '502867000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('152', '567839000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('153', '585206000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('154', '603047000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('155', '621374000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('156', '640199000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('157', '659536000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('158', '679398000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('159', '699798000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('160', '720751000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('161', '742772000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('162', '764374000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('163', '787074000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('164', '810387000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('165', '834329000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('166', '858917000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('167', '884167000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('168', '910098000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('169', '936727000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('170', '964073000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('171', '992154000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('172', '1020991000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('173', '1050603000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('174', '1081010000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('175', '1112235000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('176', '1144298000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('177', '1177222000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('178', '1211030000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('179', '1245745000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('180', '1281393000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('181', '1317997000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('182', '1355584000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('183', '1404179000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('184', '1463811000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('185', '1534506000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('186', '1616294000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('187', '1709205000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('188', '1813267000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('189', '1928513000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('190', '2054975000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('191', '2192686000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('192', '2341679000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('193', '2501990000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('194', '2673655000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('195', '2856710000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('196', '3051194000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('197', '3257146000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('198', '3474606000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('199', '3703616000', '-1', '-1', '-1');
INSERT INTO `expsteps` VALUES ('200', '7407232000', '-1', '-1', '-1');

-- ----------------------------
-- Table structure for `players`
-- ----------------------------
DROP TABLE IF EXISTS `players`;
CREATE TABLE `players` (
  `guid` int(11) NOT NULL,
  `gameGuid` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `gfx` int(11) DEFAULT NULL,
  `marchant` tinyint(1) DEFAULT NULL,
  `dead` tinyint(1) DEFAULT NULL,
  `countDead` int(11) DEFAULT NULL,
  `classe` int(11) DEFAULT NULL,
  `sexe` tinyint(1) DEFAULT NULL,
  `xp` bigint(20) DEFAULT NULL,
  `kamas` bigint(20) DEFAULT NULL,
  `capitals` int(11) DEFAULT NULL,
  `spellPoints` int(11) DEFAULT NULL,
  `pdv` int(11) DEFAULT NULL,
  `energy` int(11) DEFAULT NULL,
  `align` varchar(50) DEFAULT NULL,
  `stats` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of players
-- ----------------------------
INSERT INTO `players` VALUES ('1', '1', 'Test', '1', '110', '0', '0', '0', '1', '1', '0', '0', '0', '0', '50', '3000', '0;0;0;0;0', null);
