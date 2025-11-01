-- Inserir clientes
INSERT INTO customer (id, name, email, phone, cpf_cnpj) VALUES
('f47ac10b-58cc-4372-a567-0e02b2c3d001', 'João Silva', 'joao.silva@email.com', '(11) 99999-1111', '123.456.789-01'),
('f47ac10b-58cc-4372-a567-0e02b2c3d002', 'Maria Santos', 'maria.santos@email.com', '(11) 99999-2222', '987.654.321-02'),
('f47ac10b-58cc-4372-a567-0e02b2c3d003', 'Pedro Oliveira', 'pedro.oliveira@email.com', '(11) 99999-3333', '456.789.123-03');

-- Inserir veículos
INSERT INTO vehicle (id, model, brand, license_plate, customer_id, color, year, observations) VALUES
('f47ac10b-58cc-4372-a567-0e02b2c3d101', 'Civic', 'Honda', 'ABC-1234', 'f47ac10b-58cc-4372-a567-0e02b2c3d001', 'Prata', 2020, 'Veículo em bom estado'),
('f47ac10b-58cc-4372-a567-0e02b2c3d102', 'Corolla', 'Toyota', 'DEF-5678', 'f47ac10b-58cc-4372-a567-0e02b2c3d002', 'Branco', 2019, 'Revisão em dia'),
('f47ac10b-58cc-4372-a567-0e02b2c3d103', 'Onix', 'Chevrolet', 'GHI-9012', 'f47ac10b-58cc-4372-a567-0e02b2c3d003', 'Preto', 2021, 'Carro novo'),
('f47ac10b-58cc-4372-a567-0e02b2c3d104', 'HB20', 'Hyundai', 'JKL-3456', 'f47ac10b-58cc-4372-a567-0e02b2c3d001', 'Azul', 2018, 'Segundo carro do cliente'),
('f47ac10b-58cc-4372-a567-0e02b2c3d105', 'Gol', 'Volkswagen', 'MNO-7890', 'f47ac10b-58cc-4372-a567-0e02b2c3d002', 'Vermelho', 2017, 'Carro usado');