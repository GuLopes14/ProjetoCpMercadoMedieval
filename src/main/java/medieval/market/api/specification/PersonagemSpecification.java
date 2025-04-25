package medieval.market.api.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import medieval.market.api.controller.PersonagemController.PersonagemFilter;
import medieval.market.api.model.Personagem;

public class PersonagemSpecification {

    public static Specification<Personagem> withFilters(PersonagemFilter filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (filters.nome() != null && !filters.nome().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nome")), 
                    "%" + filters.nome().toLowerCase() + "%"));
            }
            
            if (filters.classe() != null) {
                predicates.add(criteriaBuilder.equal(root.get("classe"), filters.classe()));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}