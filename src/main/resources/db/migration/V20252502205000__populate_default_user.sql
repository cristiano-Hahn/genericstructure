INSERT INTO company (id, name, enabled, created_at, address, document_number, document_type)
VALUES ('123e4567-e89b-12d3-a456-426614174000', -- Gera um UUID automaticamente
        'My Company',
        true,
        now(),
        'Address',
        '12345678000195',
        'CNPJ');


--password: "password"
insert into "user" (id, email, password, roles, enabled, phone, company_id)
values ('123e4567-e89b-12d3-a456-426614174000',
        'test@test.com',
        '$2a$10$JycOVRTORocM5lVREtYGNeHrVrY8Y0hPq0jGVsAXsjkLF5PqDcmke',
        array['USER'],
        true,
        '99999999999',
        '123e4567-e89b-12d3-a456-426614174000');