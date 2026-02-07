import './Navbar.css'

export default function Navbar({ paginaActual, setPaginaActual, cartCount = 0, onCartClick, mostrarCarrito }) {
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <div className="navbar-logo">
          <button onClick={() => setPaginaActual('tienda')} className="logo-btn">
            <h1>🍰 SweetLab Bakery</h1>
            <p>Tu panadería favorita</p>
          </button>
        </div>
        
        <ul className="navbar-menu">
          <li>
            <button 
              className={`nav-link ${paginaActual === 'tienda' && !mostrarCarrito ? 'active' : ''}`}
              onClick={() => { setPaginaActual('tienda'); onCartClick && onCartClick(); }}
            >
              🏪 Tienda
            </button>
          </li>
          <li>
            <button 
              className={`nav-link ${paginaActual === 'sobre' ? 'active' : ''}`}
              onClick={() => setPaginaActual('sobre')}
            >
              ℹ️ Sobre Nosotros
            </button>
          </li>
          <li>
            <button 
              className={`nav-link ${paginaActual === 'contacto' ? 'active' : ''}`}
              onClick={() => setPaginaActual('contacto')}
            >
              📞 Contacto
            </button>
          </li>
          <li>
            <button 
              className={`nav-link carrito-btn ${mostrarCarrito ? 'active' : ''}`}
              onClick={onCartClick}
            >
              🛒 Carrito {cartCount > 0 && <span className="cart-count">{cartCount}</span>}
            </button>
          </li>
        </ul>
      </div>
    </nav>
  )
}
