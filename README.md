# TPO — Sistema de Reportes de Jugadores con Prioridad

Trabajo Práctico Final — Algoritmos y Estructuras de Datos II / Programación II  
Curso 559569 | Docente: Matias Ruiz | Entrega: 26/06
Alumnos: Luciana Flores - Matias Gonzalez
---

## Descripción

Sistema de gestión de reportes dentro de un juego en línea. Los reportes son recibidos y atendidos según su nivel de urgencia, garantizando que los casos más críticos se procesen primero independientemente del orden de llegada.

El sistema clasifica los reportes en tres niveles de prioridad:

| Prioridad | Tipo de reporte |
|-----------|----------------|
| 🔴 URGENTE | Problemas con pagos o la tienda |
| 🟡 MEDIA   | Bugs del juego |
| 🟢 LEVE    | Reportes de mal comportamiento entre jugadores |

---

## Estructuras de datos utilizadas

- **TDA Cola con Prioridad** — núcleo del sistema. Garantiza que los reportes urgentes se atienden primero. Ante igual prioridad, se respeta el orden de llegada (FIFO por timestamp).
- **TDA Diccionario** — permite buscar jugadores por id en O(1) promedio y consultar su historial de reportes.

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
│   └── Main.java               # Punto de entrada, flujo principal
├── modelo/
│   ├── Jugador.java
│   ├── Prioridad.java          # Enum: URGENTE, MEDIA, LEVE
│   └── Reporte.java
└── tda/
    ├── IColaPrioridad.java     # Interfaz del TDA
    └── ColaPrioridad.java      # Implementación concreta
```

---

## Cómo ejecutar

1. Clonar el repositorio
2. Importar como proyecto Java en Eclipse (`File → Import → General → Existing Projects`)
3. Ejecutar `Main.java`

No requiere dependencias externas. Java 8 o superior.

---

## Integrantes

- Tessera ([@Tessera04](https://github.com/Tessera04))
- [nombre de tu compañera]
