BEGIN;
SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table `pc-builder-test-db`.`cpu`; 
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `pc-builder-test-db`.`cpu` 
(`maker`, `max_price`, `min_price`, `name`, `base_clock`, `boost_clock`,
 `tdp`, `cores`, `has_integratedgpu`, `includes_cooler`, `socket`, `threads`, `unlocked_multiplier`)
  VALUES ('makerTest1', '100', '75', 'nameTest1', '3', '4', '65', '6', b'1', b'0', 'AM4', '12', b'0');


INSERT INTO `pc-builder-test-db`.`cpu` 
(`maker`, `max_price`, `min_price`, `name`, `base_clock`, `boost_clock`,
 `tdp`, `cores`, `has_integratedgpu`, `includes_cooler`, `socket`, `threads`, `unlocked_multiplier`)
  VALUES ('makerTest2', '200', '100', 'nameTest2', '2', '3', '60', '4', b'1', b'0', 'AM4', '12', b'0');

  INSERT INTO `pc-builder-test-db`.`cpu` 
(`maker`, `max_price`, `min_price`, `name`, `base_clock`, `boost_clock`,
 `tdp`, `cores`, `has_integratedgpu`, `includes_cooler`, `socket`, `threads`, `unlocked_multiplier`)
  VALUES ('makerTest3', '300', '200', 'nameTest3', '3.5', '3.9', '80', '8', b'1', b'0', 'LGA1151_2', '8', b'0');

  INSERT INTO `pc-builder-test-db`.`cpu` 
(`maker`, `max_price`, `min_price`, `name`, `base_clock`, `boost_clock`,
 `tdp`, `cores`, `has_integratedgpu`, `includes_cooler`, `socket`, `threads`, `unlocked_multiplier`)
  VALUES ('makerTest4', '90', '50', 'nameTest4', '2.4', '3.3', '50', '4', b'1', b'1', 'LGA1151_1', '4', b'1');

COMMIT;