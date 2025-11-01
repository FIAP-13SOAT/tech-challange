INSERT INTO service_order (id, observations, vehicle_id, status) VALUES
('550e8400-e29b-41d4-a716-446655440000'::uuid, 'Ordem de teste - Troca de óleo', 'f47ac10b-58cc-4372-a567-0e02b2c3d101'::uuid, 'CREATED');

INSERT INTO service_order_service_type (service_order_id, service_type_id) VALUES
('550e8400-e29b-41d4-a716-446655440000'::uuid, '550e8400-e29b-41d4-a716-446655440001'::uuid),
('550e8400-e29b-41d4-a716-446655440000'::uuid, '550e8400-e29b-41d4-a716-446655440003'::uuid);

INSERT INTO service_order_items (id, service_order_id, stock_id, quantity) VALUES
('660e8400-e29b-41d4-a716-446655440001'::uuid, '550e8400-e29b-41d4-a716-446655440000'::uuid, '123e4567-e89b-12d3-a456-426614174001'::uuid, 2),
('660e8400-e29b-41d4-a716-446655440002'::uuid, '550e8400-e29b-41d4-a716-446655440000'::uuid, '123e4567-e89b-12d3-a456-426614174015'::uuid, 1);