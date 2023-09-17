package com.stc.config.repository;

import java.util.List;
import java.util.Optional;

import com.stc.config.entity.User;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepoTest {
	/*
	 * 
	 * We need to apply test for saving method from repo-Jpa
	 * 
	 */
	@Autowired
	RegisterRepo registerRepo;

	@Test
	public void registerRepo_save_returnedSavedUser() {
		// Arrange => Initialize fake object
		User testUser = User.builder().name("Maged").email("majbader@stc").build();

		// Act => apply the logic which you want to test
		User savedUser = registerRepo.save(testUser);

		// Assert => your test cases
		Assertions.assertThat(savedUser).isNotNull();
		Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

	}

	@Test
	public void registerRepo_finaAll_returnedUserList() {
		// Arrange
		User user1 = User.builder().name("Ashraf").email("Aashraf@stc").build();
		User user2 = User.builder().name("Aly").email("Aldalashy@stc").build();
		
		//Act
		registerRepo.save(user1);
		registerRepo.save(user2);
		List<User> usersList = registerRepo.findAll();
		
		//Assert
		Assertions.assertThat(usersList).isNotNull();
		Assertions.assertThat(usersList.size()).isEqualTo(2);

	}
	

	@Test
	public void registerRepo_findByEmail_returnedUser() {
		// Arrange
		User user1 = User.builder().name("Ashraf").email("Aashraf@stc").build();
		User user2 = User.builder().name("Aly").email("Aldalashy@stc").build();
		
		//Act
		registerRepo.save(user1);
		registerRepo.save(user2);
		User returnedUser = registerRepo.findByEmail(user1.getEmail()).get();
		
		//Assert
		Assertions.assertThat(returnedUser).isNotNull();
		

	}
	
	
	@Test
	public void registerRepo_updateUser_returnedUser() {
		// Arrange
		User user1 = User.builder().name("Ashraf").email("Aashraf@stc").build();
	
		
		//Act
		registerRepo.save(user1);
	
		User returnedUser = registerRepo.findById(user1.getId()).get();
		returnedUser.setName("Maged");
		
		//Assert
		Assertions.assertThat(returnedUser).isNotNull();
		Assertions.assertThat(returnedUser.getName()).isEqualTo("Maged");

	}
	
	
	@Test
	public void registerRepo_deleteUser_returnedUserIsEmpty() {
		// Arrange
		User user1 = User.builder().name("Ashraf").email("Aashraf@stc").build();
		registerRepo.save(user1);
		
		//Act
		registerRepo.deleteById(user1.getId());
		Optional<User> returnedUser = registerRepo.findById(user1.getId());
		
		//Assert
		Assertions.assertThat(returnedUser).isEmpty();
	

	}
}
