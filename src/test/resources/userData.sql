BEGIN;
SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table `pc-builder-test-db`.`user`; 
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `pc-builder-test-db`.`user` (`is_admin`, `email`, `password`, `username`)
VALUES 
(b'0','test@example.com','$2a$10$gaGjd6rhKM727aNB2akKLuxsv14vmSMSZ.X4Rryc1YR9dgaotCoQO','user1'),
(b'0','test2@example.com','$2a$10$GDKQl/t5m5.6KzjvybDmEOKDACbiGtGM28JPwwtxfxPDfi6B.z3iW','user12'),
(b'1','admin@example.com','$2a$10$9/rHY5waOTDFiqv4T/bElePkWCnjAY0BdQ/JjiqeHq6rQmX9Sqy6W','admin');

COMMIT;
