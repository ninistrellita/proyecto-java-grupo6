USE carmotors;
DESCRIBE clientes;

INSERT INTO clientes (nombre, documento_identidad, telefono, email)
SELECT 'Carlos Gómez', '1090111222', '3001234567', 'carlos@correo.com' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM clientes
                  WHERE documento_identidad = '1090111222' OR email = 'carlos@correo.com');


INSERT INTO clientes (nombre, documento_identidad, telefono, email)
SELECT 'sebas rosales', '1092942229', '3001214567', 'sebasrosales@correo.com' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM clientes
                  WHERE documento_identidad = '1092942229' OR email = 'sebasrosales@correo.com');


SELECT * FROM clientes;
