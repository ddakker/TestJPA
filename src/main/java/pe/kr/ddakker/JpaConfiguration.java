package pe.kr.ddakker;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.cache.HashtableCacheProvider;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JpaConfiguration {

	@Value("#{dataSource}")
	private javax.sql.DataSource dataSource;

	@Bean
	public Map<String, Object> jpaProperties() {
		System.out.println("--1 H2Dialect.class.getName(): " + H2Dialect.class.getName());
		System.out.println("--2 HashtableCacheProvider.class.getName(): " + HashtableCacheProvider.class.getName());

		Map<String, Object> props = new HashMap<String, Object>();
		props.put("hbm2ddl.auto", "create");
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.dialect", MySQLDialect.class.getName());
		//props.put("hibernate.cache.provider_class", HashtableCacheProvider.class.getName());
		return props;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.H2);
		return hibernateJpaVendorAdapter;
	}

	/*@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager( localContainerEntityManagerFactoryBean().getObject() );
	}*/
	
	@Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(localContainerEntityManagerFactoryBean().getObject());
        jpaTransactionManager.setDataSource(this.dataSource);
        return jpaTransactionManager;
    }

	@Bean
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(this.dataSource);
		lef.setJpaPropertyMap(this.jpaProperties());
		lef.setJpaVendorAdapter(this.jpaVendorAdapter());
		return lef;
	}
	

}
