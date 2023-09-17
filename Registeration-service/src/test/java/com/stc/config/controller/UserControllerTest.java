package com.stc.config.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stc.config.entity.User;
import com.stc.config.service.UserService;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	UserService userService;
	
	private User testUser;
	
	@BeforeEach
	public void init()
	{
		 testUser = User.builder().name("Maged").email("majbader@stc").build();
	}
	
	@Test
	public void userController_register_CreatedStatus() throws Exception
	{
		// testUser = User.builder().name("Maged").email("majbader@stc").build();
	  given(userService.createUser(ArgumentMatchers.any())).willAnswer(x->x.getArgument(0)); // menaha return awl paramter da5elak
	  
	  ResultActions response = mockMvc.perform(post("/createUser")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(mapper.writeValueAsString(testUser)));
	  
	  response.andExpect(MockMvcResultMatchers.status().isCreated())
	  .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(testUser.getName()))) 
	  .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(testUser.getEmail()))) ;
	//  .andDo(MockMvcResultHandlers.print());  // deh wazfetaha tatba3 json fakat bata3 response
	} 
	
	@Test
	public void userController_findAllUsersByPagesAsList_OkStatus() throws Exception
	{
		User user1 = User.builder().name("Maged").email("majbader@stc").build();
		User user2 = User.builder().name("ali").email("ali@stc").build();
		
		when(userService.findAllUsersByList(0, 10)).thenReturn(Arrays.asList(user1,user2));
		
		ResultActions response = mockMvc.perform(get("/usersList")
				.contentType(MediaType.APPLICATION_JSON)
				.param("page", "0")
				.param("size", "10"));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
		 .andDo(MockMvcResultHandlers.print())
	     .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2))) 
		.andExpect(MockMvcResultMatchers.jsonPath("$.[1].name", CoreMatchers.is("ali"))) ;
	}
	
	@Test
	public void userController_findUserByEmail_OkStatus() throws Exception
	{
		// testUser = User.builder().name("Maged").email("majbader@stc").build();
	when(userService.findUser("majbader@stc")).thenReturn(testUser);
	  
	  ResultActions response = mockMvc.perform(get("/findUser/majbader@stc")
			  .contentType(MediaType.APPLICATION_JSON));
		//	  .content(mapper.writeValueAsString(testUser)));
	  
	  response.andExpect(MockMvcResultMatchers.status().isOk())
	  .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(testUser.getName()))) 
	  .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(testUser.getEmail()))) ;
	//  .andDo(MockMvcResultHandlers.print());  // deh wazfetaha tatba3 json fakat bata3 response
	}
	
	@Test
	public void userController_deleteUser_OkStatus() throws Exception
	{

		doNothing().when(userService).deleteUser(1);
	  
	  ResultActions response = mockMvc.perform(delete("/deleteUser/1")
			  .contentType(MediaType.APPLICATION_JSON));
		//	  .content(mapper.writeValueAsString(testUser)));
	  
	  response.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
