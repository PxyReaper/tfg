import { Eureka } from 'eureka-js-client'

// Configurar cliente Eureka
export const eurekaClient = new Eureka({
  instance: {
    app: 'total-order-service',
    hostName: 'localhost',
    ipAddr: '127.0.0.1',
    port: {
      $: 3000,
      '@enabled': 'true'
    },
    vipAddress: 'total-order-service',
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn'
    }
  },
  eureka: {
    host: 'localhost',
    port: 8761,
    servicePath: '/eureka/apps/'
  }
})

// Iniciar el cliente Eureka
eurekaClient.start((error) => {
  if (error) {
    console.error('Error al registrar en Eureka:', error)
  } else {
    console.log('Cliente registrado en Eureka')
  }
})

// Evitar que Node cierre inmediatamente
process.stdin.resume()

// Manejador de cierre
function exitHandler (options = {}, exitCode) {
  let responded = false

  if (options.cleanup) console.log('→ Realizando limpieza...')
  if (exitCode || exitCode === 0) console.log('Código de salida:', exitCode)

  if (options.exit) {
    const timeout = setTimeout(() => {
      if (!responded) {
        console.warn('⏰ Timeout: forzando cierre del proceso...')
        process.exit(1) // Forzar salida con un código de error
      }
    }, 3000)

    console.log('→ Desregistrando servicio de Eureka...')

    // Establecer responded como true dentro de `stop()` para indicar que ha terminado
    eurekaClient.stop((error) => {
      responded = true
      clearTimeout(timeout)

      if (error) {
        console.error('✗ Error al desregistrar el servicio:', error)
      } else {
        console.log('✓ Servicio desregistrado de Eureka')
      }

      process.exit(0) // Exit con código de éxito
    })
  }
}

// Registrar manejadores de eventos
process.on('SIGINT', () => exitHandler({ exit: true }))
process.on('SIGTERM', () => exitHandler({ exit: true }))
process.on('SIGUSR1', () => exitHandler({ exit: true }))
process.on('SIGUSR2', () => exitHandler({ exit: true }))
process.on('exit', () => exitHandler({ cleanup: true }))
process.on('uncaughtException', () => exitHandler({ exit: true }))
