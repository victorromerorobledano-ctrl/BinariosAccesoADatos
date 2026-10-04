import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class NumerosAleatorios {

    private static final String FICHERO = "num_aleat.bin";
    private static final int LIMITE = 1000000;

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        int cantidad = pedirEntero(teclado, "Cuantos numeros quieres guardar (1-" + LIMITE + "): ", 1, LIMITE);
        int desde = pedirEntero(teclado, "Rango, limite inferior: ", 1, LIMITE);
        int hasta = pedirEntero(teclado, "Rango, limite superior: ", desde, LIMITE);

        // el true del FileOutputStream hace que se escriba al final sin borrar nada
        try (DataOutputStream salida = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FICHERO, true)))) {
            for (int i = 0; i < cantidad; i++) {
                int n = desde + random.nextInt(hasta - desde + 1);
                salida.writeInt(n);
            }
            System.out.println("Guardados " + cantidad + " numeros nuevos entre " + desde + " y " + hasta);
        } catch (IOException e) {
            System.out.println("No se ha podido escribir en el fichero: " + e.getMessage());
            return;
        }

        mostrarFichero();
    }

    private static void mostrarFichero() {
        int total = 0;
        System.out.println("\nContenido de " + FICHERO + ":");
        try (DataInputStream entrada = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FICHERO)))) {
            while (true) {
                int n = entrada.readInt();
                System.out.print(n + "\t");
                total++;
                if (total % 10 == 0) {
                    System.out.println();
                }
            }
        } catch (EOFException e) {

        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        System.out.println("\nTotal de numeros guardados: " + total);
    }

    private static int pedirEntero(Scanner sc, String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= min && n <= max) {
                    return n;
                }
                System.out.println("  Debe estar entre " + min + " y " + max);
            } catch (NumberFormatException e) {
                System.out.println("  Eso no es un numero entero");
            }
        }
    }
}
