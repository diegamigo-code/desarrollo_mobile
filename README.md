# 🏆 WIM 100 — Sistema de Gestión y Evaluación de Candidatas

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin%202.0-purple.svg)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20M3-blue.svg)](https://developer.android.com/jetpack/compose)
[![Room](https://img.shields.io/badge/Database-Room%202.6.1-orange.svg)](https://developer.android.com/training/data-storage/room)

**WIM 100** es una aplicación nativa para Android desarrollada con **Kotlin** y **Jetpack Compose**. Diseñada para digitalizar, estandarizar y profesionalizar el proceso de selección de talentos femeninos en las áreas de Tecnología, Ciberseguridad e Ingeniería dentro de la iniciativa **WIM (Women in Mining / Women in Tech)**.

La plataforma permite a las evaluadoras registrar postulantes, aplicar matrices de evaluación técnica ponderadas y consultar el ranking consolidado desde la cuenta de Dirección, asegurando un proceso transparente, objetivo y trazable.

## Backend Spring Boot y Docker

La app usa por defecto la API de WIM 100. El login, las candidaturas, las evaluaciones, el historial y el ranking se comparten mediante MySQL. El backend está en un repositorio aparte: [backend_mobile](https://github.com/diegamigo-code/backend_mobile), donde están la arquitectura y los comandos de prueba.

Desde la carpeta del backend, inicia la API antes de abrir la app en el emulador.

```sh
cp -n .env.example .env
docker compose up --build -d
```

El emulador accede a la API en `http://10.0.2.2:8080/api`. Para usar los registros anteriores de Room en el mismo dispositivo, compila con `-PwimLocalDemo=true`. Las dos modalidades conservan sus datos por separado. Las instalaciones anteriores mantienen su base local y la API empieza con datos de demostración propios.

---

## 📋 Tablero de Gestión del Proyecto (Trello)

El seguimiento de tareas, *backlog*, historias de usuario y avance del proyecto se gestionó a través del siguiente tablero en **Trello**:

🔗 **[Ver Tablero Trello del Proyecto](https://trello.com/b/jEJZOR8N/proyecto-wim-100-dsy1105)**

---

## 💡 ¿Qué problema resuelve?

Tradicionalmente, los procesos de selección técnica sufren de sesgos, falta de estandarización en las notas y pérdida de trazabilidad. **WIM 100** resuelve este desafío mediante:

- **Matriz de evaluación ponderada**: Criterios predefinidos con porcentajes asignados.
- **Validaciones en tiempo real**: Formatos de contacto, dominios corporativos autorizados y longitud de resúmenes.
- **Control de duplicados**: Previene que una evaluadora califique varias veces a la misma candidata o que se dupliquen registros.
- **Auditoría continua**: Registro histórico de cada acción realizada dentro de la app (inicios de sesión, registros, evaluaciones y cambios de estado).

---

## ✨ Características Principales

### 🔐 1. Gestión de Sesión y Autenticación por Roles
- Soporte para dos roles principales: **Evaluadora** y **Dirección**.
- Adapta el flujo de navegación según el rol del usuario (las evaluadoras van a la lista de candidatas; la dirección al ranking consolidado).
- Trazabilidad automática de eventos de inicio y cierre de sesión.

### 📝 2. Registro de Candidatas con Validación Estricta
- Formulario interactivo con feedback inmediato:
  - **Nombre completo**: Mínimo 3 caracteres.
  - **Correo electrónico**: Formato válido restringido a dominios autorizados (`@ejemplo.cl`, `@example.com`, `@test.cl`).
  - **Teléfono chileno**: Formato estandarizado (`+56 9 XXXX XXXX`).
  - **Área de especialización**: Ciberseguridad, Desarrollo de Software, Infraestructura TI, Ciencia de Datos, etc.
  - **Experiencia**: Rango entre 0 y 50 años.
  - **Resumen profesional**: Entre 30 y 500 caracteres.
  - **Enlaces externos**: Validación de perfil de LinkedIn y nombre/ruta del CV adjunto.

### 🔍 3. Búsqueda Dinámica y Badges de Estado
- Lista filtrable en tiempo real por nombre de la candidata o área de especialidad.
- Indicadores visuales de estado mediante badges de colores (`EstadoBadge`):
  - 🟣 **Postulada**
  - 🟡 **En Evaluación**
  - ⚪ **Evaluada**
  - 🟢 **Seleccionada**
  - 🔴 **No Seleccionada**

### 📊 4. Matriz de Evaluación Ponderada
La API entrega cinco criterios ponderados para cada evaluación.

| # | Criterio | Peso (%) |
|---|---|:---:|
| 1 | **Experiencia Técnica y Trayectoria** | 25% |
| 2 | **Conocimientos en Ciberseguridad / TI** | 20% |
| 3 | **Habilidades Blandas y Liderazgo** | 20% |
| 4 | **Potencial de Crecimiento e Impacto** | 20% |
| 5 | **Calidad de la Postulación y CV** | 15% |

- **Escala de 1 a 5**: Calificación cuantitativa rápida por radio buttons.
- **Justificación obligatoria**: Exigida automáticamente si la nota es baja (1 o 2 puntos).
- **Observación general**: Comentario obligatorio de cierre (20 a 400 caracteres).
- **Actualización de estado automática**: Al registrar la primera evaluación, la candidata cambia automáticamente su estado a *En Evaluación*.

### 🥇 5. Ranking Consolidado y Categorización
- Vista exclusiva para el equipo directivo.
- Cálculo de notas en una escala de 0 a 100 puntos utilizando promedios ponderados.
- Categorización automática del resultado:
  - 🌟 **Sobresaliente**: $\ge 85.0$ pts
  - 💪 **Sólida**: $\ge 70.0$ pts
  - 📈 **En Desarrollo**: $\ge 50.0$ pts
  - ⚠️ **Insuficiente**: $< 50.0$ pts

---

## 🛠️ Arquitectura Android

El proyecto sigue una arquitectura limpia basada en **MVVM (Model-View-ViewModel)** y **Repository Pattern**, recomendada oficialmente por Android.

```
com.example.desarrollo_mobile
├── data
│   ├── local
│   │   ├── dao           # CandidataDao, CriterioDao, EvaluacionDao, HistorialDao
│   │   ├── Converters.kt # TypeConverters para enums en Room
│   │   ├── DatosSemilla.kt
│   │   └── WimDatabase.kt
│   └── repository
│       └── WimRepository.kt
├── di
│   ├── AppContainer.kt   # Inyección de dependencias manual (Container Pattern)
│   └── WimApplication.kt
├── model                 # Entidades de Room y Modelos de Dominio
├── navigation            # NavGraph y definición de Screens
├── ui
│   ├── components        # Componentes reutilizables (EstadoBadge, etc.)
│   ├── screens           # Pantallas Composable (Login, Lista, Registro, Evaluar, Ranking)
│   ├── theme             # Colores, Tipografía y Tema Material 3
│   └── viewmodel         # CandidatasViewModel, EvaluacionViewModel, SesionViewModel
└── validation            # Validadores puros (CandidataValidator, EvaluacionValidator)
```

### Stack Android

- **Lenguaje**: Kotlin 2.0.0
- **UI**: Jetpack Compose + Material Design 3
- **Navegación**: Navigation Compose 2.7.7
- **Base de Datos Local**: Room 2.6.1 (SQLite) con Coroutines y Flow
- **Concurrencia**: Kotlin Coroutines (`Dispatchers.IO`) & StateFlow / Reactive Flow
- **Patrón DI**: Manual Dependency Injection mediante `AppContainer` e integración en `Application`
- **Gestor de Dependencias**: Gradle Version Catalog (`libs.versions.toml`)

---

## 🚀 Instalación y Ejecución

### Requisitos Previos

- **Android Studio**: Ladybug (2024.2.1) o superior.
- **JDK**: JDK 21 para el daemon Gradle. El bytecode de Android apunta a Java 11.
- **Android SDK**: `minSdk 24` (Android 7.0) / `targetSdk 34` / `compileSdk 35`.

### Pasos para Ejecutar

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/diegamigo-code/desarrollo_mobile.git
   cd desarrollo_mobile
   ```

2. **Abrir en Android Studio**:
   Abre la carpeta del proyecto y espera a que Gradle sincronice las dependencias.

3. **Compilar desde la terminal (opcional)**:
   ```bash
   ./gradlew :app:assembleDebug
   ```

4. **Ejecutar la Aplicación**:
   Selecciona un emulador o dispositivo físico con Android 7.0 (API 24) o superior y presiona **Run (`Shift + F10`)**.

---

## 📝 Reglas de Negocio Destacadas

En la modalidad local con Room se aplican estas reglas.

1. **Datos Semilla**: Al crear la base de datos por primera vez, un callback de Room (`DatabaseCallback`) inserta automáticamente los 5 criterios ponderados iniciales.
2. **Consistencia Transaccional**: El repositorio guarda la evaluación y sus notas mediante escrituras sucesivas en las tablas (`evaluaciones` y `puntajes_criterio`).
3. **Auditoría Completa**: Cualquier intento fallido de registro (ej. correo repetido) o evaluación duplicada genera una entrada en la tabla `historial` con rol, usuario y detalle de la falta.

---

## 🤝 Contribución

¡Las contribuciones son bienvenidas! Si deseas proponer una mejora o corregir un problema:

1. Haz un **Fork** del proyecto.
2. Crea una rama para tu característica (`git checkout -b feature/nueva-caracteristica`).
3. Realiza tus cambios y confirma con commits descriptivos (`git commit -m 'Añade nueva característica'`).
4. Sube los cambios a tu rama (`git push origin feature/nueva-caracteristica`).
5. Abre un **Pull Request**.

---

## 📄 Licencia

Este proyecto está distribuido bajo la licencia MIT. Consulta el archivo `LICENSE` para obtener más información.

## Identidad y experiencia WIM 100

El acceso abre el panel de cada rol. Desde él, Evaluadora puede revisar candidaturas y Dirección puede consultar el ranking. Desde una candidatura se puede consultar el perfil, desplegar su historial, editar los campos del formulario y registrar una evaluación. La edición aplica las mismas validaciones del alta y actualiza la fila por ID. La preferencia Claro, Oscuro o Automático se guarda desde Perfil.

Los vectores de la W son originales. El icono adaptativo incluye capa monocromática y Android 12 o posterior muestra la marca mediante el tema de SplashScreen. Compose usa la escala de animación del sistema para el ingreso de marca, la transición de nombre entre lista y detalle, la expansión del historial y las transiciones del grafo.

La paleta, los estilos tipográficos y los valores compartidos de espaciado, forma, elevación y duración viven en el paquete UI de tema. La modalidad de demostración local conserva Room v1. La modalidad conectada valida el rol y carga los registros desde Spring Boot.

### Verificación del rediseño

Con JDK 21, SDK 35 y Gradle Wrapper 8.13, ejecutar:

```sh
./gradlew :app:assembleDebug :app:assembleRelease :app:testDebugUnitTest :app:lintDebug :app:assembleDebugAndroidTest
./gradlew :app:connectedDebugAndroidTest
```

La primera invocación se verificó con tres pruebas unitarias aprobadas. La segunda requiere un dispositivo Android disponible por ADB. Las pruebas instrumentadas compiladas comprueban el paquete y el recorrido de acceso, perfil y cierre de sesión. La fluidez, TalkBack y el resultado visual en dispositivo quedan pendientes de evaluación. La combinación Kotlin 2.0.0 y AGP 8.13.2 compiló con el catálogo actual. KAPT usa lenguaje 1.9 para sus stubs y Gradle advierte sobre incompatibilidades futuras con Gradle 9.

### Acceso de demostración

| Rol | Usuario | Contraseña |
| --- | --- | --- |
| Evaluadora | evaluadora | Rosa100! |
| Dirección | direccion | Orbita100! |

Los botones de demostración completan ambos campos. La fuente Adwaita Sans se incluye con su licencia en los assets.
