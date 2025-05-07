import { eurekaClient } from '../client/eurekaClient.js'

export const getServiceUrl = (serviceName) => {
  return new Promise((resolve, reject) => {
    const instances = eurekaClient.getInstancesByAppId(serviceName)
    if (instances.length === 0) {
      throw new Error('No se han encontrado instancias para este servicio')
    }
    console.log('Se ha encontrado la instancia ' + instances[0].hostName + ' con el puerto: ' + instances[0].port.$ +
      `http://${instances[0].hostName}:${instances[0].port.$}`
    )
    const instance = instances[0]
    resolve(`http://${instance.hostName}:${instance.port.$}`)
  })
}
