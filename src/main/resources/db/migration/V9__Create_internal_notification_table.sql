CREATE TABLE internal_notification (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type VARCHAR(50) NOT NULL,
    acknowledged BOOLEAN NOT NULL DEFAULT FALSE,
    user_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resource_id UUID NOT NULL,
    message TEXT,
    acknowledged_at TIMESTAMP
);

CREATE INDEX idx_internal_notification_type ON internal_notification(type);
