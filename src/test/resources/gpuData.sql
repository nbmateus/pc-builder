BEGIN;
SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table `pc-builder-test-db`.`gpu`; 
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `pc-builder-test-db`.`gpu` 
(`maker`, `max_price`, `min_price`, `name`, `base_clock`, `boost_clock`, `tdp`, `display_port`, `dvi`, `hdmi`, `memory`, `vga`)
VALUES ('gpuMaker1', '1', '1', 'gpuName1', '1', '1', '1', b'0', b'0', b'0','4', b'0');

INSERT INTO `pc-builder-test-db`.`gpu` 
(`maker`, `max_price`, `min_price`, `name`, `base_clock`, `boost_clock`, `tdp`, `display_port`, `dvi`, `hdmi`, `memory`, `vga`)
VALUES ('gpuMaker1', '1', '1', 'gpuName2', '1', '1', '1', b'0', b'0', b'0','4', b'0');

INSERT INTO `pc-builder-test-db`.`gpu` 
(`maker`, `max_price`, `min_price`, `name`, `base_clock`, `boost_clock`, `tdp`, `display_port`, `dvi`, `hdmi`, `memory`, `vga`)
VALUES ('gpuMaker2', '1', '1', 'gpuName3', '1', '1', '1', b'0', b'0', b'0','4', b'0');

INSERT INTO `pc-builder-test-db`.`gpu` 
(`maker`, `max_price`, `min_price`, `name`, `base_clock`, `boost_clock`, `tdp`, `display_port`, `dvi`, `hdmi`, `memory`, `vga`)
VALUES ('gpuMaker2', '1', '1', 'gpuName4', '1', '1', '1', b'0', b'0', b'0','4', b'0');



COMMIT;