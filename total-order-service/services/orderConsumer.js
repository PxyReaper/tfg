import { kafka } from '../config/kafkaClient.js'
import mysql from 'mysql2/promise'
import { loadConfig } from '../config/configClient.js'

const config = await loadConfig()
const connection = await mysql.createConnection(config)

const consumer = kafka.consumer({ groupId: 'order-service-group' })

async function startConsumer () {
  await consumer.connect()
  await consumer.subscribe({ topic: 'total-order-topic', fromBeginning: true })

  await consumer.run({
    eachMessage: async ({ message }) => {
      try {
        console.log('📥 Mensaje recibido')
        const eventData = JSON.parse(message.value.toString())

        console.log(`🧾 Pedido: ${eventData.id}`)
        for (const product of eventData.products) {
          const totalPrice = product.quantity * product.unitPrice
          await connection.execute(
            'INSERT INTO cuerpo_pedido (id_pedido, id_producto, cantidad, precio_total) VALUES (?, ?, ?, ?)',
            [eventData.id, product.productId, product.quantity, totalPrice]
          )
        }

        console.log(`✅ Pedido ${eventData.id} insertado`)
      } catch (err) {
        console.error('❌ Error procesando mensaje:', err)
      }
    }

  })
}

startConsumer()
