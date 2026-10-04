import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnalisisTemperaturas {

    public static void main(String[] args) {
        List<int[]> registros = new ArrayList<>();   // cada uno: {dia, hora, temperatura}

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("Septemp.dat")))) {
            while (true) {
                int dia = in.readInt();
                int hora = in.readInt();
                int temp = in.readInt();
                registros.add(new int[] { dia, hora, temp });
            }
        } catch (EOFException e) {

        } catch (FileNotFoundException e) {
            System.out.println("Falta Septemp.dat, genera antes el fichero con GuardarTemperaturas");
            return;
        } catch (IOException e) {
            System.out.println("Error leyendo el fichero: " + e.getMessage());
            return;
        }

        if (registros.isEmpty()) {
            System.out.println("El fichero esta vacio");
            return;
        }

        int max = registros.get(0)[2];
        int min = registros.get(0)[2];
        int suma = 0;

        for (int[] r : registros) {
            if (r[2] > max) {
                max = r[2];
            }
            if (r[2] < min) {
                min = r[2];
            }
            suma += r[2];
        }
        double media = (double) suma / registros.size();

        System.out.println("Dia " + registros.get(0)[0] + " de septiembre (" + registros.size() + " mediciones)");
        System.out.println("Temperatura maxima: " + max + " grados");
        System.out.println("Temperatura minima: " + min + " grados");
        System.out.printf("Temperatura media: %.2f grados%n", media);

        // puede haber varias horas con la misma temperatura, las pongo todas
        System.out.print("Hora mas calurosa: ");
        listarHoras(registros, max);
        System.out.print("Hora mas fria: ");
        listarHoras(registros, min);
    }

    private static void listarHoras(List<int[]> registros, int temperatura) {
        boolean primera = true;
        for (int[] r : registros) {
            if (r[2] == temperatura) {
                if (!primera) {
                    System.out.print(", ");
                }
                System.out.printf("%02d:00", r[1]);
                primera = false;
            }
        }
        System.out.println();
    }
}
