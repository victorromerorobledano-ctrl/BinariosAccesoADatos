import java.io.*;

public class GuardarProductos {

    public static void main(String[] args) {
        Producto[] lista = {
            new Producto("Raton inalambrico", 19.95, 40),
            new Producto("Monitor 24 pulgadas", 129.00, 12),
            new Producto("Auriculares", 34.50, 27)
        };
        String ruta = "productos.dat";

        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ruta))) {
            for (Producto p : lista) {
                salida.writeObject(p);
            }
            System.out.println("Guardados " + lista.length + " productos en " + ruta);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
            return;
        }

        System.out.println("\nProductos del fichero:");
        int contador = 0;
        double valorTotal = 0;
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ruta))) {
            while (true) {
                Producto p = (Producto) entrada.readObject();
                contador++;
                valorTotal += p.valorEnAlmacen();
                System.out.println(contador + ") " + p);
            }
        } catch (EOFException e) {
            System.out.printf("%nTotal: %d productos, valor del almacen: %.2f euros%n", contador, valorTotal);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}
