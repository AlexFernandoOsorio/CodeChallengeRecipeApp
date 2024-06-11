# Recetas APP


App de recetas construida con Jetpack Compose y Clean Architecture. 

## Tabla de contenidos

- [Prerequisitos](#prerequisitos)
- [Librerias](#librerias)
- [Tooling](#tooling)
- [Architectura](#architectura)
- [Testing](#testing)
- [Referencias](#references)
- [License](#license)

## Prerequisitos

- Android Studio 4.2+
- Gradle 7.0+
- SDK de Android 21+

## Librerias

- [Hilt](https://dagger.dev/hilt/) - Inyeccion de dependencias.
- [Jetpack](https://developer.android.com/jetpack)
  
  -   [Android KTX](https://developer.android.com/kotlin/ktx.html) - Las extensiones KTX proporcionan Kotlin conciso e idiomático a Jetpack, la plataforma de Android y otras APIs.
  -   [Jetpack Compose](https://developer.android.com/jetpack/compose) - Jetpack Compose es el kit de herramientas moderno de Android para compilar IU nativas.
    -   [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Realiza acciones en respuesta a un cambio en el estado del ciclo de vida de otro componente.
    -   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Diseñado para almacenar y gestionar datos relacionados con la interfaz de usuario de forma consciente del ciclo de vida.
    
    - [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) - Componente que permite una implementación más sencilla de la navegación desde elementos componibles.


- [Retrofit](https://square.github.io/retrofit/) - Retrofit es un cliente de servidores REST para Android y Java desarrollado por Square, muy simple y muy fácil de aprender. 
- [OkHttp-Logging-Interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - Logs HTTP request y response data.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support para coroutines.
- [Flow](https://developer.android.com/kotlin/flow) - Flows se crean sobre rutinas y pueden proporcionar múltiples valores. Un Flow es conceptualmente un flujo de datos que se puede calcular de forma asincrónica.
- [Material Design](https://material.io/develop/android/docs/getting-started/) - Build awesome beautiful UIs.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support para coroutinas,provee runBlocking coroutine builder usado en tests.
- [Detekt lint] (https://detekt.dev/) - Detekt es una herramienta de análisis de código estático para Kotlin que es capaz de identificar e informar olores de código
- [Coil] (https://coil-kt.github.io/coil/) - Biblioteca de Coil para cargar y mostrar fotos de Internet en tu app de Android Compose.
- [Maps Compose] (https://developers.google.com/maps/documentation/android-sdk/maps-compose) - El repositorio contiene componentes Jetpack Compose para Maps SDK para Android.

## Tooling

Todas las dependencias (bibliotecas externas) se definen en un solo lugar: (libs.versions.toml) file usando [Version Catalogs](https://docs.gradle.org/current/userguide/platforms.html).


## Architectura

Este proyecto sigue la arquitectura limpia (Clean Architecture) con las siguientes capas:

- **data**: Contiene los repositorios y fuentes de datos. Aquí se encuentra la implementación de Retrofit para las llamadas a la API.
- **domain**: Contiene los casos de uso (use cases) que representan la lógica de negocio de la aplicación.
- **features**: Contiene las pantallas y componentes de la interfaz de usuario utilizando Jetpack Compose. También incluye los ViewModels que siguen el patrón MVVM.
  - *navigation* : define todas las rutas posibles en la aplicación y proporcionar funciones para navegar en las rutas 
- **di**: Configuración de Hilt para la inyección de dependencias.

## Testing

Los Tests son principalmente pruebas unitarias que cubren la capa de data y la capa de dominio y también viewmodels, bibliotecas principales utilizadas para las pruebas son :

- Junit
- Mockk
- Coroutines Testing
- Jacoco


## Referencias

1. [The clean code blog](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) by Robert C. Martin.
2. [A detailed guide on developing android apps using clean architecture pattern](https://medium.com/@dmilicic/a-detailed-guide-on-developing-android-apps-using-the-clean-architecture-pattern-d38d71e94029) Medium article.

## License

```
   Copyright 2024 Alex Fernando

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   ```
