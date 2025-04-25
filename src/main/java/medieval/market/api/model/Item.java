package medieval.market.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "O tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @NotNull(message = "A raridade é obrigatória")
    @Enumerated(EnumType.STRING)
    private Raridade raridade;

    @NotNull(message = "O preço é obrigatório")
    private Integer preco;

    @NotNull(message = "O dono é obrigatório")
    private Personagem dono;
}

