USE joker;

CREATE TABLE IF NOT EXISTS histories (
    table_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id1 BIGINT NOT NULL,
    score1   DOUBLE NOT NULL,
    user_id2 BIGINT NOT NULL,
    score2   DOUBLE NOT NULL,
    user_id3 BIGINT NOT NULL,
    score3   DOUBLE NOT NULL,
    user_id4 BIGINT NOT NULL,
    score4   DOUBLE NOT NULL,
    PRIMARY KEY (table_id),
    FOREIGN KEY (user_id1) REFERENCES users (user_id),
    FOREIGN KEY (user_id2) REFERENCES users (user_id),
    FOREIGN KEY (user_id3) REFERENCES users (user_id),
    FOREIGN KEY (user_id4) REFERENCES users (user_id)
);