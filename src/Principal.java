import java.util.List;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        ConexionHttp conexion = new ConexionHttp();

        List<String> monedas = List.of("ARS", "BOB", "BRL", "CLP", "COP", "USD");

        System.out.println("""
                ************************************************************
                Le damos la bienvenida al nuevo Conversor de Monedas
                Por el momento estas son las Monedas disponibles:""");
        for (String codigo : monedas) {
            System.out.println("  " + codigo + " - " + Moneda.obtenerNombre(codigo));
        }
        System.out.println("************************************************************");

        while (true) {
            System.out.println("\nEscriba la moneda origen que desea convertir (o 'salir' para terminar el proceso):" +
                    "\nLe recodamos que las monedas disponibles son: " + monedas);
            String origen = lectura.nextLine().trim().toUpperCase();
            if (origen.equalsIgnoreCase("salir")) break;
            if (!monedas.contains(origen)) {
                System.out.println("Moneda no válida. Intente con una de: " + monedas);
                continue;
            }

            System.out.println("Escriba el monto a convertir (ej: 500.25): ");
            String montoStr = lectura.nextLine().trim();
            double monto;
            try {
                monto = Double.parseDouble(montoStr);
                if (monto <= 0) {
                    System.out.println("El monto debe ser mayor que cero.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Formato de monto inválido.");
                continue;
            }

            System.out.println("Escriba la moneda destino a la cual se desea convertir " +
                    "(o 'salir' para terminar el proceso): ");
            String destino = lectura.nextLine().trim().toUpperCase();
            if (destino.equalsIgnoreCase("salir")) break;
            if (!monedas.contains(destino)) {
                System.out.println("Moneda no válida. Intente con una de: " + monedas);
                continue;
            }

            try {
                ConversionDeMoneda resultado = conexion.calcularConversion(origen, destino, monto);

                if (resultado == null) {
                    System.out.println("Error: respuesta nula de la API.");
                    continue;
                }

                if (resultado.resultado() == null || !resultado.resultado().equalsIgnoreCase("success")) {
                    System.out.println("La API devolvió un error. Verifica los parámetros.");
                    continue;
                }

                // Obtener nombres completos de las monedas
                String nombreOrigen = Moneda.obtenerNombre(origen);
                String nombreDestino = Moneda.obtenerNombre(destino);

                // Mostrar resultado con formato limpio
                Double tasaConversion = resultado.tasaDeConversion();
                Double resultadoConversion = resultado.resultadoDeConversion();

                if (resultadoConversion != null && tasaConversion != null) {
                    System.out.printf("\n%.2f %s (%s) = %.2f %s (%s)%n",
                            monto, origen, nombreOrigen, resultadoConversion, destino, nombreDestino);
                    System.out.printf("Tasa de conversión: %.5f%n\n", tasaConversion);
                } else {
                    System.out.println("No se pudo obtener los valores de conversión.");
                }

            } catch (Exception e) {
                System.out.println("Error al obtener la conversión: " + e.getMessage());
            }

            boolean continuar;
            while (true) {
                System.out.println("¿Desea realizar otra conversión? (s/n)");
                String repetir = lectura.nextLine().trim().toLowerCase();
                if (repetir.equals("s") || repetir.equals("si")) {
                    continuar = true;
                    break;
                } else if (repetir.equals("n") || repetir.equals("no")) {
                    continuar = false;
                    break;
                } else {
                    System.out.println("Opción no válida. Por favor elija una opción válida");
                }
            }

            if (!continuar) break;
        }

        lectura.close();
        System.out.println("Programa finalizado.");
    }
}