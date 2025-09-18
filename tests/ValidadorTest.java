import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Validador de CPF")
public class ValidadorTest {

    @Nested
    @DisplayName("CPFs válidos")
    class CPFsValidos {
        @Test
        @DisplayName("Deve validar CPF com máscara")
        void deveValidarCPFComMascara() {
            assertTrue(Validador.validarCPF("529.982.247-25"));
        }

        @Test
        @DisplayName("Deve validar CPF sem máscara")
        void deveValidarCPFSemMascara() {
            assertTrue(Validador.validarCPF("52998224725"));
        }

        @Test
        @DisplayName("Deve validar CPF com espaços externos")
        void deveValidarCPFComEspacos() {
            assertTrue(Validador.validarCPF(" 529.982.247-25 "));
        }
    }

    @Nested
    @DisplayName("CPFs inválidos")
    class CPFsInvalidos {
        @ParameterizedTest
        @ValueSource(strings = {
                "529.982.247-2X", // Caractere inválido
                "529-982X247.25", // Caractere inválido no meio
                "00000000000",    // Todos os dígitos iguais
                "11111111111",
                "22222222222",
                "935.411.347-8",  // 10 dígitos
                "935.411.347-800",// 12 dígitos
                "529.982.247-24", // DV incorreto
                "123.456.789-00"  // DV incorreto
        })
        @DisplayName("Deve rejeitar CPFs com formato, tamanho ou DV incorretos")
        void deveRejeitarCPFsInvalidos(String cpf) {
            assertFalse(Validador.validarCPF(cpf));
        }

        @Test
        @DisplayName("Deve rejeitar CPF nulo")
        void deveRejeitarCPFNulo() {
            assertFalse(Validador.validarCPF(null));
        }

        @Test
        @DisplayName("Deve rejeitar CPF vazio")
        void deveRejeitarCPFVazio() {
            assertFalse(Validador.validarCPF(""));
        }

        @Test
        @DisplayName("Deve rejeitar CPF apenas com espaços")
        void deveRejeitarCPFComEspacosApenas() {
            assertFalse(Validador.validarCPF("   "));
        }
    }
}
