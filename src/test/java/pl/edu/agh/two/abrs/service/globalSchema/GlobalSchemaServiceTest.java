package pl.edu.agh.two.abrs.service.globalSchema;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.edu.agh.two.abrs.model.global.GlobalSchema;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class GlobalSchemaServiceTest {

    @Autowired
    private GlobalSchemaService service;

    @Test
    public void saving_test() {
        service.updateGlobalSchema(new GlobalSchema(new ArrayList<>()));
        GlobalSchema schema = service.getGlobalSchema();
        assertNotNull(schema);
    }

    @Test
    public void deleting_test() {
        service.deleteGlobalSchema();
        GlobalSchema schema = service.getGlobalSchema();
        assertNull(schema);
    }

    @Configuration
    @ComponentScan(basePackages = "pl.edu.agh.two.abrs.*")
    @EnableJpaRepositories(basePackages = "pl.edu.agh.two.abrs.repository")
    @EnableTransactionManagement
    public static class ContextConfiguration {

        @Bean
        public DataSource dataSource() {
            EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .build();
            return dataSource;
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            vendorAdapter.setGenerateDdl(Boolean.TRUE);
            vendorAdapter.setShowSql(Boolean.TRUE);

            factory.setDataSource(dataSource());
            factory.setJpaVendorAdapter(vendorAdapter);
            factory.setPackagesToScan("pl.edu.agh.two.abrs.model");

            Properties jpaProperties = new Properties();
            jpaProperties.put("hibernate.hbm2ddl.auto", "update");
            jpaProperties.put("hibernate.show_sql", "true");
            factory.setJpaProperties(jpaProperties);

            factory.afterPropertiesSet();
            factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
            return factory;
        }

        @Bean
        public PlatformTransactionManager transactionManager() {
            EntityManagerFactory factory = entityManagerFactory().getObject();
            return new JpaTransactionManager(factory);
        }
    }
}
