package com.nexdom.estoque_backend.specification;

import com.nexdom.estoque_backend.model.Transaction;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

//Especificação é usada para filtrar dados em uma base de dados
public class FiltroTransaction {
	
	public static Specification<Transaction> byFilter(String searchValue) {
        return (root, query, criteriaBuilder) -> {
            // If filter is null or empty, return true for all transactions
            if (searchValue == null || searchValue.isEmpty()) {
                return criteriaBuilder.conjunction(); // Always true
            }

            String searchPattern = "%" + searchValue.toLowerCase() + "%";

            // Create a list to hold all predicates
            List<Predicate> predicates = new ArrayList<>();

            // Check transactions fields
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nota")), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("status").as(String.class)), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("tipoMovimentacao").as(String.class)), searchPattern));           

            // Safely join and check fornecedor fields using LEFT JOIN
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("fornecedor"))) {
                root.join("fornecedor", JoinType.LEFT);
            }
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("fornecedor", JoinType.LEFT).get("nomeFornecedor")), searchPattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("fornecedor", JoinType.LEFT).get("contato")), searchPattern));

            // Safely join and check product fields using LEFT JOIN
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("produto"))) {
                root.join("produto", JoinType.LEFT);
            }

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("produto", JoinType.LEFT).get("name")), searchPattern));            
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("produto", JoinType.LEFT).get("description")), searchPattern));

            // Safely join product category using LEFT JOIN
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("produto")) &&
                    root.join("produto").getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("tipoProduto"))) {
                root.join("produto", JoinType.LEFT).join("tipoProduto", JoinType.LEFT);
            }
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("produto", JoinType.LEFT)
                    .join("tipoProduto", JoinType.LEFT).get("nome")), searchPattern));

            // Combine all predicates with OR
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }


    // New method for filtering transactions by month and year
    public static Specification<Transaction> byMonthAndYear(int month, int year) {
        return (root, query, criteriaBuilder) -> {
            // Use the month and year functions on the createdAt date field
            Expression<Integer> monthExpression = criteriaBuilder.function("month", Integer.class, root.get("criadoEm"));
            Expression<Integer> yearExpression = criteriaBuilder.function("year", Integer.class, root.get("criadoEm"));

            // Create predicates for the month and year
            Predicate monthPredicate = criteriaBuilder.equal(monthExpression, month);
            Predicate yearPredicate = criteriaBuilder.equal(yearExpression, year);

            // Combine the month and year predicates
            return criteriaBuilder.and(monthPredicate, yearPredicate);
        };
    }
}
