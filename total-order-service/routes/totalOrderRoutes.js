import { Router } from 'express'
import { TotalOrderController } from '../controllers/totalOrderController.js'

export const createTotalOrderRouter = ({ totalOrderModel }) => {
  const totalOrderRouter = Router()

  const totalOrderController = new TotalOrderController({ totalOrderModel })

  totalOrderRouter.get('/', totalOrderController.getAll)
  totalOrderRouter.get('/test', totalOrderController.test)
  totalOrderRouter.get('/:id', totalOrderController.getById)

  return totalOrderRouter
}
