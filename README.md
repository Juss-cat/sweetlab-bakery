# 🍰 Sweet Lab Bakery - Sistema de Gestión v3.0

> Aplicación de escritorio para la gestión integral de una pastelería, con interfaz gráfica moderna, base de datos integrada y sistema de exportación JSON.

![Java](https://img.shields.io/badge/Java-25-blue)
![JavaFX](https://img.shields.io/badge/JavaFX-25-green)
![SQLite](https://img.shields.io/badge/SQLite-Latest-lightblue)
![Maven](https://img.shields.io/badge/Maven-3.11.0-red)

---

## 🎯 Características Principales

✅ **Gestión Completa de Productos**
- Catálogo organizado por categorías (Postres, Bebidas, Comidas)
- Información detallada de cada producto
- Sistema de imágenes para productos

✅ **Sistema de Clientes**
- Registro y administración de clientes
- Historial de compras por cliente
- Gestión de contacto e información de envío

✅ **Gestión de Pedidos**
- Creación y seguimiento de pedidos
- Estados: Pendiente, En Preparación, Listo, Entregado, Cancelado
- Cálculo automático de totales

✅ **Promociones y Descuentos**
- Sistema de promociones por rango de fechas
- Combos especiales
- Descuentos automáticos

✅ **Reportes y Análisis**
- Estadísticas de ventas
- Exportación a JSON
- Reportes por período

✅ **Interfaz Moderna**
- Diseño "cute" con paleta pastel
- Tema personalizado
- Menú lateral intuitivo

---

## 📁 Estructura del Proyecto

```
SweetLabBakery/
├── src/                          # Código fuente
│   ├── Main.java                # Punto de entrada
│   ├── controller/              # Controladores
│   │   └── TiendaSweetLab.java  # Lógica principal
│   ├── model/                   # Modelos (Entidades)
│   │   ├── Producto.java
│   │   ├── Cliente.java
│   │   ├── Pedido.java
│   │   ├── Promocion.java
│   │   └── ...
│   ├── dao/                     # Acceso a datos
│   │   ├── ProductoDAO.java
│   │   ├── ClienteDAO.java
│   │   ├── PedidoDAO.java
│   │   └── ...
│   ├── database/                # Conexión BD
│   │   └── ConexionBD.java
│   ├── util/                    # Utilidades
│   │   ├── JsonUtil.java        # Exportación JSON
│   │   ├── ReportesUtil.java    # Generación de reportes
│   │   └── ImagenesUtil.java    # Gestión de imágenes
│   └── view/                    # Interfaz gráfica
│       └── SweetLabApp.java     # Aplicación JavaFX
│
├── resources/                    # Recursos de la aplicación
│   ├── css/                     # Estilos
│   │   └── sweetlab.css
│   ├── images/                  # Imágenes
│   │   ├── logos/
│   │   ├── products/
│   │   ├── promotions/
│   │   └── ui/
│   └── fonts/                   # Fuentes personalizadas
│
├── docs/                        # Documentación
│   ├── GUIA_JSON.md             # Guía de uso de JSON
│   ├── DIAGRAMAS_CLASES.txt     # Diagrama de clases UML
│   └── config.json              # Configuración de la app
│
├── sql/                         # Scripts SQL
│   └── BD_SCHEMA.sql            # Esquema de base de datos
│
├── logs/                        # Logs de compilación
│   └── compile_*.log            # Registros de build
│
├── bin/                         # Scripts ejecutables
│   └── ejecutar.bat             # Script para ejecutar la app
│
├── target/                      # Construcción (generado por Maven)
│
├── pom.xml                      # Configuración Maven
├── config.json                  # Configuración principal
├── datos_iniciales.json         # Datos de prueba
└── sweetlab_bakery.db           # Base de datos SQLite
```

---

## 🚀 Inicio Rápido

### Requisitos
- **Java 25** o superior
- **Maven 3.6+**
- **SQLite** (incluido con el driver JDBC)

### Instalación y Ejecución

1. **Clonar o descargar el proyecto**
```bash
cd SweetLabBakery
```

2. **Compilar el proyecto**
```bash
mvn clean compile
```

3. **Ejecutar la aplicación**

**Opción 1: Maven**
```bash
mvn javafx:run
```

**Opción 2: Script BAT (Windows)**
```bash
./bin/ejecutar.bat
```

---

## 📚 Guías Disponibles

### 🔍 Documentación Principal
- **[docs/GUIA_JSON.md](docs/GUIA_JSON.md)** - Cómo usar el sistema de exportación JSON
- **[docs/DIAGRAMAS_CLASES.txt](docs/DIAGRAMAS_CLASES.txt)** - Diagrama UML de clases

### 🔧 Configuración
- **[docs/config.json](docs/config.json)** - Personalizar colores, fuentes y configuración

### 🗄️ Base de Datos
- **[sql/BD_SCHEMA.sql](sql/BD_SCHEMA.sql)** - Script SQL de la estructura

---

## 💻 Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|----------|
| **Java** | 25 | Lenguaje principal |
| **JavaFX** | 25 | Interfaz gráfica |
| **SQLite** | Latest | Base de datos |
| **Maven** | 3.11.0 | Constructor de proyectos |
| **JSON** | 20231013 | Exportación de datos |

---

## 📊 Casos de Uso

### 👤 Dueño de Pastelería
- ✅ Ver estadísticas de ventas
- ✅ Administrar catálogo de productos
- ✅ Gestionar clientes
- ✅ Exportar reportes

### 👨‍💼 Gerente
- ✅ Registrar nuevos pedidos
- ✅ Seguimiento de estados
- ✅ Visualizar historial de ventas
- ✅ Aplicar promociones

---

## 🎨 Personalización

### Cambiar Tema de Colores
Edita `docs/config.json`:
```json
{
  "interfaz": {
    "tema": {
      "color_primario": "#FFB6D9",
      "color_secundario": "#8B6B8B"
    }
  }
}
```

### Agregar Nuevos Productos
Edita `datos_iniciales.json`:
```json
{
  "productos": [
    {
      "nombre": "Tu producto",
      "precio": 99.99,
      "tipo": "Postres"
    }
  ]
}
```

---

## 📤 Exportación de Datos

Exporta información a JSON para análisis externo:

```java
// Exportar reporte completo
JsonUtil.exportarReporteJSON(
    productos, clientes, pedidos,
    "reporte_tienda.json"
);
```

Para más detalles, ver [docs/GUIA_JSON.md](docs/GUIA_JSON.md)

---

## 🔐 Base de Datos

La aplicación utiliza **SQLite** para almacenamiento persistente:

- **Archivo**: `sweetlab_bakery.db`
- **Tablas**: Productos, Clientes, Pedidos, Items, Promociones, Historial
- **Relaciones**: Foráneas entre tablas

Ver esquema completo en [sql/BD_SCHEMA.sql](sql/BD_SCHEMA.sql)

---

## 🐛 Resolución de Problemas

### "Base de datos no encontrada"
→ Se crea automáticamente en la primera ejecución

### "Imagen de producto no existe"
→ Coloca imágenes en `resources/images/products/`

### "Error de compilación"
→ Asegúrate de tener Java 25: `java -version`

---

## 📝 Roadmap Futuro

- [ ] API REST con Spring Boot
- [ ] Aplicación móvil (Android/iOS)
- [ ] Sincronización en la nube
- [ ] Multi-usuario con autenticación
- [ ] Dashboard en tiempo real
- [ ] QR para escaneo de productos

---

## 📄 Licencia

Este proyecto es para propósitos educativos. Uso libre.

---

## 👨‍💻 Autor

**Desarrollador**: Sistema de Gestión para Pastelerías  
**Fecha**: 6 de febrero de 2026  
**Versión**: 3.0.1

---

## 📞 Soporte

Para reportes de bugs o sugerencias:
1. Revisa [docs/GUIA_JSON.md](docs/GUIA_JSON.md)
2. Verifica los [logs/](logs/) de compilación
3. Consulta [docs/DIAGRAMAS_CLASES.txt](docs/DIAGRAMAS_CLASES.txt)

---

**¡Gracias por usar Sweet Lab Bakery! 🍰✨**
