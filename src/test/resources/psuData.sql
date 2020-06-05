BEGIN;
SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table `pc-builder-test-db`.`psu`; 
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `pc-builder-test-db`.`psu`
(`maker`, `max_price`, `min_price`, `name`, `certification`, `wattage`)
VALUES ('psuMaker1', '1', '1', 'psuName1', 'WHITE', '500');

INSERT INTO `pc-builder-test-db`.`psu`
(`maker`, `max_price`, `min_price`, `name`, `certification`, `wattage`)
VALUES ('psuMaker1', '1', '1', 'psuName2', 'WHITE', '500');

INSERT INTO `pc-builder-test-db`.`psu`
(`maker`, `max_price`, `min_price`, `name`, `certification`, `wattage`)
VALUES ('psuMaker2', '1', '1', 'psuName3', 'WHITE', '500');

INSERT INTO `pc-builder-test-db`.`psu`
(`maker`, `max_price`, `min_price`, `name`, `certification`, `wattage`)
VALUES ('psuMaker2', '1', '1', 'psuName4', 'WHITE', '500');

COMMIT;