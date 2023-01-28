CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `highschool`.`new_view` AS
    SELECT 
        `highschool`.`highschool`.`identification_card` AS `identification_card`,
        `highschool`.`highschool`.`grade_avg` AS `grade_avg`
    FROM
        `highschool`.`highschool`