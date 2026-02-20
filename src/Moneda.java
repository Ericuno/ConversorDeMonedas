public enum Moneda {
    ARS("Pesos Argentino"),
    BOB("Boliviano"),
    BRL("Reales Brasileño"),
    CLP("Pesos Chileno"),
    COP("Pesos Colombiano"),
    USD("Dólares Estadounidense");

    private final String nombreCompleto;

    Moneda(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public static String obtenerNombre(String codigo) {
        try {
            return Moneda.valueOf(codigo).getNombreCompleto();
        } catch (IllegalArgumentException e) {
            return codigo; // si no existe, devuelve el código
        }
    }
}

