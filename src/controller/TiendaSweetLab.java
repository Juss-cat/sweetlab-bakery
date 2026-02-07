package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.ClienteDAO;
import dao.ProductoDAO;
import dao.PromocionDAO;
import database.ConexionBD;
import model.CarroCompras;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Producto;
import model.Promocion;

/**
 * Clase controladora que gestiona toda la lógica de negocio de Sweet Lab
 * Patrón: Singleton + Facade + DAO
 * Ahora integrada con base de datos SQLite
 */
public class TiendaSweetLab {
    private static TiendaSweetLab instancia;

    private List<Producto> productos;
    private List<Cliente> clientes;
    private List<Pedido> pedidos;
    private List<Promocion> promociones;
    private CarroCompras carroCompras;
    
    // DAOs para acceso a base de datos
    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;
    // private PedidoDAO pedidoDAO;
    private PromocionDAO promocionDAO;
    // private HistorialVentaDAO historialVentaDAO;
    // private FacturaDAO facturaDAO;
    
    // Contadores para IDs (si no hay en BD)
    private int proximoIdProducto = 1;
    private int proximoIdCliente = 1;
    private int proximoIdPedido = 1;
    private int proximoIdPromocion = 1;

    // Constructor privado para Singleton
    private TiendaSweetLab() {
        try {
            // Inicializar DAOs
            this.productoDAO = new ProductoDAO();
            this.clienteDAO = new ClienteDAO();
            // this.pedidoDAO = new PedidoDAO();
            this.promocionDAO = new PromocionDAO();
            // this.historialVentaDAO = new HistorialVentaDAO();
            // this.facturaDAO = new FacturaDAO();
            
            // Inicializar conexión a BD
            ConexionBD.obtenerConexion();
            
            // Cargar datos desde BD
            this.productos = new ArrayList<>();
            this.clientes = new ArrayList<>();
            this.pedidos = new ArrayList<>();
            this.promociones = new ArrayList<>();
            this.carroCompras = new CarroCompras();
            
            cargarDatosDesdeBaseDatos();
            
            // Si la BD está vacía, inicializar con datos de prueba
            if (productos.isEmpty()) {
                inicializarDatos();
            }
            
            System.out.println("✓ TiendaSweetLab inicializada con BD");
        } catch (Exception e) {
            System.err.println("Error al inicializar TiendaSweetLab: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Obtener instancia única
    public static TiendaSweetLab obtenerInstancia() {
        if (instancia == null) {
            instancia = new TiendaSweetLab();
        }
        return instancia;
    }

    /**
     * Carga todos los datos desde la base de datos
     */
    private void cargarDatosDesdeBaseDatos() {
        try {
            this.productos = productoDAO.obtenerTodos();
            this.clientes = clienteDAO.obtenerTodos();
            // this.pedidos = pedidoDAO.obtenerTodos();
            this.promociones = promocionDAO.obtenerTodas();
            
            System.out.println("✓ Datos cargados desde BD: " + 
                    productos.size() + " productos, " + 
                    clientes.size() + " clientes");
        } catch (Exception e) {
            System.err.println("Error al cargar datos de BD: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Inicializa con datos de prueba (solo si la BD está vacía)
     */
    private void inicializarDatos() {
        System.out.println("Inicializando datos de prueba en BD...");
        
        // THE SWEET CORNER - Postres y pasteles
        agregarProducto("CupcakeDeChocolate", 5.99, "Postres", "Cupcake de chocolate con cobertura de crema", 50);
        agregarProducto("StrawberryLayerDream", 7.50, "Postres", "Capas de fresas con crema deliciosa", 45);
        agregarProducto("CinnamonClouds", 6.50, "Postres", "Nubes de canela suave y esponjosa", 40);
        agregarProducto("BlueberryCloudStack", 7.99, "Postres", "Pila de arándanos con crema", 30);
        agregarProducto("BrazoGitanoFresa", 8.50, "Postres", "Tradicional brazo gitano con fresa", 35);
        agregarProducto("PastelDeChocolateConFrutosRojos", 9.99, "Postres", "Pastel de chocolate con frutos rojos frescos", 25);
        agregarProducto("MaritozzodeFresa", 6.99, "Postres", "Martozzo italiano con crema y fresa", 40);
        agregarProducto("Berry-ChocoCroissant", 5.50, "Postres", "Croissant con chocolate y berries", 50);
        agregarProducto("GoldenSweetBun", 4.99, "Postres", "Bollito dorado y dulce tradicional", 60);

        // THE COFFEE LAB - Bebidas calientes y frías
        agregarProducto("CafeCaliente", 3.50, "Bebidas", "Café espresso caliente recién preparado", 100);
        agregarProducto("CafeHeladoConCrema", 4.50, "Bebidas", "Café helado con crema batida", 80);
        agregarProducto("Choco-Latte", 4.99, "Bebidas", "Latte con chocolate delicioso", 90);
        agregarProducto("MatchaTradicional", 5.50, "Bebidas", "Matcha tradicional japonés", 70);
        agregarProducto("TeVerde", 3.00, "Bebidas", "Té verde aromático premium", 85);
        agregarProducto("BobaBrownSugarSpecial", 5.99, "Bebidas", "Boba con azúcar morena especial", 75);
        agregarProducto("BerryLabRefresher", 4.99, "Bebidas", "Bebida refrescante de berries", 95);

        // THE SAVORY LAB - Hamburguesas y pizza
        agregarProducto("TheLabBurger", 10.99, "Comidas", "Hamburguesa gourmet de la casa", 40);
        agregarProducto("RebanadaDePizza", 8.50, "Comidas", "Rebanada de pizza artesanal", 60);
        agregarProducto("SunnysideToast", 6.99, "Comidas", "Pan tostado con huevo de corral", 50);
        agregarProducto("Dona-IceSandwich", 7.50, "Comidas", "Sándwich con doña y helado", 35);

        // Promociones de ejemplo
        crearPromocion("Oferta de Bienvenida", 10, LocalDate.now(), LocalDate.now().plusDays(30));
        
        System.out.println("✓ Datos de prueba insertados en BD");
    }


    public Producto obtenerProducto(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void agregarProducto(String nombre, double precio, String tipo, String descripcion, int stock) {
        Producto producto = new Producto(proximoIdProducto++, nombre, precio, tipo, descripcion, stock);
        productos.add(producto);
        try {
            // Si la BD tiene un método insertar, usarlo; sino guardar en memoria
            // productoDAO.crear(producto);
        } catch (Exception e) {
            System.err.println("Error al guardar producto en BD: " + e.getMessage());
        }
    }

    public void actualizarProducto(int id, String nombre, double precio, String tipo, String descripcion, int stock) {
        Producto p = obtenerProducto(id);
        if (p != null) {
            p.setNombre(nombre);
            p.setPrecio(precio);
            p.setTipo(tipo);
            p.setDescripcion(descripcion);
            p.setStock(stock);
        }
    }

    public void eliminarProducto(int id) {
        productos.removeIf(p -> p.getId() == id);
    }

    public List<Producto> obtenerProductosPorTipo(String tipo) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getTipo().equalsIgnoreCase(tipo)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<Producto> obtenerTodosProductos() {
        return new ArrayList<>(productos);
    }

    // ==================== GESTIÓN DE CLIENTES ====================

    public void agregarCliente(String nombre, String email, String telefono, String direccion) {
        Cliente cliente = new Cliente(proximoIdCliente++, nombre, email, telefono, direccion, "", "");
        clientes.add(cliente);
    }

    public void agregarCliente(String nombre, String email, String telefono, String direccion, String ciudad, String codigoPostal) {
        Cliente cliente = new Cliente(proximoIdCliente++, nombre, email, telefono, direccion, ciudad, codigoPostal);
        clientes.add(cliente);
    }

    public Cliente obtenerCliente(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public void actualizarCliente(int id, String nombre, String email, String telefono, String direccion) {
        Cliente c = obtenerCliente(id);
        if (c != null) {
            c.setNombre(nombre);
            c.setEmail(email);
            c.setTelefono(telefono);
            c.setDireccion(direccion);
        }
    }

    public void actualizarCliente(int id, String nombre, String email, String telefono, String direccion, String ciudad, String codigoPostal) {
        Cliente c = obtenerCliente(id);
        if (c != null) {
            c.setNombre(nombre);
            c.setEmail(email);
            c.setTelefono(telefono);
            c.setDireccion(direccion);
            c.setCiudad(ciudad);
            c.setCodigoPostal(codigoPostal);
        }
    }

    public void eliminarCliente(int id) {
        clientes.removeIf(c -> c.getId() == id);
    }

    public List<Cliente> obtenerTodosClientes() {
        return new ArrayList<>(clientes);
    }

    // ==================== GESTIÓN DEL CARRITO ====================

    public void agregarAlCarrito(Producto producto, int cantidad) {
        if (producto.getStock() >= cantidad) {
            carroCompras.agregarProducto(producto, cantidad);
        }
    }

    public void removerDelCarrito(int indice) {
        carroCompras.removerProducto(indice);
    }

    public void actualizarCarrito(int indice, int nuevaCantidad) {
        carroCompras.actualizarCantidad(indice, nuevaCantidad);
    }

    public void aplicarPromocionCarrito(int indiceItem, int idPromocion) {
        Promocion promo = obtenerPromocion(idPromocion);
        if (promo != null && promo.esValida()) {
            carroCompras.aplicarPromocion(indiceItem, promo);
        }
    }

    public void vaciarCarrito() {
        carroCompras.vaciar();
    }

    public CarroCompras obtenerCarrito() {
        return carroCompras;
    }

    // ==================== GESTIÓN DE PEDIDOS ====================

    public Pedido crearPedido(Cliente cliente) {
        if (carroCompras.estaVacio()) {
            return null;
        }

        Pedido pedido = new Pedido(proximoIdPedido++, cliente, LocalDate.now());

        // Copiar items del carrito al pedido
        for (ItemPedido item : carroCompras.getItems()) {
            pedido.agregarItem(item);
            // Descontar del stock
            item.getProducto().setStock(item.getProducto().getStock() - item.getCantidad());
        }

        pedidos.add(pedido);
        carroCompras.vaciar();
        return pedido;
    }

    public Pedido obtenerPedido(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void actualizarEstadoPedido(int id, Pedido.EstadoPedido nuevoEstado) {
        Pedido p = obtenerPedido(id);
        if (p != null) {
            p.setEstado(nuevoEstado);
        }
    }

    public void eliminarPedido(int id) {
        pedidos.removeIf(p -> p.getId() == id);
    }

    public List<Pedido> obtenerPedidosCliente(int idCliente) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getCliente().getId() == idCliente) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<Pedido> obtenerTodosPedidos() {
        return new ArrayList<>(pedidos);
    }

    // ==================== GESTIÓN DE PROMOCIONES ====================

    public void crearPromocion(String nombre, double descuento, LocalDate fechaInicio, LocalDate fechaFin) {
        Promocion promo = new Promocion(proximoIdPromocion++, nombre, descuento, fechaInicio, fechaFin);
        promociones.add(promo);
    }

    public Promocion obtenerPromocion(int id) {
        for (Promocion p : promociones) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void agregarProductoAPromocion(int idPromocion, int idProducto) {
        Promocion promo = obtenerPromocion(idPromocion);
        Producto prod = obtenerProducto(idProducto);
        if (promo != null && prod != null) {
            promo.agregarProducto(prod);
        }
    }

    public List<Promocion> obtenerTodasPromociones() {
        return new ArrayList<>(promociones);
    }

    public List<Promocion> obtenerPromocionesActivas() {
        List<Promocion> activas = new ArrayList<>();
        for (Promocion p : promociones) {
            if (p.esValida()) {
                activas.add(p);
            }
        }
        return activas;
    }

    // ==================== REPORTES Y ESTADÍSTICAS ====================

    public double calcularVentasTotales() {
        double total = 0;
        for (Pedido p : pedidos) {
            if (p.getEstado() != Pedido.EstadoPedido.CANCELADO) {
                total += p.getTotal();
            }
        }
        return total;
    }

    public int obtenerCantidadPedidos() {
        return pedidos.size();
    }

    public Producto obtenerProductoMasVendido() {
        if (productos.isEmpty())
            return null;

        Producto masVendido = productos.get(0);
        int maxVentas = 0;

        for (Producto p : productos) {
            int ventas = contarVentasProducto(p.getId());
            if (ventas > maxVentas) {
                maxVentas = ventas;
                masVendido = p;
            }
        }
        return masVendido;
    }

    private int contarVentasProducto(int idProducto) {
        int total = 0;
        for (Pedido p : pedidos) {
            for (ItemPedido item : p.getItems()) {
                if (item.getProducto().getId() == idProducto) {
                    total += item.getCantidad();
                }
            }
        }
        return total;
    }
}
