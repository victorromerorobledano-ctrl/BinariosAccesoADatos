import java.io.*;
import java.util.Scanner;

public class AltaBecario {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Alta de un becario");
        String nombre = pedirTexto(sc, "Nombre y apellido: ");
        char sexo = pedirSexo(sc);
        int edad = pedirEntero(sc, "Edad (20-60): ", 20, 60);
        int suspensos = pedirEntero(sc, "Suspensos del curso anterior (0-4): ", 0, 4);
        boolean residencia = pedirSiNo(sc, "Residencia familiar (SI/NO): ");
        double ingresos = pedirDecimal(sc, "Ingresos anuales de la familia: ");
        boolean beca = pedirSiNo(sc, "Tiene beca (SI/NO): ");

        // aqui no se anade, se crea el fichero de cero con un solo becario
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("datosbeca.bin"))) {
            out.writeUTF(nombre);
            out.writeChar(sexo);
            out.writeInt(edad);
            out.writeInt(suspensos);
            out.writeBoolean(residencia);
            out.writeDouble(ingresos);
            out.writeBoolean(beca);
            System.out.println("\nDatos guardados en datosbeca.bin");
        } catch (IOException e) {
            System.out.println("No se pudo guardar: " + e.getMessage());
            return;
        }

        // lo leo otra vez para comprobar que se ha guardado bien
        try (DataInputStream in = new DataInputStream(new FileInputStream("datosbeca.bin"))) {
            System.out.println("Comprobacion:");
            System.out.println("  Nombre: " + in.readUTF());
            System.out.println("  Sexo: " + in.readChar());
            System.out.println("  Edad: " + in.readInt());
            System.out.println("  Suspensos: " + in.readInt());
            System.out.println("  Residencia familiar: " + (in.readBoolean() ? "SI" : "NO"));
            System.out.println("  Ingresos: " + in.readDouble());
            System.out.println("  Tiene beca: " + (in.readBoolean() ? "SI" : "NO"));
        } catch (IOException e) {
            System.out.println("Error en la comprobacion: " + e.getMessage());
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

    private static char pedirSexo(Scanner sc) {
        while (true) {
            System.out.print("Sexo (H/M): ");
            String s = sc.nextLine().trim().toUpperCase();
            if (s.equals("H") || s.equals("M")) {
                return s.charAt(0);
            }
            System.out.println("  Solo vale H o M");
        }
    }

    private static int pedirEntero(Scanner sc, String msg, int min, int max) {
        while (true) {
            System.out.print(msg);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= min && n <= max) {
                    return n;
                }
                System.out.println("  Fuera de rango, tiene que estar entre " + min + " y " + max);
            } catch (NumberFormatException e) {
                System.out.println("  Introduce un numero entero");
            }
        }
    }

    private static boolean pedirSiNo(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim().toUpperCase();
            if (s.equals("SI")) {
                return true;
            }
            if (s.equals("NO")) {
                return false;
            }
            System.out.println("  Responde SI o NO");
        }
    }

    private static double pedirDecimal(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);
            try {
                double d = Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
                if (d >= 0) {
                    return d;
                }
                System.out.println("  No puede ser negativo");
            } catch (NumberFormatException e) {
                System.out.println("  Numero no valido");
            }
        }
    }
}
