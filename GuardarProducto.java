import java.io.*;

public class GuardarProducto {

    public static void main(String[] args) {
        Producto teclado = new Producto("Teclado mecanico", 54.90, 23);
        String ruta = "producto.dat";

        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ruta))) {
            salida.writeObject(teclado);
            System.out.println("Objeto guardado: " + teclado);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el producto: " + e.getMessage());
            return;
        }

        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ruta))) {
            Producto leido = (Producto) entrada.readObject();
            System.out.println("\nObjeto recuperado del fichero:");
            System.out.println("Nombre: " + leido.getNombre());
            System.out.println("Precio: " + leido.getPrecio());
            System.out.println("Stock: " + leido.getStock());
            System.out.println("Valor total en almacen: " + leido.valorEnAlmacen());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudo recuperar el producto: " + e.getMessage());
        }
    }
}
