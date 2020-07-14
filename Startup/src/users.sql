USE joker;

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL UNIQUE,
    mail VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    rank INT NOT NULL,
    PRIMARY KEY (user_id)
);