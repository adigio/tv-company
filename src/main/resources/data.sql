INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Plan(id, nombre, precio)
VALUES
    (null, 'Básico', 5000),
    (null, 'Premium', 10000);

INSERT INTO Cliente(id, dni, plan_id)
VALUES
    (null, '35978444', 1),
    (null, '1234', 2),
    (null, '1111', 1);
