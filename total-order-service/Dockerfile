FROM node:22.15.0-slim

# Instala bash para usarlo en wait-for-it.sh
RUN apt-get update && apt-get install -y bash && rm -rf /var/lib/apt/lists/*

# Crea el directorio de trabajo
WORKDIR /app

# Copia y instala dependencias (sólo producción)
COPY package*.json ./
RUN npm install --production

# Copia el resto del código
COPY . .

# Copia el script de espera y asegúrate de que tiene permisos de ejecución
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Configurar Node.js para manejar señales correctamente
ENV NODE_OPTIONS="--unhandled-rejections=strict"

# Usar una forma que propague correctamente las señales
CMD ["/bin/bash", "-c", "/wait-for-it.sh eureka-server:8761 --timeout=48 --strict -- node app.js"]