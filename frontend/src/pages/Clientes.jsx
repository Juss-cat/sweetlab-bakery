import { useState, useEffect } from 'react'
import './Clientes.css'

export default function Clientes() {
  const [clientes, setClientes] = useState([])
  const [loading, setLoading] = useState(true)
  const [showForm, setShowForm] = useState(false)
  const [formData, setFormData] = useState({
    nombre: '',
    email: '',
    telefono: ''
  })

  useEffect(() => {
    setTimeout(() => setLoading(false), 1000)
  }, [])

  const handleSubmit = (e) => {
    e.preventDefault()
    console.log('Nuevo cliente:', formData)
    setFormData({ nombre: '', email: '', telefono: '' })
    setShowForm(false)
  }

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
        <p>Cargando clientes...</p>
      </div>
    )
  }

  return (
    <div className="clientes-container">
      <div className="header-clientes">
        <h2>Gestión de Clientes</h2>
        <button onClick={() => setShowForm(!showForm)} className="btn-nuevo">
          {showForm ? '✕ Cancelar' : '+ Nuevo Cliente'}
        </button>
      </div>

      {showForm && (
        <div className="formulario-cliente card">
          <h3>Registrar Nuevo Cliente</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Nombre</label>
              <input
                type="text"
                value={formData.nombre}
                onChange={(e) => setFormData({ ...formData, nombre: e.target.value })}
                placeholder="Nombre completo"
                required
              />
            </div>
            <div className="form-group">
              <label>Email</label>
              <input
                type="email"
                value={formData.email}
                onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                placeholder="correo@ejemplo.com"
                required
              />
            </div>
            <div className="form-group">
              <label>Teléfono</label>
              <input
                type="tel"
                value={formData.telefono}
                onChange={(e) => setFormData({ ...formData, telefono: e.target.value })}
                placeholder="+1 234 567 8900"
              />
            </div>
            <button type="submit">Registrar Cliente</button>
          </form>
        </div>
      )}

      <div className="clientes-lista">
        {clientes.length === 0 ? (
          <div className="info">
            📋 No hay clientes registrados. ¡Comienza a registrar tus primeros clientes!
          </div>
        ) : (
          <div className="tabla">
            <table>
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th>Email</th>
                  <th>Teléfono</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                {clientes.map(cliente => (
                  <tr key={cliente.id}>
                    <td>{cliente.nombre}</td>
                    <td>{cliente.email}</td>
                    <td>{cliente.telefono}</td>
                    <td>
                      <button className="btn-editar">Editar</button>
                      <button className="btn-eliminar">Eliminar</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  )
}
