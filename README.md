# Microservicios para Validación y Procesamiento de Archivos

Este proyecto es una implementación de ejemplo de dos microservicios que se encargan de validar y procesar archivos en formatos CSV y Excel. Los microservicios están desarrollados en Spring Boot y se comunican entre sí mediante RestTemplate.

## Microservicio "Procesador"

El microservicio "Procesador" carga y lee archivos en los formatos CSV y Excel. Luego, realiza peticiones al microservicio "Validador" para verificar la validez de cada registro en el archivo. Al final, devuelve el número de líneas válidas e inválidas.

### Endpoints

- `POST /procesar`: Recibe un la url y su tipo (CSV o Excel) en el cuerpo de la solicitud. Procesa el archivo y devuelve el resultado de la validación.

## Microservicio "Validador"

El microservicio "Validador" realiza las validaciones específicas para cada formato de archivo. Evalúa campos como correo electrónico, fecha de nacimiento, título de trabajo, ubicación de lesión y tipo de informe.

### Endpoints

- `POST /validar`: Recibe un registro y su tipo de archivo en el cuerpo de la solicitud. Realiza las validaciones correspondientes y devuelve un valor booleano indicando si el registro es válido.

## Tecnologías Utilizadas

- Spring Boot
- RestTemplate para la comunicación entre microservicios
- Java
- Expresiones regulares para validaciones
- ...

## Configuración

Cada microservicio se encuentra en su propio directorio y contiene su propio archivo `pom.xml` para gestionar las dependencias. Asegúrate de configurar las URL de comunicación en el código según tu entorno.

## Uso

1. Clona este repositorio: `git clone https://github.com/tuusuario/turepositorio.git`
2. Importa los microservicios en tu IDE y configura los puertos y rutas necesarios.
3. Ejecuta los microservicios y realiza solicitudes a los endpoints para ver la validación y el procesamiento de archivos en acción.

## Contribuciones

Siéntete libre de realizar contribuciones y mejoras a este proyecto. ¡Tus ideas son bienvenidas!

## Licencia

Este proyecto está bajo la [Licencia MIT](LICENSE).

