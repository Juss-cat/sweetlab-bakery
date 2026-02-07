#!/bin/bash

# Instalar dependencias del frontend
cd frontend
npm install
npm run build

# Volver a la raíz
cd ..

# Instalar y compilar backend
cd backend
mvn clean package -DskipTests

# Copiar JAR a la carpeta dist
cp target/*.jar ../frontend/dist/ 2>/dev/null || true

cd ..
echo "✅ Build completado!"
