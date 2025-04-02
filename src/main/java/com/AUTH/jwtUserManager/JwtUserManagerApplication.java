package com.AUTH.jwtUserManager;

import com.AUTH.jwtUserManager.configs.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)

public class JwtUserManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtUserManagerApplication.class, args);
	}

}
