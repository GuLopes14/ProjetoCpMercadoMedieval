package medieval.market.api.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import medieval.market.api.model.Item;
import medieval.market.api.model.Personagem;
import medieval.market.api.model.Personagem.Classe;
import medieval.market.api.model.Item.Raridade;
import medieval.market.api.model.Item.Tipo;
import medieval.market.api.repository.ItemRepository;
import medieval.market.api.repository.PersonagemRepository;

@Component
public class DataBaseSeeder {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PersonagemRepository personagemRepository;

    @PostConstruct
    public void init() {
        // Criando personagens
        var personagens = List.of(
                Personagem.builder().nome("Aragorn").classe(Classe.GUERREIRO).nivel(42).moedas(500).build(),
                Personagem.builder().nome("Gandalf").classe(Classe.MAGO).nivel(60).moedas(1000).build(),
                Personagem.builder().nome("Legolas").classe(Classe.ARQUEIRO).nivel(35).moedas(300).build());

        personagemRepository.saveAll(personagens);

        var nomesItens = List.of("Espada Lendária", "Arco Élfico", "Cajado do Mago", "Escudo de Mithril",
                "Poção de Cura");
        var tiposItens = List.of(Tipo.ARMA, Tipo.ARMADURA, Tipo.POCAO, Tipo.ACESSORIO);
        var raridades = List.of(Raridade.COMUM, Raridade.RARO, Raridade.EPICO, Raridade.LENDARIO);
        var itens = new ArrayList<Item>();

        for (int i = 0; i < 10; i++) {
            itens.add(Item.builder()
                    .nome(nomesItens.get(new Random().nextInt(nomesItens.size())))
                    .tipo(tiposItens.get(new Random().nextInt(tiposItens.size())))
                    .raridade(raridades.get(new Random().nextInt(raridades.size())))
                    .preco(100 + new Random().nextInt(1000))
                    .dono(personagens.get(new Random().nextInt(personagens.size())))
                    .build());
        }

        itemRepository.saveAll(itens);
    }
}