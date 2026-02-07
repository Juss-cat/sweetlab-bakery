import { useState, useEffect } from 'react'
import './Pedidos.css'

export default function Pedidos() {
  const [pedidos, setPedidos] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    setTimeout(() => setLoading(false), 1000)
  }, [])

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
        <p>Cargando pedidos...</p>
      </div>
    )
  }

  return (
    <div className="pedidos-container">
      <h2>Historial de Pedidos</h2>
      
      <div className="pedidos-lista">
        {pedidos.length === 0 ? (
          <div className="info">
            📦 No hay pedidos registrados aún.
          </div>
        ) : (
          <div className="pedidos-grid">
            {pedidos.map(pedido => (
              <div key={pedido.id} className="pedido-card card">
                <h3>Pedido #{pedido.id}</h3>
                <p><strong>Cliente:</strong> {pedido.cliente}</p>
                <p><strong>Fecha:</strong> {new Date(pedido.fecha).toLocaleDateString()}</p>
                <p><strong>Total:</strong> ${pedido.total.toFixed(2)}</p>
                <span className={`estado estado-${pedido.estado.toLowerCase()}`}>
                  {pedido.estado}
                </span>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}
