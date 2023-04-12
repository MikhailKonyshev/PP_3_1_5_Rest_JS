package ru.kata.spring.boot_security.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.util.*;

@SpringBootApplication
public class SpringBootSecurityDemoApplication implements CommandLineRunner {

	private final UserServiceImp userServiceImp;

	public SpringBootSecurityDemoApplication(UserServiceImp userServiceImp) {
		this.userServiceImp = userServiceImp;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List <User> allUsers = userServiceImp.getAllUsers();

		if (allUsers.isEmpty()) {
			Role role = new Role("ROLE_ADMIN");
//			HashSet<Role> roles = new HashSet<>();
			ArrayList<Role> rolesFirst = new ArrayList<>();
			rolesFirst.add(role);
			userServiceImp.saveUser(new User("admin", "admin", 18, "admin@admin.com", "admin", rolesFirst));
		}
	}
}

