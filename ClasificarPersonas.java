import java.io.*;

public class ClasificarPersonas {

    private static final String ORIGEN = "muchosdatos.bin";
    private static final String MENORES = "menores.dat";
    private static final String ADULTOS = "adultos.dat";
    private static final String MAYORES = "mayores.dat";

    public static void main(String[] args) {
        int nMenores = 0;
        int nAdultos = 0;
        int nMayores = 0;

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(ORIGEN)));
             DataOutputStream outMenores = new DataOutputStream(new FileOutputStream(MENORES));
             DataOutputStream outAdultos = new DataOutputStream(new FileOutputStream(ADULTOS));
             DataOutputStream outMayores = new DataOutputStream(new FileOutputStream(MAYORES))) {

            while (true) {
                String nombre = in.readUTF();
                String apellidos = in.readUTF();
                int edad = in.readInt();
                String telefono = in.readUTF();
                String email = in.readUTF();
                String ciudad = in.readUTF();
                String nacionalidad = in.readUTF();
                String profesion = in.readUTF();

                // se decide a que fichero va segun la edad
                DataOutputStream destino;
                if (edad < 18) {
                    destino = outMenores;
                    nMenores++;
                } else if (edad > 65) {
                    destino = outMayores;
                    nMayores++;
                } else {
                    destino = outAdultos;
                    nAdultos++;
                }

                destino.writeUTF(nombre);
                destino.writeUTF(apellidos);
                destino.writeInt(edad);
                destino.writeUTF(telefono);
                destino.writeUTF(email);
                destino.writeUTF(ciudad);
                destino.writeUTF(nacionalidad);
                destino.writeUTF(profesion);
            }
        } catch (EOFException e) {
            // terminado de leer el original
        } catch (FileNotFoundException e) {
            System.out.println("No encuentro " + ORIGEN + " en la carpeta del programa");
            return;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("Reparto hecho: " + nMenores + " menores, " + nAdultos + " adultos y " + nMayores + " mayores");

        mostrar(MENORES, "MENORES DE EDAD");
        mostrar(ADULTOS, "ADULTOS");
        mostrar(MAYORES, "MAYORES DE 65");
    }

    private static void mostrar(String fichero, String titulo) {
        System.out.println("\n===== " + titulo + " (" + fichero + ") =====");
        int n = 0;
        try (DataInputStream in = new DataInputStream(new FileInputStream(fichero))) {
            while (true) {
                String nombre = in.readUTF();
                String apellidos = in.readUTF();
                int edad = in.readInt();
                String telefono = in.readUTF();
                String email = in.readUTF();
                String ciudad = in.readUTF();
                String nacionalidad = in.readUTF();
                String profesion = in.readUTF();
                n++;
                System.out.println(n + ". " + nombre.trim() + " " + apellidos + ", " + edad + " anos, "
                        + profesion + " - " + ciudad + " (" + nacionalidad.trim() + ")");
                System.out.println("   " + telefono + " / " + email);
            }
        } catch (EOFException e) {
            if (n == 0) {
                System.out.println("(vacio)");
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer " + fichero + ": " + e.getMessage());
        }
    }
}
