# Trabajo de fin de grado  de desarrollo de aplicaciones Web en el centro TEIDE IV

Este repositorio muestra el desarrollo de la aplicacion **AMAZONIAS**, la cual es la que se presentará  en la exposición del trabajo de fin de grado del actual curso 2024/2025.

**Para poder hacer las pruebas en los respectivos proyectos**, primero hay que levantar tanto el servidor de configuración(**config-service**) junto con el servidor de nombre (**eureka-server**) para empezar a hacer cambios dentro de la infraestructura del proyecto.

Otra cosa a tener en cuenta es en los perfiles de las aplicaciones(En el caso de las aplicaciones de Spring).En caso de que estes trabajando con la base de datos de Railway para poder obtener su configuracion: url de base de datos, usuario,contraseña...etc.

Tenemos que ir a cualquiera de los proyectos que vayamos a hacer las pruebas o crear(Si son de **Spring Boot** ) y activar el profile de **dev**.
```properties
    spring.profiles.active=local #SI ESTA ASI, CAMBIAR EL PARAMETRO DE LOCAL A DEV
    spring.profiles.active=dev #ALGO TAL QUE ASI
```
**En caso de que estes trabajando en los microservicios de ExpressJs** tienes que irte al archivo configClient.js y modificar en el config options el array de profiles y ponerlo como dev.
```javascript
const configOptions = {
  endpoint: 'http://localhost:8888',
  name: 'total-order-service',
  profiles: ['local'], // AQUI CAMBIAR A DEV
  label: 'master' 
}
```

A partir de aqui podremos sin problema desarrollar y hacer cambios en nuestras aplicaciones que usen el framework de Spring.

## Ejecución de todos los microservicios

Si deseas ejecutar todos los microservicios para hacer las pruebas, tienes que tener instalado docker (o docker desktop en windows y ejecutar el siguiente comando)

```bash
docker-compose up
```
Tienes que ejecutarlo dentro de la carpeta del tfg, en cualquiera de los microservicios.

