-- Script SQL para Sweet Lab Bakery
-- Base de datos SQLite

-- Crear tabla de productos
CREATE TABLE IF NOT EXISTS productos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    precio REAL NOT NULL,
    tipo TEXT NOT NULL,
    descripcion TEXT,
    stock INTEGER NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    email TEXT,
    telefono TEXT,
    direccion TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de pedidos
CREATE TABLE IF NOT EXISTS pedidos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    cliente_id INTEGER NOT NULL,
    total REAL NOT NULL,
    estado TEXT DEFAULT 'Pendiente',
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

-- Crear tabla de items_pedido
CREATE TABLE IF NOT EXISTS items_pedido (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    pedido_id INTEGER NOT NULL,
    producto_id INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario REAL NOT NULL,
    subtotal REAL NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);

-- Crear tabla de promociones
CREATE TABLE IF NOT EXISTS promociones (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    descuento REAL NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    activa BOOLEAN DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de contactos
CREATE TABLE IF NOT EXISTS contactos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    email TEXT NOT NULL,
    telefono TEXT,
    asunto TEXT NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_contacto TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para mejor rendimiento
CREATE INDEX IF NOT EXISTS idx_productos_tipo ON productos(tipo);
CREATE INDEX IF NOT EXISTS idx_clientes_email ON clientes(email);
CREATE INDEX IF NOT EXISTS idx_pedidos_cliente ON pedidos(cliente_id);
CREATE INDEX IF NOT EXISTS idx_items_pedido ON items_pedido(pedido_id);

-- Datos de prueba (opcional)
INSERT OR IGNORE INTO productos (id, nombre, precio, tipo, descripcion, stock) VALUES
(1, 'Cupcake Vainilla', 0.50, 'Cupcake', 'Delicioso cupcake de vainilla con cobertura', 50),
(2, 'Cupcake Menta', 0.50, 'Cupcake', 'Cupcake de menta fresca', 45),
(3, 'Cupcake Fresa', 0.50, 'Cupcake', 'Cupcake de fresa y nata', 40),
(4, 'Macaron Violeta', 0.50, 'Macaron', 'Elegante macaron de violeta', 30),
(5, 'Macaron Rosa', 0.50, 'Macaron', 'Macaron rosa con relleno cremoso', 35),
(6, 'Brownie de Chocolate', 0.50, 'Brownie', 'Brownie casero de chocolate', 25),
(7, 'Tarta de Frutos Rojos', 0.50, 'Tarta', 'Tarta fresca con frutos rojos', 20),
(8, 'Donut Rosa', 0.50, 'Donut', 'Donut recubierto con cobertura rosa', 60);

-- Ejemplos de consultas útiles
-- SELECT * FROM productos WHERE tipo = 'Cupcake';
-- SELECT COUNT(*) as total_productos FROM productos;
-- SELECT p.nombre, COUNT(ip.id) as cantidad_vendida FROM productos p 
--   LEFT JOIN items_pedido ip ON p.id = ip.producto_id 
--   GROUP BY p.id ORDER BY cantidad_vendida DESC;
