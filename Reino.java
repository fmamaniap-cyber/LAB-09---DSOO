import java.util.ArrayList;
import java.util.List;

public class Reino {
    private final String nombre;
    private final ArrayList<Ejercito> ejercitos;

    public Reino(String nombre) {
        this.nombre = nombre;
        this.ejercitos = new ArrayList<>();
    }
    public String getNombre() { return nombre; }
    public List<Ejercito> getEjercitos() { return ejercitos; }
    public void agregarEjercito(Ejercito ejercito) { ejercitos.add(ejercito); }
    public void eliminarEjercito(Ejercito ejercito) { ejercitos.remove(ejercito); }
    public String toString() { return nombre + " (" + ejercitos.size() + " ejércitos)"; }
}
