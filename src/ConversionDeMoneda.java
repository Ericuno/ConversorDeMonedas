import com.google.gson.annotations.SerializedName;

public record ConversionDeMoneda(
    @SerializedName("conversion_rate")
    Double tasaDeConversion,
    @SerializedName("conversion_result")
    Double resultadoDeConversion,
    @SerializedName("result")
    String resultado
) {
}
