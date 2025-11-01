CREATE TABLE service_order_execution (
    service_order_id UUID PRIMARY KEY,

    start_date TIMESTAMP WITHOUT TIME ZONE,

    end_date TIMESTAMP WITHOUT TIME ZONE,

    execution_time BIGINT
);