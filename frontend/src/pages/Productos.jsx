import { useState, useEffect } from 'react'
import axios from 'axios'
import './Productos.css'

const API_URL = 'http://localhost:8080/api'

export default function Productos() {
  const [productos, setProductos] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [carrito, setCarrito] = useState([])

  useEffect(() => {
    cargarProductos()
  }, [])

  const cargarProductos = async () => {
    try {
      setLoading(true)
      const response = await axios.get(`${API_URL}/productos`)
      setProductos(response.data)
      setError(null)
    } catch (err) {
      setError('No se pudieron cargar los productos. ¿El backend está corriendo en puerto 8080?')
      console.error('Error:', err)
    } finally {
      setLoading(false)
    }
  }

  const agregarCarrito = (producto) => {
    const existe = carrito.find(item => item.id === producto.id)
    if (existe) {
      setCarrito(carrito.map(item =>
        item.id === producto.id
          ? { ...item, cantidad: item.cantidad + 1 }
          : item
      ))
    } else {
      setCarrito([...carrito, { ...producto, cantidad: 1 }])
    }
  }

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
        <p>Cargando productos...</p>
      </div>
    )
  }

  return (
    <div className="productos-container">
      <h2>Catálogo de Productos</h2>
      
      {error && <div className="error">{error}</div>}
      
      <div className="flex-container">
        <div className="productos-grid">
          {productos.length === 0 ? (
            <div className="info">
              No hay productos disponibles. Crea algunos desde el administrador.
            </div>
          ) : (
            productos.map(producto => (
              <div key={producto.id} className="producto-card card">
                <div className="producto-imagen">
                  {producto.foto ? (
                    <img src={producto.foto} alt={producto.nombre} />
                  ) : (
                    <div className="no-imagen">Sin imagen 🍰</div>
                  )}
                </div>
                <h3>{producto.nombre}</h3>
                <p className="descripcion">{producto.descripcion}</p>
                <p className="categoria">Categoría: {producto.categoria}</p>
                <div className="precio-container">
                  <span className="precio">${producto.precio.toFixed(2)}</span>
                </div>
                <button onClick={() => agregarCarrito(producto)}>
                  Agregar al carrito
                </button>
              </div>
            ))
          )}
        </div>

        <div className="carrito-sidebar">
          <div className="carrito-card card">
            <h3>🛒 Carrito ({carrito.length})</h3>
            {carrito.length === 0 ? (
              <p className="carrito-vacio">Carrito vacío</p>
            ) : (
              <div className="carrito-items">
                {carrito.map(item => (
                  <div key={item.id} className="carrito-item">
                    <span>{item.nombre}</span>
                    <span className="cantidad">x{item.cantidad}</span>
                    <span className="total">${(item.precio * item.cantidad).toFixed(2)}</span>
                  </div>
                ))}
                <hr />
                <div className="carrito-total">
                  <strong>Total:</strong>
                  <strong>${carrito.reduce((sum, item) => sum + (item.precio * item.cantidad), 0).toFixed(2)}</strong>
                </div>
                <button className="btn-comprar">Finalizar Compra</button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}
