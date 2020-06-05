BEGIN;
SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table `pc-builder-test-db`.`motherboard`; 
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `pc-builder-test-db`.`motherboard`
(`maker`, `max_price`, `min_price`, `name`, `allows_overclock`, `m2slots`,
 `max_ram_speed`, `ram_slots`, `ram_type`, `sata_slots`, `size`, `socket`)
 VALUES ('motherboardMaker1', '1', '1', 'motherboardName1', b'1', '1', '1', '1', 'DDR4', '1', 'ATX', 'AM4');

 INSERT INTO `pc-builder-test-db`.`motherboard`
(`maker`, `max_price`, `min_price`, `name`, `allows_overclock`, `m2slots`,
 `max_ram_speed`, `ram_slots`, `ram_type`, `sata_slots`, `size`, `socket`)
 VALUES ('motherboardMaker1', '1', '1', 'motherboardName2', b'1', '1', '1', '1', 'DDR4', '1', 'ATX', 'AM4');

  INSERT INTO `pc-builder-test-db`.`motherboard`
(`maker`, `max_price`, `min_price`, `name`, `allows_overclock`, `m2slots`,
 `max_ram_speed`, `ram_slots`, `ram_type`, `sata_slots`, `size`, `socket`)
 VALUES ('motherboardMaker2', '1', '1', 'motherboardName3', b'1', '1', '1', '1', 'DDR4', '1', 'mATX', 'AM4');

  INSERT INTO `pc-builder-test-db`.`motherboard`
(`maker`, `max_price`, `min_price`, `name`, `allows_overclock`, `m2slots`,
 `max_ram_speed`, `ram_slots`, `ram_type`, `sata_slots`, `size`, `socket`)
 VALUES ('motherboardMaker2', '1', '1', 'motherboardName4', b'1', '1', '1', '1', 'DDR4', '1', 'mATX', 'AM4');


COMMIT;