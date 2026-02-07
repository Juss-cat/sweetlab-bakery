# Frontend React - SweetLab Bakery Web

## 📋 Descripción

Interfaz web moderna construida con **React 18 + Vite** para SweetLab Bakery. 

Características:
- Catálogo de productos interactivo
- Carrito de compras
- Gestión de clientes
- Historial de pedidos
- Diseño responsive
- Paleta de colores SweetLab (rosa, morado)

## ⚙️ Requisitos

- Node.js 18+ (descargalo de https://nodejs.org/)
- npm 9+ (viene con Node)
- Git (opcional)

## 🚀 Inicio Rápido

### 1. Instalar dependencias

```bash
cd frontend
npm install
```

### 2. Ejecutar en desarrollo

```bash
npm run dev
```

El sitio estará en: `http://localhost:5173`

Abre en tu navegador y verás la interfaz.

### 3. Compilar para producción

```bash
npm run build
```

Genera carpeta `dist/` lista para deploy.

## 📁 Estructura de Carpetas

```
frontend/
├── package.json              (Dependencias)
├── vite.config.js            (Configuración Vite)
├── index.html                (HTML principal)
├── src/
│   ├── main.jsx              (Punto de entrada React)
│   ├── App.jsx               (Componente principal)
│   ├── App.css               (Estilos app)
│   ├── index.css             (Estilos globales + Tailwind)
│   │
│   ├── components/
│   │   ├── Navbar.jsx        (Barra de navegación)
│   │   └── Navbar.css
│   │
│   ├── pages/
│   │   ├── Productos.jsx     (Catálogo)
│   │   ├── Productos.css
│   │   ├── Clientes.jsx      (Gestión clientes)
│   │   ├── Clientes.css
│   │   ├── Pedidos.jsx       (Historial)
│   │   └── Pedidos.css
│   │
│   ├── services/
│   │   └── api.js            (Llamadas HTTP a backend)
│   │
│   ├── store/
│   │   └── store.js          (Estado global con Zustand)
│   │
│   └── hooks/
│       └── useApi.js         (Hook para API)
│
├── public/                   (Activos estáticos)
└── dist/                     (Generado al compilar)
```

## 🎨 Colores Tema

```
--pink-primary: #FFB6D9     (Rosa claro)
--pink-dark: #FF99C8        (Rosa oscuro)
--purple-main: #8B6B8B      (Morado)
--purple-light: #B8A8B8     (Morado claro)
--red-accent: #C41E3A       (Rojo acentos)
--white: #FFFFFF
--gray-light: #F5F5F5
--gray-dark: #333333
```

Definidos en `src/index.css`

## 📚 Componentes Principales

### App.jsx
- Componente raíz
- Gestiona navegación entre páginas

### Navbar.jsx
- Barra de navegación sticky
- Botones para cambiar de sección
- Logo SweetLab

### Productos.jsx
- **GET /api/productos** → Carga catálogo
- Muestra productos en grid
- Carrito funcional (estado local)
- Lado derecho: resumen carrito

### Clientes.jsx
- Formulario para registrar clientes
- **POST /api/clientes** → Guardar nuevo
- Tabla de clientes existentes
- Botones editar/eliminar

### Pedidos.jsx
- **GET /api/pedidos** → Carga historial
- Muestra tarjetas de pedidos
- Indica estado (pendiente, completado, etc.)

## 🔄 Flujo de Datos

```
React Component
    ↓
    √ Llama axios.get('/api/productos')
    ↓
Backend Spring Boot (puerto 8080)
    ↓
    √ ProductoController
    ↓
    √ ProductoService
    ↓
    √ ProductoRepository
    ↓
BD SQLite (sweetlab_bakery.db)
    ↓
Retorna JSON al Front
    ↓
React renderiza componentes
    ↓
Usuario ve productos en pantalla ✅
```

## 📡 Ejemplo: Conexión a API

```javascript
// services/api.js
import axios from 'axios'

const API_BASE = 'http://localhost:8080/api'

export const getProductos = () => {
  return axios.get(`${API_BASE}/productos`)
}

export const createProducto = (producto) => {
  return axios.post(`${API_BASE}/productos`, producto)
}
```

```javascript
// pages/Productos.jsx
import { getProductos } from '../services/api'

useEffect(() => {
  getProductos()
    .then(res => setProductos(res.data))
    .catch(err => console.error(err))
}, [])
```

## 🛠️ Instalación de Dependencias

Si algo no funciona después de `npm install`:

```bash
# Limpia cache
npm cache clean --force

# Reinstala todo
rm -rf node_modules package-lock.json
npm install
```

## 🔧 Variables de Entorno

Crea archivo `.env`:

```env
VITE_API_URL=http://localhost:8080/api
VITE_APP_NAME=SweetLab Bakery
```

Acceso en React:

```javascript
const apiUrl = import.meta.env.VITE_API_URL
```

## 📝 Build y Deploy

### Desarrollo

```bash
npm run dev     # Con hot reload
```

### Producción

```bash
npm run build   # Genera carpeta dist/
npm run preview # Visualiza build
```

La carpeta `dist/` contiene archivos optimizados listos para:
- Netlify
- Vercel
- AWS S3
- GCP

## 🐛 Troubleshooting

### ¿"Module not found"?

```bash
npm install --save axios
npm install --save zustand
```

### ¿"Port 5173 already in use"?

```javascript
// En vite.config.js
server: {
  port: 5174  // Cambiar puerto
}
```

### ¿"Cannot connect to backend"?

1. Verifica backend corriendo en puerto 8080
2. Asegúrate que CORS esté habilitado:

Backend (`application.properties`):
```properties
# Ya está configurado en SweetLabBackendApplication.java
```

3. En Productos.jsx verifica:
```javascript
const API_URL = 'http://localhost:8080/api'
```

## 📦 Dependencias Principales

- **react**: Framework UI
- **react-dom**: Renderizo DOM
- **react-router-dom**: Navegación
- **axios**: Cliente HTTP
- **zustand**: Gestión estado
- **vite**: Empaquetador rápido

## ✨ Funcionalidades Listas

- ✅ Navbar con navegación
- ✅ Página Productos
- ✅ Página Clientes (estructura)
- ✅ Página Pedidos (estructura)
- ✅ Carrito de compras funcional
- ✅ Estilos tema SweetLab
- ✅ Responsive design
- ✅ Conexión a API (lista)

## 🚀 Próximos Pasos

1. ⏳ Conectar Productos.jsx a API real
2. ⏳ Implementar formulario clientes
3. ⏳ Guardar carrito en Zustand
4. ⏳ Checkout de compra
5. ⏳ Dashboard administrador

---

**¡Frontend listo para conectar al backend!** 🎨
