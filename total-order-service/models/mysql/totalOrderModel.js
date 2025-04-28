import mysql from 'mysql2/promise'

const config = {
  host: 'shinkansen.proxy.rlwy.net',
  user: 'root',
  port: 23706,
  password: 'hWrgMKYKnrNrkbQPXbnKobPqeZPWBSNk',
  database: 'railway'
}

const connection = await mysql.createConnection(config)

export class TotalOrderModel {
  static async getAll () {
    console.log('getAll')

    const [totalOrder] = await connection.query(
        `SELECT 
            cuerpo_pedido.id_pedido,
            cuerpo_pedido.id_producto,
            cuerpo_pedido.cantidad,
            productos.nombre,
            productos.descripcion,
            productos.precio_unit
        FROM 
            cuerpo_pedido
        INNER JOIN 
            productos
        ON 
            cuerpo_pedido.id_producto = productos.id_producto;`
    )

    return totalOrder
  }

  static async getById ({ id }) {
    const [totalOrder] = await connection.query(
      `SELECT 
            cuerpo_pedido.id_pedido,
            cuerpo_pedido.id_producto,
            cuerpo_pedido.cantidad,
            productos.nombre,
            productos.descripcion,
            productos.precio_unit
        FROM 
            cuerpo_pedido
        INNER JOIN 
            productos
        ON 
            cuerpo_pedido.id_producto = productos.id_producto
        WHERE 
            cuerpo_pedido.id_pedido = ?;`,
      [id]
    )

    if (totalOrder.length === 0) return null

    return totalOrder[0]
  }
}
