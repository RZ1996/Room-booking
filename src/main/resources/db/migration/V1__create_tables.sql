CREATE TABLE user (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(50)  NOT NULL,
    sur_name   VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL,
    created_at DATE         NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       ENUM ('ADMIN','USER'),
    PRIMARY KEY (id),
    CONSTRAINT uk_user_email UNIQUE (email)
) ENGINE = InnoDB;

CREATE TABLE room (
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    name     VARCHAR(50) NOT NULL,
    capacity INT         NOT NULL,
    active   BIT         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_room_name UNIQUE (name)
) ENGINE = InnoDB;

CREATE TABLE booking (
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    user_id              BIGINT       NOT NULL,
    room_id              BIGINT       NOT NULL,
    start_of_reservation DATETIME(6)  NOT NULL,
    end_of_reservation   DATETIME(6)  NOT NULL,
    note                 VARCHAR(200),
    state                ENUM ('CANCELLED','OPENED'),
    version              BIGINT       NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_booking_room FOREIGN KEY (room_id) REFERENCES room (id)
) ENGINE = InnoDB;

CREATE INDEX idx_booking_room_state_start_end
    ON booking (room_id, state, start_of_reservation, end_of_reservation);

CREATE INDEX idx_booking_user_state_start
    ON booking (user_id, state, start_of_reservation);
