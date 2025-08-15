# Literalura

Aplicación Java/Spring Boot para explorar y gestionar libros y autores utilizando datos públicos de Gutendex. Permite buscar títulos, registrar libros/autores/idiomas en una base de datos relacional y navegar por un menú interactivo en consola.

## Descripción del proyecto
Literalura consume la API pública de Gutendex para obtener información de libros y autores:
- API base: https://gutendex.com/books/
- Búsqueda por título: `?search=<término>` (se reemplazan espacios por "+")

El flujo principal se ejecuta por consola (CommandLineRunner) y persiste los datos usando Spring Data JPA. Se evitan duplicados de autores e idiomas al guardar.

## Funcionalidades reales
Menú de opciones (Principal.muestraElMenu):
1. Buscar libro por título (consulta Gutendex y guarda el resultado)
2. Listar libros registrados (incluye autores e idiomas)
3. Listar autores registrados (con sus libros)
4. Listar autores vivos en un determinado año
5. Listar libros por idioma (muestra idiomas disponibles y permite elegir)
0. Salir

Detalles:
- Persistencia con relaciones Many-to-Many: Libro <-> Autor y Libro <-> Idioma.
- Carga ansiosa controlada en listados mediante consultas con JOIN FETCH.
- Idiomas soportados por defecto a través de un enum de mapeo de códigos a nombre (en=Inglés, es=Español, fr=Francés). Si aparece un código no mapeado, se usa el propio código como nombre para poder ampliarlo después.

## Stack y requisitos
- Java 24 (propiedad `java.version` en pom.xml)
- Spring Boot 3.5.4
- Spring Data JPA
- Jackson Databind para parsear JSON
- PostgreSQL (driver runtime)
- Maven 3.8+ (o usar `mvnw`/`mvnw.cmd`)

## Configuración de base de datos (por defecto: PostgreSQL)
El archivo `src/main/resources/application.properties` usa variables de entorno:
- `PG_DB_HOST` (por ejemplo: `localhost:5432`)
- `PG_DB_USER`
- `PG_DB_PASSWORD`

Propiedades relevantes:
- `spring.datasource.url=jdbc:postgresql://${PG_DB_HOST}/literalura`
- `spring.jpa.hibernate.ddl-auto=update`
- `spring.jpa.show-sql=true`

Ejemplo (Windows PowerShell) para variables de entorno:
- `$Env:PG_DB_HOST = "localhost:5432"`
- `$Env:PG_DB_USER = "postgres"`
- `$Env:PG_DB_PASSWORD = "postgres"`

Alternativa H2 en memoria (para pruebas rápidas):
Sustituya temporalmente en `application.properties` por:
- `spring.datasource.url=jdbc:h2:mem:literalura;DB_CLOSE_DELAY=-1;MODE=PostgreSQL`
- `spring.datasource.driverClassName=org.h2.Driver`
- `spring.datasource.username=sa`
- `spring.datasource.password=`
- `spring.jpa.hibernate.ddl-auto=update`
- `spring.h2.console.enabled=true`

## Compilación y ejecución
- Usando Maven Wrapper en Windows:
  - `.\mvnw.cmd clean package`
  - `.\mvnw.cmd spring-boot:run`

- Usando Maven instalado:
  - `mvn clean package`
  - `java -jar target\literalura-0.0.1-SNAPSHOT.jar`

Al iniciar, se mostrará el menú en consola y podrá interactuar con las opciones.

## Ejemplo de uso
- Opción 1: Buscar libro por título
  - Ingrese: `Pride and Prejudice`
  - La app consultará: `https://gutendex.com/books/?search=Pride+and+Prejudice`
  - Mostrará el libro encontrado (título, autores, idiomas, descargas) y lo guardará.
- Opción 5: Listar libros por idioma
  - La app listará los idiomas registrados (por ejemplo, Inglés, Español, Francés) y permitirá seleccionar uno.

## Entidades y repositorios
- Libro (`idGutendex`, `titulo`, `numeroDeDescargas`) – Repo: `LibroRepository` con consultas para traer autores e idiomas y filtrar por idioma.
- Autor (`nombre`, `fechaDeNacimiento`, `fechaDeFallecimiento`) – Repo: `AutorRepository` con consultas para autores vivos por año y carga de libros.
- Idioma (`codigo`, `nombre`) – Repo: `IdiomaRepository` con búsqueda por código.

## Código relevante
- Consola/menú: `src/main/java/com/alura/literalura/principal/Principal.java`
- Lógica de negocio: `src/main/java/com/alura/literalura/service/LibroService.java`
- Consumo de API: `src/main/java/com/alura/literalura/service/ConsumoAPI.java`
- Conversión JSON: `src/main/java/com/alura/literalura/service/ConvierteDatos.java`
- Repositorios: `src/main/java/com/alura/literalura/repository/`
- Modelos/entidades: `src/main/java/com/alura/literalura/model/`

## Autor
- Leonardo Sanchez
