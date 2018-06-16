package tk.burdukowsky.beauty_api.public_api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.burdukowsky.beauty_api.company.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public")
public class PublicController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/companies-by-service-ids")
    public ResponseEntity<CustomPage<Company>> getCompaniesByServiceIds(
            @RequestParam(required = false, defaultValue = "0") long page,
            @RequestParam(required = false, defaultValue = "20") long limit,
            @RequestParam(required = false, defaultValue = "name") CompanySortField sort,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) CompanyType type,
            @RequestParam List<Long> ids) {

        StringBuilder sbQuery = new StringBuilder();

        appendSelectClauseToSelectCompanies(sbQuery);

        appendWhereClauseToSelectCompaniesByServiceIdsSql(sbQuery, type);

        appendOrderByAndLimitAndOffsetClauses(sbQuery, sort, sortDirection);

        Query query = entityManager.createNativeQuery(sbQuery.toString(), Company.class);

        setIdsAndTypeParametersToQuery(query, ids, type);

        setLimitAndOffsetParametersToQuery(query, page, limit);

        @SuppressWarnings("unchecked")
        List<Company> data = query.getResultList();

        StringBuilder sbQueryTotal = new StringBuilder();

        appendSelectClauseToSelectCompaniesCount(sbQueryTotal);

        appendWhereClauseToSelectCompaniesByServiceIdsSql(sbQueryTotal, type);

        Query queryTotal = entityManager.createNativeQuery(sbQueryTotal.toString());

        setIdsAndTypeParametersToQuery(queryTotal, ids, type);

        long totalElements = ((BigInteger) queryTotal.getSingleResult()).longValue();

        return ResponseEntity.ok()
                .body(new CustomPage<>(data, limit, totalElements, calculateTotalPages(totalElements, limit), page));
    }

    private void appendWhereClauseToSelectCompaniesByServiceIdsSql(StringBuilder sb, CompanyType type) {
        sb.append(" where cast(string_to_array(:ids, ',') as bigint []) <@ array(select cs.service_id ")
                .append(" from companies_services cs where cs.company_id = c.id) ");

        if (type != null) {
            sb.append(" and c.company_type = :companyType ");
        }
    }

    private void setIdsAndTypeParametersToQuery(Query query, List<Long> ids, CompanyType type) {
        query.setParameter("ids", ids.stream()
                .filter(Objects::nonNull)
                .map(Object::toString).collect(Collectors.joining(",")));

        if (type != null) {
            query.setParameter("companyType", type.name());
        }
    }

    @GetMapping("/companies-by-category-ids")
    public ResponseEntity<CustomPage<Company>> getCompaniesByCategoryIds(
            @RequestParam(required = false, defaultValue = "0") long page,
            @RequestParam(required = false, defaultValue = "20") long limit,
            @RequestParam(required = false, defaultValue = "name") CompanySortField sort,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) CompanyType type,
            @RequestParam List<Long> ids) {
        StringBuilder sbQuery = new StringBuilder();

        appendSelectClauseToSelectCompanies(sbQuery);

        appendWhereClauseToSelectCompaniesByCategoryIdsSql(sbQuery, type);

        appendOrderByAndLimitAndOffsetClauses(sbQuery, sort, sortDirection);

        Query query = entityManager.createNativeQuery(sbQuery.toString(), Company.class);

        setIdsAndTypeParametersToQuery(query, ids, type);

        setLimitAndOffsetParametersToQuery(query, page, limit);

        @SuppressWarnings("unchecked")
        List<Company> data = query.getResultList();

        StringBuilder sbQueryTotal = new StringBuilder();

        appendSelectClauseToSelectCompaniesCount(sbQueryTotal);

        appendWhereClauseToSelectCompaniesByCategoryIdsSql(sbQueryTotal, type);

        Query queryTotal = entityManager.createNativeQuery(sbQueryTotal.toString());

        setIdsAndTypeParametersToQuery(queryTotal, ids, type);

        long totalElements = ((BigInteger) queryTotal.getSingleResult()).longValue();

        return ResponseEntity.ok()
                .body(new CustomPage<>(data, limit, totalElements, calculateTotalPages(totalElements, limit), page));
    }

    private void appendWhereClauseToSelectCompaniesByCategoryIdsSql(StringBuilder sb, CompanyType type) {
        sb.append(" where cast(string_to_array(:ids, ',') as bigint []) <@ array(select distinct s.category_id ")
                .append(" from companies_services cs left join services s on cs.service_id = s.id ")
                .append(" where cs.company_id = c.id) ");

        if (type != null) {
            sb.append(" and c.company_type = :companyType ");
        }
    }

    private void appendOrderByAndLimitAndOffsetClauses(StringBuilder sb, CompanySortField sort, String sortDirection) {
        sb.append(" order by ")
                .append(sort.toString())
                .append(sortDirection.equalsIgnoreCase("desc") ? " desc " : " asc ")
                .append(" limit :limit offset :offset ");
    }

    private void setLimitAndOffsetParametersToQuery(Query query, long page, long limit) {
        query.setParameter("limit", limit);
        query.setParameter("offset", limit * page);
    }

    private long calculateTotalPages(long totalElements, long limit) {
        return totalElements % limit == 0 ? totalElements / limit : totalElements / limit + 1;
    }

    private void appendSelectClauseToSelectCompanies(StringBuilder sb) {
        sb.append(" select c.* from companies c ");
    }

    private void appendSelectClauseToSelectCompaniesCount(StringBuilder sb) {
        sb.append(" select count(c.id) from companies c ");
    }
}
