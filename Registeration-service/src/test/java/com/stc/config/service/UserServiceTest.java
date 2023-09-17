package com.stc.config.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.stc.config.entity.User;
import com.stc.config.repository.RegisterRepo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	RegisterRepo registerRepo;
	
	@InjectMocks
	UserService userService;
	
	@Test
	public void userService_register_returnSuccessMsg()
	{
		User testUser = User.builder().name("Maged").email("majbader@stc").build();
		
		when(registerRepo.save(Mockito.any(User.class))).thenReturn(testUser);

		
		String msg = userService.register(testUser);
		
		Assertions.assertThat(msg).isNotNull();
	}
	
	/*
	 *  
	 *  Two different ways to implement findAll method.
	 * 
	 */
	
	@Test
	public void userService_findAllUsers_returnUserList()
	{
		User user1 = User.builder().name("Maged").email("majbader@stc").build();
		User user2 = User.builder().name("ali").email("ali@stc").build();
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		

		
		when(registerRepo.save(Mockito.any(User.class))).thenReturn(user1);
		when(registerRepo.findAll()).thenReturn(users);
		
		
		userService.register(user1);
		userService.register(user2);
		
		List<User> savedUsers = userService.findAllUsers();
		
		Assertions.assertThat(savedUsers).isNotNull();
		Assertions.assertThat(savedUsers.size()).isEqualTo(2);
		
		
	}
	
	@Test
	public void userService_findAllUsers_returnUserList_2()
	{
		List<User> users =  Mockito.mock(ArrayList.class);
		
	    when(registerRepo.findAll()).thenReturn(users);

		List<User> savedUsers = userService.findAllUsers();
		
		Assertions.assertThat(savedUsers).isNotNull();
	
	}
	
	/////////////////////////////////////
	
	@Test
	public void userService_findByEmail_returnSavedUser()
	{
		User testUser = User.builder().name("Maged").email("majbader@stc").build();
		Optional<User> tstUser = Optional.of(testUser);
		when(registerRepo.findByEmail(Mockito.any(String.class))).thenReturn(tstUser);

		
		User returnedUser = userService.findUser("majbader@stc");
		
		Assertions.assertThat(returnedUser).isNotNull();
		Assertions.assertThat(returnedUser.getName()).isEqualTo("Maged");
	}
	
	// If the method which you want test is (void) then test it with assertAll()
	@Test
	public void userService_deleteUser_noReturn()
	{
		User testUser = User.builder().name("Maged").email("majbader@stc").build();
		
	    assertAll(() -> userService.deleteUser(1));
	}
}
