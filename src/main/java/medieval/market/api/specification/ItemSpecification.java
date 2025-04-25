package medieval.market.api.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import medieval.market.api.controller.ItemController.ItemFilter;
import medieval.market.api.model.Item;

public class ItemSpecification {

    public static Specification<Item> withFilters(ItemFilter filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            
            if (filters.nome() != null && !filters.nome().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nome")), 
                    "%" + filters.nome().toLowerCase() + "%"));
            }

            
            if (filters.tipo() != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipo"), filters.tipo()));
            }

            
            if (filters.raridade() != null) {
                predicates.add(criteriaBuilder.equal(root.get("raridade"), filters.raridade()));
            }

            
            if (filters.precoMinimo() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), filters.precoMinimo()));
            }

            
            if (filters.precoMaximo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("preco"), filters.precoMaximo()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}