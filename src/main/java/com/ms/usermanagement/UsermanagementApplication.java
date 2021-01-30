package com.ms.usermanagement;

import com.ms.usermanagement.model.User;
import com.ms.usermanagement.repositry.UserRepository;
import com.ms.usermanagement.util.EncryptDecrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class UsermanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementApplication.class, args);
	}

	@Autowired
	private UserRepository repository;

	@PostConstruct
	public void initUsers() {
		List<User> users = Stream.of(
				new User(UUID.fromString("68877038-0a08-4e8b-a920-57d2d5a165e9"), "fareed", EncryptDecrypt.encrypt("password"), "fareed@gmail.com","ROLE_ADMIN"),
				new User(UUID.randomUUID(), "Client1", EncryptDecrypt.encrypt("pwd1"), "client1@gmail.com","ROLE_USER"),
				new User(UUID.randomUUID(), "Client2", EncryptDecrypt.encrypt("pwd2"), "client2@gmail.com","ROLE_USER"),
				new User(UUID.randomUUID(), "Client3", EncryptDecrypt.encrypt("pwd3"), "client3@gmail.com","ROLE_USER")
		).collect(Collectors.toList());
		repository.saveAll(users);
	}
}
