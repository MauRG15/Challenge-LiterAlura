# 📚 Challenge LiterAlura

Aplicación desarrollada como parte del **Challenge de Oracle ONE + Alura Latam**.  
Consiste en una app de consola en Java con Spring Boot que permite consultar libros desde la API de Gutendex y almacenarlos en una base de datos PostgreSQL.

---

## 🚀 Funcionalidades

La aplicación permite:

1. 🔎 Buscar libro por título (desde la API Gutendex)
2. 📖 Listar libros registrados en la base de datos
3. 🌎 Listar libros por idioma
4. ✍️ Listar autores registrados
5. 📅 Listar autores vivos en un determinado año
6. 📊 Mostrar estadísticas de libros por idioma

---

## 🛠️ Tecnologías utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- API pública: https://gutendex.com/
- Maven

---

## ▶️ Cómo ejecutar el proyecto

Sigue estos pasos para ejecutar la aplicación en tu entorno local.

---

### ✅ 1. Requisitos previos

Asegúrate de tener instalado:

- Java 17 o superior  
- Maven  
- PostgreSQL  
- Un IDE (IntelliJ IDEA, Eclipse o VS Code recomendado)

Verifica que estén correctamente instalados:

```bash
java -version
mvn -version
```

---

### ✅ 2. Clonar el repositorio

```bash
git clone https://github.com/TU_USUARIO/Challenge-LiterAlura.git
cd Challenge-LiterAlura
```

---

### ✅ 3. Crear la base de datos en PostgreSQL

Ingresa a PostgreSQL (psql o pgAdmin) y ejecuta:

```sql
CREATE DATABASE literalura;
```

---

### ✅ 4. Configurar la conexión a la base de datos

Edita el archivo:

```
src/main/resources/application.properties
```

Configura tus credenciales:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.format-sql=true
```

⚠️ Reemplaza `TU_PASSWORD` por tu contraseña real de PostgreSQL.

---

### ✅ 5. Ejecutar la aplicación

#### 🔹 Opción A — Desde tu IDE

1. Abre el proyecto.
2. Busca la clase principal `LiterAluraApplication`.
3. Ejecuta el método `main`.

---

#### 🔹 Opción B — Desde la terminal con Maven

Dentro de la carpeta del proyecto ejecuta:

```bash
mvn spring-boot:run
```

O si prefieres compilar primero:

```bash
mvn clean install
mvn spring-boot:run
```

---

### ✅ 6. Uso de la aplicación

Una vez iniciada, aparecerá un menú interactivo en consola:

```
1 - Buscar libro por título
2 - Listar libros registrados
3 - Listar libros por idioma
4 - Listar autores registrados
5 - Listar autores vivos en determinado año
6 - Mostrar estadísticas de libros por idioma
0 - Salir
```

Selecciona la opción ingresando el número correspondiente.

---

## 🛑 Posibles errores comunes

### ❌ Error de conexión a PostgreSQL

- Verifica que el servicio esté activo.
- Confirma que el puerto 5432 sea correcto.
- Revisa usuario y contraseña.

### ❌ Error de versión de Java

Verifica tu versión:

```bash
java -version
```

Debe ser 17 o superior.

---

## 🔄 Reiniciar la base de datos (opcional)

Si deseas comenzar desde cero:

```sql
DROP DATABASE literalura;
CREATE DATABASE literalura;
```
