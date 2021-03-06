BEGIN;
SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table `pc-builder-test-db`.`cooler`; 
SET FOREIGN_KEY_CHECKS = 1;


INSERT INTO `pc-builder-test-db`.`cooler` 
(`maker`, `max_price`, `min_price`, `name`, `max_cputdp`, `maxrpm`, `noise_level`)
 VALUES ('coolerMaker1', '1', '1', 'coolerName1', '60', '1', '1');

 INSERT INTO `pc-builder-test-db`.`cooler` 
(`maker`, `max_price`, `min_price`, `name`, `max_cputdp`, `maxrpm`, `noise_level`)
 VALUES ('coolerMaker1', '1', '1', 'coolerName2', '65', '1', '1');

 INSERT INTO `pc-builder-test-db`.`cooler` 
(`maker`, `max_price`, `min_price`, `name`, `max_cputdp`, `maxrpm`, `noise_level`)
 VALUES ('coolerMaker2', '1', '1', 'coolerName3', '70', '1', '1');

 INSERT INTO `pc-builder-test-db`.`cooler` 
(`maker`, `max_price`, `min_price`, `name`, `max_cputdp`, `maxrpm`, `noise_level`)
 VALUES ('coolerMaker2', '1', '1', 'coolerName4', '90', '1', '1');


COMMIT;