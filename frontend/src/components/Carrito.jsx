import React from 'react'
import './Carrito.css'

export default function Carrito({ carrito, removerDelCarrito, actualizarCantidad, procesarPago, onCerrarCarrito }) {
  const total = carrito.reduce((acc, item) => acc + (item.precio * item.cantidad), 0)

  return (
    <div className="carrito-container">
      <h2>🛒 Tu Carrito de Compras</h2>

      {carrito.length === 0 ? (
        <div className="carrito-vacio">
          <p>Tu carrito está vacío</p>
          <p className="mensaje">¡Añade algunos productos deliciosos!</p>
        </div>
      ) : (
        <>
          <div className="carrito-items">
            {carrito.map(item => (
              <div key={item.id} className="carrito-item">
                <div className="item-foto">
                  {item.foto}
                </div>
                <div className="item-info">
                  <h4>{item.nombre}</h4>
                  <p className="item-categoria">{item.categoria}</p>
                  <p className="item-precio">${item.precio.toFixed(2)}</p>
                </div>
                <div className="item-cantidad">
                  <button 
                    className="btn-cantidad"
                    onClick={() => actualizarCantidad(item.id, item.cantidad - 1)}
                  >
                    −
                  </button>
                  <input 
                    type="number" 
                    value={item.cantidad}
                    onChange={(e) => actualizarCantidad(item.id, parseInt(e.target.value) || 1)}
                    min="1"
                  />
                  <button 
                    className="btn-cantidad"
                    onClick={() => actualizarCantidad(item.id, item.cantidad + 1)}
                  >
                    +
                  </button>
                </div>
                <div className="item-subtotal">
                  ${(item.precio * item.cantidad).toFixed(2)}
                </div>
                <button 
                  className="btn-eliminar"
                  onClick={() => removerDelCarrito(item.id)}
                >
                  🗑️
                </button>
              </div>
            ))}
          </div>

          <div className="carrito-resumen">
            <div className="resumen-fila">
              <span>Subtotal:</span>
              <span>${total.toFixed(2)}</span>
            </div>
            <div className="resumen-fila">
              <span>Envío:</span>
              <span>$5.00</span>
            </div>
            <div className="resumen-fila total">
              <span>TOTAL:</span>
              <span>${(total + 5).toFixed(2)}</span>
            </div>
            
            <button className="btn-proceder" onClick={procesarPago}>
              ✅ Proceder a Pagar
            </button>
            <button className="btn-seguir-comprando" onClick={onCerrarCarrito}>
              🛍️ Seguir Comprando
            </button>
          </div>

          <div className="info-compra">
            <h3>ℹ️ Información de Compra</h3>
            <ul>
              <li>✅ Envío disponible para toda la zona</li>
              <li>✅ Paga en efectivo o transferencia</li>
              <li>✅ Entrega en 24-48 horas</li>
              <li>✅ Productos frescos garantizados</li>
            </ul>
          </div>
        </>
      )}
    </div>
  )
}
