import java.io.*;
import java.util.Scanner;

public class RegistroVehiculos {

    private static final String FICHERO = "vehiculos.bin";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int cuantos = pedirEntero(sc, "Cuantos vehiculos vas a introducir: ", 1, 100);

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(FICHERO, true))) {
            for (int i = 1; i <= cuantos; i++) {
                System.out.println("\nVehiculo " + i + " de " + cuantos);
                String matricula = pedirTexto(sc, "  Matricula: ").toUpperCase();
                String marca = pedirTexto(sc, "  Marca: ");
                double deposito = pedirDecimal(sc, "  Tamano del deposito (litros): ");
                String modelo = pedirTexto(sc, "  Modelo: ");

                out.writeUTF(matricula);
                out.writeUTF(marca);
                out.writeDouble(deposito);
                out.writeUTF(modelo);
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo en " + FICHERO + ": " + e.getMessage());
            return;
        }

        System.out.println("\n--- Vehiculos guardados ---");
        int contador = 0;
        try (DataInputStream in = new DataInputStream(new FileInputStream(FICHERO))) {
            while (true) {
                String matricula = in.readUTF();
                String marca = in.readUTF();
                double deposito = in.readDouble();
                String modelo = in.readUTF();
                contador++;
                System.out.printf("%d) %s | %s %s | deposito: %.1f L%n", contador, matricula, marca, modelo, deposito);
            }
        } catch (EOFException e) {
            System.out.println("(" + contador + " vehiculos en total)");
        } catch (IOException e) {
            System.out.println("Error leyendo " + FICHERO + ": " + e.getMessage());
        }
    }

    private static String pedirTexto(Scanner sc, String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();
        } while (texto.isEmpty());
        return texto;
    }

    private static int pedirEntero(Scanner sc, String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= min && n <= max) {
                    return n;
                }
                System.out.println("  Valor fuera de rango (" + min + "-" + max + ")");
            } catch (NumberFormatException e) {
                System.out.println("  Numero no valido");
            }
        }
    }

    private static double pedirDecimal(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double d = Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
                if (d > 0) {
                    return d;
                }
                System.out.println("  Tiene que ser mayor que 0");
            } catch (NumberFormatException e) {
                System.out.println("  Numero no valido");
            }
        }
    }
}
