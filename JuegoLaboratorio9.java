import java.util.Scanner;

public class JuegoLaboratorio9 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Mapa mapa = new Mapa();
        int turno = 0;
        System.out.println("PRACTICA DE LABORATORIO 9 - EJERCITO, SOLDADO Y MAPA");
        System.out.println("Territorio generado: " + mapa.getTerritorio());
        for (Reino r : mapa.getReinos()) System.out.println(r);
        mapa.dibujar();
        while (!mapa.juegoTerminado()) {
            Reino reino = mapa.getReinos().get(turno % 2);
            System.out.println("\nTurno de " + reino.getNombre());
            mapa.mostrarEjercitos();
            System.out.println("Ingrese la coordenada del ejército que desea mover.");
            System.out.print("Fila (0-9, -1 para salir): ");
            int fila = leerEntero(teclado);
            if (fila == -1) break;
            System.out.print("Columna (0-9): ");
            int columna = leerEntero(teclado);
            System.out.println("Direcciones: 1 arriba, 2 abajo, 3 izquierda, 4 derecha, 5 arriba-izquierda, 6 arriba-derecha, 7 abajo-izquierda, 8 abajo-derecha");
            System.out.print("Dirección: ");
            int direccion = leerEntero(teclado);
            boolean correcto = mapa.moverEjercito(reino.getNombre(), fila, columna, direccion);
            if (correcto) {
                mapa.dibujar();
                turno++;
            } else {
                System.out.println("Intente nuevamente; no se cambia el turno.");
            }
        }
        if (mapa.juegoTerminado()) System.out.println("\n¡Ganó " + mapa.getGanador() + "! El otro reino se quedó sin ejércitos.");
        else System.out.println("Partida finalizada por el usuario.");
        teclado.close();
    }
    private static int leerEntero(Scanner teclado) {
        while (!teclado.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            teclado.next();
        }
        return teclado.nextInt();
    }
}
