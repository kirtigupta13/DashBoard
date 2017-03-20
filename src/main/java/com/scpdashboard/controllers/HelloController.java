package com.scpdashboard.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/*
 * controller which displays the Welcome Message. This is just to check the Proper
 * project setup 
 */
	@EnableWebMvc 
	@Controller
	public class HelloController {
	 
		@RequestMapping("/welcome")
		public ModelAndView welcome( ) throws Exception {
			ModelAndView modelandview = new ModelAndView("HelloView");
			modelandview.addObject("welcomeMessage","Hi, welcome to the SCPDashBoard Application");
			return modelandview;
		} 
	}

