package medieval.market.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

enum Tipo {
    ARMA,
    ARMADURA,
    POCAO,
    ACESSORIO
}

enum Raridade {
    COMUM,
    RARO,
    EPICO,
    LENDARIO
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O tipo é obrigatório")
    private Tipo tipo;

    @NotBlank(message = "A raridade é obrigatória")
    @Enumerated
    private Raridade raridade;

    @NotBlank(message = "O preço é obrigatório")
    private Integer preco;

    private Personagem dono;
}
