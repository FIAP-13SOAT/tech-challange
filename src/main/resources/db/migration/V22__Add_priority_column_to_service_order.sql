ALTER TABLE service_order ADD COLUMN priority INTEGER;

UPDATE service_order SET priority = 4 WHERE status = 'RECEIVED';
UPDATE service_order SET priority = 3 WHERE status = 'IN_DIAGNOSIS';
UPDATE service_order SET priority = 2 WHERE status = 'AWAITING_APPROVAL';
UPDATE service_order SET priority = 1 WHERE status = 'IN_PROGRESS';
UPDATE service_order SET priority = 99 WHERE status = 'COMPLETED';
UPDATE service_order SET priority = 99 WHERE status = 'DELIVERED';
UPDATE service_order SET priority = 99 WHERE status = 'CANCELLED';

ALTER TABLE service_order ALTER COLUMN priority SET NOT NULL;
