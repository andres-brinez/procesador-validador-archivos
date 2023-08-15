
# Proyecto de procesamiento de archivos

## Microservicios para Validación y Procesamiento de Archivos

Este proyecto es una implementación de ejemplo de dos microservicios que se encargan de procesar y  validar   archivos en formatos CSV y Excel. Los microservicios están desarrollados en Spring Boot y se comunican entre sí mediante RestTemplate.

Este proyecto consta de dos microservicios: uno encargado del procesamiento de archivos y otro encargado de la validación de los registros en esos archivos. Los microservicios se comunican entre sí mediante RESTful API utilizando RestTemplate

## Estructura de Carpetas
ProcesadorService: Este microservicio maneja la carga y procesamiento de archivos. Lee archivos CSV y Excel, y envía cada registro al ValidadorService para su validación.

ValidadorService: Este microservicio se encarga de validar los registros recibidos del ProcesadorService. Implementa validaciones específicas según el tipo de archivo (CSV o Excel) y devuelve un booleano que indica si el registro es válido o no.

## Microservicio "Procesador"

El microservicio "Procesador" carga (URL) y lee archivos en los formatos CSV y Excel. Luego, realiza peticiones al microservicio "Validador" para verificar la validez de cada registro en el archivo. Al final, devuelve el número de líneas válidas e inválidas.

### Endpoints

- `POST /procesar`: Recibe un la url y su tipo (CSV o Excel) en el cuerpo de la solicitud. Procesa el archivo y devuelve el resultado de la validación.

  ![image](https://github.com/andres-brinez/procesador-validador-archivos/assets/94869227/b83ac1a7-0f6a-4229-a7e7-c53db0beef99)
  ![image](https://github.com/andres-brinez/procesador-validador-archivos/assets/94869227/881b18f1-c9ac-4d85-b4b4-f383a1e478b0)
  ![image](https://github.com/andres-brinez/procesador-validador-archivos/assets/94869227/86799c03-a2a1-44f3-9bbb-8edd76a45adb)


  

## Microservicio "Validador"

El microservicio "Validador" realiza las validaciones específicas para cada formato de archivo. Evalúa campos como correo electrónico, fecha de nacimiento, título de trabajo, ubicación de lesión y tipo de informe.

### Endpoints

- `POST /validador/validarRegistros`: Recibe un registro y su tipo de archivo en el cuerpo de la solicitud. Realiza las validaciones correspondientes y devuelve un valor booleano indicando si el registro es válido.

## Tecnologías Utilizadas

- Spring Boot: Framework para el desarrollo de aplicaciones Java.
- RestTemplate: Librería para realizar llamadas RESTful entre microservicios.
- Java Time API: Para el manejo de fechas y tiempos.
- Apache POI: Para el manejo de archivos Excel.
- Expresiones Regulares: Para validar los archivos.

## Configuración

Cada microservicio se encuentra en su propio directorio y contiene su propio archivo `build.gradle` para gestionar las dependencias. Asegúrate de configurar las rutas y las conexiones adecuadas en los archivos de configuración de cada microservicio para que puedan comunicarse correctamente entre sí.

## Uso

1. Clona este repositorio:
2. Ejecuta los microservicios `ProcesadorService` y `ValidadorService` en puertos diferente
3. Realiza una solicitud POST al endpoint `/procesador/procesarArchivo`   con la ruta del archivo en el cuerpo del mensaje. 
4. El `ProcesadorService` leerá el archivo, procesará los registros y enviará cada registro al `ValidadorService`.
5. El `ValidadorService` validará cada registro según el tipo de archivo y devolverá el resultado al `ProcesadorService`. 
6. Al finalizar el procesamiento, el `ProcesadorService` retornará el número de registros válidos e inválidos al cliente.
5. ¡Disfruta!


## Contribuciones

Siéntete libre de realizar contribuciones y mejoras a este proyecto. ¡Tus ideas son bienvenidas!
Si deseas contribuir a este proyecto, crea un fork del repositorio y envía una solicitud de extracción con tus cambios.

## Notas
Este es un proyecto de ejemplo con fines educativos. Asegúrate de adecuarlo a tus necesidades y considerar aspectos de seguridad, manejo de errores y escalabilidad en un entorno de producción.

¡Disfruta explorando y aprendiendo con este proyecto de microservicios! Si tienes alguna pregunta o necesitas ayuda, no dudes en preguntar.
## Licencia

Este proyecto está bajo la [Licencia MIT](LICENSE).

