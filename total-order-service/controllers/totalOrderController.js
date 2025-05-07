import axios from 'axios'
import { getServiceUrl } from '../services/instanceService.js'

export class TotalOrderController {
  constructor ({ totalOrderModel }) {
    this.totalOrderModel = totalOrderModel
  }

  getAll = async (req, res) => {
    const totalOrder = await this.totalOrderModel.getAll()
    res.json(totalOrder)
  }

  getById = async (req, res) => {
    console.log('entrando en el id')
    const { id } = req.params
    const totalOrder = await this.totalOrderModel.getById({ id })
    if (totalOrder) return res.json(totalOrder)
    return res.status(404).json({ message: 'Total Order not found' })
  }

  test = async (req, res) => {
    try {
      console.log('Entrando...')
      const serviceUrl = await getServiceUrl('user-service')
      console.log('servicio encontrado')
      const response = await axios.get(`${serviceUrl}/id/59562c8f-4d9f-475f-97a5-113a937370e7`)
      return res.json(response.data)
    } catch (err) {
      console.log('Ha ocurrido un error ', err)
      return res.json(err)
    }
  }
}
