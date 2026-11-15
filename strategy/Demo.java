package strategy;

import java.time.LocalDate;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        LocalDate hoy = LocalDate.now();
        List<Lote> lotes = List.of(
            new Lote("L-A", 30, hoy.minusDays(10), hoy.plusDays(40)),
            new Lote("L-B", 30, hoy.minusDays(5),  hoy.plusDays(15)),
            new Lote("L-C", 30, hoy.minusDays(1),  hoy.plusDays(90)));

        System.out.println("=== Picking: misma peticion (40 u.), distinta politica ===");
        Picking picking = new Picking(new FefoPolicy());
        for (PickingPolicy p : new PickingPolicy[]{new FefoPolicy(), new FifoPolicy(), new LifoPolicy()}) {
            picking.setPolitica(p); // cambio de algoritmo en tiempo de ejecucion
            System.out.println(p.nombre() + " -> " + picking.seleccionar(lotes, 40));
        }

        System.out.println("\n=== Reposicion: stock 15, demanda anual 1200 ===");
        ReplenishmentPolicy[] politicas = {
            new ReorderPointPolicy(20, 100), new MinMaxPolicy(20, 200), new EoqPolicy(50, 2)};
        for (ReplenishmentPolicy r : politicas)
            System.out.println(r.nombre() + " -> pedir " + r.cantidadAPedir(15, 1200) + " u.");
    }
}
