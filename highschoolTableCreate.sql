CREATE TABLE highschool.highschool (
    id INT NOT NULL AUTO_INCREMENT,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL,
    gender TEXT NOT NULL,
    ip_address TEXT NOT NULL,
    cm_high INT NOT NULL,
    age INT NOT NULL,
    has_car BOOLEAN NOT NULL,
    car_color TEXT,
    grade INT NOT NULL,
    grade_avg DOUBLE NOT NULL,
    identification_card TEXT NOT NULL,
    PRIMARY KEY (id)
);