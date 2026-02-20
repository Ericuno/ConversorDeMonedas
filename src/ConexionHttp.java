import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexionHttp {

    private final String clave = "38a6b4c7b4e67a30d69a1730";

    public ConversionDeMoneda calcularConversion(String monedaBase, String monedaObjetivo, Double cantidadAConvertir) {
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/" + clave + "/pair/" + monedaBase + "/" +
                monedaObjetivo + "/" + cantidadAConvertir);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .GET()
                .build();

        try {
            HttpResponse<String> response = null;
            response = client.
                    send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), ConversionDeMoneda.class);

        } catch (Exception /*IOException | InterruptedException*/ e) {
            throw new RuntimeException("Moneda no encontrada o error en la solicitud HTTP: " + e.getMessage(), e);
        }
    }
}