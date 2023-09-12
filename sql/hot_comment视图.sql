CREATE
OR REPLACE ALGORITHM = UNDEFINED DEFINER = `root`@`localhost` SQL SECURITY DEFINER VIEW `analysis_sys_db`.`hot_comments` AS
SELECT `raw_comment`.`id`         AS `id`,
       `raw_comment`.`content`    AS `content`,
       `raw_comment`.`translated` AS `translated`,
       `raw_comment`.`likes`      AS `likes`,
       `raw_comment`.`workId`     AS `workId`,
       `raw_comment`.`sentiment`  AS `sentiment`,
       `raw_comment`.`country`    AS `country`,
       `raw_comment`.`platform`   AS `platform`,
       `raw_comment`.`postTime`   AS `postTime`
FROM `raw_comment`
ORDER BY `raw_comment`.`likes` DESC LIMIT 0,
	600;
