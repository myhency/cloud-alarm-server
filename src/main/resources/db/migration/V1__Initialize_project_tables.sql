DROP TABLE IF EXISTS alarms;
DROP TABLE IF EXISTS stockitems;

CREATE TABLE alarms (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(255) NOT NULL,
    item_code VARCHAR(255) NOT NULL,
    recommend_price DECIMAL(19) NOT NULL,
    losscut_price DECIMAL(19) NOT NULL,
    comment VARCHAR(4000),
    theme VARCHAR(4000),
    alarm_status VARCHAR(255),
    created_at DATETIME NOT NULL,
    last_updated_at DATETIME NOT NULL,
    alarmed_at DATETIME,
    losscut_at DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE stockitems (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(255) NOT NULL,
    item_code VARCHAR(255) NOT NULL,
    theme VARCHAR(4000),
    created_at DATETIME,
    last_updated_at DATETIME,
    PRIMARY KEY (id)
);
