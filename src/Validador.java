/**
 * Oferece um método para validar CPFs.
 */
public class Validador {

    /**
     * Valida um CPF, com ou sem máscara.
     *
     * @param cpf O CPF a ser validado.
     * @return {@code true} se o CPF for válido.
     */
    public static boolean validarCPF(String cpf) {
        if (cpf == null) return false;
        String limpo = removerMascara(cpf);
        if (limpo.length() != 11 || !limpo.matches("\\d{11}")) return false;
        if (limpo.chars().distinct().count() == 1) return false;
        return checarDigitos(limpo);
    }

    private static String removerMascara(String cpf) {
        return cpf.trim().replaceAll("[.-]", "");
    }

    private static boolean checarDigitos(String cpf) {
        try {
            int d1 = calcularDV(cpf.substring(0, 9));
            int d2 = calcularDV(cpf.substring(0, 9) + d1);
            return d1 == Integer.parseInt(String.valueOf(cpf.charAt(9)))
                && d2 == Integer.parseInt(String.valueOf(cpf.charAt(10)));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int calcularDV(String trecho) {
        int soma = 0;
        for (int i = trecho.length() - 1, peso = 2; i >= 0; i--, peso++) {
            soma += Integer.parseInt(trecho.substring(i, i + 1)) * peso;
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}
