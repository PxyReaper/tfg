import { Kafka } from 'kafkajs'

export const kafka = new Kafka({
  clientId: 'total-order-service', // Nombre del servicio
  brokers: ['kafka:29092'] // Dirección del broker Kafka
})
