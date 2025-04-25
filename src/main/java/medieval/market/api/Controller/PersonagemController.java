package medieval.market.api.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import medieval.market.api.model.Personagem;
import medieval.market.api.repository.PersonagemRepository;

@RestController
@RequestMapping("/personagem")
public class PersonagemController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired

    private PersonagemRepository repository;

    @GetMapping
    @Cacheable("personagem")
    public List<Personagem> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(value = "personagem", allEntries = true)
    public ResponseEntity<Personagem> create(@RequestBody @Valid Personagem personagem) {
        log.info("Cadastrando o personagem " + personagem.getNome());
        repository.save(personagem);
        return ResponseEntity.status(201).body(personagem);
    }

    @GetMapping("/{id}")
    public Personagem get(@PathVariable Long id) {
        log.info("Buscando anúncio " + id);
        return getPersonagem(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "personagem", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Deletando o personagem: " + id);
        repository.delete(getPersonagem(id));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listings", allEntries = true)
    public Personagem update(@PathVariable Long id, @RequestBody @Valid Personagem personagem) {
        log.info("Atualizando anúncio: " + id);

        personagem.setId(id);
        return repository.save(personagem);
    }

    private Personagem getPersonagem(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
