package com.cms;

import com.cms.model.entity.FileUploadDir;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileUploadDir.class
})
public class CmsBackendSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsBackendSpringBootApplication.class, args);
	}

}
