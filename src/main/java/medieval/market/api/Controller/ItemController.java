package medieval.market.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import medieval.market.api.model.Item;
import medieval.market.api.repository.ItemRepository;
import medieval.market.api.specification.ItemSpecification;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ItemRepository repository;

    public record ItemFilter(String nome, Item.Tipo tipo, Item.Raridade raridade, Integer precoMinimo, Integer precoMaximo) {
    }

    @GetMapping
    @Cacheable("item")
    public Page<Item> index(ItemFilter filters,
            @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable) {
        log.info("Filtrando itens por nome: {}, tipo: {}, raridade: {}, preço mínimo: {}, preço máximo: {}",
                filters.nome(), filters.tipo(), filters.raridade(), filters.precoMinimo(), filters.precoMaximo());
        var specification = ItemSpecification.withFilters(filters);
        return repository.findAll(specification, pageable);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(value = "item", allEntries = true)
    public ResponseEntity<Item> create(@RequestBody @Valid Item item) {
        log.info("Cadastrando o item " + item.getNome());
        repository.save(item);
        return ResponseEntity.status(201).body(item);
    }

    @GetMapping("/{id}")
    public Item get(@PathVariable Long id) {
        log.info("Buscando item " + id);
        return getItem(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "item", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Deletando o item: " + id);
        repository.delete(getItem(id));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "item", allEntries = true)
    public Item update(@PathVariable Long id, @RequestBody @Valid Item item) {
        log.info("Atualizando item: " + id);

        item.setId(id);
        return repository.save(item);
    }

    private Item getItem(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}