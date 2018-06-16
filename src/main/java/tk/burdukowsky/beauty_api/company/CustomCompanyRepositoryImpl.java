package tk.burdukowsky.beauty_api.company;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class CustomCompanyRepositoryImpl implements CustomCompanyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CustomPage<Company> findAllByTypeAndProvidableServiceIds(CompanyType type,
                                                                    List<Long> ids,
                                                                    long page,
                                                                    long limit,
                                                                    CompanySortField sort,
                                                                    String sortDirection) {
        StringBuilder sbQuery = new StringBuilder();

        appendSelectClauseToSelectCompanies(sbQuery);

        appendWhereClauseToSelectCompaniesByServiceIdsSql(sbQuery, ids, type);

        appendOrderByAndLimitAndOffsetClauses(sbQuery, sort, sortDirection);

        Query query = entityManager.createNativeQuery(sbQuery.toString(), Company.class);

        setTypeParameterToQuery(query, type);

        setLimitAndOffsetParametersToQuery(query, page, limit);

        @SuppressWarnings("unchecked")
        List<Company> data = query.getResultList();

        StringBuilder sbQueryTotal = new StringBuilder();

        appendSelectClauseToSelectCompaniesCount(sbQueryTotal);

        appendWhereClauseToSelectCompaniesByServiceIdsSql(sbQueryTotal, ids, type);

        Query queryTotal = entityManager.createNativeQuery(sbQueryTotal.toString());

        setTypeParameterToQuery(queryTotal, type);

        long totalElements = ((BigInteger) queryTotal.getSingleResult()).longValue();

        return new CustomPage<>(data, limit, totalElements, calculateTotalPages(totalElements, limit), page);
    }

    @Override
    public CustomPage<Company> findAllByTypeAndCategoryIds(CompanyType type,
                                                           List<Long> ids,
                                                           long page,
                                                           long limit,
                                                           CompanySortField sort,
                                                           String sortDirection) {
        StringBuilder sbQuery = new StringBuilder();

        appendSelectClauseToSelectCompanies(sbQuery);

        appendWhereClauseToSelectCompaniesByCategoryIdsSql(sbQuery, ids, type);

        appendOrderByAndLimitAndOffsetClauses(sbQuery, sort, sortDirection);

        Query query = entityManager.createNativeQuery(sbQuery.toString(), Company.class);

        setTypeParameterToQuery(query, type);

        setLimitAndOffsetParametersToQuery(query, page, limit);

        @SuppressWarnings("unchecked")
        List<Company> data = query.getResultList();

        StringBuilder sbQueryTotal = new StringBuilder();

        appendSelectClauseToSelectCompaniesCount(sbQueryTotal);

        appendWhereClauseToSelectCompaniesByCategoryIdsSql(sbQueryTotal, ids, type);

        Query queryTotal = entityManager.createNativeQuery(sbQueryTotal.toString());

        setTypeParameterToQuery(queryTotal, type);

        long totalElements = ((BigInteger) queryTotal.getSingleResult()).longValue();

        return new CustomPage<>(data, limit, totalElements, calculateTotalPages(totalElements, limit), page);
    }

    private void appendSelectClauseToSelectCompanies(StringBuilder sb) {
        sb.append(" select c.* from companies c ");
    }

    private void appendWhereClauseToSelectCompaniesByServiceIdsSql(StringBuilder sb, List<Long> ids, CompanyType type) {
        sb.append(" where cast(array [ ")
                .append(joinIdList(ids))
                .append(" ] as bigint []) <@ array(select cs.service_id ")
                .append(" from companies_services cs where cs.company_id = c.id) ");

        if (type != null) {
            sb.append(" and c.company_type = :companyType ");
        }
    }

    private void appendWhereClauseToSelectCompaniesByCategoryIdsSql(StringBuilder sb,
                                                                    List<Long> ids,
                                                                    CompanyType type) {
        sb.append(" where cast(array [ ")
                .append(joinIdList(ids))
                .append(" ] as bigint []) <@ array(select distinct s.category_id ")
                .append(" from companies_services cs left join services s on cs.service_id = s.id ")
                .append(" where cs.company_id = c.id) ");

        if (type != null) {
            sb.append(" and c.company_type = :companyType ");
        }
    }

    private String joinIdList(List<Long> ids) {
        return ids.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.joining(","));
    }

    private void appendOrderByAndLimitAndOffsetClauses(StringBuilder sb, CompanySortField sort, String sortDirection) {
        sb.append(" order by ")
                .append(sort.toString())
                .append(sortDirection.equalsIgnoreCase("desc") ? " desc " : " asc ")
                .append(" limit :limit offset :offset ");
    }

    private void setTypeParameterToQuery(Query query, CompanyType type) {
        if (type != null) {
            query.setParameter("companyType", type.name());
        }
    }

    private void setLimitAndOffsetParametersToQuery(Query query, long page, long limit) {
        query.setParameter("limit", limit);
        query.setParameter("offset", limit * page);
    }

    private void appendSelectClauseToSelectCompaniesCount(StringBuilder sb) {
        sb.append(" select count(c.id) from companies c ");
    }

    private long calculateTotalPages(long totalElements, long limit) {
        return totalElements % limit == 0 ? totalElements / limit : totalElements / limit + 1;
    }
}
