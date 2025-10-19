CREATE TABLE service_order
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    observations TEXT,
    vehicle_id   UUID        NOT NULL,
    status       VARCHAR(50) NOT NULL,
    CONSTRAINT fk_vehicle
        FOREIGN KEY (vehicle_id) REFERENCES vehicle (id)
);

CREATE TABLE service_order_service_type
(
    service_order_id UUID NOT NULL,
    service_type_id  UUID NOT NULL,
    PRIMARY KEY (service_order_id, service_type_id),
    CONSTRAINT fk_service_order
        FOREIGN KEY (service_order_id) REFERENCES service_order (id),
    CONSTRAINT fk_service_type
        FOREIGN KEY (service_type_id) REFERENCES service_type (id)
);