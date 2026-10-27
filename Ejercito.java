import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ejercito {
    private static int siguienteId = 1;
    private final int id;
    private String nombre;
    private String reino;
    private final ArrayList<Soldado> soldados;
    private int fila;
    private int columna;

    public Ejercito(String reino, int fila, int columna, Random azar) {
        this.id = siguienteId++;
        this.reino = reino;
        this.nombre = "Ejercito" + id;
        this.fila = fila;
        this.columna = columna;
        this.soldados = new ArrayList<>();
        int cantidad = azar.nextInt(10) + 1;
        for (int i = 1; i <= cantidad; i++) {
            soldados.add(new Soldado("Soldado" + id + "_" + i,
                    azar.nextInt(5) + 1, azar.nextInt(5) + 1, azar.nextInt(5) + 1));
        }
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getReino() { return reino; }
    public int getFila() { return fila; }
    public int getColumna() { return columna; }
    public List<Soldado> getSoldados() { return soldados; }
    public int getCantidadSoldados() { return soldados.size(); }
    public int getVidaTotal() {
        int total = 0;
        for (Soldado s : soldados) total += Math.max(0, s.getVidaActual());
        return total;
    }
    public void moverA(int nuevaFila, int nuevaColumna) { fila = nuevaFila; columna = nuevaColumna; }
    public void aplicarBonusTerritorio() {
        for (Soldado s : soldados) {
            s.setNivelVida(s.getNivelVida() + 1);
            s.setVidaActual(s.getVidaActual() + 1);
        }
    }
    public String toString() {
        return nombre + " (" + reino + ") en [" + fila + "," + columna + "], soldados: " + soldados.size() + ", vida total: " + getVidaTotal();
    }
}
