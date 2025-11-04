DROP TABLE IF EXISTS quote CASCADE;
DROP TABLE IF EXISTS budget_item CASCADE;
DROP TABLE IF EXISTS budget CASCADE;

CREATE TABLE quote
(
    id               UUID PRIMARY KEY        DEFAULT gen_random_uuid(),
    service_order_id UUID           NOT NULL REFERENCES service_order (id),
    total_amount     DECIMAL(10, 2) NOT NULL,
    status           VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    created_at       TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (service_order_id)
);

CREATE TABLE quote_item
(
    id          UUID PRIMARY KEY        DEFAULT gen_random_uuid(),
    quote_id    UUID           NOT NULL REFERENCES quote (id) ON DELETE CASCADE,
    description VARCHAR(255)   NOT NULL,
    unit_price  DECIMAL(10, 2) NOT NULL,
    quantity    INTEGER        NOT NULL,
    type        VARCHAR(20)    NOT NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_quote_service_order ON quote (service_order_id);
CREATE INDEX idx_quote_item_quote ON quote_item (quote_id);
