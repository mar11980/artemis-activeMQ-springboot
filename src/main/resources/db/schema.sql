DROP TABLE IF EXISTS order_event;

CREATE TABLE order (
    id BIGSERIAL PRIMARY KEY,
    customer VARCHAR(255),
    amount NUMERIC(10,0),
    fail VARCHAR(255)
);

