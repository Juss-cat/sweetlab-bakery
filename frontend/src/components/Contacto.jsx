import React, { useState } from 'react'
import './Contacto.css'

export default function Contacto() {
  const [formulario, setFormulario] = useState({
    nombre: '',
    email: '',
    telefono: '',
    asunto: '',
    mensaje: ''
  })

  const [enviado, setEnviado] = useState(false)

  const handleChange = (e) => {
    const { name, value } = e.target
    setFormulario({
      ...formulario,
      [name]: value
    })
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    
    // Enviar a API
    fetch('http://localhost:8080/api/contactos', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formulario)
    })
      .then(res => {
        if (res.ok) {
          console.log('Mensaje enviado:', formulario)
          setEnviado(true)
          setFormulario({
            nombre: '',
            email: '',
            telefono: '',
            asunto: '',
            mensaje: ''
          })
          setTimeout(() => setEnviado(false), 5000)
        } else {
          alert('Error al enviar el mensaje')
        }
      })
      .catch(err => {
        console.error('Error:', err)
        alert('No se pudo conectar con el servidor')
      })
  }

  return (
    <div className="contacto-container">
      <div className="contacto-header">
        <h2>📞 Contacto</h2>
        <p>¿Tienes preguntas? Nos encantaría saber de ti</p>
      </div>

      <div className="contacto-content">
        <div className="contacto-info">
          <h3>Información de Contacto</h3>
          
          <div className="info-item">
            <div className="info-icon">📍</div>
            <div className="info-texto">
              <h4>Ubicación</h4>
              <p>Calle Principal 123, Centro<br/>Tu Ciudad, 12345</p>
            </div>
          </div>

          <div className="info-item">
            <div className="info-icon">📱</div>
            <div className="info-texto">
              <h4>Teléfono</h4>
              <p>(555) 123-4567</p>
              <p className="whatsapp">WhatsApp: (555) 987-6543</p>
            </div>
          </div>

          <div className="info-item">
            <div className="info-icon">✉️</div>
            <div className="info-texto">
              <h4>Email</h4>
              <p>info@sweetlabbakery.com</p>
              <p>pedidos@sweetlabbakery.com</p>
            </div>
          </div>

          <div className="horario">
            <h3>⏰ Horario de Atención</h3>
            <div className="horario-item">
              <span>Lunes - Viernes:</span>
              <span>8:00 AM - 7:00 PM</span>
            </div>
            <div className="horario-item">
              <span>Sábado:</span>
              <span>9:00 AM - 6:00 PM</span>
            </div>
            <div className="horario-item">
              <span>Domingo:</span>
              <span>9:00 AM - 5:00 PM</span>
            </div>
          </div>

          <div className="redes-sociales">
            <h3>🌐 Síguenos</h3>
            <div className="botones-redes">
              <button className="red-btn facebook">f</button>
              <button className="red-btn instagram">📷</button>
              <button className="red-btn twitter">𝕏</button>
              <button className="red-btn whatsapp-btn">💬</button>
            </div>
          </div>
        </div>

        <div className="formulario-contacto">
          {enviado && (
            <div className="mensaje-exito">
              ✅ ¡Mensaje enviado exitosamente! Pronto nos pondremos en contacto.
            </div>
          )}

          <form onSubmit={handleSubmit}>
            <div className="grupo-formulario">
              <label htmlFor="nombre">Nombre *</label>
              <input
                type="text"
                id="nombre"
                name="nombre"
                value={formulario.nombre}
                onChange={handleChange}
                required
                placeholder="Tu nombre completo"
              />
            </div>

            <div className="grupo-formulario">
              <label htmlFor="email">Email *</label>
              <input
                type="email"
                id="email"
                name="email"
                value={formulario.email}
                onChange={handleChange}
                required
                placeholder="tu@email.com"
              />
            </div>

            <div className="grupo-formulario">
              <label htmlFor="telefono">Teléfono</label>
              <input
                type="tel"
                id="telefono"
                name="telefono"
                value={formulario.telefono}
                onChange={handleChange}
                placeholder="(555) 123-4567"
              />
            </div>

            <div className="grupo-formulario">
              <label htmlFor="asunto">Asunto *</label>
              <select
                id="asunto"
                name="asunto"
                value={formulario.asunto}
                onChange={handleChange}
                required
              >
                <option value="">-- Selecciona un asunto --</option>
                <option value="pedido">Consulta sobre pedidos</option>
                <option value="promocion">Ofertas y promociones</option>
                <option value="evento">Eventos y celebraciones</option>
                <option value="general">Consulta general</option>
                <option value="otro">Otro</option>
              </select>
            </div>

            <div className="grupo-formulario">
              <label htmlFor="mensaje">Mensaje *</label>
              <textarea
                id="mensaje"
                name="mensaje"
                value={formulario.mensaje}
                onChange={handleChange}
                required
                rows="6"
                placeholder="Cuéntanos cómo podemos ayudarte..."
              ></textarea>
            </div>

            <button type="submit" className="btn-enviar">
              📤 Enviar Mensaje
            </button>
          </form>
        </div>
      </div>
    </div>
  )
}
