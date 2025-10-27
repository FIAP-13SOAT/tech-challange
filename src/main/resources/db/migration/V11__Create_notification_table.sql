CREATE TABLE notification (
                              id UUID PRIMARY KEY,
                              type VARCHAR(50) NOT NULL,
                              message TEXT NOT NULL,
                              stock_id UUID REFERENCES stock(id),
                              is_read BOOLEAN DEFAULT FALSE,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
