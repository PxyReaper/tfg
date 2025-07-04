import express from 'express' // require -> commonJS
import { createTotalOrderRouter } from './routes/totalOrderRoutes.js'
import './client/eurekaClient.js'
import { TotalOrderModel } from './models/mysql/totalOrderModel.js'
import './services/orderConsumer.js'
export const createApp = async ({ totalOrderModel }) => {
  const app = express()
  app.disable('x-powered-by')

  app.use('/totalOrder', createTotalOrderRouter({ totalOrderModel }))

  const PORT = process.env.PORT ?? 3000

  app.listen(PORT, () => {
    console.log(`server listening on port http://localhost:${PORT}`)
  })
}
createApp({ totalOrderModel: TotalOrderModel }).catch(err => {
  console.log(err)
})
