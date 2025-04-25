package medieval.market.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Personagem {

    enum Classe {
        GUERREIRO,
        MAGO,
        ARQUEIRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "A classe é obrigatória")
    @Enumerated(EnumType.STRING)
    private Classe classe;

    @NotBlank(message = "O nível é obrigatório")
    @Size(min = 1, max = 99, message = "O nível deve ser entre 1 e 99")
    private Integer nivel;

    @NotBlank(message = "O saldo é obrigatório")
    private Integer moedas;

}
