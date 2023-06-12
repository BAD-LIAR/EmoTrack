ALTER TABLE event
    ADD COLUMN predicted_emotional_condition bigint DEFAULT NULL;
ALTER TABLE event
    ADD COLUMN predicted_day_emotional_condition bigint DEFAULT NULL;

CREATE TABLE IF NOT EXISTS event (
    id SERIAL NOT NULL,
    title varchar(255) DEFAULT NULL,
    description varchar(2000) DEFAULT NULL,
    emotional_condition bigint DEFAULT NULL,
    day_emotional_condition bigint DEFAULT NULL,
    start_date timestamp DEFAULT NULL,
    end_date timestamp DEFAULT NULL,
    user_id bigint default null,

    PRIMARY KEY (id),

    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users (id)

);



