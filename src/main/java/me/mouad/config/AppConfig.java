package me.mouad.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import me.mouad.repositories.PatientRepository;
import me.mouad.services.PatientService;
import me.mouad.views.MainView;
import me.mouad.views.PatientFormView;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "me.mouad.repositories")
public class AppConfig {

    @Bean
    public PatientService patientService(PatientRepository repository) {
        return new PatientService(repository);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("me.mouad.models"); // Replace with the package where your entity classes are located
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); // Use Hibernate as the JPA vendor
        emf.setJpaProperties(jpaProperties());
        return emf;
    }

    private Properties jpaProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"); // Replace with your database dialect
        props.setProperty("hibernate.hbm2ddl.auto", "update"); // Automatically update the database schema
        props.setProperty("hibernate.show_sql", "true"); // Show SQL statements in logs
        props.setProperty("hibernate.format_sql", "true"); // Format SQL statements in logs
        return props;
    }

    @Bean
    public DataSource dataSource() {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Replace with your database driver class
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/patients?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true"); // Replace with your database URL
        hikariConfig.setUsername("patients_user"); // Replace with your database username
        hikariConfig.setPassword("123456"); // Replace with your database password
        return new HikariDataSource(hikariConfig);
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public MainView mainView(PatientRepository patientRepository) {
        return new MainView(patientService(patientRepository));
    }

}
