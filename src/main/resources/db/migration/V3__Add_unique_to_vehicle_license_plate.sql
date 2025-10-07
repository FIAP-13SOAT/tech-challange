ALTER TABLE vehicle
    ADD CONSTRAINT vehicle_license_plate_uidx UNIQUE (license_plate);