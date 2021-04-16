package com.example.ignite;

import com.example.ignite.domain.Person;
import com.example.ignite.repository.PersonRepository;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;
import org.apache.ignite.springframework.boot.autoconfigure.IgniteConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableIgniteRepositories
public class IgniteApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgniteApplication.class, args);
	}

	@Bean
	public Ignite igniteInstance() {
		IgniteConfiguration cfg = new IgniteConfiguration();
		cfg.setIgniteInstanceName("igniteInstance");
		cfg.setPeerClassLoadingEnabled(false);
		CacheConfiguration ccfg = new CacheConfiguration("PersonCache");

		ccfg.setIndexedTypes(Long.class, Person.class);
		cfg.setCacheConfiguration(ccfg);
		return Ignition.start(cfg);
	}

	@Bean
	public CacheConfiguration createCacheConf(){
		CacheConfiguration conf = new CacheConfiguration();
		conf.setName("igniteInstance");
		conf.setAtomicityMode(CacheAtomicityMode.ATOMIC);
		conf.setBackups(1);
		conf.setEagerTtl(true);
		conf.setExpiryPolicyFactory( CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, 5)));
		return conf;
	}

}
