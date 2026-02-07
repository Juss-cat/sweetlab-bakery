package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase para gestionar la conexión a la base de datos SQLite
 */
public class ConexionBD {
    private static final String URL = "jdbc:sqlite:sweetlab_bakery.db";
    private static Connection conexion;

    // Constructor privado (Singleton)
    private ConexionBD() {
    }

    /**
     * Obtiene la conexión a la base de datos
     * Si no existe, la crea
     */
    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                // Cargar el driver de SQLite
                Class.forName("org.sqlite.JDBC");
                conexion = DriverManager.getConnection(URL);
                System.out.println("✓ Conexión a BD establecida");
                inicializarBD();
            } catch (ClassNotFoundException e) {
                System.err.println("Error: Driver SQLite no encontrado");
                e.printStackTrace();
                throw new SQLException("Driver SQLite no encontrado", e);
            }
        }
        return conexion;
    }

    /**
     * Inicializa la base de datos creando las tablas si no existen
     */
    private static void inicializarBD() {
        try (Statement stmt = conexion.createStatement()) {
            // Tabla de productos
            stmt.execute("CREATE TABLE IF NOT EXISTS productos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "precio REAL NOT NULL," +
                    "tipo TEXT NOT NULL," +
                    "descripcion TEXT," +
                    "stock INTEGER NOT NULL," +
                    "fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");

            // Tabla de clientes
            stmt.execute("CREATE TABLE IF NOT EXISTS clientes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "email TEXT," +
                    "telefono TEXT," +
                    "direccion TEXT," +
                    "fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");

            // Tabla de pedidos
            stmt.execute("CREATE TABLE IF NOT EXISTS pedidos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "cliente_id INTEGER NOT NULL," +
                    "total REAL NOT NULL," +
                    "estado TEXT DEFAULT 'Pendiente'," +
                    "fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (cliente_id) REFERENCES clientes(id)" +
                    ")");

            // Tabla de items de pedidos
            stmt.execute("CREATE TABLE IF NOT EXISTS items_pedido (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "pedido_id INTEGER NOT NULL," +
                    "producto_id INTEGER NOT NULL," +
                    "cantidad INTEGER NOT NULL," +
                    "precio_unitario REAL NOT NULL," +
                    "subtotal REAL NOT NULL," +
                    "FOREIGN KEY (pedido_id) REFERENCES pedidos(id)," +
                    "FOREIGN KEY (producto_id) REFERENCES productos(id)" +
                    ")");

            // Tabla de promociones
            stmt.execute("CREATE TABLE IF NOT EXISTS promociones (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "descuento REAL NOT NULL," +
                    "fecha_inicio DATE NOT NULL," +
                    "fecha_fin DATE NOT NULL," +
                    "activa BOOLEAN DEFAULT 1," +
                    "fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");

            // Tabla de historial de ventas (NUEVA)
            stmt.execute("CREATE TABLE IF NOT EXISTS historial_venta (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_pedido INTEGER NOT NULL," +
                    "id_cliente INTEGER NOT NULL," +
                    "fecha_venta TIMESTAMP NOT NULL," +
                    "monto_total REAL NOT NULL," +
                    "estado TEXT DEFAULT 'Completado'," +
                    "metodo_pago TEXT," +
                    "descuento_aplicado REAL DEFAULT 0," +
                    "notas TEXT," +
                    "FOREIGN KEY (id_pedido) REFERENCES pedidos(id)," +
                    "FOREIGN KEY (id_cliente) REFERENCES clientes(id)" +
                    ")");

            // Tabla de facturas (NUEVA)
            stmt.execute("CREATE TABLE IF NOT EXISTS factura (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_pedido INTEGER NOT NULL," +
                    "numero_factura INTEGER NOT NULL UNIQUE," +
                    "id_cliente INTEGER NOT NULL," +
                    "fecha_emision TIMESTAMP NOT NULL," +
                    "subtotal REAL NOT NULL," +
                    "impuesto REAL DEFAULT 0," +
                    "descuento REAL DEFAULT 0," +
                    "total REAL NOT NULL," +
                    "estado TEXT DEFAULT 'Emitida'," +
                    "metodo_pago TEXT," +
                    "fecha_pago TIMESTAMP," +
                    "observaciones TEXT," +
                    "FOREIGN KEY (id_pedido) REFERENCES pedidos(id)," +
                    "FOREIGN KEY (id_cliente) REFERENCES clientes(id)" +
                    ")");

            // Tabla de items de factura (NUEVA)
            stmt.execute("CREATE TABLE IF NOT EXISTS item_factura (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_factura INTEGER NOT NULL," +
                    "id_producto INTEGER NOT NULL," +
                    "nombre_producto TEXT NOT NULL," +
                    "cantidad INTEGER NOT NULL," +
                    "precio_unitario REAL NOT NULL," +
                    "FOREIGN KEY (id_factura) REFERENCES factura(id)," +
                    "FOREIGN KEY (id_producto) REFERENCES productos(id)" +
                    ")");

            // Crear índices para optimización
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_historial_cliente ON historial_venta(id_cliente)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_historial_fecha ON historial_venta(fecha_venta)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_factura_cliente ON factura(id_cliente)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_factura_numero ON factura(numero_factura)");

            System.out.println("✓ Tablas de BD creadas correctamente");
        } catch (SQLException e) {
            System.err.println("Error al inicializar BD: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Cierra la conexión a la base de datos
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✓ Conexión a BD cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
