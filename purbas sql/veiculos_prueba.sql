USE carmotors;
DESCRIBE vehiculos;


INSERT INTO vehiculos (cliente_id, placa, marca, modelo)
SELECT c.id, 'jadshfkjdf', 'bugati', '1'
FROM clientes c
WHERE c.email = 'carlos@correo.com'
  AND NOT EXISTS (SELECT 1 FROM vehiculos WHERE placa = 'jadshfkjdf');

INSERT INTO vehiculos (cliente_id, placa, marca, modelo)
SELECT c.id, 'dfmslkfm', 'ferrari', '3'
FROM clientes c
WHERE c.email = 'sebasrosales@correo.com'
  AND NOT EXISTS (SELECT 1 FROM vehiculos WHERE placa = 'dfmslkfm');

SELECT v.id, v.placa, v.marca, v.modelo, c.nombre AS dueno
FROM vehiculos v
LEFT JOIN clientes c ON c.id = v.cliente_id;
