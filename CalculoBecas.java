import java.io.*;

public class CalculoBecas {

    private static final String FICHERO = "datosbeca.bin";
    private static final double BASE_FIJA = 1500;
    private static final double INGRESOS_MEDIA = 12000;

    public static void main(String[] args) {
        int concedidas = 0;
        int rechazadasPorSuspensos = 0;
        double totalEuros = 0;

        System.out.println("Cuantia de las becas concedidas");
        System.out.println("-------------------------------");

        try (DataInputStream in = new DataInputStream(new FileInputStream(FICHERO))) {
            while (true) {
                String nombre = in.readUTF();
                char sexo = in.readChar();
                int edad = in.readInt();
                int suspensos = in.readInt();
                boolean residenciaFamiliar = in.readBoolean();
                double ingresos = in.readDouble();
                boolean tieneBeca = in.readBoolean();

                if (!tieneBeca) {
                    continue;
                }
                if (suspensos >= 2) {
                    rechazadasPorSuspensos++;
                    continue;
                }

                double cuantia = BASE_FIJA;

                if (ingresos <= INGRESOS_MEDIA) {
                    cuantia += 500;
                }
                if (edad < 23) {
                    cuantia += 200;
                }
                if (suspensos == 0) {
                    cuantia += 500;
                } else if (suspensos == 1) {
                    cuantia += 200;
                }
                if (!residenciaFamiliar) {
                    cuantia += 1000;
                }

                System.out.printf("%-25s %8.2f euros%n", nombre, cuantia);
                concedidas++;
                totalEuros += cuantia;
            }
        } catch (EOFException e) {
            // fin del fichero
        } catch (FileNotFoundException e) {
            System.out.println("No existe " + FICHERO + ", ejecuta antes el programa de altas.");
            return;
        } catch (IOException e) {
            System.out.println("Error leyendo el fichero: " + e.getMessage());
            return;
        }

        System.out.println("-------------------------------");
        System.out.println("Becas concedidas: " + concedidas);
        System.out.printf("Importe total: %.2f euros%n", totalEuros);
        if (rechazadasPorSuspensos > 0) {
            System.out.println("Sin beca por tener 2 o mas suspensos: " + rechazadasPorSuspensos);
        }
    }
}
