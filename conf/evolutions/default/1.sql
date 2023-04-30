# Employee schema


# --- !Ups
CREATE TABLE IF NOT EXISTS `scalatestdb`.`employee` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL DEFAULT NULL,
    `email` VARCHAR(45) NULL DEFAULT NULL,
    `phone` VARCHAR(45) NULL DEFAULT NULL,
    `department` VARCHAR(45) NULL DEFAULT NULL, 
    PRIMARY KEY (`id`)
)

AUTO_INCREMENT = 2 
DEFAULT CHARACTER SET = utf8

# --- !Downs
drop table 'employee'