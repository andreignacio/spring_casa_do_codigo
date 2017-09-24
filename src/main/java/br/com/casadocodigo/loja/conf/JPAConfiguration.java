package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


// Spring ativa o gerenciamento de transações e já reconhece o TransactionManager
//Essa antotation habilita para o String cuidar da transacao, depois cada cada dao, vc tem que 
//colocar a anotation @Transactional
@EnableTransactionManagement
public class JPAConfiguration {
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

	    factoryBean.setJpaVendorAdapter(vendorAdapter);
	    factoryBean.setDataSource(dataSource);

	    Properties props = aditionalProperties();

	    factoryBean.setJpaProperties(props);
	    factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");

	    return factoryBean;
		
	}
	
	@Bean
	@Profile("dev")
	public DataSource dataSource(){
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setUsername("root");
	    dataSource.setPassword("root");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo");
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    return dataSource;
	}
	
	private Properties aditionalProperties(){
	    Properties props = new Properties();
	    props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
	    props.setProperty("hibernate.show_sql", "true");
	    props.setProperty("hibernate.hbm2ddl.auto", "update");
	    return props;
	}
	
	// Esse metodo cria e que cuida da transacao, ele é um PlatformTransactionManagar, existem varios dessas classes
	// que podemos utilizar, em nosso caso utilizaremos o 
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager();
	}

}
