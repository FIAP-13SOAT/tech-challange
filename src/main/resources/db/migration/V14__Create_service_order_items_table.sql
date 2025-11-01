CREATE TABLE service_order_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    service_order_id UUID NOT NULL,
    stock_id UUID NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (service_order_id) REFERENCES service_order(id) ON DELETE CASCADE,
    FOREIGN KEY (stock_id) REFERENCES stock(id)
);