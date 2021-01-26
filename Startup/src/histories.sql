USE joker;

CREATE TABLE IF NOT EXISTS histories (
    table_id BIGINT NOT NULL,
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


-- IntelliJ generated script: >>>>>>>>>

-- create table histories
-- (
--     table_id bigint not null,
--     user_id1 bigint not null,
--     user_id2 bigint not null,
--     user_id3 bigint not null,
--     user_id4 bigint not null,
--     score1 double not null,
--     score2 double not null,
--     score3 double not null,
--     score4 double not null,
--     constraint histories_pk
--         primary key (table_id),
--     constraint user1_fk
--         foreign key (user_id1) references users (user_id),
--     constraint user2_fk
--         foreign key (user_id2) references users (user_id),
--     constraint user3_fk
--         foreign key (user_id3) references users (user_id),
--     constraint user4_fk
--         foreign key (user_id4) references users (user_id)
-- );
