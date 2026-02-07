package view;

import java.util.List;

import controller.TiendaSweetLab;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.CarroCompras;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Producto;

public class SweetLabApp extends Application {
    private TiendaSweetLab tienda;
    private BorderPane root;
    private GridPane gridProductos;
    private GridPane gridProductosCategorizados;
    private TableView<ItemPedido> tablaCarrito;
    private Label lblTotal;
    private Label lblProductosCarrito;

    @Override
    public void start(Stage primaryStage) {
        tienda = TiendaSweetLab.obtenerInstancia();
        
        // Cargar font personalizado
        cargarFontPersonalizado();

        root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Crear componentes
        crearMenuLateral();
        crearAreaProductos();

        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setTitle("Sweet Lab Bakery - Sistema de Gestión");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    
    private void cargarFontPersonalizado() {
        try {
            String fontPath = "resources/fonts/Daily Vibes.otf";
            java.io.InputStream fontStream = new java.io.FileInputStream(fontPath);
            Font fontCustom = Font.loadFont(fontStream, 14);
            if (fontCustom != null) {
                System.out.println("✓ Font personalizado 'Daily Vibes' cargado exitosamente");
            } else {
                System.out.println("⚠ No se pudo cargar el font personalizado");
            }
        } catch (Exception e) {
            System.out.println("⚠ Error al cargar el font: " + e.getMessage());
        }
    }
    
    private Font crearFont(FontWeight peso, double tamaño) {
        return Font.font("Daily Vibes", peso, tamaño);
    }
    
    private Font crearFont(double tamaño) {
        return Font.font("Daily Vibes", tamaño);
    }

    // ==================== MENÚ LATERAL ====================
    private void crearMenuLateral() {
        VBox menuLateral = new VBox(15);
        menuLateral.setPadding(new Insets(20));
        menuLateral.setStyle("-fx-background-color: linear-gradient(to bottom, #e8d4e8, #f0e4f0); -fx-border-color: #FFB6D9; -fx-border-width: 0 2 0 0;");
        menuLateral.setPrefWidth(210);

        // Título con decoración
        Label lblTitulo = new Label("✨ SWEET LAB ✨\nBAKERY");
        lblTitulo.setFont(crearFont(FontWeight.BOLD, 14.0));
        lblTitulo.setTextFill(Color.web("#C41E3A"));
        lblTitulo.setStyle("-fx-text-alignment: center; -fx-padding: 10; -fx-background-color: #FFB6D9; -fx-border-radius: 10; -fx-text-fill: white;");
        lblTitulo.setTextFill(Color.web("#8B1530"));
        lblTitulo.setWrapText(true);

        // Separador decorativo
        Separator sep1 = new Separator();
        sep1.setStyle("-fx-border-color: #FFD1E8; -fx-padding: 5;");

        // Botones de navegación CON DECORACIÓN
        Button btnHome = crearBotonMenuDecorado("🏠 HOME", "#C41E3A", "#E0243F");
        Button btnClientes = crearBotonMenuDecorado("👥 CLIENTES", "#FFB6D9", "#FFA5CC");
        Button btnPedidos = crearBotonMenuDecorado("📋 PEDIDOS", "#FFD1E8", "#FFC5D6");
        Button btnPromociones = crearBotonMenuDecorado("🎁 PROMOCIONES", "#FFB6D9", "#FFA5CC");

        // Configurar acciones
        btnHome.setOnAction(e -> mostrarHome());
        btnClientes.setOnAction(e -> mostrarGestionClientes());
        btnPedidos.setOnAction(e -> mostrarGestionPedidos());
        btnPromociones.setOnAction(e -> mostrarGestionPromociones());

        // Aplicar efectos hover
        agregarEfectoHoverBotonMenu(btnHome, "#C41E3A", "#E0243F");
        agregarEfectoHoverBotonMenu(btnClientes, "#FFB6D9", "#FFA5CC");
        agregarEfectoHoverBotonMenu(btnPedidos, "#FFD1E8", "#FFC5D6");
        agregarEfectoHoverBotonMenu(btnPromociones, "#FFB6D9", "#FFA5CC");

        // Separador decorativo
        Separator sep2 = new Separator();
        sep2.setStyle("-fx-border-color: #FFD1E8; -fx-padding: 5;");

        // Sección del carrito CON DECORACIÓN
        VBox seccionCarrito = crearSeccionCarritoDecorada();

        // Separador decorativo
        Separator sep3 = new Separator();
        sep3.setStyle("-fx-border-color: #FFD1E8; -fx-padding: 5;");

        // Botón salir
        Button btnSalir = crearBotonMenuDecorado("❌ SALIR", "#A31D37", "#C41E3A");
        btnSalir.setOnAction(e -> System.exit(0));
        agregarEfectoHoverBotonMenu(btnSalir, "#A31D37", "#C41E3A");

        menuLateral.getChildren().addAll(
                lblTitulo,
                sep1,
                btnHome,
                btnClientes,
                btnPedidos,
                btnPromociones,
                sep2,
                seccionCarrito,
                sep3,
                btnSalir);

        root.setLeft(menuLateral);
    }

    private Button crearBotonMenuDecorado(String texto, String colorBase, String colorHover) {
        Button btn = new Button(texto);
        btn.setPrefWidth(165);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-background-color: " + colorBase + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11; -fx-padding: 10; -fx-border-radius: 8; -fx-border-color: #FFFFFF; -fx-border-width: 1;");
        return btn;
    }

    private void agregarEfectoHoverBotonMenu(Button btn, String colorBase, String colorHover) {
        btn.setOnMouseEntered(e -> {
            btn.setStyle("-fx-background-color: " + colorHover + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11; -fx-padding: 10; -fx-border-radius: 8; -fx-border-color: #FFFFFF; -fx-border-width: 2; -fx-cursor: hand;");
            btn.setScaleX(1.05);
            btn.setScaleY(1.05);
        });
        btn.setOnMouseExited(e -> {
            btn.setStyle("-fx-background-color: " + colorBase + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11; -fx-padding: 10; -fx-border-radius: 8; -fx-border-color: #FFFFFF; -fx-border-width: 1;");
            btn.setScaleX(1.0);
            btn.setScaleY(1.0);
        });
    }

    private VBox crearSeccionCarritoDecorada() {
        VBox seccion = new VBox(12);
        seccion.setPadding(new Insets(12));
        seccion.setStyle("-fx-background-color: #FFE6F0; -fx-border-color: linear-gradient(to bottom, #FFD1E8, #FFB6D9); -fx-border-radius: 12; -fx-border-width: 2;");

        Label lblCarrito = new Label("🛒 MI CARRITO");
        lblCarrito.setFont(crearFont(FontWeight.BOLD, 13.0));
        lblCarrito.setTextFill(Color.web("#C41E3A"));
        lblCarrito.setStyle("-fx-text-alignment: center; -fx-padding: 8;");

        lblProductosCarrito = new Label("0 productos");
        lblProductosCarrito.setStyle("-fx-font-size: 12; -fx-text-alignment: center; -fx-text-fill: #5a3a5a;");

        lblTotal = new Label("Total: $0.00");
        lblTotal.setFont(crearFont(FontWeight.BOLD, 14.0));
        lblTotal.setTextFill(Color.web("#C41E3A"));
        lblTotal.setStyle("-fx-text-alignment: center; -fx-padding: 5;");

        Button btnVerCarrito = crearBotonMenuDecorado("Ver Carrito", "#7dd3c0", "#6ac0ad");
        btnVerCarrito.setPrefHeight(35);
        btnVerCarrito.setOnAction(e -> mostrarCarrito());
        agregarEfectoHoverBotonMenu(btnVerCarrito, "#7dd3c0", "#6ac0ad");

        Button btnCheckout = new Button("💳 CHECKOUT");
        btnCheckout.setPrefWidth(165);
        btnCheckout.setPrefHeight(35);
        btnCheckout.setStyle("-fx-background-color: #b8d4b8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11; -fx-padding: 10; -fx-border-radius: 8; -fx-border-color: #FFFFFF; -fx-border-width: 1;");
        btnCheckout.setOnAction(e -> mostrarCarrito());
        agregarEfectoHoverBotonMenu(btnCheckout, "#b8d4b8", "#a0c0a0");

        seccion.getChildren().addAll(
                lblCarrito,
                new Separator(),
                lblProductosCarrito,
                lblTotal,
                btnVerCarrito,
                btnCheckout);

        return seccion;
    }

    // ==================== ÁREA DE PRODUCTOS ====================
    private void crearAreaProductos() {
        VBox areaContenido = new VBox(20);
        areaContenido.setPadding(new Insets(20));
        areaContenido.setStyle("-fx-background-color: #f5f5f5;");

        // Búsqueda y filtros
        HBox herramientas = crearBarraHerramientas();

        // Grid de productos
        ScrollPane scrollProductos = new ScrollPane();
        gridProductos = new GridPane();
        gridProductos.setHgap(15);
        gridProductos.setVgap(15);
        gridProductos.setPadding(new Insets(15));
        gridProductos.setStyle("-fx-background-color: #f5f5f5;");

        scrollProductos.setContent(gridProductos);
        scrollProductos.setFitToWidth(true);

        areaContenido.getChildren().addAll(
                herramientas,
                scrollProductos);

        VBox.setVgrow(scrollProductos, Priority.ALWAYS);
        root.setCenter(areaContenido);

        mostrarHome();
    }

    private HBox crearBarraHerramientas() {
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");

        Label lblTitulo = new Label("CATÁLOGO DE PRODUCTOS");
        lblTitulo.setFont(crearFont(FontWeight.BOLD, 14.0));
        lblTitulo.setTextFill(Color.web("#8B6B8B"));

        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar producto...");
        txtBuscar.setPrefWidth(200);

        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("Todos", "Postres", "Bebidas", "Comidas");
        cbTipo.setValue("Todos");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle("-fx-background-color: #b8d4b8; -fx-text-fill: white;");

        toolbar.getChildren().addAll(
                lblTitulo,
                new Separator(),
                new Label("Tipo:"),
                cbTipo,
                new Label("Buscar:"),
                txtBuscar,
                btnBuscar);

        return toolbar;
    }

    // ==================== PANTALLAS PRINCIPALES ====================
    
    private void mostrarHome() {
        gridProductos.getChildren().clear();
        
        VBox contenedorHome = new VBox(15);
        contenedorHome.setPadding(new Insets(20));
        contenedorHome.setStyle("-fx-background-color: #FFF8F0;");
        
        // ============ SECCIÓN DE ENCABEZADO CON LOGO GRANDE ============
        HBox seccionEncabezado = new HBox(20);
        seccionEncabezado.setAlignment(Pos.CENTER_LEFT);
        seccionEncabezado.setPadding(new Insets(15));
        seccionEncabezado.setStyle("-fx-background-color: white; -fx-border-color: #FFB6D9; -fx-border-radius: 10; -fx-border-width: 2;");
        
        // Contenedor izquierdo - Texto de bienvenida CON MÁS DECORACIÓN
        VBox cajaTexto = new VBox(15);
        cajaTexto.setAlignment(Pos.TOP_CENTER);
        cajaTexto.setPrefWidth(400);
        cajaTexto.setStyle("-fx-padding: 20;");
        
        Label lblBienvenida = new Label("🍰 BIENVENIDO A\nSWEET LAB BAKERY");
        lblBienvenida.setFont(crearFont(FontWeight.BOLD, 24.0));
        lblBienvenida.setTextFill(Color.web("#C41E3A"));
        lblBienvenida.setWrapText(true);
        lblBienvenida.setTextAlignment(TextAlignment.CENTER);
        
        Label lblDescripcion = new Label("Descubre nuestras tres secciones especiales:\nPostres artesanales, Bebidas deliciosas y Comidas sabrosas");
        lblDescripcion.setFont(crearFont(13));
        lblDescripcion.setTextFill(Color.web("#A31D37"));
        lblDescripcion.setWrapText(true);
        lblDescripcion.setTextAlignment(TextAlignment.CENTER);
        
        // Agregar decoraciones adicionales
        Label lblDecoracion = new Label("🌸 ✨ 🌸");
        lblDecoracion.setStyle("-fx-font-size: 16; -fx-text-alignment: center;");
        
        Label lblInfoExtra = new Label("Disfruta de productos frescos y deliciosos\nhecho con amor para ti");
        lblInfoExtra.setFont(crearFont(11));
        lblInfoExtra.setTextFill(Color.web("#FFB6D9"));
        lblInfoExtra.setWrapText(true);
        lblInfoExtra.setTextAlignment(TextAlignment.CENTER);
        lblInfoExtra.setStyle("-fx-font-style: italic; -fx-padding: 10; -fx-background-color: #FFF8F0; -fx-border-radius: 8; -fx-border-color: #FFB6D9; -fx-border-width: 1;");
        
        cajaTexto.getChildren().addAll(lblBienvenida, lblDecoracion, lblDescripcion, lblInfoExtra);
        
        // Contenedor derecho - Logo grande CON DECORACIÓN
        VBox cajaLogo = new VBox();
        cajaLogo.setAlignment(Pos.CENTER);
        cajaLogo.setPrefWidth(280);
        cajaLogo.setPrefHeight(280);
        
        // DECORACIÓN EXTERNA - Marco con brillo
        VBox marcoBrillo = new VBox();
        marcoBrillo.setAlignment(Pos.CENTER);
        marcoBrillo.setPrefWidth(280);
        marcoBrillo.setPrefHeight(280);
        marcoBrillo.setStyle("-fx-background-color: #FFE6F0; -fx-border-color: #FF69B4; -fx-border-radius: 20; -fx-border-width: 3; -fx-padding: 8;");
        
        // DECORACIÓN INTERIOR - Logo Container
        VBox logoContainer = new VBox();
        logoContainer.setAlignment(Pos.CENTER);
        logoContainer.setPrefWidth(265);
        logoContainer.setPrefHeight(265);
        logoContainer.setStyle("-fx-background-color: #FFFBF8; -fx-border-color: #FFD1E8; -fx-border-radius: 18; -fx-border-width: 3; -fx-padding: 5;");
        
        ImageView logoHome = cargarLogo("logo1");
        logoHome.setFitHeight(220);
        logoHome.setFitWidth(220);
        logoHome.setPreserveRatio(true);
        
        logoContainer.getChildren().add(logoHome);
        marcoBrillo.getChildren().add(logoContainer);
        cajaLogo.getChildren().add(marcoBrillo);
        
        seccionEncabezado.getChildren().addAll(cajaTexto, cajaLogo);
        HBox.setHgrow(cajaTexto, Priority.ALWAYS);
        
        // ============ SECCIÓN DE BOTONES DE CATEGORÍAS ============
        VBox seccionBotones = new VBox(10);
        seccionBotones.setPadding(new Insets(15));
        seccionBotones.setStyle("-fx-background-color: rgba(255, 216, 233, 0.2); -fx-border-radius: 10;");
        
        Label lblCategorias = new Label("✨ Nuestras tres secciones especiales ✨");
        lblCategorias.setFont(crearFont(FontWeight.BOLD, 16.0));
        lblCategorias.setTextFill(Color.web("#C41E3A"));
        lblCategorias.setStyle("-fx-effect: dropshadow(one-pass-box, rgba(196, 30, 58, 0.3), 3, 0, 1, 1);");
        
        HBox botonesCategorias = new HBox(15);
        botonesCategorias.setAlignment(Pos.CENTER);
        botonesCategorias.setPadding(new Insets(10));
        
        Button btnTodos = new Button("📦 TODOS");
        btnTodos.setPrefWidth(160);
        btnTodos.setPrefHeight(45);
        btnTodos.setStyle("-fx-background-color: #C41E3A; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12; -fx-border-radius: 10; -fx-border-color: #8B1530; -fx-border-width: 2; -fx-padding: 10;");

        btnTodos.setOnAction(e -> mostrarProductosPorCategoria(null));
        
        Button btnPostres = new Button("🍰 POSTRES");
        btnPostres.setPrefWidth(160);
        btnPostres.setPrefHeight(45);
        btnPostres.setStyle("-fx-background-color: #FFB6D9; -fx-text-fill: #5a3a5a; -fx-font-weight: bold; -fx-font-size: 12; -fx-border-radius: 10; -fx-border-color: #FFA5CC; -fx-border-width: 2; -fx-padding: 10;");
        btnPostres.setOnAction(e -> mostrarProductosPorCategoria("Postres"));
        
        Button btnBebidas = new Button("☕ BEBIDAS");
        btnBebidas.setPrefWidth(160);
        btnBebidas.setPrefHeight(45);
        btnBebidas.setStyle("-fx-background-color: #FFD1E8; -fx-text-fill: #5a3a5a; -fx-font-weight: bold; -fx-font-size: 12; -fx-border-radius: 10; -fx-border-color: #FFB6D9; -fx-border-width: 2; -fx-padding: 10;");
        btnBebidas.setOnAction(e -> mostrarProductosPorCategoria("Bebidas"));
        
        Button btnComidas = new Button("🍔 COMIDAS");
        btnComidas.setPrefWidth(160);
        btnComidas.setPrefHeight(45);
        btnComidas.setStyle("-fx-background-color: #FFB6D9; -fx-text-fill: #5a3a5a; -fx-font-weight: bold; -fx-font-size: 12; -fx-border-radius: 10; -fx-border-color: #FFA5CC; -fx-border-width: 2; -fx-padding: 10;");
        btnComidas.setOnAction(e -> mostrarProductosPorCategoria("Comidas"));
        
        // Aplicar efectos hover a los botones
        agregarEfectoHoverBoton(btnTodos, "#C41E3A", "#E0243F");
        agregarEfectoHoverBoton(btnPostres, "#FFB6D9", "#FFA5CC");
        agregarEfectoHoverBoton(btnBebidas, "#FFD1E8", "#FFC5D6");
        agregarEfectoHoverBoton(btnComidas, "#FFB6D9", "#FFA5CC");
        
        botonesCategorias.getChildren().addAll(btnTodos, btnPostres, btnBebidas, btnComidas);
        seccionBotones.getChildren().addAll(lblCategorias, botonesCategorias);
        
        // GridPane para mostrar productos (asignado a variable de instancia)
        gridProductosCategorizados = new GridPane();
        gridProductosCategorizados.setHgap(15);
        gridProductosCategorizados.setVgap(15);
        gridProductosCategorizados.setPadding(new Insets(10));
        gridProductosCategorizados.setStyle("-fx-background-color: #FFF8F0;");
        
        ScrollPane scrollProductos = new ScrollPane(gridProductosCategorizados);
        scrollProductos.setFitToWidth(true);
        scrollProductos.setPrefHeight(500);
        
        contenedorHome.getChildren().addAll(
                seccionEncabezado,
                new Separator(),
                seccionBotones,
                scrollProductos
        );
        
        gridProductos.add(contenedorHome, 0, 0);
        GridPane.setHgrow(contenedorHome, Priority.ALWAYS);
        GridPane.setVgrow(contenedorHome, Priority.ALWAYS);
        
        // Mostrar todos los productos por defecto
        mostrarProductosPorCategoria(null);
    }
    
    private void mostrarProductosPorCategoria(String categoria) {
        List<Producto> productos = tienda.obtenerTodosProductos();
        
        // Filtrar si hay categoría seleccionada
        if (categoria != null && !categoria.isEmpty()) {
            productos = productos.stream()
                .filter(p -> p.getTipo().equals(categoria))
                .toList();
        }
        
        // Limpiar y actualizar el grid
        if (gridProductosCategorizados != null) {
            gridProductosCategorizados.getChildren().clear();
            mostrarProductosEnGrid(productos, gridProductosCategorizados);
        }
    }
    
    private void mostrarProductosEnGrid(List<Producto> productos, GridPane grid) {
        int columna = 0;
        int fila = 0;

        for (Producto p : productos) {
            VBox tarjeta = crearTarjetaProducto(p);
            grid.add(tarjeta, columna, fila);
            columna++;
            if (columna >= 3) {
                columna = 0;
                fila++;
            }
        }
    }

    private VBox crearTarjetaProducto(Producto p) {
        VBox tarjeta = new VBox(10);
        tarjeta.setPadding(new Insets(15));
        tarjeta.setStyle("-fx-border-color: #ddd; -fx-border-radius: 10; -fx-background-color: white;");
        tarjeta.setPrefWidth(200);
        tarjeta.setAlignment(Pos.TOP_CENTER);

        // Contenedor de etiquetas
        HBox etiquetas = new HBox();
        etiquetas.setPrefWidth(170);
        if (p.isEsNuevo()) {
            Label lblNuevo = new Label("NEW");
            lblNuevo.setStyle(
                    "-fx-background-color: #f5d547; -fx-text-fill: #8B6B8B; -fx-padding: 3 8; -fx-font-weight: bold; -fx-font-size: 10;");
            etiquetas.getChildren().add(lblNuevo);
        }
        if (p.isEsMasVendido()) {
            Label lblMasVendido = new Label("BEST SELLER");
            lblMasVendido.setStyle(
                    "-fx-background-color: #f5d547; -fx-text-fill: #8B6B8B; -fx-padding: 3 8; -fx-font-weight: bold; -fx-font-size: 10;");
            etiquetas.getChildren().add(lblMasVendido);
        }

        // Imagen - cargar desde archivo
        VBox imagenPlaceholder = new VBox();
        imagenPlaceholder.setPrefHeight(150);
        imagenPlaceholder.setAlignment(Pos.CENTER);
        imagenPlaceholder.setStyle("-fx-background-color: #FFF8F0; -fx-border-radius: 8;");
        
        // Intentar cargar imagen del producto
        String nombreProducto = p.getNombre();
        String[] extensiones = {".png", ".jpg", ".jpeg"};
        Image img = null;
        
        for (String ext : extensiones) {
            try {
                String imagenPath = "resources/images/products/" + nombreProducto + ext;
                img = new Image(new java.io.FileInputStream(imagenPath), 150, 150, true, true);
                break;
            } catch (Exception ignored) {
                // Continuar con la siguiente extensión
            }
        }
        
        if (img != null) {
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(150);
            imgView.setFitWidth(150);
            imgView.setPreserveRatio(false);
            imagenPlaceholder.getChildren().add(imgView);
        } else {
            // Si no encuentra la imagen, mostrar color de placeholder
            imagenPlaceholder.setStyle(obtenerColorProducto(p.getTipo()));
            Label lblPlaceholder = new Label("📷\nSin imagen");
            lblPlaceholder.setStyle("-fx-text-alignment: center; -fx-text-fill: #999999; -fx-font-size: 11;");
            imagenPlaceholder.getChildren().add(lblPlaceholder);
        }

        Label lblProducto = new Label(p.getNombre());
        lblProducto.setFont(crearFont(FontWeight.BOLD, 12.0));
        lblProducto.setTextFill(Color.web("#5a3a5a"));
        lblProducto.setWrapText(true);

        Label lblPrecio = new Label("$" + String.format("%.2f", p.getPrecio()));
        lblPrecio.setFont(crearFont(FontWeight.BOLD, 14.0));
        lblPrecio.setTextFill(Color.web("#d4555a"));

        // Spinner para cantidad
        Spinner<Integer> spinnerCantidad = new Spinner<>(1, 100, 1);
        spinnerCantidad.setPrefWidth(100);

        // Botón Agregar al carrito
        Button btnAgregarCarrito = new Button("➕ AGREGAR");
        btnAgregarCarrito.setPrefWidth(170);
        btnAgregarCarrito.setStyle("-fx-background-color: #f5d547; -fx-text-fill: #5a3a5a; -fx-font-weight: bold;");
        btnAgregarCarrito.setOnAction(e -> {
            int cantidad = spinnerCantidad.getValue();
            tienda.agregarAlCarrito(p, cantidad);
            actualizarInfoCarrito();
            mostrarAlerta("✅ Producto agregado al carrito", "alert-info");
        });

        tarjeta.getChildren().addAll(
                etiquetas,
                imagenPlaceholder,
                lblProducto,
                lblPrecio,
                new Label("Cantidad:"),
                spinnerCantidad,
                btnAgregarCarrito);

        return tarjeta;
    }

    private String obtenerColorProducto(String tipo) {
        return switch (tipo) {
            case "Postres" -> "-fx-background-color: #fff9e6;";
            case "Bebidas" -> "-fx-background-color: #e6f0f9;";
            case "Comidas" -> "-fx-background-color: #fce6f0;";
            case "Cupcake" -> "-fx-background-color: #fff9e6;";
            case "Macaron" -> "-fx-background-color: #fce6f0;";
            case "Brownie" -> "-fx-background-color: #f5e6d3;";
            case "Tarta" -> "-fx-background-color: #e6f0f9;";
            case "Donut" -> "-fx-background-color: #f0e6f9;";
            default -> "-fx-background-color: #e6e6e6;";
        };
    }

    private void actualizarInfoCarrito() {
        CarroCompras carrito = tienda.obtenerCarrito();
        lblProductosCarrito.setText(carrito.getCantidadProductos() + " productos");
        lblTotal.setText("Total: $" + String.format("%.2f", carrito.getTotal()));
    }

    // ==================== PANTALLAS SECUNDARIAS ====================
    private void mostrarGestionProductos() {
        gridProductos.getChildren().clear();
        Label lblTitulo = new Label("GESTIÓN DE PRODUCTOS");
        lblTitulo.setFont(crearFont(FontWeight.BOLD, 16.0));
        gridProductos.add(lblTitulo, 0, 0);
    }

    private void mostrarGestionClientes() {
        gridProductos.getChildren().clear();
        
        VBox contenedorClientes = new VBox(15);
        contenedorClientes.setPadding(new Insets(20));
        
        // ============ SECCIÓN DE ENCABEZADO CON LOGO GRANDE ============
        HBox seccionEncabezado = new HBox(20);
        seccionEncabezado.setAlignment(Pos.CENTER_LEFT);
        seccionEncabezado.setPadding(new Insets(15));
        seccionEncabezado.setStyle("-fx-background-color: white; -fx-border-color: #FFB6D9; -fx-border-radius: 10; -fx-border-width: 2;");
        
        // Contenedor izquierdo - Información CON MÁS DECORACIÓN
        VBox cajaTexto = new VBox(15);
        cajaTexto.setAlignment(Pos.TOP_CENTER);
        cajaTexto.setPrefWidth(400);
        cajaTexto.setStyle("-fx-padding: 20;");
        
        Label lblTitulo = new Label("👥 GESTIÓN DE\nCLIENTES");
        lblTitulo.setFont(crearFont(FontWeight.BOLD, 24.0));
        lblTitulo.setTextFill(Color.web("#C41E3A"));
        lblTitulo.setWrapText(true);
        lblTitulo.setTextAlignment(TextAlignment.CENTER);
        
        Label lblDescripcion = new Label("Administra la información de tus clientes,\nagrégalos, edítalos y mantén un registro completo\nde sus datos y pedidos anteriores");
        lblDescripcion.setFont(crearFont(12));
        lblDescripcion.setTextFill(Color.web("#A31D37"));
        lblDescripcion.setWrapText(true);
        lblDescripcion.setTextAlignment(TextAlignment.CENTER);
        
        // Agregar decoraciones adicionales
        Label lblDecoracion1 = new Label("💫 ✨ 💫");
        lblDecoracion1.setStyle("-fx-font-size: 14; -fx-text-alignment: center;");
        
        Label lblInfoExtra = new Label("Mantén un control total de tus clientes\ny sus compras anteriores");
        lblInfoExtra.setFont(crearFont(11));
        lblInfoExtra.setTextFill(Color.web("#FFB6D9"));
        lblInfoExtra.setWrapText(true);
        lblInfoExtra.setTextAlignment(TextAlignment.CENTER);
        lblInfoExtra.setStyle("-fx-font-style: italic; -fx-padding: 10; -fx-background-color: #FFF8F0; -fx-border-radius: 8; -fx-border-color: #FFB6D9; -fx-border-width: 1;");
        
        cajaTexto.getChildren().addAll(lblTitulo, lblDecoracion1, lblDescripcion, lblInfoExtra);
        
        // Contenedor derecho - Logo grande CON DECORACIÓN
        VBox cajaLogo = new VBox();
        cajaLogo.setAlignment(Pos.CENTER);
        cajaLogo.setPrefWidth(280);
        cajaLogo.setPrefHeight(280);
        
        // DECORACIÓN EXTERNA - Marco con brillo
        VBox marcoBrillo = new VBox();
        marcoBrillo.setAlignment(Pos.CENTER);
        marcoBrillo.setPrefWidth(280);
        marcoBrillo.setPrefHeight(280);
        marcoBrillo.setStyle("-fx-background-color: #FFE6F0; -fx-border-color: #FF69B4; -fx-border-radius: 20; -fx-border-width: 3; -fx-padding: 8;");
        
        // DECORACIÓN INTERIOR - Logo Container
        VBox logoContainer = new VBox();
        logoContainer.setAlignment(Pos.CENTER);
        logoContainer.setPrefWidth(265);
        logoContainer.setPrefHeight(265);
        logoContainer.setStyle("-fx-background-color: #FFFBF8; -fx-border-color: #FFD1E8; -fx-border-radius: 18; -fx-border-width: 3; -fx-padding: 5;");
        
        ImageView logoClientes = cargarLogo("logo2");
        logoClientes.setFitHeight(220);
        logoClientes.setFitWidth(220);
        logoClientes.setPreserveRatio(true);
        
        logoContainer.getChildren().add(logoClientes);
        marcoBrillo.getChildren().add(logoContainer);
        cajaLogo.getChildren().add(marcoBrillo);
        
        seccionEncabezado.getChildren().addAll(cajaTexto, cajaLogo);
        HBox.setHgrow(cajaTexto, Priority.ALWAYS);
        
        // Botones de acción
        HBox botonesAccion = new HBox(10);
        botonesAccion.setPadding(new Insets(10));
        botonesAccion.setStyle("-fx-background-color: #f0e4f0; -fx-border-radius: 5;");
        
        Button btnAgregarCliente = new Button("➕ AGREGAR CLIENTE");
        btnAgregarCliente.setStyle("-fx-background-color: #7dd3c0; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10;");
        btnAgregarCliente.setOnAction(e -> mostrarDialogoAgregarCliente());
        
        Button btnActualizar = new Button("🔄 ACTUALIZAR");
        btnActualizar.setStyle("-fx-background-color: #b8d4b8; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10;");
        btnActualizar.setOnAction(e -> mostrarGestionClientes());
        
        botonesAccion.getChildren().addAll(btnAgregarCliente, btnActualizar);
        
        // Tabla de clientes
        TableView<Cliente> tablaClientes = new TableView<>();
        
        TableColumn<Cliente, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);
        
        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(150);
        
        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(180);
        
        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTelefono.setPrefWidth(120);
        
        TableColumn<Cliente, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colDireccion.setPrefWidth(200);
        
        TableColumn<Cliente, Void> colAcciones = new TableColumn<>("Acciones");
        colAcciones.setPrefWidth(150);
        colAcciones.setCellFactory(param -> new TableCell<Cliente, Void>() {
            private final Button btnEditar = new Button("✏️ Editar");
            private final Button btnEliminar = new Button("❌ Eliminar");
            
            {
                btnEditar.setStyle("-fx-font-size: 10;");
                btnEliminar.setStyle("-fx-font-size: 10; -fx-text-fill: white; -fx-background-color: #d4555a;");
                
                btnEditar.setOnAction(event -> {
                    Cliente cliente = getTableView().getItems().get(getIndex());
                    mostrarDialogoEditarCliente(cliente);
                });
                
                btnEliminar.setOnAction(event -> {
                    Cliente cliente = getTableView().getItems().get(getIndex());
                    Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmar.setTitle("Confirmar eliminación");
                    confirmar.setHeaderText("¿Eliminar cliente?");
                    confirmar.setContentText("¿Deseas eliminar a " + cliente.getNombre() + "?");
                    confirmar.showAndWait().ifPresent(resultado -> {
                        if (resultado == ButtonType.OK) {
                            tienda.eliminarCliente(cliente.getId());
                            mostrarGestionClientes();
                            mostrarAlerta("✅ Cliente eliminado", "alert-success");
                        }
                    });
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox contenedorBotones = new HBox(5);
                    contenedorBotones.getChildren().addAll(btnEditar, btnEliminar);
                    setGraphic(contenedorBotones);
                }
            }
        });
        
        tablaClientes.getColumns().addAll(colId, colNombre, colEmail, colTelefono, colDireccion, colAcciones);
        tablaClientes.setItems(javafx.collections.FXCollections.observableArrayList(
                tienda.obtenerTodosClientes()));
        
        // Información
        Label lblInfo = new Label("Total de clientes registrados: " + tienda.obtenerTodosClientes().size());
        lblInfo.setStyle("-fx-font-size: 12; -fx-text-fill: #666;");
        
        contenedorClientes.getChildren().addAll(
                seccionEncabezado,
                new Separator(),
                botonesAccion,
                tablaClientes,
                lblInfo);
        
        VBox.setVgrow(tablaClientes, Priority.ALWAYS);
        gridProductos.add(contenedorClientes, 0, 0);
    }
    
    private void mostrarDialogoAgregarCliente() {
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Agregar Nuevo Cliente");
        dialog.setHeaderText("Ingresa los datos del cliente");
        
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre completo");
        
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("correo@ejemplo.com");
        
        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("123-456-7890");
        
        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Calle principal 123");
        
        TextField txtCiudad = new TextField();
        txtCiudad.setPromptText("Ciudad");
        
        TextField txtCodigoPostal = new TextField();
        txtCodigoPostal.setPromptText("Código postal");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        vbox.getChildren().addAll(
                new Label("Nombre:"), txtNombre,
                new Label("Email:"), txtEmail,
                new Label("Teléfono:"), txtTelefono,
                new Label("Dirección:"), txtDireccion,
                new Label("Ciudad:"), txtCiudad,
                new Label("Código Postal:"), txtCodigoPostal);
        
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new Cliente(0, txtNombre.getText(), txtEmail.getText(),
                        txtTelefono.getText(), txtDireccion.getText(),
                        txtCiudad.getText(), txtCodigoPostal.getText());
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(cliente -> {
            tienda.agregarCliente(cliente.getNombre(), cliente.getEmail(),
                    cliente.getTelefono(), cliente.getDireccion(),
                    cliente.getCiudad(), cliente.getCodigoPostal());
            mostrarGestionClientes();
            mostrarAlerta("✅ Cliente agregado exitosamente", "alert-success");
        });
    }
    
    private void mostrarDialogoEditarCliente(Cliente cliente) {
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Editar Cliente");
        dialog.setHeaderText("Modifica los datos del cliente");
        
        TextField txtNombre = new TextField(cliente.getNombre());
        TextField txtEmail = new TextField(cliente.getEmail());
        TextField txtTelefono = new TextField(cliente.getTelefono());
        TextField txtDireccion = new TextField(cliente.getDireccion() != null ? cliente.getDireccion() : "");
        TextField txtCiudad = new TextField(cliente.getCiudad() != null ? cliente.getCiudad() : "");
        TextField txtCodigoPostal = new TextField(cliente.getCodigoPostal() != null ? cliente.getCodigoPostal() : "");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        vbox.getChildren().addAll(
                new Label("Nombre:"), txtNombre,
                new Label("Email:"), txtEmail,
                new Label("Teléfono:"), txtTelefono,
                new Label("Dirección:"), txtDireccion,
                new Label("Ciudad:"), txtCiudad,
                new Label("Código Postal:"), txtCodigoPostal);
        
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new Cliente(cliente.getId(), txtNombre.getText(), txtEmail.getText(),
                        txtTelefono.getText(), txtDireccion.getText(),
                        txtCiudad.getText(), txtCodigoPostal.getText());
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(clienteActualizado -> {
            tienda.actualizarCliente(clienteActualizado.getId(),
                    clienteActualizado.getNombre(), clienteActualizado.getEmail(),
                    clienteActualizado.getTelefono(), clienteActualizado.getDireccion(),
                    clienteActualizado.getCiudad(), clienteActualizado.getCodigoPostal());
            mostrarGestionClientes();
            mostrarAlerta("✅ Cliente actualizado exitosamente", "alert-success");
        });
    }

    private void mostrarGestionPedidos() {
        gridProductos.getChildren().clear();
        
        VBox contenedorPedidos = new VBox(15);
        contenedorPedidos.setPadding(new Insets(20));
        
        // ============ SECCIÓN DE ENCABEZADO CON LOGO GRANDE ============
        HBox seccionEncabezado = new HBox(20);
        seccionEncabezado.setAlignment(Pos.CENTER_LEFT);
        seccionEncabezado.setPadding(new Insets(15));
        seccionEncabezado.setStyle("-fx-background-color: white; -fx-border-color: #FFB6D9; -fx-border-radius: 10; -fx-border-width: 2;");
        
        // Contenedor izquierdo - Información CON MÁS DECORACIÓN
        VBox cajaTexto = new VBox(15);
        cajaTexto.setAlignment(Pos.TOP_CENTER);
        cajaTexto.setPrefWidth(400);
        cajaTexto.setStyle("-fx-padding: 20;");
        
        Label lblTitulo = new Label("📋 HISTORIAL DE\nPEDIDOS");
        lblTitulo.setFont(crearFont(FontWeight.BOLD, 24));
        lblTitulo.setTextFill(Color.web("#C41E3A"));
        lblTitulo.setWrapText(true);
        lblTitulo.setTextAlignment(TextAlignment.CENTER);
        
        Label lblDescripcion = new Label("Controla el estado de todos los pedidos,\nsigue su progreso y gestiona las entregas\nde manera eficiente");
        lblDescripcion.setFont(crearFont(12));
        lblDescripcion.setTextFill(Color.web("#A31D37"));
        lblDescripcion.setWrapText(true);
        lblDescripcion.setTextAlignment(TextAlignment.CENTER);
        
        // Agregar decoraciones adicionales
        Label lblDecoracion1 = new Label("🎁 📦 🎁");
        lblDecoracion1.setStyle("-fx-font-size: 14; -fx-text-alignment: center;");
        
        Label lblInfoExtra = new Label("Sigue cada pedido paso a paso\ndesde la orden hasta la entrega");
        lblInfoExtra.setFont(crearFont(11));
        lblInfoExtra.setTextFill(Color.web("#FFB6D9"));
        lblInfoExtra.setWrapText(true);
        lblInfoExtra.setTextAlignment(TextAlignment.CENTER);
        lblInfoExtra.setStyle("-fx-font-style: italic; -fx-padding: 10; -fx-background-color: #FFF8F0; -fx-border-radius: 8; -fx-border-color: #FFB6D9; -fx-border-width: 1;");
        
        cajaTexto.getChildren().addAll(lblTitulo, lblDecoracion1, lblDescripcion, lblInfoExtra);
        
        // Contenedor derecho - Logo grande CON DECORACIÓN
        VBox cajaLogo = new VBox();
        cajaLogo.setAlignment(Pos.CENTER);
        cajaLogo.setPrefWidth(280);
        cajaLogo.setPrefHeight(280);
        
        // DECORACIÓN EXTERNA - Marco con brillo
        VBox marcoBrillo = new VBox();
        marcoBrillo.setAlignment(Pos.CENTER);
        marcoBrillo.setPrefWidth(280);
        marcoBrillo.setPrefHeight(280);
        marcoBrillo.setStyle("-fx-background-color: #FFE6F0; -fx-border-color: #FF69B4; -fx-border-radius: 20; -fx-border-width: 3; -fx-padding: 8;");
        
        // DECORACIÓN INTERIOR - Logo Container
        VBox logoContainer = new VBox();
        logoContainer.setAlignment(Pos.CENTER);
        logoContainer.setPrefWidth(265);
        logoContainer.setPrefHeight(265);
        logoContainer.setStyle("-fx-background-color: #FFFBF8; -fx-border-color: #FFD1E8; -fx-border-radius: 18; -fx-border-width: 3; -fx-padding: 5;");
        
        ImageView logoPedidos = cargarLogo("logo3");
        logoPedidos.setFitHeight(220);
        logoPedidos.setFitWidth(220);
        logoPedidos.setPreserveRatio(true);
        
        logoContainer.getChildren().add(logoPedidos);
        marcoBrillo.getChildren().add(logoContainer);
        cajaLogo.getChildren().add(marcoBrillo);
        
        seccionEncabezado.getChildren().addAll(cajaTexto, cajaLogo);
        HBox.setHgrow(cajaTexto, Priority.ALWAYS);
        
        // Botones de acción
        HBox botonesAccion = new HBox(10);
        botonesAccion.setPadding(new Insets(10));
        botonesAccion.setStyle("-fx-background-color: #f0e4f0; -fx-border-radius: 5;");
        
        Button btnRefrescar = new Button("🔄 ACTUALIZAR");
        btnRefrescar.setStyle("-fx-background-color: #b8d4b8; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10;");
        btnRefrescar.setOnAction(e -> mostrarGestionPedidos());
        
        ComboBox<String> cbEstado = new ComboBox<>();
        cbEstado.getItems().addAll("Todos", "PENDIENTE", "EN_PREPARACION", "LISTO", "ENTREGADO", "CANCELADO");
        cbEstado.setValue("Todos");
        cbEstado.setStyle("-fx-padding: 8;");
        
        Button btnFiltrar = new Button("🔍 Filtrar");
        btnFiltrar.setStyle("-fx-background-color: #d4b8d4; -fx-text-fill: white; -fx-padding: 8;");
        
        botonesAccion.getChildren().addAll(
                new Label("Estado:"), cbEstado,
                btnFiltrar,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                btnRefrescar);
        
        // Tabla de pedidos
        TableView<Pedido> tablaPedidos = new TableView<>();
        
        TableColumn<Pedido, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);
        
        TableColumn<Pedido, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCliente() != null ? cellData.getValue().getCliente().getNombre() : "N/A"));
        colCliente.setPrefWidth(150);
        
        TableColumn<Pedido, String> colEmail = new TableColumn<>("Email Cliente");
        colEmail.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCliente() != null ? cellData.getValue().getCliente().getEmail() : "N/A"));
        colEmail.setPrefWidth(180);
        
        TableColumn<Pedido, String> colFechaPedido = new TableColumn<>("Fecha Pedido");
        colFechaPedido.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFechaPedido() != null ? cellData.getValue().getFechaPedido().toString() : "N/A"));
        colFechaPedido.setPrefWidth(120);
        
        TableColumn<Pedido, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getEstado() != null ? cellData.getValue().getEstado().toString() : "N/A"));
        colEstado.setPrefWidth(130);
        
        TableColumn<Pedido, Double> colTotal = new TableColumn<>("Total ($)");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colTotal.setPrefWidth(100);
        
        TableColumn<Pedido, Integer> colItems = new TableColumn<>("Items");
        colItems.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(
                cellData.getValue().getCantidadItems()).asObject());
        colItems.setPrefWidth(60);
        
        TableColumn<Pedido, Void> colAcciones = new TableColumn<>("Acciones");
        colAcciones.setPrefWidth(200);
        colAcciones.setCellFactory(param -> new TableCell<Pedido, Void>() {
            private final Button btnDetalles = new Button("📋 Detalles");
            private final Button btnCambiarEstado = new Button("🔄 Estado");
            private final Button btnEliminar = new Button("❌ Eliminar");
            
            {
                btnDetalles.setStyle("-fx-font-size: 10;");
                btnCambiarEstado.setStyle("-fx-font-size: 10; -fx-background-color: #f5d547; -fx-text-fill: #5a3a5a;");
                btnEliminar.setStyle("-fx-font-size: 10; -fx-text-fill: white; -fx-background-color: #d4555a;");
                
                btnDetalles.setOnAction(event -> {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    mostrarDetallesPedido(pedido);
                });
                
                btnCambiarEstado.setOnAction(event -> {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    mostrarDialogoCambiarEstado(pedido);
                });
                
                btnEliminar.setOnAction(event -> {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmar.setTitle("Confirmar eliminación");
                    confirmar.setHeaderText("¿Eliminar pedido?");
                    confirmar.setContentText("¿Deseas eliminar el pedido #" + pedido.getId() + "?");
                    confirmar.showAndWait().ifPresent(resultado -> {
                        if (resultado == ButtonType.OK) {
                            tienda.eliminarPedido(pedido.getId());
                            mostrarGestionPedidos();
                            mostrarAlerta("✅ Pedido eliminado", "alert-success");
                        }
                    });
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox contenedorBotones = new HBox(5);
                    contenedorBotones.getChildren().addAll(btnDetalles, btnCambiarEstado, btnEliminar);
                    setGraphic(contenedorBotones);
                }
            }
        });
        
        tablaPedidos.getColumns().addAll(colId, colCliente, colEmail, colFechaPedido, colEstado, colTotal, colItems, colAcciones);
        
        // Filtrar pedidos según estado seleccionado
        btnFiltrar.setOnAction(e -> {
            String estadoSeleccionado = cbEstado.getValue();
            List<Pedido> pedidosFiltrados = tienda.obtenerTodosPedidos();
            
            if (!estadoSeleccionado.equals("Todos")) {
                pedidosFiltrados = pedidosFiltrados.stream()
                    .filter(p -> p.getEstado().toString().equals(estadoSeleccionado))
                    .toList();
            }
            
            tablaPedidos.setItems(javafx.collections.FXCollections.observableArrayList(pedidosFiltrados));
        });
        
        tablaPedidos.setItems(javafx.collections.FXCollections.observableArrayList(
                tienda.obtenerTodosPedidos()));
        
        // Información estadística
        List<Pedido> todosLosPedidos = tienda.obtenerTodosPedidos();
        double ingresoTotal = todosLosPedidos.stream().mapToDouble(Pedido::getTotal).sum();
        
        HBox estadisticas = new HBox(30);
        estadisticas.setPadding(new Insets(15));
        estadisticas.setStyle("-fx-background-color: #e8d4e8; -fx-border-radius: 5;");
        
        Label lblTotalPedidos = new Label("Total de pedidos: " + todosLosPedidos.size());
        lblTotalPedidos.setFont(crearFont(FontWeight.BOLD, 12.0));
        
        Label lblIngresoTotal = new Label("Ingreso total: $" + String.format("%.2f", ingresoTotal));
        lblIngresoTotal.setFont(crearFont(FontWeight.BOLD, 12.0));
        lblIngresoTotal.setTextFill(Color.web("#d4555a"));
        
        long pedidosEntregados = todosLosPedidos.stream()
            .filter(p -> p.getEstado() == Pedido.EstadoPedido.ENTREGADO)
            .count();
        Label lblEntregados = new Label("Entregados: " + pedidosEntregados);
        lblEntregados.setFont(crearFont(FontWeight.BOLD, 12.0));
        
        estadisticas.getChildren().addAll(lblTotalPedidos, lblIngresoTotal, lblEntregados);
        
        contenedorPedidos.getChildren().addAll(
                seccionEncabezado,
                new Separator(),
                botonesAccion,
                tablaPedidos,
                estadisticas);
        
        VBox.setVgrow(tablaPedidos, Priority.ALWAYS);
        gridProductos.add(contenedorPedidos, 0, 0);
    }
    
    private void mostrarDetallesPedido(Pedido pedido) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Detalles del Pedido #" + pedido.getId());
        
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(15));
        
        // Información del cliente
        VBox clienteInfo = new VBox(5);
        clienteInfo.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        clienteInfo.getChildren().addAll(
                new Label("INFORMACIÓN DEL CLIENTE"),
                new Label("Nombre: " + (pedido.getCliente() != null ? pedido.getCliente().getNombre() : "N/A")),
                new Label("Email: " + (pedido.getCliente() != null ? pedido.getCliente().getEmail() : "N/A")),
                new Label("Teléfono: " + (pedido.getCliente() != null ? pedido.getCliente().getTelefono() : "N/A")),
                new Label("Dirección: " + (pedido.getCliente() != null ? pedido.getCliente().getDireccion() : "N/A")));
        
        // Información del pedido
        VBox pedidoInfo = new VBox(5);
        pedidoInfo.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        pedidoInfo.getChildren().addAll(
                new Label("INFORMACIÓN DEL PEDIDO"),
                new Label("ID: #" + pedido.getId()),
                new Label("Fecha: " + pedido.getFechaPedido()),
                new Label("Estado: " + pedido.getEstado()),
                new Label("Total: $" + String.format("%.2f", pedido.getTotal())));
        
        // Tabla de items
        TableView<ItemPedido> tablaItems = new TableView<>();
        TableColumn<ItemPedido, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getProducto().getNombre()));
        
        TableColumn<ItemPedido, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<ItemPedido, Double> colPrecio = new TableColumn<>("Precio Unit.");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        
        TableColumn<ItemPedido, Double> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(
                cellData.getValue().getTotal()).asObject());
        
        tablaItems.getColumns().addAll(colProducto, colCantidad, colPrecio, colTotal);
        tablaItems.setItems(javafx.collections.FXCollections.observableArrayList(pedido.getItems()));
        tablaItems.setPrefHeight(200);
        
        contenido.getChildren().addAll(clienteInfo, pedidoInfo, new Label("ITEMS DEL PEDIDO:"), tablaItems);
        
        ScrollPane scroll = new ScrollPane(contenido);
        scroll.setFitToWidth(true);
        
        dialog.getDialogPane().setContent(scroll);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }
    
    private void mostrarDialogoCambiarEstado(Pedido pedido) {
        Dialog<Pedido.EstadoPedido> dialog = new Dialog<>();
        dialog.setTitle("Cambiar Estado del Pedido #" + pedido.getId());
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        
        Label lblEstadoActual = new Label("Estado actual: " + pedido.getEstado());
        lblEstadoActual.setStyle("-fx-font-weight: bold;");
        
        ComboBox<Pedido.EstadoPedido> cbEstado = new ComboBox<>();
        cbEstado.getItems().addAll(Pedido.EstadoPedido.values());
        cbEstado.setValue(pedido.getEstado());
        
        vbox.getChildren().addAll(
                lblEstadoActual,
                new Label("Nuevo estado:"),
                cbEstado);
        
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return cbEstado.getValue();
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(nuevoEstado -> {
            tienda.actualizarEstadoPedido(pedido.getId(), nuevoEstado);
            mostrarGestionPedidos();
            mostrarAlerta("✅ Estado del pedido actualizado", "alert-success");
        });
    }

    private void mostrarGestionPromociones() {
        gridProductos.getChildren().clear();
        
        VBox contenedorPromociones = new VBox(15);
        contenedorPromociones.setPadding(new Insets(20));
        contenedorPromociones.setStyle("-fx-background-color: #FFF8F0;");
        
        // ============ SECCIÓN DE ENCABEZADO CON LOGO GRANDE ============
        HBox seccionEncabezado = new HBox(20);
        seccionEncabezado.setAlignment(Pos.CENTER_LEFT);
        seccionEncabezado.setPadding(new Insets(15));
        seccionEncabezado.setStyle("-fx-background-color: white; -fx-border-color: #FFB6D9; -fx-border-radius: 10; -fx-border-width: 2;");
        
        // Contenedor izquierdo - Información CON MÁS DECORACIÓN
        VBox cajaTexto = new VBox(15);
        cajaTexto.setAlignment(Pos.TOP_CENTER);
        cajaTexto.setPrefWidth(400);
        cajaTexto.setStyle("-fx-padding: 20;");
        
        Label lblTitulo = new Label("🎁 PROMOCIONES\nESPECIALES");
        lblTitulo.setFont(crearFont(FontWeight.BOLD, 24.0));
        lblTitulo.setTextFill(Color.web("#C41E3A"));
        lblTitulo.setWrapText(true);
        lblTitulo.setTextAlignment(TextAlignment.CENTER);
        
        Label lblDescripcion = new Label("Aprovecharemos nuestras ofertas exclusivas,\ndescuentos especiales y promociones limitadas\npara disfrutar de los mejores productos");
        lblDescripcion.setFont(crearFont(12));
        lblDescripcion.setTextFill(Color.web("#A31D37"));
        lblDescripcion.setWrapText(true);
        lblDescripcion.setTextAlignment(TextAlignment.CENTER);
        
        // Agregar decoraciones adicionales
        Label lblDecoracion = new Label("💝 ✨ 💝");
        lblDecoracion.setStyle("-fx-font-size: 16; -fx-text-alignment: center;");
        
        Label lblInfoExtra = new Label("Descuentos de hasta 50% en productos seleccionados\n¡No te pierdas nuestras ofertas!");
        lblInfoExtra.setFont(crearFont(11));
        lblInfoExtra.setTextFill(Color.web("#FFB6D9"));
        lblInfoExtra.setWrapText(true);
        lblInfoExtra.setTextAlignment(TextAlignment.CENTER);
        lblInfoExtra.setStyle("-fx-font-style: italic; -fx-padding: 10; -fx-background-color: #FFF8F0; -fx-border-radius: 8; -fx-border-color: #FFB6D9; -fx-border-width: 1;");
        
        cajaTexto.getChildren().addAll(lblTitulo, lblDecoracion, lblDescripcion, lblInfoExtra);
        
        // Contenedor derecho - Imagen de promoción (placeholder)
        VBox cajaLogo = new VBox();
        cajaLogo.setAlignment(Pos.CENTER);
        cajaLogo.setPrefWidth(280);
        cajaLogo.setPrefHeight(280);
        
        // DECORACIÓN EXTERNA - Marco con brillo
        VBox marcoBrillo = new VBox();
        marcoBrillo.setAlignment(Pos.CENTER);
        marcoBrillo.setPrefWidth(280);
        marcoBrillo.setPrefHeight(280);
        marcoBrillo.setStyle("-fx-background-color: #FFE6F0; -fx-border-color: #FF69B4; -fx-border-radius: 20; -fx-border-width: 3; -fx-padding: 8;");
        
        // DECORACIÓN INTERIOR - Logo Container
        VBox logoContainer = new VBox();
        logoContainer.setAlignment(Pos.CENTER);
        logoContainer.setPrefWidth(265);
        logoContainer.setPrefHeight(265);
        logoContainer.setStyle("-fx-background-color: #FFFBF8; -fx-border-color: #FFD1E8; -fx-border-radius: 18; -fx-border-width: 3; -fx-padding: 5;");
        
        // Cargar imagen del banner de promociones
        try {
            Image imgBannerPromo = new Image("file:resources/images/promotions/banner_promo.png");
            ImageView imgViewPromo = new ImageView(imgBannerPromo);
            imgViewPromo.setFitWidth(255);
            imgViewPromo.setFitHeight(255);
            imgViewPromo.setPreserveRatio(true);
            logoContainer.getChildren().add(imgViewPromo);
        } catch (Exception e) {
            Label lblImagenPromo = new Label("📸 IMAGEN\nBANNER");
            lblImagenPromo.setFont(crearFont(11));
            lblImagenPromo.setTextFill(Color.web("#FFB6D9"));
            lblImagenPromo.setWrapText(true);
            lblImagenPromo.setTextAlignment(TextAlignment.CENTER);
            lblImagenPromo.setStyle("-fx-padding: 20;");
            logoContainer.getChildren().add(lblImagenPromo);
        }
        
        marcoBrillo.getChildren().add(logoContainer);
        cajaLogo.getChildren().add(marcoBrillo);
        
        seccionEncabezado.getChildren().addAll(cajaTexto, cajaLogo);
        HBox.setHgrow(cajaTexto, Priority.ALWAYS);
        
        // ============ SECCIÓN DE COMBOS/PROMOCIONES ============
        VBox seccionPromociones = new VBox(10);
        seccionPromociones.setPadding(new Insets(15));
        seccionPromociones.setStyle("-fx-background-color: rgba(255, 216, 233, 0.2); -fx-border-radius: 10;");
        
        Label lblCombos = new Label("📦 Combos Disponibles");
        lblCombos.setFont(crearFont(FontWeight.BOLD, 16.0));
        lblCombos.setTextFill(Color.web("#C41E3A"));
        
        // ScrollPane para combos
        ScrollPane scrollCombos = new ScrollPane();
        VBox vboxCombos = new VBox(15);
        vboxCombos.setPadding(new Insets(10));
        
        // Crear combos con productos Y IMÁGENES REALES
        VBox combo1 = crearComboPromocion(
            "🍰 The Pink Lab Combo",
            "30% OFF - $18.99",
            new String[]{"Malteada Royal", "Sundae Clásico", "Fruit Blast Cup"},
            "The Pink Lab Combo.png"
        );
        
        VBox combo2 = crearComboPromocion(
            "🥤 Fruit Fusion Pair",
            "25% OFF - $14.99",
            new String[]{"Mango Cloud Shake", "Berry Swirl Blast"},
            "Fruit Fusion Pair.png"
        );
        
        VBox combo3 = crearComboPromocion(
            "🍰 Infinite Sweetness Combo",
            "40% OFF - $26.99",
            new String[]{"Bubble Tea de perlas negras", "Pastel de arándanos y fresa", "Cupcake de frutos rojos"},
            "Infinite Sweetness Combo.png"
        );
        
        VBox combo4 = crearComboPromocion(
            "🍓 Berry & Kiwi Sando Box",
            "35% OFF - $22.99",
            new String[]{"Fruit Sandos de fresas y kiwi", "Pon de Ring Donut", "Fresas Premium"},
            "Berry & Kiwi Sando Box.png"
        );
        
        VBox combo5 = crearComboPromocion(
            "☕ Cinnamon Cozy Combo",
            "28% OFF - $16.99",
            new String[]{"Chocolate caliente con malvaviscos", "Cinnamon Roll con glaseado"},
            "Cinnamon Cozy Combo.png"
        );
        
        VBox combo6 = crearComboPromocion(
            "🥐 Sweet Lab Brunch Board",
            "45% OFF - $34.99",
            new String[]{"Croissants con mermelada de fresa", "Tostadas con huevo estrellado", "Mini pancakes con arándanos y dona"},
            "Sweet Lab Brunch Board.png"
        );
        
        vboxCombos.getChildren().addAll(combo1, combo2, combo3, combo4, combo5, combo6);
        scrollCombos.setContent(vboxCombos);
        scrollCombos.setFitToWidth(true);
        
        seccionPromociones.getChildren().addAll(lblCombos, scrollCombos);
        VBox.setVgrow(scrollCombos, Priority.ALWAYS);
        
        contenedorPromociones.getChildren().addAll(seccionEncabezado, seccionPromociones);
        VBox.setVgrow(seccionPromociones, Priority.ALWAYS);
        
        gridProductos.add(contenedorPromociones, 0, 0);
    }
    
    private VBox crearComboPromocion(String nombreCombo, String precioDescuento, String[] productos, String rutaImagen) {
        VBox combo = new VBox(10);
        combo.setPadding(new Insets(15));
        combo.setStyle("-fx-border-color: #FFB6D9; -fx-border-radius: 12; -fx-background-color: white; -fx-border-width: 2;");
        
        // Contenedor horizontal para imagen y contenido
        HBox contenido = new HBox(15);
        contenido.setAlignment(Pos.TOP_LEFT);
        
        // Sección de imagen - CARGANDO IMAGEN REAL
        VBox seccionImagen = new VBox();
        seccionImagen.setAlignment(Pos.CENTER);
        seccionImagen.setPrefWidth(120);
        seccionImagen.setPrefHeight(120);
        seccionImagen.setStyle("-fx-background-color: #FFE6F0; -fx-border-color: #FFB6D9; -fx-border-radius: 10; -fx-border-width: 2;");
        
        try {
            // Intentar cargar la imagen desde la carpeta promotions
            Image img = new Image("file:resources/images/promotions/" + rutaImagen);
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(115);
            imgView.setFitHeight(115);
            imgView.setPreserveRatio(true);
            seccionImagen.getChildren().add(imgView);
        } catch (Exception e) {
            // Si no carga la imagen, mostrar un placeholder
            Label lblImagen = new Label("🖼️");
            lblImagen.setFont(crearFont(40));
            lblImagen.setTextFill(Color.web("#FFB6D9"));
            seccionImagen.getChildren().add(lblImagen);
        }
        
        // Sección de información
        VBox seccionInfo = new VBox(8);
        seccionInfo.setPrefWidth(350);
        
        Label lblNombreCombo = new Label(nombreCombo);
        lblNombreCombo.setFont(crearFont(FontWeight.BOLD, 14.0));
        lblNombreCombo.setTextFill(Color.web("#C41E3A"));
        
        Label lblPrecio = new Label(precioDescuento);
        lblPrecio.setFont(crearFont(FontWeight.BOLD, 12.0));
        lblPrecio.setTextFill(Color.web("#7dd3c0"));
        
        Label lblProductos = new Label("Incluye:");
        lblProductos.setFont(crearFont(FontWeight.BOLD, 11.0));
        lblProductos.setTextFill(Color.web("#8B6B8B"));
        
        VBox listProductos = new VBox(5);
        listProductos.setStyle("-fx-padding: 8; -fx-background-color: #FFF8F0; -fx-border-radius: 8;");
        
        for (String producto : productos) {
            Label lblProducto = new Label("✓ " + producto);
            lblProducto.setFont(crearFont(10));
            lblProducto.setTextFill(Color.web("#5a3a5a"));
            listProductos.getChildren().add(lblProducto);
        }
        
        Button btnAplicar = new Button("🛒 Agregar al Carrito");
        btnAplicar.setStyle("-fx-background-color: #FFB6D9; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8; -fx-border-radius: 6;");
        btnAplicar.setPrefWidth(200);
        btnAplicar.setOnAction(e -> mostrarAlerta("✅ Combo " + nombreCombo + " agregado", "alert-info"));
        
        seccionInfo.getChildren().addAll(lblNombreCombo, lblPrecio, lblProductos, listProductos, btnAplicar);
        
        contenido.getChildren().addAll(seccionImagen, seccionInfo);
        combo.getChildren().add(contenido);
        
        return combo;
    }

    private void mostrarCarrito() {
        gridProductos.getChildren().clear();

        VBox contenedorCarrito = new VBox(15);
        contenedorCarrito.setPadding(new Insets(20));

        Label lblTitulo = new Label("🛒 MI CARRITO");
        lblTitulo.setFont(crearFont(FontWeight.BOLD, 18.0));
        lblTitulo.setTextFill(Color.web("#8B6B8B"));

        // Tabla de carrito
        tablaCarrito = new TableView<>();
        TableColumn<ItemPedido, String> colProducto = new TableColumn<>("Producto");
        TableColumn<ItemPedido, Integer> colCantidad = new TableColumn<>("Cantidad");
        TableColumn<ItemPedido, Double> colPrecio = new TableColumn<>("Precio Unit.");
        TableColumn<ItemPedido, Double> colTotal = new TableColumn<>("Total");

        colProducto.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getProducto().getNombre()));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colTotal.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(
                cellData.getValue().getTotal()).asObject());

        tablaCarrito.getColumns().addAll(colProducto, colCantidad, colPrecio, colTotal);
        tablaCarrito.setItems(javafx.collections.FXCollections.observableArrayList(
                tienda.obtenerCarrito().getItems()));

        contenedorCarrito.getChildren().addAll(
                lblTitulo,
                tablaCarrito,
                new Label("Total a pagar: $" + String.format("%.2f", tienda.obtenerCarrito().getTotal())));

        gridProductos.add(contenedorCarrito, 0, 0);
    }

    private void mostrarCheckout() {
        if (tienda.obtenerCarrito().estaVacio()) {
            mostrarAlerta("❌ El carrito está vacío", "alert-error");
            return;
        }

        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Checkout - Información del Cliente");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");

        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección de entrega");

        VBox vbox = new VBox(10,
                new Label("Datos del Cliente:"),
                new Label("Nombre:"), txtNombre,
                new Label("Email:"), txtEmail,
                new Label("Teléfono:"), txtTelefono,
                new Label("Dirección:"), txtDireccion);
        vbox.setPadding(new Insets(15));

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new Cliente(0, txtNombre.getText(), txtEmail.getText(),
                        txtTelefono.getText(), txtDireccion.getText(), "", "");
            }
            return null;
        });

        dialog.showAndWait().ifPresent(cliente -> {
            tienda.agregarCliente(cliente.getNombre(), cliente.getEmail(),
                    cliente.getTelefono(), cliente.getDireccion());
            Cliente clienteRegistrado = tienda.obtenerTodosClientes().get(
                    tienda.obtenerTodosClientes().size() - 1);

            Pedido pedido = tienda.crearPedido(clienteRegistrado);
            if (pedido != null) {
                mostrarAlerta("✅ Pedido creado exitosamente #" + pedido.getId(), "alert-success");
                actualizarInfoCarrito();
                mostrarHome();
            }
        });
    }

    private ImageView cargarLogo(String nombre) {
        ImageView imgView = new ImageView();
        try {
            String rutaLogo = "resources/images/logos/" + nombre + ".png";
            java.io.InputStream inputStream = getClass().getClassLoader().getResourceAsStream(rutaLogo);
            
            if (inputStream == null) {
                // Intentar como archivo directo si no está en recursos
                inputStream = new java.io.FileInputStream(rutaLogo);
            }
            
            Image img = new Image(inputStream, 0, 0, true, true);
            imgView.setImage(img);
            imgView.setFitHeight(220);
            imgView.setFitWidth(220);
            imgView.setPreserveRatio(true);
            System.out.println("✓ Logo cargado correctamente: " + nombre);
        } catch (Exception e) {
            System.err.println("✗ Error al cargar logo '" + nombre + "': " + e.getMessage());
        }
        return imgView;
    }
    
    private void agregarEfectoHoverBoton(Button btn, String colorBase, String colorHover) {
        // Guardar el estilo original
        String estiloOriginal = btn.getStyle();
        
        btn.setOnMouseEntered(e -> {
            // Cambiar a color hover - más brillante
            String nuevoEstilo = estiloOriginal.replace(colorBase, colorHover)
                    .replace("dropshadow(gaussian, rgba", "dropshadow(gaussian, rgba(255, 105, 180, 0.8)");
            btn.setStyle(nuevoEstilo);
            // Efecto de crecimiento
            btn.setScaleX(1.08);
            btn.setScaleY(1.08);
        });
        
        btn.setOnMouseExited(e -> {
            // Volver al estilo original
            btn.setStyle(estiloOriginal);
            btn.setScaleX(1.0);
            btn.setScaleY(1.0);
        });
    }

    private void mostrarAlerta(String mensaje, String tipo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notificación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
