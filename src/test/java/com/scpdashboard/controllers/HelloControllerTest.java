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
import com.scpdashboard.controllers.HelloController;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MockServletContext.class)

public class HelloControllerTest {
	private MockMvc mockMvc;
	 /**
     * This method initializes the MockMvc object used in each test using the
     * webapp's application context
     */
    @Before
    public void setUp()
    {
    	 mockMvc = MockMvcBuilders.standaloneSetup(
                 new HelloController())
             .build();
    }
    /**
     * Test to perform wether the url is redirecting to the right page and it is 
     * giving the correct jsp page
     * @throws Exception
     */
    @Test
    public void homePageAccessSuccessTest() throws Exception
    {
        // making a GET request to '/' using the mock mvc object and verify that
        // all testable fields are correct
        mockMvc.perform(MockMvcRequestBuilders.get("/welcome"))
             .andDo(print())
             .andExpect(status().isOk())
             .andExpect(view().name("HelloView"))
             .andExpect(forwardedUrl("HelloView"));
    }
}
