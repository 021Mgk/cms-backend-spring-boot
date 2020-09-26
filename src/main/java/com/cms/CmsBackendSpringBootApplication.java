package com.cms;

import com.cms.model.entity.Link;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties({
		Link.class
})
public class CmsBackendSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsBackendSpringBootApplication.class, args);
	}

}
