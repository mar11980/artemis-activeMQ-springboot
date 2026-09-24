DROP TABLE IF EXISTS "orders";

CREATE TABLE "orders" (
    id BIGSERIAL PRIMARY KEY,
    customer VARCHAR(255),
    amount NUMERIC(10,0),
    fail VARCHAR(255)
);

