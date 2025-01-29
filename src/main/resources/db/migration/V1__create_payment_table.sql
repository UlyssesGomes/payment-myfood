
-- Create Payment table

CREATE TABLE payments (
    id bigserial PRIMARY KEY,
    payment_value decimal(19,2) NOT NULL,
    name varchar(100) DEFAULT NULL,
    number varchar(19) DEFAULT NULL,
    expiration_date varchar(7) DEFAULT NULL,
    code varchar(3) DEFAULT NULL,
    status varchar(255) NOT NULL,
    order_id bigint NOT NULL,
    payment_method_id bigint NOT NULL
);
