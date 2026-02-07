import React, { useState, useEffect } from 'react';
import { pedidosAPI, clientesAPI } from '../services/api';
import './Pedidos.css';

export default function Pedidos() {
  const [pedidos, setPedidos] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filtroEstado, setFiltroEstado] = useState('');
  const [showFormulario, setShowFormulario] = useState(false);
  const [nuevoPedido, setNuevoPedido] = useState({
    clienteId: '',
    estado: 'PENDIENTE'
  });

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      setLoading(true);
      const [respPedidos, respClientes] = await Promise.all([
        pedidosAPI.obtenerTodos(),
        clientesAPI.obtenerTodos()
      ]);
      setPedidos(respPedidos.data);
      setClientes(respClientes.data);
      setError(null);
    } catch (err) {
      setError('Error al cargar datos: ' + err.message);
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleCrearPedido = async (e) => {
    e.preventDefault();
    try {
      if (!nuevoPedido.clienteId) {
        setError('Debes seleccionar un cliente');
        return;
      }
      
      const cliente = clientes.find(c => c.id == nuevoPedido.clienteId);
      await pedidosAPI.crear({
        cliente: cliente,
        estado: nuevoPedido.estado,
        total: 0,
        items: []
      });
      
      setNuevoPedido({ clienteId: '', estado: 'PENDIENTE' });
      setShowFormulario(false);
      await cargarDatos();
    } catch (err) {
      setError('Error al crear pedido: ' + err.message);
    }
  };

  const handleCambiarEstado = async (id, nuevoEstado) => {
    try {
      await pedidosAPI.cambiarEstado(id, nuevoEstado);
      await cargarDatos();
    } catch (err) {
      setError('Error al cambiar estado: ' + err.message);
    }
  };

  const handleEliminar = async (id) => {
    if (confirm('¿Deseas eliminar este pedido?')) {
      try {
        await pedidosAPI.eliminar(id);
        await cargarDatos();
      } catch (err) {
        setError('Error al eliminar pedido: ' + err.message);
      }
    }
  };

  const pedidosFiltrados = filtroEstado 
    ? pedidos.filter(p => p.estado === filtroEstado)
    : pedidos;

  const getNombreCliente = (clienteId) => {
    const cliente = clientes.find(c => c.id === clienteId);
    return cliente ? cliente.nombre : 'Desconocido';
  };

  if (loading) return <div className="loading">Cargando pedidos...</div>;

  return (
    <div className="pedidos-container">
      <h2>📦 Pedidos - SweetLab Bakery</h2>
      
      {error && <div className="error-message">{error}</div>}
      
      <div className="pedidos-controles">
        <div className="filtro-estado">
          <label>Filtrar por estado:</label>
          <select 
            value={filtroEstado} 
            onChange={(e) => setFiltroEstado(e.target.value)}
          >
            <option value="">Todos</option>
            <option value="PENDIENTE">Pendiente</option>
            <option value="COMPLETADO">Completado</option>
            <option value="CANCELADO">Cancelado</option>
          </select>
        </div>

        <button 
          className="btn-crear"
          onClick={() => setShowFormulario(!showFormulario)}
        >
          {showFormulario ? '✕ Cancelar' : '➕ Nuevo Pedido'}
        </button>
      </div>

      {showFormulario && (
        <form className="formulario-pedido" onSubmit={handleCrearPedido}>
          <h3>Crear Nuevo Pedido</h3>
          
          <select
            value={nuevoPedido.clienteId}
            onChange={(e) => setNuevoPedido({...nuevoPedido, clienteId: e.target.value})}
            required
          >
            <option value="">Seleccionar Cliente</option>
            {clientes.map(cliente => (
              <option key={cliente.id} value={cliente.id}>
                {cliente.nombre} ({cliente.email})
              </option>
            ))}
          </select>
          
          <button type="submit" className="btn-guardar">💾 Crear Pedido</button>
        </form>
      )}

      <div className="pedidos-tabla">
        {pedidosFiltrados.length === 0 ? (
          <p>No hay pedidos disponibles</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Cliente</th>
                <th>Estado</th>
                <th>Total</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {pedidosFiltrados.map(pedido => (
                <tr key={pedido.id}>
                  <td>#{pedido.id}</td>
                  <td>{getNombreCliente(pedido.cliente?.id)}</td>
                  <td>
                    <select 
                      value={pedido.estado}
                      onChange={(e) => handleCambiarEstado(pedido.id, e.target.value)}
                      className={`estado-selector ${pedido.estado.toLowerCase()}`}
                    >
                      <option value="PENDIENTE">Pendiente</option>
                      <option value="COMPLETADO">Completado</option>
                      <option value="CANCELADO">Cancelado</option>
                    </select>
                  </td>
                  <td>${pedido.total?.toFixed(2) || '0.00'}</td>
                  <td>
                    <button 
                      className="btn-eliminar"
                      onClick={() => handleEliminar(pedido.id)}
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
