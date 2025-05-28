const { Kafka } = require('kafkajs')

const kafka = new Kafka({
  clientId: 'total-order-service', // Nombre del servicio
  brokers: ['kafka:29092'] // Dirección del broker Kafka
})

module.exports = kafka
