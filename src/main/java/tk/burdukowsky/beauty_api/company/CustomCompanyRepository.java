package tk.burdukowsky.beauty_api.company;

import java.util.List;

public interface CustomCompanyRepository {

    CustomPage<Company> findAllByTypeAndProvidableServiceIds(CompanyType type,
                                                             List<Long> ids,
                                                             long page,
                                                             long limit,
                                                             CompanySortField sort,
                                                             String sortDirection);

    CustomPage<Company> findAllByTypeAndCategoryIds(CompanyType type,
                                                    List<Long> ids,
                                                    long page,
                                                    long limit,
                                                    CompanySortField sort,
                                                    String sortDirection);
}
