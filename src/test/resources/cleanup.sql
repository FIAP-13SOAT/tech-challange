TRUNCATE TABLE
    tests.service_order_execution,
    tests.service_order_items,
    tests.service_order_service_type,
    tests.quote,
    tests.service_order,
    tests.vehicle,
    tests.customer,
    tests.service_type,
    tests."user",
    tests.stock_movement,
    tests.stock,
    tests.notification
RESTART IDENTITY CASCADE;
