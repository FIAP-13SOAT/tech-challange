CREATE TABLE notification (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    stock_id UUID REFERENCES stock(id),
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notification_read ON notification(is_read);
CREATE INDEX idx_notification_created_at ON notification(created_at);
CREATE INDEX idx_notification_stock_id ON notification(stock_id);