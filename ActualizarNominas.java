import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class ActualizarNominas {

    private static final String FICHERO = "nominas.bin";
    private static final String COPIA = "nominas_original.bin";
    private static final String TEMPORAL = "nominas_nuevo.tmp";

    public static void main(String[] args) {
        File fichero = new File(FICHERO);
        if (!fichero.exists()) {
            System.out.println("No existe " + FICHERO);
            return;
        }

        // la primera vez guardo una copia del original; siempre se calcula a partir de ella
        // asi si ejecuto el programa varias veces no se aplican los cambios encima de los anteriores
        File copia = new File(COPIA);
        try {
            if (!copia.exists()) {
                Files.copy(fichero.toPath(), copia.toPath());
                System.out.println("Copia de seguridad creada: " + COPIA);
            }
        } catch (IOException e) {
            System.out.println("No se pudo hacer la copia: " + e.getMessage());
            return;
        }

        int dadosDeBaja = 0;
        List<String> despedidos = new ArrayList<>();

        try (DataInputStream in = new DataInputStream(new FileInputStream(copia));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(TEMPORAL))) {

            while (true) {
                String nombre = in.readUTF();
                int diasBaja = in.readInt();
                double nomina = in.readDouble();

                double nueva;
                if (diasBaja == 0) {
                    nueva = nomina * 1.05;
                } else if (diasBaja <= 3) {
                    nueva = nomina;
                } else if (diasBaja <= 10) {
                    nueva = nomina * 0.90;
                } else {
                    // mas de 10 dias: no se copia al fichero nuevo
                    dadosDeBaja++;
                    despedidos.add(nombre);
                    continue;
                }

                nueva = Math.round(nueva * 100) / 100.0;
                out.writeUTF(nombre);
                out.writeInt(diasBaja);
                out.writeDouble(nueva);
            }
        } catch (EOFException e) {
            // fin del original
        } catch (IOException e) {
            System.out.println("Error actualizando: " + e.getMessage());
            return;
        }

        try {
            Files.move(new File(TEMPORAL).toPath(), fichero.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("No se pudo sustituir el fichero: " + e.getMessage());
            return;
        }

        System.out.println("\n===== " + FICHERO + " actualizado =====");
        System.out.printf("%-12s %-6s %10s%n", "Empleado", "Bajas", "Nomina");
        try (DataInputStream in = new DataInputStream(new FileInputStream(fichero))) {
            while (true) {
                String nombre = in.readUTF();
                int dias = in.readInt();
                double nomina = in.readDouble();
                System.out.printf("%-12s %-6d %10.2f%n", nombre, dias, nomina);
            }
        } catch (EOFException e) {
            // fin
        } catch (IOException e) {
            System.out.println("Error mostrando el fichero: " + e.getMessage());
        }

        System.out.println("\nEmpleados dados de baja (mas de 10 dias): " + dadosDeBaja);
        if (!despedidos.isEmpty()) {
            System.out.println("Eliminados: " + String.join(", ", despedidos));
        }
    }
}
