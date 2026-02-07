import React, { useState, useEffect } from 'react';
import { clientesAPI } from '../services/api';
import './Clientes.css';

export default function Clientes() {
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showFormulario, setShowFormulario] = useState(false);
  const [busqueda, setBusqueda] = useState('');
  const [nuevoCliente, setNuevoCliente] = useState({
    nombre: '',
    email: '',
    telefono: '',
    direccion: '',
    ciudad: '',
    codigoPostal: ''
  });

  useEffect(() => {
    cargarClientes();
  }, []);

  const cargarClientes = async () => {
    try {
      setLoading(true);
      const respuesta = await clientesAPI.obtenerTodos();
      setClientes(respuesta.data);
      setError(null);
    } catch (err) {
      setError('Error al cargar clientes: ' + err.message);
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleCrearCliente = async (e) => {
    e.preventDefault();
    try {
      await clientesAPI.crear({
        ...nuevoCliente,
        activo: true
      });
      setNuevoCliente({
        nombre: '',
        email: '',
        telefono: '',
        direccion: '',
        ciudad: '',
        codigoPostal: ''
      });
      setShowFormulario(false);
      await cargarClientes();
    } catch (err) {
      setError('Error al crear cliente: ' + err.message);
    }
  };

  const handleBuscar = async (e) => {
    e.preventDefault();
    if (!busqueda.trim()) {
      cargarClientes();
      return;
    }
    try {
      const respuesta = await clientesAPI.buscarPorNombre(busqueda);
      setClientes(respuesta.data);
    } catch (err) {
      setError('Error al buscar: ' + err.message);
    }
  };

  const handleEliminar = async (id) => {
    if (confirm('¿Deseas eliminar este cliente?')) {
      try {
        await clientesAPI.eliminar(id);
        await cargarClientes();
      } catch (err) {
        setError('Error al eliminar cliente: ' + err.message);
      }
    }
  };

  if (loading) return <div className="loading">Cargando clientes...</div>;

  return (
    <div className="clientes-container">
      <h2>👥 Clientes - SweetLab Bakery</h2>
      
      {error && <div className="error-message">{error}</div>}
      
      <div className="clientes-controles">
        <form className="busqueda-form" onSubmit={handleBuscar}>
          <input
            type="text"
            placeholder="Buscar cliente por nombre..."
            value={busqueda}
            onChange={(e) => setBusqueda(e.target.value)}
          />
          <button type="submit">🔍 Buscar</button>
          <button 
            type="button"
            onClick={() => { setBusqueda(''); cargarClientes(); }}
          >
            🔄 Limpiar
          </button>
        </form>

        <button 
          className="btn-crear"
          onClick={() => setShowFormulario(!showFormulario)}
        >
          {showFormulario ? '✕ Cancelar' : '➕ Nuevo Cliente'}
        </button>
      </div>

      {showFormulario && (
        <form className="formulario-cliente" onSubmit={handleCrearCliente}>
          <h3>Registrar Nuevo Cliente</h3>
          
          <input
            type="text"
            placeholder="Nombre Completo"
            value={nuevoCliente.nombre}
            onChange={(e) => setNuevoCliente({...nuevoCliente, nombre: e.target.value})}
            required
          />
          
          <input
            type="email"
            placeholder="Correo Electrónico"
            value={nuevoCliente.email}
            onChange={(e) => setNuevoCliente({...nuevoCliente, email: e.target.value})}
            required
          />
          
          <input
            type="tel"
            placeholder="Teléfono"
            value={nuevoCliente.telefono}
            onChange={(e) => setNuevoCliente({...nuevoCliente, telefono: e.target.value})}
          />
          
          <input
            type="text"
            placeholder="Dirección"
            value={nuevoCliente.direccion}
            onChange={(e) => setNuevoCliente({...nuevoCliente, direccion: e.target.value})}
          />
          
          <input
            type="text"
            placeholder="Ciudad"
            value={nuevoCliente.ciudad}
            onChange={(e) => setNuevoCliente({...nuevoCliente, ciudad: e.target.value})}
          />
          
          <input
            type="text"
            placeholder="Código Postal"
            value={nuevoCliente.codigoPostal}
            onChange={(e) => setNuevoCliente({...nuevoCliente, codigoPostal: e.target.value})}
          />
          
          <button type="submit" className="btn-guardar">💾 Registrar Cliente</button>
        </form>
      )}

      <div className="clientes-tabla">
        {clientes.length === 0 ? (
          <p>No hay clientes registrados</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Email</th>
                <th>Teléfono</th>
                <th>Ciudad</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {clientes.map(cliente => (
                <tr key={cliente.id}>
                  <td>{cliente.nombre}</td>
                  <td>{cliente.email}</td>
                  <td>{cliente.telefono || '-'}</td>
                  <td>{cliente.ciudad || '-'}</td>
                  <td>
                    <span className={`estado ${cliente.activo ? 'activo' : 'inactivo'}`}>
                      {cliente.activo ? '✓ Activo' : '✗ Inactivo'}
                    </span>
                  </td>
                  <td>
                    <button 
                      className="btn-eliminar"
                      onClick={() => handleEliminar(cliente.id)}
                    >
                      🗑️
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}
