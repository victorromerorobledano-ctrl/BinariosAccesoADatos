import java.io.*;

public class NotasAlumnos {

    public static void main(String[] args) {
        String[] nombres = { "Lucia Fernandez", "Pablo Ortega", "Irene Castro", "Dani Molina", "Nerea Vidal" };
        double[] notas = { 7.8, 5.4, 9.1, 6.25, 8.4 };

        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream("alumnos.dat"))) {
            for (int i = 0; i < nombres.length; i++) {
                salida.writeUTF(nombres[i]);
                salida.writeDouble(notas[i]);
            }
            System.out.println("Alumnos guardados en alumnos.dat");
        } catch (IOException e) {
            System.out.println("Error guardando los alumnos: " + e.getMessage());
            return;
        }

        System.out.println("\nLeyendo el fichero (mismo orden en que se guardo):");
        double suma = 0;
        int total = 0;
        String mejorAlumno = "";
        double mejorNota = -1;

        try (DataInputStream entrada = new DataInputStream(new FileInputStream("alumnos.dat"))) {
            while (true) {
                String nombre = entrada.readUTF();
                double nota = entrada.readDouble();
                total++;
                suma += nota;
                System.out.printf("%d. %-18s nota media: %.2f%n", total, nombre, nota);
                if (nota > mejorNota) {
                    mejorNota = nota;
                    mejorAlumno = nombre;
                }
            }
        } catch (EOFException e) {
            System.out.printf("%nNota media de la clase: %.2f%n", suma / total);
            System.out.println("Mejor nota: " + mejorAlumno + " (" + mejorNota + ")");
        } catch (IOException e) {
            System.out.println("Error leyendo los alumnos: " + e.getMessage());
        }
    }
}
