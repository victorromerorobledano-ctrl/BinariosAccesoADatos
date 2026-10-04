import java.io.*;
import java.util.Scanner;

public class AgendaPersonas {

    private static final String FICHERO = "datospersonas.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int cuantas = pedirEntero(sc, "Cuantas personas vas a guardar: ", 1, 200);

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(FICHERO))) {
            for (int i = 1; i <= cuantas; i++) {
                System.out.println("\nPersona " + i + " de " + cuantas);
                String nombre = pedirTexto(sc, "  Nombre: ");
                String apellidos = pedirTexto(sc, "  Apellidos: ");
                int edad = pedirEntero(sc, "  Edad: ", 0, 120);
                String telefono = pedirTexto(sc, "  Telefono: ");
                String email = pedirTexto(sc, "  Email: ");
                String ciudad = pedirTexto(sc, "  Ciudad de residencia: ");
                String nacionalidad = pedirTexto(sc, "  Nacionalidad: ");
                String profesion = pedirTexto(sc, "  Profesion: ");

                out.writeUTF(nombre);
                out.writeUTF(apellidos);
                out.writeInt(edad);
                out.writeUTF(telefono);
                out.writeUTF(email);
                out.writeUTF(ciudad);
                out.writeUTF(nacionalidad);
                out.writeUTF(profesion);
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());
            return;
        }

        System.out.println("\n===== Contenido de " + FICHERO + " =====");
        int contador = 0;
        try (DataInputStream in = new DataInputStream(new FileInputStream(FICHERO))) {
            while (true) {
                String nombre = in.readUTF();
                String apellidos = in.readUTF();
                int edad = in.readInt();
                String telefono = in.readUTF();
                String email = in.readUTF();
                String ciudad = in.readUTF();
                String nacionalidad = in.readUTF();
                String profesion = in.readUTF();
                contador++;

                System.out.println(contador + ". " + nombre + " " + apellidos + " (" + edad + " anos)");
                System.out.println("   Tel: " + telefono + " | Email: " + email);
                System.out.println("   " + ciudad + " | " + nacionalidad + " | " + profesion);
            }
        } catch (EOFException e) {
            System.out.println("\nPersonas guardadas: " + contador);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    private static String pedirTexto(Scanner sc, String msg) {
        String t;
        do {
            System.out.print(msg);
            t = sc.nextLine().trim();
        } while (t.isEmpty());
        return t;
    }

    private static int pedirEntero(Scanner sc, String msg, int min, int max) {
        while (true) {
            System.out.print(msg);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= min && n <= max) {
                    return n;
                }
                System.out.println("  Debe estar entre " + min + " y " + max);
            } catch (NumberFormatException e) {
                System.out.println("  Numero no valido");
            }
        }
    }
}
