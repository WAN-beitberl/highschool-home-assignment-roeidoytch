CREATE TABLE highschool.highschool_friendships (
    id INT NOT NULL AUTO_INCREMENT,
    friend_id INT NOT NULL,
	other_friend_id INT NOT NULL,
    PRIMARY KEY (id)
);