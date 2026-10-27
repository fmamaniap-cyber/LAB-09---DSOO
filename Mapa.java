import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mapa {
    public static final int TAMANO = 10;
    private final String territorio;
    private final Ejercito[][] tablero;
    private final ArrayList<Reino> reinos;
    private final Random azar;

    public Mapa() {
        azar = new Random();
        String[] territorios = {"bosque", "campo abierto", "montaña", "desierto", "playa"};
        territorio = territorios[azar.nextInt(territorios.length)];
        tablero = new Ejercito[TAMANO][TAMANO];
        reinos = new ArrayList<>();
        crearReinosYEjercitos();
        aplicarBonusInicial();
    }

    private void crearReinosYEjercitos() {
        String[] nombres = {"Inglaterra", "Francia", "Castilla-Aragón", "Moros", "Sacro Imperio Romano-Germánico"};
        int primero = azar.nextInt(nombres.length);
        int segundo;
        do { segundo = azar.nextInt(nombres.length); } while (segundo == primero);
        Reino r1 = new Reino(nombres[primero]);
        Reino r2 = new Reino(nombres[segundo]);
        reinos.add(r1); reinos.add(r2);
        generarEjercitos(r1);
        generarEjercitos(r2);
    }

    private void generarEjercitos(Reino reino) {
        int cantidad = azar.nextInt(10) + 1;
        for (int i = 0; i < cantidad; i++) {
            int f, c;
            do { f = azar.nextInt(TAMANO); c = azar.nextInt(TAMANO); }
            while (tablero[f][c] != null);
            Ejercito e = new Ejercito(reino.getNombre(), f, c, azar);
            tablero[f][c] = e;
            reino.agregarEjercito(e);
        }
    }

    private void aplicarBonusInicial() {
        for (Reino reino : reinos) {
            if (tieneBonus(reino.getNombre(), territorio)) {
                for (Ejercito e : reino.getEjercitos()) e.aplicarBonusTerritorio();
                System.out.println("Bonus de territorio: " + reino.getNombre() + " recibe +1 de vida por soldado.");
            }
        }
    }

    private boolean tieneBonus(String reino, String tipo) {
        switch (reino) {
            case "Inglaterra": return tipo.equals("bosque");
            case "Francia": return tipo.equals("campo abierto");
            case "Castilla-Aragón": return tipo.equals("montaña");
            case "Moros": return tipo.equals("desierto");
            case "Sacro Imperio Romano-Germánico": return tipo.equals("bosque") || tipo.equals("playa") || tipo.equals("campo abierto");
            default: return false;
        }
    }

    public String getTerritorio() { return territorio; }
    public List<Reino> getReinos() { return reinos; }
    public Ejercito getEjercito(int fila, int columna) {
        if (!coordenadaValida(fila, columna)) return null;
        return tablero[fila][columna];
    }
    public boolean coordenadaValida(int fila, int columna) {
        return fila >= 0 && fila < TAMANO && columna >= 0 && columna < TAMANO;
    }
    public void dibujar() {
        System.out.println("\nMAPA - Territorio: " + territorio);
        for (int f = 0; f < TAMANO; f++) {
            for (int c = 0; c < TAMANO; c++) {
                if (tablero[f][c] == null) System.out.print("| . ");
                else System.out.print("|" + (reinos.get(0).getNombre().equals(tablero[f][c].getReino()) ? " R1" : " R2"));
            }
            System.out.println("|");
        }
        System.out.println("R1 = " + reinos.get(0).getNombre() + " | R2 = " + reinos.get(1).getNombre());
    }
    public void mostrarEjercitos() {
        for (Reino r : reinos) {
            System.out.println("\n" + r.getNombre() + ":");
            for (Ejercito e : r.getEjercitos()) System.out.println("  " + e);
        }
    }
    public boolean moverEjercito(String reinoActual, int fila, int columna, int direccion) {
        if (!coordenadaValida(fila, columna)) { System.out.println("Coordenada fuera del mapa."); return false; }
        Ejercito e = tablero[fila][columna];
        if (e == null) { System.out.println("No hay un ejército en esa coordenada."); return false; }
        if (!e.getReino().equals(reinoActual)) { System.out.println("Ese ejército no pertenece a " + reinoActual + "."); return false; }
        int nf = fila, nc = columna;
        switch (direccion) {
            case 1: nf--; break; // arriba
            case 2: nf++; break; // abajo
            case 3: nc--; break; // izquierda
            case 4: nc++; break; // derecha
            case 5: nf--; nc--; break;
            case 6: nf--; nc++; break;
            case 7: nf++; nc--; break;
            case 8: nf++; nc++; break;
            default: System.out.println("Dirección inválida."); return false;
        }
        if (!coordenadaValida(nf, nc)) { System.out.println("Movimiento fuera del tablero."); return false; }
        Ejercito rival = tablero[nf][nc];
        if (rival != null && rival.getReino().equals(e.getReino())) {
            System.out.println("Movimiento inválido: no puede haber dos ejércitos del mismo reino en una casilla.");
            return false;
        }
        if (rival != null) resolverBatalla(e, rival, nf, nc);
        else { tablero[fila][columna] = null; tablero[nf][nc] = e; e.moverA(nf, nc); }
        return true;
    }

    private void resolverBatalla(Ejercito atacante, Ejercito defensor, int fila, int columna) {
        int vidaA = atacante.getVidaTotal();
        int vidaD = defensor.getVidaTotal();
        if (vidaA + vidaD == 0) { System.out.println("No hay vida para resolver la batalla."); return; }
        double probA = 100.0 * vidaA / (vidaA + vidaD);
        double probD = 100.0 * vidaD / (vidaA + vidaD);
        System.out.printf("\nBATALLA: %s contra %s%n", atacante.getNombre(), defensor.getNombre());
        System.out.printf("Vida total: %s = %d, %s = %d%n", atacante.getReino(), vidaA, defensor.getReino(), vidaD);
        System.out.printf("Probabilidades: %s = %.2f%%, %s = %.2f%%%n", atacante.getReino(), probA, defensor.getReino(), probD);
        boolean ganaAtacante = azar.nextDouble() * 100 < probA;
        Ejercito ganador = ganaAtacante ? atacante : defensor;
        Ejercito perdedor = ganaAtacante ? defensor : atacante;
        System.out.println("Ganó " + ganador.getReino() + " porque su probabilidad calculada fue " + String.format("%.2f%%", ganaAtacante ? probA : probD) + ".");
        Reino reinoPerdedor = buscarReino(perdedor.getReino());
        reinoPerdedor.eliminarEjercito(perdedor);
        if (ganador == atacante) {
            tablero[atacante.getFila()][atacante.getColumna()] = null;
            tablero[fila][columna] = atacante;
            atacante.moverA(fila, columna);
        } else {
            tablero[atacante.getFila()][atacante.getColumna()] = null;
            tablero[fila][columna] = defensor;
        }
        ganador.aplicarBonusTerritorio();
        System.out.println("Bono de victoria: cada soldado del ejército ganador recibe +1 de vida.");
    }
    private Reino buscarReino(String nombre) {
        for (Reino r : reinos) if (r.getNombre().equals(nombre)) return r;
        throw new IllegalStateException("Reino no encontrado: " + nombre);
    }
    public boolean juegoTerminado() { return reinos.get(0).getEjercitos().isEmpty() || reinos.get(1).getEjercitos().isEmpty(); }
    public String getGanador() {
        if (reinos.get(0).getEjercitos().isEmpty()) return reinos.get(1).getNombre();
        if (reinos.get(1).getEjercitos().isEmpty()) return reinos.get(0).getNombre();
        return "";
    }
}
