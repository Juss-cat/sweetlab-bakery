import React, { useState, useEffect } from 'react'
import './App.css'
import Navbar from './components/Navbar'
import Tienda from './components/Tienda'
import Carrito from './components/Carrito'
import Contacto from './components/Contacto'
import Sobre from './components/Sobre'

function App() {
  const [paginaActual, setPaginaActual] = useState('tienda')
  const [carrito, setCarrito] = useState([])
  const [mostrarCarrito, setMostrarCarrito] = useState(false)

  // Cargar carrito desde localStorage al iniciar
  useEffect(() => {
    const carritoGuardado = localStorage.getItem('carrito_sweetlab')
    if (carritoGuardado) {
      try {
        setCarrito(JSON.parse(carritoGuardado))
      } catch (err) {
        console.error('Error al cargar carrito:', err)
      }
    }
  }, [])

  // Guardar carrito en localStorage cuando cambia
  useEffect(() => {
    localStorage.setItem('carrito_sweetlab', JSON.stringify(carrito))
  }, [carrito])

  const agregarAlCarrito = (producto) => {
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

  const removerDelCarrito = (id) => {
    setCarrito(carrito.filter(item => item.id !== id))
  }

  const actualizarCantidad = (id, cantidad) => {
    if (cantidad <= 0) {
      removerDelCarrito(id)
    } else {
      setCarrito(carrito.map(item =>
        item.id === id ? { ...item, cantidad } : item
      ))
    }
  }

  const procesarPago = async () => {
    if (carrito.length === 0) {
      alert('El carrito está vacío')
      return
    }

    try {
      const total = carrito.reduce((acc, item) => acc + (item.precio * item.cantidad), 0) + 5
      const pedido = {
        total: parseFloat(total.toFixed(2)),
        estado: 'PENDIENTE',
        items: carrito.map(item => ({
          productoId: item.id,
          cantidad: item.cantidad,
          precio: item.precio
        }))
      }

      const response = await fetch('http://localhost:8080/api/pedidos', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(pedido)
      })

      if (response.ok) {
        alert('✅ ¡Pedido registrado exitosamente! Te contactaremos pronto.')
        setCarrito([])
        setMostrarCarrito(false)
        setPaginaActual('tienda')
      } else {
        alert('❌ Error al procesar el pedido. Intenta de nuevo.')
      }
    } catch (err) {
      console.error('Error:', err)
      alert('❌ No se pudo conectar con el servidor.')
    }
  }

  const renderPagina = () => {
    if (mostrarCarrito) {
      return <Carrito 
        carrito={carrito} 
        removerDelCarrito={removerDelCarrito} 
        actualizarCantidad={actualizarCantidad} 
        procesarPago={procesarPago}
        onCerrarCarrito={() => setMostrarCarrito(false)}
      />
    }

    switch(paginaActual) {
      case 'tienda':
        return <Tienda agregarAlCarrito={agregarAlCarrito} />
      case 'contacto':
        return <Contacto />
      case 'sobre':
        return <Sobre />
      default:
        return <Tienda agregarAlCarrito={agregarAlCarrito} />
    }
  }

  return (
    <div className="app">
      <Navbar 
        paginaActual={paginaActual} 
        setPaginaActual={setPaginaActual}
        cartCount={carrito.length}
        onCartClick={() => setMostrarCarrito(!mostrarCarrito)}
        mostrarCarrito={mostrarCarrito}
      />
      <main className="main-content">
        {renderPagina()}
      </main>
    </div>
  )
}

export default App

