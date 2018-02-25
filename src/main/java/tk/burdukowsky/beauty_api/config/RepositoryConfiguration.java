package tk.burdukowsky.beauty_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import tk.burdukowsky.beauty_api.category.Category;
import tk.burdukowsky.beauty_api.company.Company;
import tk.burdukowsky.beauty_api.service.Service;
import tk.burdukowsky.beauty_api.user.ApplicationUser;
import tk.burdukowsky.beauty_api.user.Role;

@Configuration
public class RepositoryConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setPageParamName("page")
                .setLimitParamName("limit")
                .setSortParamName("sort");

        config.exposeIdsFor(Category.class);
        config.exposeIdsFor(Company.class);
        config.exposeIdsFor(Service.class);
        config.exposeIdsFor(ApplicationUser.class);
        config.exposeIdsFor(Role.class);
    }
}
