import java.io.*;
import java.util.Scanner;

public class AltaBecarios {

    private static final String FICHERO = "datosbeca.bin";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int cuantos = pedirEntero(sc, "Cuantos becarios quieres dar de alta (1-50): ", 1, 50);

        // append = true para no perder los becarios que ya estuvieran
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(FICHERO, true))) {
            for (int i = 1; i <= cuantos; i++) {
                System.out.println("\nBecario " + i + "/" + cuantos);
                String nombre = pedirTexto(sc, "  Nombre y apellido: ");
                char sexo = pedirSexo(sc);
                int edad = pedirEntero(sc, "  Edad (20-60): ", 20, 60);
                int suspensos = pedirEntero(sc, "  Suspensos curso anterior (0-4): ", 0, 4);
                boolean residencia = pedirSiNo(sc, "  Residencia familiar (SI/NO): ");
                double ingresos = pedirDecimal(sc, "  Ingresos anuales familia: ");
                boolean beca = pedirSiNo(sc, "  Tiene beca (SI/NO): ");

                out.writeUTF(nombre);
                out.writeChar(sexo);
                out.writeInt(edad);
                out.writeInt(suspensos);
                out.writeBoolean(residencia);
                out.writeDouble(ingresos);
                out.writeBoolean(beca);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
            return;
        }

        mostrarTodos();
    }

    private static void mostrarTodos() {
        System.out.println("\n===== Becarios en " + FICHERO + " =====");
        int n = 0;
        try (DataInputStream in = new DataInputStream(new FileInputStream(FICHERO))) {
            while (true) {
                String nombre = in.readUTF();
                char sexo = in.readChar();
                int edad = in.readInt();
                int suspensos = in.readInt();
                boolean residencia = in.readBoolean();
                double ingresos = in.readDouble();
                boolean beca = in.readBoolean();
                n++;

                System.out.println(n + ". " + nombre);
                System.out.println("   Sexo: " + sexo + " | Edad: " + edad + " | Suspensos: " + suspensos);
                System.out.println("   Residencia familiar: " + (residencia ? "SI" : "NO")
                        + " | Ingresos: " + ingresos + " | Beca: " + (beca ? "SI" : "NO"));
            }
        } catch (EOFException e) {
            System.out.println("\nTotal de becarios: " + n);
        } catch (IOException e) {
            System.out.println("Error leyendo el fichero: " + e.getMessage());
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
            System.out.print("  Sexo (H/M): ");
            String s = sc.nextLine().trim().toUpperCase();
            if (s.equals("H") || s.equals("M")) {
                return s.charAt(0);
            }
            System.out.println("    Solo H o M");
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
                System.out.println("    Tiene que estar entre " + min + " y " + max);
            } catch (NumberFormatException e) {
                System.out.println("    Introduce un numero entero");
            }
        }
    }

    private static boolean pedirSiNo(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim().toUpperCase();
            if (s.equals("SI")) {
                return true;
            } else if (s.equals("NO")) {
                return false;
            }
            System.out.println("    Responde SI o NO");
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
                System.out.println("    No puede ser negativo");
            } catch (NumberFormatException e) {
                System.out.println("    Numero no valido");
            }
        }
    }
}
