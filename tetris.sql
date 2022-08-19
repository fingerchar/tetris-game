-- MySQL dump 10.13  Distrib 8.0.24, for Linux (x86_64)
--
-- Host: localhost    Database: back_tetris
-- ------------------------------------------------------
-- Server version	8.0.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tt_contract_nft`
--

DROP TABLE IF EXISTS `tt_contract_nft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tt_contract_nft` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '表id',
  `contract_id` bigint NOT NULL DEFAULT '0' COMMENT '合约id',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '合约地址',
  `name` varchar(200) NOT NULL DEFAULT '',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `category_id` bigint NOT NULL DEFAULT '0' COMMENT '分类id',
  `storage_id` bigint DEFAULT '0' COMMENT '图片保存Id',
  `token_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '对应tokenId',
  `quantity` bigint NOT NULL DEFAULT '0' COMMENT '数量',
  `royalties` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '版权',
  `nft_verify` int NOT NULL DEFAULT '0' COMMENT '是否已验证',
  `is_sync` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已同步链',
  `type` int NOT NULL DEFAULT '0' COMMENT '类型',
  `creator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'nft拥有者',
  `tx_hash` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '区块链交易hash值',
  `metadata_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '资源地址',
  `metadata_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '资源内容',
  `get_meta_times` int DEFAULT '0' COMMENT '获取资源次数',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` bigint NOT NULL DEFAULT '0',
  `update_time` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index2` (`address`),
  KEY `index3` (`creator`),
  KEY `index4` (`token_id`),
  KEY `index5` (`is_sync`),
  KEY `index6` (`create_time`),
  KEY `nft_verify` (`nft_verify`),
  KEY `deleted` (`deleted`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tt_contract_nft`
--

LOCK TABLES `tt_contract_nft` WRITE;
/*!40000 ALTER TABLE `tt_contract_nft` DISABLE KEYS */;
/*!40000 ALTER TABLE `tt_contract_nft` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tt_nft_items`
--

DROP TABLE IF EXISTS `tt_nft_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tt_nft_items` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '表id',
  `nft_id` bigint NOT NULL DEFAULT '0' COMMENT 'nft  id',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `token_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `price` decimal(24,6) NOT NULL DEFAULT '0.000000' COMMENT '价格',
  `usdt_price` decimal(24,6) NOT NULL DEFAULT '0.000000' COMMENT 'usdt价格',
  `paytoken_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '支付币种地址',
  `paytoken_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '支付币种名称',
  `paytoken_decimals` int NOT NULL DEFAULT '0' COMMENT '支付币种精确度',
  `paytoken_symbol` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '支付币种符号',
  `sell_quantity` bigint NOT NULL DEFAULT '0' COMMENT '售卖数量',
  `quantity` bigint NOT NULL DEFAULT '0' COMMENT '数量',
  `signature` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '签名',
  `item_owner` varchar(50) NOT NULL COMMENT 'token拥有者',
  `category_id` bigint NOT NULL DEFAULT '0',
  `onsell` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否在售',
  `onsell_type` tinyint NOT NULL DEFAULT '0' COMMENT '1=>sale 2=> auction',
  `onsell_time` bigint NOT NULL DEFAULT '0' COMMENT '发布售卖的时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` bigint NOT NULL DEFAULT '0',
  `update_time` bigint NOT NULL DEFAULT '0',
  `is_sync` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index2` (`item_owner`),
  KEY `index3` (`address`),
  KEY `index5` (`is_sync`),
  KEY `index6` (`onsell`),
  KEY `token_id` (`token_id`),
  KEY `deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tt_nft_items`
--

LOCK TABLES `tt_nft_items` WRITE;
/*!40000 ALTER TABLE `tt_nft_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `tt_nft_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tt_paytoken`
--

DROP TABLE IF EXISTS `tt_paytoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tt_paytoken` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `token_id` int NOT NULL DEFAULT '0',
  `decimals` int NOT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `create_time` bigint DEFAULT NULL,
  `update_time` bigint DEFAULT NULL,
  `symbol` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index2` (`address`),
  KEY `deleted` (`deleted`),
  KEY `token_id` (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tt_paytoken`
--

LOCK TABLES `tt_paytoken` WRITE;
/*!40000 ALTER TABLE `tt_paytoken` DISABLE KEYS */;
/*!40000 ALTER TABLE `tt_paytoken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tt_paytoken_tx`
--

DROP TABLE IF EXISTS `tt_paytoken_tx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tt_paytoken_tx` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `order_no` bigint NOT NULL,
  `from` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `to` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `amounts` varchar(125) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tx_hash` varchar(125) DEFAULT NULL,
  `type` int NOT NULL,
  `status` int DEFAULT '0',
  `token` varchar(50) DEFAULT NULL,
  `confirm_time` bigint DEFAULT '0',
  `expired_time` bigint DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` bigint NOT NULL DEFAULT '0',
  `update_time` bigint DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index2` (`from`),
  KEY `index3` (`to`),
  KEY `deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tt_paytoken_tx`
--

LOCK TABLES `tt_paytoken_tx` WRITE;
/*!40000 ALTER TABLE `tt_paytoken_tx` DISABLE KEYS */;
/*!40000 ALTER TABLE `tt_paytoken_tx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tt_system`
--

DROP TABLE IF EXISTS `tt_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tt_system` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `key_name` varchar(125) DEFAULT NULL,
  `key_value` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `show` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` bigint DEFAULT '0',
  `update_time` bigint DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `deleted` (`deleted`),
  KEY `show` (`show`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tt_system`
--

LOCK TABLES `tt_system` WRITE;
/*!40000 ALTER TABLE `tt_system` DISABLE KEYS */;
INSERT INTO `tt_system` VALUES (1,'LastTime','1645606208',0,0,0,1648897056),(2,'RsaPublicKey','',0,0,0,0),(3,'RsaPrivateKey','',0,0,0,0),(4,'AppId','',1,0,0,0),(5,'ProductId','1',1,0,0,0),(6,'OrderSyncUrl','http://merchant.fingerchar.com/merchant/productorder/sync',0,0,0,0),(7,'WithDrawKey','',0,0,0,0),(8,'NftQueryUrl','http://merchant.fingerchar.com/merchant/transfer/sync',0,0,0,0),(9,'RedirectUri','https://fingernft.fingerchar.com/authorize',1,0,0,0),(10,'TokenUrl','https://fingernft.fingerchar.com/oauth/accessToken',0,0,0,0),(11,'UserInfoUrl','https://fingernft.fingerchar.com/oauth/info',0,0,0,0),(12,'NftUseFee','{\n\"L\":0.01,\n\"J\":0.01,\n\"I\": 0.1,\n\"O\":0.05,\n\"Z\": 0.02,\n\"S\": 0.02,\n\"T\": 0.03\n}',0,0,0,0),(13,'PlayFee','0.1',0,0,0,0),(14,'ScoreRate','[{\"maxScore\":5000,\"amount\":\"0.00001\"},\r\n				{\"maxScore\":10000,\"amount\":\"0.000015\"},\r\n				{\"maxScore\":15000,\"amount\":\"0.00002\"},\r\n				{\"maxScore\":20000,\"amount\":\"0.000025\"},\r\n				{\"maxScore\":25000,\"amount\":\"0.00003\"},\r\n				{\"maxScore\":30000,\"amount\":\"0.000035\"}]',0,0,0,0),(15,'ResponseType','code',0,0,0,0),(16,'ClientSecret','abc',0,0,0,0),(17,'ValidPayToken','0xdee4583b28315b266639d575130c2fa2d75ae7ef',0,0,0,0),(18,'NftCheckUrl','http://merchant.fingerchar.com/merchant/querynft/get',0,0,0,0),(19,'NFTAddress','0xb7a8f767ae980095f71d2610c54dd08488cba165',0,0,0,0),(20,'StartBlockNumber','11227410',0,0,0,1660876926),(21,'LimitBlockNumber','1000',0,0,0,0);
/*!40000 ALTER TABLE `tt_system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tt_transfer_log`
--

DROP TABLE IF EXISTS `tt_transfer_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tt_transfer_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '表id',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作NFT的地址',
  `token_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作NFT的tokenId',
  `amount` bigint NOT NULL DEFAULT '0' COMMENT '数量',
  `from` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '订单发起人地址',
  `to` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '交易对象地址',
  `tx_hash` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '交易hash',
  `block_number` bigint NOT NULL DEFAULT '0' COMMENT '区块高度',
  `block_timestamp` bigint NOT NULL DEFAULT '0' COMMENT '区块的时间戳',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` bigint NOT NULL DEFAULT '0',
  `update_time` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='所有nft的转移记录，包含买，卖';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tt_transfer_log`
--

LOCK TABLES `tt_transfer_log` WRITE;
/*!40000 ALTER TABLE `tt_transfer_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tt_transfer_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tt_user`
--

DROP TABLE IF EXISTS `tt_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tt_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `avatar` varchar(512) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `open_token` varchar(512) DEFAULT NULL,
  `brief` varchar(512) DEFAULT NULL,
  `max_score` varchar(128) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` bigint DEFAULT NULL,
  `update_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index2` (`address`),
  KEY `index3` (`email`),
  KEY `deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tt_user`
--

LOCK TABLES `tt_user` WRITE;
/*!40000 ALTER TABLE `tt_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `tt_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tt_user_balance`
--

DROP TABLE IF EXISTS `tt_user_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tt_user_balance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(50) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  `amount` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  `lock_amount` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` bigint DEFAULT NULL,
  `update_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tt_user_balance`
--

LOCK TABLES `tt_user_balance` WRITE;
/*!40000 ALTER TABLE `tt_user_balance` DISABLE KEYS */;
/*!40000 ALTER TABLE `tt_user_balance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'back_tetris'
--

--
-- Dumping routines for database 'back_tetris'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-19 10:45:52
