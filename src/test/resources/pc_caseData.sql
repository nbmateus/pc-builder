BEGIN;
SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table `pc-builder-test-db`.`pc_case`; 
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `pc-builder-test-db`.`pc_case`
(`maker`, `max_price`, `min_price`, `name`, `drive_bays25`, `drive_bays35`, `max_mobo_size`)
VALUES ('pcCaseMaker1', '1', '1', 'pcCaseName1', '1', '1', 'ATX');

INSERT INTO `pc-builder-test-db`.`pc_case`
(`maker`, `max_price`, `min_price`, `name`, `drive_bays25`, `drive_bays35`, `max_mobo_size`)
VALUES ('pcCaseMaker1', '1', '1', 'pcCaseName2', '1', '1', 'ATX');

INSERT INTO `pc-builder-test-db`.`pc_case`
(`maker`, `max_price`, `min_price`, `name`, `drive_bays25`, `drive_bays35`, `max_mobo_size`)
VALUES ('pcCaseMaker2', '1', '1', 'pcCaseName3', '1', '1', 'ATX');

INSERT INTO `pc-builder-test-db`.`pc_case`
(`maker`, `max_price`, `min_price`, `name`, `drive_bays25`, `drive_bays35`, `max_mobo_size`)
VALUES ('pcCaseMaker2', '1', '1', 'pcCaseName4', '1', '1', 'ATX');



COMMIT;