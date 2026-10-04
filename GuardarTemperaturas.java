import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuardarTemperaturas {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        int dia = 0;
        while (dia < 1 || dia > 30) {
            System.out.print("Que dia de septiembre quieres guardar (1-30): ");
            try {
                dia = Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                dia = 0;
            }
            if (dia < 1 || dia > 30) {
                System.out.println("  Dia no valido");
            }
        }


        Pattern patron = Pattern.compile("(\\d+), Hora (\\d+):\\d+, Temperatura (-?\\d+)");
        int guardadas = 0;

        try (BufferedReader lector = new BufferedReader(new InputStreamReader(
                new FileInputStream("temperaturas.txt"), StandardCharsets.UTF_8));
             DataOutputStream salida = new DataOutputStream(new FileOutputStream("Septemp.dat"))) {

            String linea;
            while ((linea = lector.readLine()) != null) {
                Matcher m = patron.matcher(linea);
                if (m.find() && Integer.parseInt(m.group(1)) == dia) {
                    int hora = Integer.parseInt(m.group(2));
                    int temperatura = Integer.parseInt(m.group(3));
                    salida.writeInt(dia);
                    salida.writeInt(hora);
                    salida.writeInt(temperatura);
                    guardadas++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra temperaturas.txt (tiene que estar junto al programa)");
            return;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if (guardadas == 0) {
            System.out.println("No habia datos para el dia " + dia);
            return;
        }
        System.out.println("Guardadas " + guardadas + " temperaturas del dia " + dia + " en Septemp.dat");


        try (DataInputStream in = new DataInputStream(new FileInputStream("Septemp.dat"))) {
            while (true) {
                int d = in.readInt();
                int h = in.readInt();
                int t = in.readInt();
                System.out.printf("  dia %02d - %02d:00 -> %d grados%n", d, h, t);
            }
        } catch (EOFException e) {
            // listo
        } catch (IOException e) {
            System.out.println("Error en la comprobacion: " + e.getMessage());
        }
    }
}
