import React from 'react'
import './Sobre.css'

export default function Sobre() {
  return (
    <div className="sobre-container">
      {/* Hero Section */}
      <div className="sobre-hero">
        <h1>🍰 Sweet Lab Bakery</h1>
        <p className="subtitulo">La magia del.pan dulce hecho con amor</p>
      </div>

      {/* Nuestra Historia */}
      <section className="seccion-historia">
        <div className="historia-content">
          <div className="historia-texto">
            <h2>📖 Nuestra Historia</h2>
            <p>
              Sweet Lab Bakery nació de un sueño compartido: crear un espacio donde 
              la tradición de la repostería se encuentre con la innovación. Todo comenzó 
              en 2015 en una pequeña cocina, cuando nuestros fundadores decidieron 
              compartir sus creaciones caseras con el mundo.
            </p>
            <p>
              Hoy, después de años de dedicación y pasión, nos hemos convertido en 
              la panadería favorita de la ciudad. Cada producto que ofrecemos es 
              resultado de investigación, experimentación y mucho amor.
            </p>
            <p>
              Nuestro laboratorio de repostería es un lugar donde la creatividad no 
              tiene límites. Aquí, nuestros maestros panaderos crean nuevas recetas 
              inspiradas en técnicas tradicionales y tendencias modernas.
            </p>
          </div>
          <div className="historia-imagen">
            <div className="imagen-placeholder">
              🍪🍩🧁🥐
            </div>
          </div>
        </div>
      </section>

      {/* Nuestra Misión */}
      <section className="seccion-mision">
        <h2>🎯 Nuestra Misión y Valores</h2>
        <div className="mision-grid">
          <div className="mision-card">
            <div className="mision-icono">🌟</div>
            <h3>Calidad Premium</h3>
            <p>Utilizamos solo los mejores ingredientes, frescos y de origen local cuando es posible.</p>
          </div>
          <div className="mision-card">
            <div className="mision-icono">❤️</div>
            <h3>Pasión por el Detalle</h3>
            <p>Cada producto es elaborado con atención meticulosa y dedicación absoluta.</p>
          </div>
          <div className="mision-card">
            <div className="mision-icono">🌱</div>
            <h3>Sustentabilidad</h3>
            <p>Nos comprometemos con prácticas ecológicas y packaging responsable.</p>
          </div>
          <div className="mision-card">
            <div className="mision-icono">👨‍👩‍👧‍👦</div>
            <h3>Comunidad</h3>
            <p>Somos parte de tu familia. Tu felicidad es nuestro mayor éxito.</p>
          </div>
        </div>
      </section>

      {/* Nuestro Equipo */}
      <section className="seccion-equipo">
        <h2>👥 Nuestro Equipo</h2>
        <div className="equipo-grid">
          <div className="miembro-equipo">
            <div className="miembro-avatar">👨‍🍳</div>
            <h3>Chef Maestro</h3>
            <p className="cargo">Maestro Panadero</p>
            <p className="descripcion">20 años de experiencia en repostería francesa e internacional.</p>
          </div>
          <div className="miembro-equipo">
            <div className="miembro-avatar">👩‍🍳</div>
            <h3>Chef Creativa</h3>
            <p className="cargo">Repostera Innovadora</p>
            <p className="descripcion">Especialista en tartas personalizadas y diseño de postres.</p>
          </div>
          <div className="miembro-equipo">
            <div className="miembro-avatar">👨‍⚕️</div>
            <h3>Nutricionista</h3>
            <p className="cargo">Consultor de Salud</p>
            <p className="descripcion">Desarrollo de opciones saludables sin sacrificar sabor.</p>
          </div>
          <div className="miembro-equipo">
            <div className="miembro-avatar">👩‍💼</div>
            <h3>Gerente</h3>
            <p className="cargo">Directora General</p>
            <p className="descripcion">Lidera con pasión y garantiza la excelencia en cada detalle.</p>
          </div>
        </div>
      </section>

      {/* Especializaciones */}
      <section className="seccion-especializaciones">
        <h2>✨ Nuestras Especializaciones</h2>
        <div className="especializaciones-lista">
          <div className="especializacion-item">
            <span className="item-icon">🎂</span>
            <h4>Tortas Personalizadas</h4>
            <p>Diseñadas especialmente para tus eventos únicos</p>
          </div>
          <div className="especializacion-item">
            <span className="item-icon">🍰</span>
            <h4>Pasteles Gourmet</h4>
            <p>Sabores exóticos y técnicas de repostería avanzada</p>
          </div>
          <div className="especializacion-item">
            <span className="item-icon">🧁</span>
            <h4>Cupcakes Artesanales</h4>
            <p>Variedades de sabores y decoraciones creativas</p>
          </div>
          <div className="especializacion-item">
            <span className="item-icon">🥐</span>
            <h4>Panes Artesanales</h4>
            <p>Fermentación lenta y recetas tradicionales</p>
          </div>
          <div className="especializacion-item">
            <span className="item-icon">🍪</span>
            <h4>Galletas Gourmet</h4>
            <p>Recetas secretas transmitidas de generación en generación</p>
          </div>
          <div className="especializacion-item">
            <span className="item-icon">🍩</span>
            <h4>Donas Artesanales</h4>
            <p>Frescas cada mañana con glaseados deliciosos</p>
          </div>
        </div>
      </section>

      {/* Por Qué Elegirnos */}
      <section className="seccion-ventajas">
        <h2>💪 ¿Por Qué Elegirnos?</h2>
        <div className="ventajas-container">
          <div className="ventaja">
            <div className="ventaja-numero">✓</div>
            <h4>Ingredientes Frescos</h4>
            <p>Comprados diariamente de proveedores de confianza</p>
          </div>
          <div className="ventaja">
            <div className="ventaja-numero">✓</div>
            <h4>Entrega Rápida</h4>
            <p>Entrega en 24-48 horas en tu puerta</p>
          </div>
          <div className="ventaja">
            <div className="ventaja-numero">✓</div>
            <h4>Garantía de Satisfacción</h4>
            <p>Si no estás satisfecho, te devolvemos tu dinero</p>
          </div>
          <div className="ventaja">
            <div className="ventaja-numero">✓</div>
            <h4>Opciones Personalizadas</h4>
            <p>Adapta cualquier producto a tus preferencias</p>
          </div>
          <div className="ventaja">
            <div className="ventaja-numero">✓</div>
            <h4>Atención Personalizada</h4>
            <p>Nuestro equipo está siempre disponible para ti</p>
          </div>
          <div className="ventaja">
            <div className="ventaja-numero">✓</div>
            <h4>Precios Justos</h4>
            <p>Excelente relación calidad-precio garantizada</p>
          </div>
        </div>
      </section>

      {/* CTA Final */}
      <section className="seccion-cta">
        <h2>¿Listo para probar nuestras creaciones?</h2>
        <p>Visita nuestra tienda o realiza tu pedido en línea hoy</p>
        <button className="btn-visitar">🛒 Ir a la Tienda</button>
      </section>
    </div>
  )
}
