import React, { useState, useEffect } from 'react';
import { productosAPI } from '../services/api';
import './Productos.css';

export default function Productos() {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filtroCategoria, setFiltroCategoria] = useState('');
  const [showFormulario, setShowFormulario] = useState(false);
  const [nuevoProducto, setNuevoProducto] = useState({
    nombre: '',
    descripcion: '',
    precio: '',
    categoria: '',
    foto: ''
  });

  useEffect(() => {
    cargarProductos();
  }, []);

  const cargarProductos = async () => {
    try {
      setLoading(true);
      const respuesta = await productosAPI.obtenerTodos();
      setProductos(respuesta.data);
      setError(null);
    } catch (err) {
      setError('Error al cargar productos: ' + err.message);
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleCrearProducto = async (e) => {
    e.preventDefault();
    try {
      await productosAPI.crear({
        ...nuevoProducto,
        precio: parseFloat(nuevoProducto.precio),
        disponible: true
      });
      setNuevoProducto({ nombre: '', descripcion: '', precio: '', categoria: '', foto: '' });
      setShowFormulario(false);
      await cargarProductos();
    } catch (err) {
      setError('Error al crear producto: ' + err.message);
    }
  };

  const handleEliminar = async (id) => {
    if (confirm('¿Deseas eliminar este producto?')) {
      try {
        await productosAPI.eliminar(id);
        await cargarProductos();
      } catch (err) {
        setError('Error al eliminar producto: ' + err.message);
      }
    }
  };

  const productosFiltrados = filtroCategoria 
    ? productos.filter(p => p.categoria === filtroCategoria)
    : productos;

  const categorias = [...new Set(productos.map(p => p.categoria))];

  if (loading) return <div className="loading">Cargando productos...</div>;

  return (
    <div className="productos-container">
      <h2>🍰 Productos - SweetLab Bakery</h2>
      
      {error && <div className="error-message">{error}</div>}
      
      <div className="productos-controles">
        <div className="filtro-categoria">
          <label>Filtrar por categoría:</label>
          <select 
            value={filtroCategoria} 
            onChange={(e) => setFiltroCategoria(e.target.value)}
          >
            <option value="">Todas</option>
            {categorias.map(cat => (
              <option key={cat} value={cat}>{cat}</option>
            ))}
          </select>
        </div>

        <button 
          className="btn-crear"
          onClick={() => setShowFormulario(!showFormulario)}
        >
          {showFormulario ? '✕ Cancelar' : '➕ Nuevo Producto'}
        </button>
      </div>

      {showFormulario && (
        <form className="formulario-producto" onSubmit={handleCrearProducto}>
          <h3>Crear Nuevo Producto</h3>
          
          <input
            type="text"
            placeholder="Nombre"
            value={nuevoProducto.nombre}
            onChange={(e) => setNuevoProducto({...nuevoProducto, nombre: e.target.value})}
            required
          />
          
          <textarea
            placeholder="Descripción"
            value={nuevoProducto.descripcion}
            onChange={(e) => setNuevoProducto({...nuevoProducto, descripcion: e.target.value})}
          />
          
          <input
            type="number"
            placeholder="Precio"
            step="0.01"
            value={nuevoProducto.precio}
            onChange={(e) => setNuevoProducto({...nuevoProducto, precio: e.target.value})}
            required
          />
          
          <input
            type="text"
            placeholder="Categoría"
            value={nuevoProducto.categoria}
            onChange={(e) => setNuevoProducto({...nuevoProducto, categoria: e.target.value})}
            required
          />
          
          <input
            type="text"
            placeholder="URL de Foto"
            value={nuevoProducto.foto}
            onChange={(e) => setNuevoProducto({...nuevoProducto, foto: e.target.value})}
          />
          
          <button type="submit" className="btn-guardar">💾 Guardar Producto</button>
        </form>
      )}

      <div className="productos-grid">
        {productosFiltrados.length === 0 ? (
          <p>No hay productos disponibles</p>
        ) : (
          productosFiltrados.map(producto => (
            <div key={producto.id} className="producto-card">
              {producto.foto && (
                <img src={producto.foto} alt={producto.nombre} />
              )}
              <h3>{producto.nombre}</h3>
              <p className="descripcion">{producto.descripcion}</p>
              <p className="categoria">{producto.categoria}</p>
              <p className="precio">${producto.precio.toFixed(2)}</p>
              <span className={`disponible ${producto.disponible ? 'si' : 'no'}`}>
                {producto.disponible ? '✓ Disponible' : '✗ No disponible'}
              </span>
              <button 
                className="btn-eliminar"
                onClick={() => handleEliminar(producto.id)}
              >
                🗑️ Eliminar
              </button>
            </div>
          ))
        )}
      </div>
    </div>
  );
}
