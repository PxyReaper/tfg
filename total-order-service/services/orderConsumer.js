import kafka from '../config/kafkaClient.js'
import mysql from 'mysql2/promise'
import { loadConfig } from '../../config/configClient.js'

const config = await loadConfig()
const connection = await mysql.createConnection(config)

const consumer = kafka.consumer({ groupId: 'order-service-group' })

async function startConsumer () {
  await consumer.connect()
  await consumer.subscribe({ topic: 'total-order-topic', fromBeginning: true })

  await consumer.run({
    eachMessage: async ({ message }) => {
      const eventData = JSON.parse(message.value.toString())

      console.log(`Procesando pedido: ${eventData.id}`)

      for (const product of eventData.products) {
        await connection.execute(
          'INSERT INTO cuerpo_pedido (pedido_id, producto_id, cantidad) VALUES (?, ?, ?)',
          [eventData.id, product.productId, product.quantity]
        )
      }

      console.log(`Pedido ${eventData.id} insertado en cuerpo_pedido`)
    }
  })
}

startConsumer()
