CREATE TABLE service_type
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    description VARCHAR(255) NOT NULL,
    value       DECIMAL(10, 2) NOT NULL
);