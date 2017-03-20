package com.scpdashboard.controllers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.scpdashboard.controllers.LoginController;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * This class encapsulates loginPageAccessSuccessTest test case.
 * @author KG048707
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MockServletContext.class)
public class LoginControllerTest{
	/**
	 * Initializes the MockMvc object used in test cases.
	 */
	private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(
			new LoginController()).build();
	/**
	 * Checks if the URL is redirecting to the "/login" page.
	 * @throws Exception
	 */
	@Test
	public void testLogin() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/login"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("LoginPageView"))
		.andExpect(forwardedUrl("LoginPageView"));
	}
}