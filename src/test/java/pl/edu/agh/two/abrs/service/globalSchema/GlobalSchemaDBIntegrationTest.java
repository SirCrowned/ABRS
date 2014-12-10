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
import pl.edu.agh.two.abrs.model.ColumnType;
import pl.edu.agh.two.abrs.model.global.GlobalSchema;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaColumn;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaRecord;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaTable;
import pl.edu.agh.two.abrs.repository.GlobalSchemaColumnRepository;
import pl.edu.agh.two.abrs.repository.GlobalSchemaTableRepository;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class GlobalSchemaDBIntegrationTest {

    @Autowired
    private GlobalSchemaService service;

    @Autowired
    private GlobalSchemaTableRepository globalSchemaTableRepository;

    @Autowired
    private GlobalSchemaColumnRepository globalSchemaColumnRepository;

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

    @Test
    @Transactional
    public void cascade_saving() {
        ArrayList<GlobalSchemaColumn> columns = new ArrayList<>();
        columns.add(new GlobalSchemaColumn(ColumnType.BOOLEAN, "col1"));
        ArrayList<GlobalSchemaTable> tables = new ArrayList<>();
        tables.add(new GlobalSchemaTable("Tabela", columns));
        tables.add(new GlobalSchemaTable("Tablea2", new ArrayList<>()));
        GlobalSchema schema = new GlobalSchema(tables);
        service.updateGlobalSchema(schema);
        GlobalSchema newSchema = service.getGlobalSchema();
        assertEquals(newSchema.getTables().size(), 2);
        assertEquals(newSchema.getTables().get(0).getColumns().size(), 1);
    }

    @Test
    public void cascade_removing() {
        service.deleteGlobalSchema();

        ArrayList<GlobalSchemaColumn> columns = new ArrayList<>();
        columns.add(new GlobalSchemaColumn(ColumnType.BOOLEAN, "col1"));
        ArrayList<GlobalSchemaTable> tables = new ArrayList<>();
        tables.add(new GlobalSchemaTable("Tabela", columns));
        tables.add(new GlobalSchemaTable("Tablea2", new ArrayList<>()));
        GlobalSchema schema = new GlobalSchema(tables);
        service.updateGlobalSchema(schema);
        GlobalSchema newSchema = service.getGlobalSchema();

        service.deleteGlobalSchema();

        assertEquals(globalSchemaColumnRepository.findAll().size(), 0);
        assertEquals(globalSchemaTableRepository.findAll().size(), 0);
    }

    @Test
    @Transactional
    public void test_data() {
        ArrayList<GlobalSchemaColumn> columns = new ArrayList<>();
        columns.add(new GlobalSchemaColumn(ColumnType.BOOLEAN, "col1"));
        ArrayList<GlobalSchemaTable> tables = new ArrayList<>();
        GlobalSchemaTable test_table = new GlobalSchemaTable("Tabela", columns);
        test_table.addRecord(new GlobalSchemaRecord(Arrays.asList("a", "b", "c")));
        tables.add(test_table);
        //tables.add(new GlobalSchemaTable("Tablea2", new ArrayList<>()));
        GlobalSchema schema = new GlobalSchema(tables);
        service.updateGlobalSchema(schema);
        GlobalSchema newSchema = service.getGlobalSchema();
        assertEquals(newSchema.getTables().size(), 1);
        assertEquals(newSchema.getTables().get(0).getColumns().size(), 1);
        List<GlobalSchemaTable> got_tables = newSchema.getTables();
        GlobalSchemaTable first_table = got_tables.get(0);
        assertEquals(first_table.getRecords().size(), 1);
        GlobalSchemaRecord record = first_table.getRecords().get(0);
        assertEquals(record.getValues().size(),3);
        assertEquals(record.getValues().get(0), "a");
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
