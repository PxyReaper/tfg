import configClient from 'cloud-config-client'

// Opciones de configuración para el cliente
const configOptions = {
  endpoint: 'http://config-server:8888', // URL del servidor de configuración (Spring Cloud Config por defecto)
  name: 'total-order-service', // Nombre de la aplicación
  profiles: ['dev'], // Perfiles a cargar (por ejemplo: dev, prod, test)
  // Otras opciones disponibles:
  label: 'master' // La etiqueta git a usar (por defecto: master)
  // rejectUnauthorized: false,     // Para servidores con certificados SSL autofirmados
  // agent: proxy,                  // Proxy HTTP
}

// Variable para almacenar la configuración
let appConfig = {}

// Función para cargar la configuración
export async function loadConfig () {
  try {
    // Obtenemos la configuración del servidor
    const config = await configClient.load(configOptions)

    // Convertimos la configuración a un objeto plano
    appConfig = config.properties
    console.log(appConfig.database)
    console.log('Configuración cargada correctamente')
    return appConfig
  } catch (error) {
    console.error('Error al cargar la configuración:', error)
    throw error
  }
}
export {
  appConfig
}
