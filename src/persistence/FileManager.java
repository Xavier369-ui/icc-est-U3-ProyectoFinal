package persistence;

import java.io.*;

public class FileManager {

    public static void crearDirectorio(String ruta) {

        File dir = new File(ruta);

        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void escribirArchivo(String ruta, String contenido) {

        try {
            FileWriter writer = new FileWriter(ruta);
            writer.write(contenido);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String leerArchivo(String ruta) {

        String texto = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(ruta));
            String linea;

            while ((linea = reader.readLine()) != null) {
                texto = texto + linea + "\n";
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return texto;
    }
}
