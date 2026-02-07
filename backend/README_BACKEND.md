# Backend REST API - SweetLab Bakery

## 📋 Descripción

Backend REST API construido con **Spring Boot 3.2** para SweetLab Bakery. Proporciona endpoints CRUD para:
- Productos
- Clientes
- Pedidos
- Promociones

## ⚙️ Requisitos

- Java 25+
- Maven 3.11.0+
- Git (opcional)

## 🚀 Inicio Rápido

### 1. Compilar

```bash
mvn clean compile
```

### 2. Ejecutar

```bash
mvn spring-boot:run
```

El servidor estará disponible en:
```
http://localhost:8080/api
```

## 📚 API Endpoints (Próximos a implementar)

### Productos
```
GET    /api/productos              → Lista todos
GET    /api/productos/{id}         → Obtiene uno
GET    /api/productos/categoria/{tipo}  → Por categoría
POST   /api/productos              → Crea nuevo
PUT    /api/productos/{id}         → Actualiza
DELETE /api/productos/{id}         → Elimina
```

### Clientes
```
GET    /api/clientes               → Lista todos
GET    /api/clientes/{id}          → Obtiene uno
POST   /api/clientes               → Crea nuevo
PUT    /api/clientes/{id}          → Actualiza
DELETE /api/clientes/{id}          → Elimina
```

### Pedidos
```
GET    /api/pedidos                → Lista todos
GET    /api/pedidos/{id}           → Obtiene uno
POST   /api/pedidos                → Crea nuevo
PUT    /api/pedidos/{id}           → Cambia estado
DELETE /api/pedidos/{id}           → Cancela
```

### Promociones
```
GET    /api/promociones            → Lista todas
GET    /api/promociones/{id}       → Obtiene una
POST   /api/promociones            → Crea nueva
```

## 🛠️ Testear API

### Con Postman

1. Descarga [Postman](https://www.postman.com/downloads/)
2. Crea una nueva request:
   - **Método**: GET
   - **URL**: `http://localhost:8080/api/productos`
   - **Click**: Send

### Con cURL

```bash
curl http://localhost:8080/api/productos
```

### Con RestClient (VS Code)

Instala extensión `REST Client` y crea archivo `requests.http`:

```http
### Obtener todos los productos
GET http://localhost:8080/api/productos

### Obtener un producto por ID
GET http://localhost:8080/api/productos/1

### Crear un nuevo producto
POST http://localhost:8080/api/productos
Content-Type: application/json

{
  "nombre": "Pastel de Chocolate",
  "descripcion": "Delicioso pastel casero",
  "precio": 15.99,
  "categoria": "postres"
}
```

## 📁 Estructura de Carpetas

```
backend/
├── pom.xml
├── src/main/
│   ├── java/com/sweetlab/
│   │   ├── SweetLabBackendApplication.java    (Main)
│   │   ├── controller/
│   │   │   ├── ProductoController.java        (PRÓXIMO)
│   │   │   ├── ClienteController.java
│   │   │   ├── PedidoController.java
│   │   │   └── PromocionController.java
│   │   ├── service/
│   │   │   ├── ProductoService.java
│   │   │   ├── ClienteService.java
│   │   │   └── PedidoService.java
│   │   ├── repository/
│   │   │   ├── ProductoRepository.java
│   │   │   ├── ClienteRepository.java
│   │   │   └── PedidoRepository.java
│   │   ├── entity/
│   │   │   ├── Producto.java
│   │   │   ├── Cliente.java
│   │   │   ├── Pedido.java
│   │   │   ├── ItemPedido.java
│   │   │   └── Promocion.java
│   │   ├── dto/
│   │   │   ├── ProductoDTO.java
│   │   │   ├── ClienteDTO.java
│   │   │   └── PedidoDTO.java
│   │   └── config/
│   │       └── CorsConfig.java
│   └── resources/
│       └── application.properties
└── README_BACKEND.md (este archivo)
```

## 💾 Base de Datos

Se utiliza **SQLite** para máxima simplicidad:

```
sweetlab_bakery.db
```

**Tablas**: Productos, Clientes, Pedidos, ItemsPedidos, Promociones

Spring Boot crea automáticamente las tablas basándose en las anotaciones @Entity.

## 🔧 Configuración

Edita `backend/src/main/resources/application.properties`:

```properties
server.port=8080                    # Puerto del servidor
spring.jpa.hibernate.ddl-auto=update # Actualiza BD automáticamente
```

## 📦 Dependencias

- Spring Boot Starter Web (REST)
- Spring Data JPA (Base de datos)
- SQLite JDBC Driver
- Lombok (Reduce boilerplate)
- Validation

Todas están en `pom.xml`

## 🐛 Troubleshooting

### ¿Puerto 8080 ya está en uso?

```bash
# Windows - encontrar proceso en puerto 8080
netstat -ano | findstr :8080

# Cambiar puerto en application.properties
server.port=9090
```

### ¿No se conecta la BD?

```bash
# Verifica que exista la carpeta backend
mvn clean compile
mvn spring-boot:run
```

### ¿Errores de compilación?

```bash
# Limpia y recompila
mvn clean install
```

## 📝 Próximos Pasos

1. ✅ Setup inicial completado
2. ⏳ Implementar Controllers
3. ⏳ Implementar Entities JPA
4. ⏳ Implementar Repositories
5. ⏳ Conectar frontend React

---

**¡El backend está listo para desarrollos!** 🚀
