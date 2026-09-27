-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema BD_Prototype
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema BD_Prototype
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `BD_Mfpai` DEFAULT CHARACTER SET latin1 ;
USE `BD_Mfpai` ;

-- -----------------------------------------------------
-- Table `BD_Prototype`.`TD_DisposableEmail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_Mfpai`.`TD_DisposableEmail` (
  `Dis_id` BIGINT(20) NOT NULL,
  `Dis_Domain` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`Dis_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `BD_Prototype`.`TD_FailedMail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_Mfpai`.`TD_FailedMail` (
  `Fai_Id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Fai_CreatedDate` DATETIME NULL DEFAULT NULL,
  `Fai_Email` VARCHAR(255) NULL DEFAULT NULL,
  `Fai_IsSent` TINYINT(1) NULL DEFAULT '0',
  `Fai_Subject` VARCHAR(255) NULL DEFAULT NULL,
  `Fai_Text` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`Fai_Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `BD_Prototype`.`TP_Profil`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_Mfpai`.`TP_Profil` (
  `Pro_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Pro_Code` VARCHAR(10) NULL DEFAULT NULL,
  `Pro_Libelle` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`Pro_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `BD_Prototype`.`TD_Utilisateur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_Mfpai`.`TD_Utilisateur` (
  `Uti_Id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Uti_CreatedBy` BIGINT(20) NULL DEFAULT NULL,
  `Uti_CreatedDate` DATE NULL DEFAULT NULL,
  `Uti_ModifiedBy` BIGINT(20) NULL DEFAULT NULL,
  `Uti_ModifiedDate` DATETIME NULL DEFAULT NULL,
  `Uti_Prenom` VARCHAR(255) NOT NULL,
  `Uti_Nom` VARCHAR(50) NULL DEFAULT NULL,
  `Uti_Adresse` VARCHAR(255) NULL DEFAULT NULL,
  `Uti_Email` VARCHAR(30) NULL DEFAULT NULL,
  `Uti_FirstLog` TINYINT(1) NULL DEFAULT '0',
  `Uti_IsDeleted` TINYINT(1) NULL DEFAULT '0',
  `Uti_Password` VARCHAR(255) NULL DEFAULT NULL,
  `Uti_Status` TINYINT(1) NULL DEFAULT '1',
  `Uti_Telephone` VARCHAR(50) NULL DEFAULT NULL,
  `Uti_Pro_Id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`Uti_Id`),
  INDEX `FK9qoi20mba1rqjijaywyc555bv` (`Uti_Pro_Id` ASC) VISIBLE,
  CONSTRAINT `FK9qoi20mba1rqjijaywyc555bv`
    FOREIGN KEY (`Uti_Pro_Id`)
    REFERENCES `BD_Prototype`.`TP_Profil` (`Pro_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 417
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `BD_Prototype`.`TP_Menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_Mfpai`.`TP_Menu` (
  `Men_id` BIGINT(20) NOT NULL,
  `Men_iconType` VARCHAR(255) NULL DEFAULT NULL,
  `Men_path` VARCHAR(255) NULL DEFAULT NULL,
  `Men_title` VARCHAR(255) NULL DEFAULT NULL,
  `Men_type` VARCHAR(255) NULL DEFAULT NULL,
  `Men_icon_type` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`Men_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `BD_Prototype`.`TR_ProfilMenu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_Mfpai`.`TR_ProfilMenu` (
  `Aut_Pro_Id` INT(11) NOT NULL,
  `Aut_Men_Id` INT(11) NOT NULL,
  `Pro_Id` BIGINT(20) NOT NULL,
  `Men_Id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`Aut_Pro_Id`, `Aut_Men_Id`),
  INDEX `FKl3actfmb4k3db1l65fx9jedua` (`Men_Id` ASC) VISIBLE,
  INDEX `FKl1c9nek2st8ihma0is2y6na38` (`Pro_Id` ASC) VISIBLE,
  CONSTRAINT `FKl1c9nek2st8ihma0is2y6na38`
    FOREIGN KEY (`Pro_Id`)
    REFERENCES `BD_Prototype`.`TP_Profil` (`Pro_id`),
  CONSTRAINT `FKl3actfmb4k3db1l65fx9jedua`
    FOREIGN KEY (`Men_Id`)
    REFERENCES `BD_Prototype`.`TP_Menu` (`Men_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `BD_Prototype`.`Tr_Profil_Menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_Mfpai`.`Tr_Profil_Menu` (
  `Pro_Id` BIGINT(20) NOT NULL,
  `Men_Id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`Pro_Id`, `Men_Id`),
  INDEX `FKp7xbah75q1oi0993w9nlscu83` (`Men_Id` ASC) VISIBLE,
  CONSTRAINT `FKrr67ncf26d1x8hpj9asidm3hn`
    FOREIGN KEY (`Pro_Id`)
    REFERENCES `BD_Prototype`.`TP_Profil` (`Pro_id`),
  CONSTRAINT `FKp7xbah75q1oi0993w9nlscu83`
    FOREIGN KEY (`Men_Id`)
    REFERENCES `BD_Prototype`.`TP_Menu` (`Men_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
