import React, { useState, useEffect } from 'react'
import { productosAPI } from '../services/api'
import './Tienda.css'

export default function Tienda({ agregarAlCarrito }) {
  const [productos, setProductos] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [filtroCategoria, setFiltroCategoria] = useState('')

  useEffect(() => {
    cargarProductos()
  }, [])

  const cargarProductos = async () => {
    try {
      setLoading(true)
      const respuesta = await productosAPI.obtenerDisponibles()
      setProductos(respuesta.data)
      setError(null)
    } catch (err) {
      console.error(err)
      // Si falla, mostrar productos de demostración
      setProductos(productosDemo)
    } finally {
      setLoading(false)
    }
  }

  const productosFiltrados = filtroCategoria 
    ? productos.filter(p => p.categoria === filtroCategoria)
    : productos

  const categorias = [...new Set(productos.map(p => p.categoria).filter(Boolean))]

  if (loading) return <div className="loading">🍰 Cargando productos deliciosos...</div>

  return (
    <div className="tienda-container">
      <div className="tienda-hero">
        <h1>🍰 Bienvenido a SweetLab Bakery</h1>
        <p>Los mejores productos de panadería artesanal</p>
      </div>

      {error && <div className="info-demo">Modo demostración - Los datos se mostrarán cuando el servidor esté disponible</div>}

      <div className="tienda-controles">
        {categorias.length > 0 && (
          <div className="filtro-categoria">
            <label>Filtrar por categoría:</label>
            <select 
              value={filtroCategoria} 
              onChange={(e) => setFiltroCategoria(e.target.value)}
            >
              <option value="">Todas las categorías</option>
              {categorias.map(cat => (
                <option key={cat} value={cat}>{cat}</option>
              ))}
            </select>
          </div>
        )}
      </div>

      <div className="productos-grid">
        {productosFiltrados.length === 0 ? (
          <div className="sin-productos">
            <p>No hay productos disponibles en esta categoría</p>
          </div>
        ) : (
          productosFiltrados.map(producto => (
            <div key={producto.id} className="producto-card-publico">
              {producto.foto && (
                <img src={producto.foto} alt={producto.nombre} />
              )}
              <div className="producto-info">
                <h3>{producto.nombre}</h3>
                <p className="descripcion">{producto.descripcion}</p>
                <span className="categoria-badge">{producto.categoria}</span>
                <p className="precio">${producto.precio.toFixed(2)}</p>
                <button 
                  className="btn-comprar"
                  onClick={() => agregarAlCarrito(producto)}
                >
                  🛒 Añadir al carrito
                </button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  )
}

// Datos de demostración
const productosDemo = [
  {
    id: 1,
    nombre: 'Pan Francés',
    descripcion: 'Pan francés crujiente y delicioso',
    precio: 5.99,
    categoria: 'Panes',
    foto: '🥖'
  },
  {
    id: 2,
    nombre: 'Croissant',
    descripcion: 'Croissant de mantequilla fresco',
    precio: 4.50,
    categoria: 'Pastelería',
    foto: '🥐'
  },
  {
    id: 3,
    nombre: 'Torta de Chocolate',
    descripcion: 'Torta de chocolate belga premium',
    precio: 25.00,
    categoria: 'Tartas',
    foto: '🍰'
  },
  {
    id: 4,
    nombre: 'Donas Rellenas',
    descripcion: 'Donas rellenas de crema y mermelada',
    precio: 3.95,
    categoria: 'Dulces',
    foto: '🍩'
  },
  {
    id: 5,
    nombre: 'Pan Integral',
    descripcion: 'Pan 100% integral y saludable',
    precio: 6.50,
    categoria: 'Panes',
    foto: '🍞'
  },
  {
    id: 6,
    nombre: 'Tarta de Fresas',
    descripcion: 'Tarta fresca con fresas naturales',
    precio: 22.99,
    categoria: 'Tartas',
    foto: '🍓'
  }
]
