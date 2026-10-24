# 🏆 WIM 100 — Sistema de Gestión y Evaluación de Candidatas

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin%202.0-purple.svg)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20M3-blue.svg)](https://developer.android.com/jetpack/compose)
[![Room](https://img.shields.io/badge/Database-Room%202.6.1-orange.svg)](https://developer.android.com/training/data-storage/room)

**WIM 100** es una aplicación nativa para Android desarrollada con **Kotlin** y **Jetpack Compose**. Diseñada para digitalizar, estandarizar y profesionalizar el proceso de selección de talentos femeninos en las áreas de Tecnología, Ciberseguridad e Ingeniería dentro de la iniciativa **WIM (Women in Mining / Women in Tech)**.

La plataforma permite a las evaluadoras registrar postulantes, aplicar matrices de evaluación técnica ponderadas y generar reportes consolidados en tiempo real para el equipo directivo, asegurando un proceso transparente, objetivo y trazable.

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
Evaluación basada en 5 criterios clave precargados como datos semilla en la base de datos:

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

## 🛠️ Arquitectura y Tecnologías

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

### Stack Tecnológico

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
- **JDK**: Java 11 o superior (configurado en las opciones del proyecto).
- **Android SDK**: `minSdk 24` (Android 7.0) / `targetSdk 34` / `compileSdk 35`.

### Pasos para Ejecutar

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/desarrollo_mobile.git
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

1. **Datos Semilla**: Al crear la base de datos por primera vez, un callback de Room (`DatabaseCallback`) inserta automáticamente los 5 criterios ponderados iniciales.
2. **Consistencia Transaccional**: Las evaluaciones y sus desglose de notas se guardan de forma atómica en tablas relacionadas (`evaluaciones` y `puntajes_criterio`).
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
