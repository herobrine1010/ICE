/*create a table to save all the sequences*/
drop table if exists Sequences;
CREATE TABLE `Sequences` (
    `sequence_name`  varchar(64) NOT NULL COMMENT '序列名称' ,
    `value`  bigint NOT NULL DEFAULT 0 COMMENT '当前值' ,
    PRIMARY KEY (`sequence_name`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
;

/*create the function of getting the next val of a sequence*/
drop function if exists NEXT_VAL;
DELIMITER //
CREATE FUNCTION NEXT_VAL(sequence_name VARCHAR(64)) 
RETURNS BIGINT 
BEGIN
    UPDATE sequences t, (SELECT @current_val:=`value` FROM sequences t2 WHERE t2.sequence_name=sequence_name) t3 SET t.value = t.value + 1 WHERE t.sequence_name =sequence_name AND @current_val=t.value;
    RETURN @current_val+1;
END//
DELIMITER ;



/*trigger for USERS*/
INSERT INTO sequences VALUES('seq_user_id', 1000000);

DROP TRIGGER IF EXISTS `tri_user_id`;
DELIMITER ;;
CREATE TRIGGER `tri_user_id` BEFORE INSERT ON `users` FOR EACH ROW begin
    SET new.user_id = NEXT_VAL('seq_user_id');
end;;
DELIMITER ;



/*triggers for CATEGORIES*/
INSERT INTO sequences VALUES('seq_cate_id', 0);

DROP TRIGGER IF EXISTS `tri_cate_id`;
DELIMITER ;;
CREATE TRIGGER `tri_cate_id` BEFORE INSERT ON `CATEGORIES` FOR EACH ROW begin
	set new.cate_id = NEXT_VAL('seq_cate_id');
end;;
DELIMITER ;



/*triggers for CONSOLES*/
INSERT INTO sequences VALUES('seq_console_id', 0);

DROP TRIGGER IF EXISTS `tri_consoles_id`;
DELIMITER ;;
CREATE TRIGGER `tri_consoles_id` BEFORE INSERT ON `CONSOLES` FOR EACH ROW begin
	set new.console_id = NEXT_VAL('seq_console_id');
end;;
DELIMITER ;



/*triggers for GAMES*/
INSERT INTO sequences VALUES('seq_game_id', 0);

DROP TRIGGER IF EXISTS `tri_game_id`;
DELIMITER ;;
CREATE TRIGGER `tri_game_id` BEFORE INSERT ON `GAMES` FOR EACH ROW begin
	set new.game_id = NEXT_VAL('seq_game_id');
end;;
DELIMITER ;



/*triggers for ORDERS*/
INSERT INTO sequences VALUES('seq_order_id', 0);

DROP TRIGGER IF EXISTS `tri_order_id`;
DELIMITER ;;
CREATE TRIGGER `tri_order_id` BEFORE INSERT ON `ORDERS` FOR EACH ROW begin
	set new.order_id = NEXT_VAL('seq_order_id');
end;;
DELIMITER ;



/*triggers for PUBLISHIERS*/
INSERT INTO sequences VALUES('seq_publisher_id', 0);

DROP TRIGGER IF EXISTS `tri_publisher_id`;
DELIMITER ;;
CREATE TRIGGER `tri_publisher_id` BEFORE INSERT ON `PUBLISHERS` FOR EACH ROW begin
	set new.publisher_id = NEXT_VAL('seq_publisher_id');
end;;
DELIMITER ;



/*triggers for REVIEWS*/
INSERT INTO sequences VALUES('seq_review_id', 0);

DROP TRIGGER IF EXISTS `tri_review_id`;
DELIMITER ;;
CREATE TRIGGER `tri_review_id` BEFORE INSERT ON `REVIEWS` FOR EACH ROW begin
	set new.review_id = NEXT_VAL('seq_review_id');
end;;
DELIMITER ;



/*triggers for TAGS*/
INSERT INTO sequences VALUES('seq_tag_id', 0);

DROP TRIGGER IF EXISTS `tri_tag_id`;
DELIMITER ;;
CREATE TRIGGER `tri_tag_id` BEFORE INSERT ON `TAGS` FOR EACH ROW begin
	set new.tag_id = NEXT_VAL('seq_tag_id');
end;;
DELIMITER ;