CREATE TABLE alarm (
    alarm_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(255) NOT NULL,
    item_code VARCHAR(255) NOT NULL,
    recommend_price INT NOT NULL,
    losscut_price INT NOT NULL,
    comment VARCHAR(4000),
    theme VARCHAR(4000),
    alarm_status VARCHAR(255),
    created_date DATETIME NOT NULL,
    modified_date DATETIME NOT NULL,
    alarmed_at DATETIME,
    losscut_at DATETIME,
    PRIMARY KEY (alarm_id)
);

CREATE TABLE stock_item (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(255) NOT NULL,
    item_code VARCHAR(255) NOT NULL,
    theme VARCHAR(4000),
    created_at DATETIME,
    last_updated_at DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE user (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    password VARCHAR(255) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    user_id BIGINT(20) NOT NULL,
    roles VARCHAR(255)
);

ALTER TABLE user ADD CONSTRAINT UK_lqjrcobrh9jc8wpcar64q1bfh UNIQUE (user_name);
ALTER TABLE user_roles ADD CONSTRAINT FK55itppkw3i07do3h7qoclqd4k FOREIGN KEY (user_id) REFERENCES user;