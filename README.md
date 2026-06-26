# TPO – Sistema de Tickets de Soporte con Prioridad

Trabajo Práctico Final – Algoritmos y Estructuras de Datos II / Programación II  
Curso 559569 | Docente: Matias Ruiz | Entrega: 26/06  
Alumnos: Luciana Flores - Matias Gonzalez

---

## Descripción

Sistema de gestión de tickets de soporte para un juego en línea. Los tickets son recibidos y atendidos según su nivel de urgencia, garantizando que los casos más críticos se procesen primero independientemente del orden de llegada.

El sistema clasifica los tickets en tres niveles de prioridad, determinados por el tipo de herramienta de soporte elegida:

| Prioridad | Tipo de soporte |
|-----------|----------------|
| 🔴 ALTA   | Hackeo de cuenta / Resolución de contracargos |
| 🟡 MEDIA  | Reembolso de compra / Problemas con tarjeta de regalo |
| 🟢 BAJA   | Cambiar región de cuenta / Solicitud de cambio de contraseña |

El sistema cuenta con dos flujos de uso: **Cliente** (crea y consulta sus tickets) y **Staff** (atiende tickets en orden de prioridad y los resuelve). Los datos se persisten automáticamente en un archivo `tickets.json`.

---

## Estructuras de datos utilizadas

- **TDA Cola con Prioridad** – núcleo del sistema. Implementada sobre un heap binario (`PriorityQueue` de Java). Garantiza que los tickets de mayor urgencia se atienden primero. Ante igual prioridad, se respeta el orden de llegada (FIFO por timestamp). Complejidad: O(log n) en encolar/desacolar, O(1) en consultar el próximo.
- **TDA Diccionario (HashMap)** – utilizado en tres instancias dentro de `GestorTickets`:
  - Búsqueda de tickets por ID en O(1) promedio.
  - Búsqueda del historial de tickets por nombre de usuario en O(1) promedio.
  - Validación de credenciales del staff en O(1) promedio.

---

## Temas de la cursada aplicados

1. TDA Cola con Prioridad
2. Especificación de TDAs y programación contra interfaces
3. Implementaciones dinámicas
4. Análisis de complejidad temporal y espacial
5. Comparación de estructuras y criterios de selección

---

## Estructura del proyecto

```
src/
├── main/
│   └── Main.java                    # Punto de entrada, flujo principal
├── ui/
│   └── Menu.java                    # Interfaz de usuario por consola
├── modelo/
│   └── Ticket.java                  # Modelo de datos del ticket
├── servicios/
│   └── GestorTickets.java           # Lógica de negocio y coordinación
├── tda/
│   ├── IColaPrioridad.java          # Interfaz genérica del TDA
│   └── ColaPrioridad.java           # Implementación concreta (heap binario)
├── enums/
│   └── HerramientaSoporte.java      # Enum con los 6 tipos de soporte y sus prioridades
├── estructura/
│   └── ComparadorPrioridad.java     # Comparador: prioridad numérica + orden de llegada
└── util/
    ├── Constantes.java              # Constantes del sistema
    ├── GeneradorID.java             # Generador de IDs únicos de 5 dígitos
    └── PersistenciaJSON.java        # Serialización/deserialización manual a JSON
```

---

## Cómo ejecutar

1. Clonar el repositorio
2. Importar como proyecto Java en Eclipse
3. Ejecutar `Main.java`

No requiere dependencias externas. Java 8 o superior.

---
