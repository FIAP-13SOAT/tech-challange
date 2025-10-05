CREATE TABLE vehicle (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    model VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    license_plate VARCHAR(255) NOT NULL,
    customer_id UUID REFERENCES customer(id),
    color VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    observations TEXT
);