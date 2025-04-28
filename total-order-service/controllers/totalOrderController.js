export class TotalOrderController {
  constructor ({ totalOrderModel }) {
    this.totalOrderModel = totalOrderModel
  }

  getAll = async (req, res) => {
    const totalOrder = await this.totalOrderModel.getAll()
    res.json(totalOrder)
  }

  getById = async (req, res) => {
    const { id } = req.params
    const totalOrder = await this.totalOrderModel.getById({ id })
    if (totalOrder) return res.json(totalOrder)
    res.status(404).json({ message: 'Total Order not found' })
  }
}
