BEGIN;
SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table `pc-builder-test-db`.`ram`; 
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `pc-builder-test-db`.`ram`
(`maker`, `max_price`, `min_price`, `name`, `capacity`, `latency`, `speed`, `type`)
VALUES ('ramMaker1', '1', '1', 'ramName1', '1', 'asda', '1', 'DDR4');

INSERT INTO `pc-builder-test-db`.`ram`
(`maker`, `max_price`, `min_price`, `name`, `capacity`, `latency`, `speed`, `type`)
VALUES ('ramMaker1', '1', '1', 'ramName2', '1', 'asda', '1', 'DDR4');

INSERT INTO `pc-builder-test-db`.`ram`
(`maker`, `max_price`, `min_price`, `name`, `capacity`, `latency`, `speed`, `type`)
VALUES ('ramMaker2', '1', '1', 'ramName3', '1', 'asda', '1', 'DDR4');

INSERT INTO `pc-builder-test-db`.`ram`
(`maker`, `max_price`, `min_price`, `name`, `capacity`, `latency`, `speed`, `type`)
VALUES ('ramMaker2', '1', '1', 'ramName4', '1', 'asda', '1', 'DDR4');



COMMIT;