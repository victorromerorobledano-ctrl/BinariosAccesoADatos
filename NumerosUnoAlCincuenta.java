import java.io.*;

public class NumerosUnoAlCincuenta {

    public static void main(String[] args) {
        String nombreFichero = "numeros_1_50.dat";

        // escritura
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream(nombreFichero))) {
            for (int i = 1; i <= 50; i++) {
                salida.writeInt(i);
            }
            System.out.println("Escritos los numeros del 1 al 50 en " + nombreFichero);
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
            return;
        }

        // lectura
        int contador = 0;
        int suma = 0;
        try (DataInputStream entrada = new DataInputStream(new FileInputStream(nombreFichero))) {
            System.out.println("\nValores leidos:");
            while (true) {
                int valor = entrada.readInt();
                contador++;
                suma += valor;
                System.out.print(String.format("%3d", valor));
                if (contador % 10 == 0) {
                    System.out.println();
                } else {
                    System.out.print(" ");
                }
            }
        } catch (EOFException e) {
            System.out.println("\nSe han leido " + contador + " numeros, suma total = " + suma);
            System.out.println("Tamano del fichero: " + new File(nombreFichero).length() + " bytes (4 por cada int)");
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}
