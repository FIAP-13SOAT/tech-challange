CREATE TABLE quote
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    value            NUMERIC(19, 2) NOT NULL,
    service_order_id UUID UNIQUE,
    customer_id      UUID,
    CONSTRAINT fk_quote_service_order FOREIGN KEY (service_order_id) REFERENCES service_order (id),
    CONSTRAINT fk_quote_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
);