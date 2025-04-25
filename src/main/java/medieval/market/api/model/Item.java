package medieval.market.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    public enum Tipo {
        ARMA,
        ARMADURA,
        POCAO,
        ACESSORIO
    }
    
    public enum Raridade {
        COMUM,
        RARO,
        EPICO,
        LENDARIO
    }

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

    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "personagem_id")  
    @NotNull(message = "O dono é obrigatório")
    private Personagem dono;
}

