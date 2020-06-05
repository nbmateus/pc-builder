BEGIN;
SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table `pc-builder-test-db`.`storage`; 
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `pc-builder-test-db`.`storage`
(`maker`, `max_price`, `min_price`, `name`, `capacity`, `read_speed`, `storage_type`, `write_speed`)
VALUES ('storageMaker1', '1', '1', 'storageName1', '1tb', '1', 'M2', '1');

INSERT INTO `pc-builder-test-db`.`storage`
(`maker`, `max_price`, `min_price`, `name`, `capacity`, `read_speed`, `storage_type`, `write_speed`)
VALUES ('storageMaker1', '1', '1', 'storageName2', '1tb', '1', 'M2', '1');

INSERT INTO `pc-builder-test-db`.`storage`
(`maker`, `max_price`, `min_price`, `name`, `capacity`, `read_speed`, `storage_type`, `write_speed`)
VALUES ('storageMaker2', '1', '1', 'storageName3', '1tb', '1', 'M2', '1');

INSERT INTO `pc-builder-test-db`.`storage`
(`maker`, `max_price`, `min_price`, `name`, `capacity`, `read_speed`, `storage_type`, `write_speed`)
VALUES ('storageMaker2', '1', '1', 'storageName4', '1tb', '1', 'M2', '1');

COMMIT;