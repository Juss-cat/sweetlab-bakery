import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Servicios de Productos
export const productosAPI = {
  obtenerTodos: () => api.get('/productos'),
  obtenerPorId: (id) => api.get(`/productos/${id}`),
  obtenerPorCategoria: (categoria) => api.get(`/productos/categoria/${categoria}`),
  obtenerDisponibles: () => api.get('/productos/disponibles'),
  crear: (producto) => api.post('/productos', producto),
  actualizar: (id, producto) => api.put(`/productos/${id}`, producto),
  eliminar: (id) => api.delete(`/productos/${id}`),
};

// Servicios de Clientes
export const clientesAPI = {
  obtenerTodos: () => api.get('/clientes'),
  obtenerPorId: (id) => api.get(`/clientes/${id}`),
  obtenerActivos: () => api.get('/clientes/activos'),
  buscarPorNombre: (nombre) => api.get(`/clientes/buscar/${nombre}`),
  crear: (cliente) => api.post('/clientes', cliente),
  actualizar: (id, cliente) => api.put(`/clientes/${id}`, cliente),
  eliminar: (id) => api.delete(`/clientes/${id}`),
};

// Servicios de Pedidos
export const pedidosAPI = {
  obtenerTodos: () => api.get('/pedidos'),
  obtenerPorId: (id) => api.get(`/pedidos/${id}`),
  obtenerPorCliente: (clienteId) => api.get(`/pedidos/cliente/${clienteId}`),
  obtenerPorEstado: (estado) => api.get(`/pedidos/estado/${estado}`),
  crear: (pedido) => api.post('/pedidos', pedido),
  actualizar: (id, pedido) => api.put(`/pedidos/${id}`, pedido),
  cambiarEstado: (id, estado) => api.patch(`/pedidos/${id}/estado?nuevoEstado=${estado}`),
  eliminar: (id) => api.delete(`/pedidos/${id}`),
};

// Servicios de Promociones
export const promocionesAPI = {
  obtenerTodas: () => api.get('/promociones'),
  obtenerPorId: (id) => api.get(`/promociones/${id}`),
  obtenerActivas: () => api.get('/promociones/activas'),
  obtenerPorCodigo: (codigo) => api.get(`/promociones/codigo/${codigo}`),
  crear: (promocion) => api.post('/promociones', promocion),
  actualizar: (id, promocion) => api.put(`/promociones/${id}`, promocion),
  eliminar: (id) => api.delete(`/promociones/${id}`),
};

export default api;
