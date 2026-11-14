USE carmotors;
DESCRIBE proveedores;


INSERT INTO proveedores (nombre, contacto)
SELECT 'Repuestos El Motor', NULL FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM proveedores WHERE nombre = 'Repuestos El Motor');

INSERT INTO repuestos (nombre, repuesto_tipo_id, proveedor_id, precio_compra, precio_venta, cantidad_stock, stock_minimo)
SELECT 'Pastillas de freno', t.id, p.id, 60000, 95000, 10, 3
FROM repuesto_tipos t
JOIN proveedores p ON p.nombre = 'Repuestos El Motor'
WHERE t.nombre = 'Mecánico'
  AND NOT EXISTS (SELECT 1 FROM repuestos r WHERE r.nombre = 'Pastillas de freno' AND r.proveedor_id = p.id);

INSERT INTO repuestos (nombre, repuesto_tipo_id, proveedor_id, precio_compra, precio_venta, cantidad_stock, stock_minimo)
SELECT 'Batería 12V', t.id, p.id, 280000, 380000, 6, 2
FROM repuesto_tipos t
JOIN proveedores p ON p.nombre = 'Repuestos El Motor'
WHERE t.nombre = 'Eléctrico'
  AND NOT EXISTS (SELECT 1 FROM repuestos r WHERE r.nombre = 'Batería 12V' AND r.proveedor_id = p.id);

SELECT * FROM proveedores;
SELECT * FROM repuestos;
