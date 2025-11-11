ALTER TABLE service_order ADD COLUMN customer_id UUID NOT NULL;
ALTER TABLE service_order ADD CONSTRAINT fk_service_order_customer FOREIGN KEY (customer_id) REFERENCES customer(id);
