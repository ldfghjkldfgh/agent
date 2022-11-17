package ru.mirea.agency.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mirea.agency.db.model.Plan;
import ru.mirea.agency.db.model.PropertyType;
import ru.mirea.agency.db.model.Role;
import ru.mirea.agency.db.model.User;
import ru.mirea.agency.db.repository.PlanRepository;
import ru.mirea.agency.db.repository.PropertyTypeRepository;
import ru.mirea.agency.db.repository.RoleRepository;
import ru.mirea.agency.db.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationConfiguration {

    private String adminName;

    private String adminPassword;

    private ApplicationContext applicationContext;

    @Bean
    public ApplicationRunner dataLoader(PlanRepository planRepository,
                                        PropertyTypeRepository propertyTypeRepository,
                                        RoleRepository roleRepository,
                                        UserRepository userRepository,
                                        PasswordEncoder passwordEncoder) {
        return args -> {
            List<Plan> plans = Arrays.asList(
                    new Plan("Посуточно"),
                    new Plan("На длительный срок")
            );
            for (Plan plan : plans) {
                planRepository.save(plan);
            }

            List<PropertyType> propertyTypes = Arrays.asList(
                    new PropertyType("Комната"),
                    new PropertyType("Квартира"),
                    new PropertyType("Дом"),
                    new PropertyType("Земельный участок"),
                    new PropertyType("Гараж"),
                    new PropertyType("Коммерч. площадь")
            );
            for (PropertyType type : propertyTypes) {
                propertyTypeRepository.save(type);
            }

            List<Role> roles = Arrays.asList(
                    new Role("ROLE_USER"),
                    new Role("ROLE_ADMIN")
            );
            for (Role role : roles) {
                roleRepository.save(role);
            }

            User user = new User(adminName, "root", passwordEncoder.encode(adminPassword));
            user.addRole(roles.get(0));
            user.addRole(roles.get(1));
            userRepository.save(user);
        };
    }
}
