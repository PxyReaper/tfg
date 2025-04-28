import { createApp } from './app.js'

import { TotalOrderModel } from './models/mysql/totalOrderModel.js'

createApp({ totalOrderModel: TotalOrderModel })
