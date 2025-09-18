public class Validador {

    /**
     * Valida um CPF, com ou sem máscara de formatação (pontos e traço).
     *
     * @param cpf O número do CPF a ser validado.
     * @return {@code true} se o CPF for válido, {@code false} caso contrário.
     */
    public static boolean validarCPF(String cpf) {
        // Um CPF não pode ser nulo ou vazio.
        if (cpf == null) {
            return false;
        }

        // Passo 1: Limpa o CPF, removendo a máscara para trabalhar só com os números.
        String cpfLimpo = removerMascara(cpf);

        // Passo 2: Verifica se o CPF tem o tamanho padrão de 11 dígitos e se contém apenas números.
        if (cpfLimpo.length() != 11 || !cpfLimpo.matches("\\d{11}")) {
            return false;
        }

        // Passo 3: CPFs com todos os dígitos repetidos (ex: "111.111.111-11") são inválidos por regra.
        if (cpfLimpo.chars().distinct().count() == 1) {
            return false;
        }

        // Passo 4: A prova final é checar se os dígitos verificadores estão corretos.
        return checarDigitos(cpfLimpo);
    }

    /**
     * Remove a formatação (pontos, traços) e espaços de um CPF.
     */
    private static String removerMascara(String cpf) {
        return cpf.trim().replaceAll("[.-]", "");
    }

    /**
     * Etapa final: Calcula os dígitos verificadores e os compara com os dígitos do CPF informado.
     */
    private static boolean checarDigitos(String cpf) {
        try {
            // Calcula o primeiro dígito verificador com base nos 9 primeiros números.
            int primeiroDigitoCalculado = calcularDV(cpf.substring(0, 9));
            // Calcula o segundo, usando os 9 primeiros números mais o primeiro dígito que acabamos de achar.
            int segundoDigitoCalculado = calcularDV(cpf.substring(0, 9) + primeiroDigitoCalculado);

            // Pega os dígitos verificadores originais do CPF.
            int primeiroDigitoOriginal = Integer.parseInt(String.valueOf(cpf.charAt(9)));
            int segundoDigitoOriginal = Integer.parseInt(String.valueOf(cpf.charAt(10)));

            // O CPF só é válido se os dígitos calculados forem iguais aos originais.
            return primeiroDigitoCalculado == primeiroDigitoOriginal && segundoDigitoCalculado == segundoDigitoOriginal;
        } catch (NumberFormatException e) {
            // Se houver erro na conversão, significa que o CPF continha caracteres inválidos.
            return false;
        }
    }

    /**
     * Executa a lógica matemática para calcular um dígito verificador (DV).
     * Este método implementa o "módulo 11".
     */
    private static int calcularDV(String trecho) {
        int soma = 0;
        // Multiplica cada dígito por um peso, em ordem decrescente (de 10 a 2 para o primeiro DV, de 11 a 2 para o segundo).
        for (int i = trecho.length() - 1, peso = 2; i >= 0; i--, peso++) {
            soma += Integer.parseInt(trecho.substring(i, i + 1)) * peso;
        }

        // Pega o resto da divisão da soma por 11.
        int resto = soma % 11;

        // Regra final: se o resto for 0 ou 1, o dígito é 0. Caso contrário, o dígito é 11 menos o resto.
        return resto < 2 ? 0 : 11 - resto;
    }
}