package com.luv2code.springdemo.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext theServletContext) throws ServletException {

		AnnotationConfigWebApplicationContext theAppContext = new AnnotationConfigWebApplicationContext();
		
		// where to scan for @Configuration classes
		theAppContext.setConfigLocation("com.luv2code.springdemo.config");

		// add a listener for for event
		theServletContext.addListener(new ContextLoaderListener(theAppContext));

		// register Spring MVC Dispatcher servlet
		ServletRegistration.Dynamic dispatcher = theServletContext.addServlet("dispatcher",
				new DispatcherServlet(theAppContext));

		// add mapping for root
		dispatcher.addMapping("/");
		
		// set loading order
		dispatcher.setLoadOnStartup(1);

	}

}
