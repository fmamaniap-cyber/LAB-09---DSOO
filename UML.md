# Diagrama UML (Laboratorio 9)

```mermaid
classDiagram
 direction TB
 class Soldado {
   -String nombre
   -int nivelAtaque
   -int nivelDefensa
   -int nivelVida
   -int vidaActual
   -int velocidad
   -String actitud
   -boolean vive
   -int totalCreados$ 
   +atacar() void
   +defender() void
   +avanzar() void
   +retroceder() void
   +huir() void
   +serAtacado(danio) void
   +morir() void
   +setVidaActual(vida) void
   +setNivelVida(valor) void
 }
 class Ejercito {
   -int id
   -String nombre
   -String reino
   -ArrayList~Soldado~ soldados
   -int fila
   -int columna
   +getVidaTotal() int
   +moverA(fila,columna) void
   +aplicarBonusTerritorio() void
 }
 class Reino {
   -String nombre
   -ArrayList~Ejercito~ ejercitos
   +agregarEjercito(ejercito) void
   +eliminarEjercito(ejercito) void
 }
 class Mapa {
   +int TAMANO$
   -String territorio
   -Ejercito[][] tablero
   -ArrayList~Reino~ reinos
   +dibujar() void
   +mostrarEjercitos() void
   +moverEjercito(reino,fila,columna,direccion) boolean
   +juegoTerminado() boolean
   +getGanador() String
 }
 class JuegoLaboratorio9 {
   +main(args) void
 }
 Reino o-- "1..10" Ejercito : agrupa
 Ejercito *-- "1..10" Soldado : contiene
 Mapa o-- "2" Reino : administra
 Mapa *-- "100 casillas" Ejercito : posiciona
 JuegoLaboratorio9 --> Mapa : utiliza
