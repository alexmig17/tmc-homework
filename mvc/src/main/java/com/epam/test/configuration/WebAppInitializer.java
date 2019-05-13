package com.epam.test.configuration;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import com.epam.test.configuration.filter.DisableUrlSessionFilter;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        //rootContext not need???

        //WebAppContext
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(WebMvcConfiguration.class);
        dispatcherContext.register(SecurityConfig.class);

        //dispatcher servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        //alternative configuration for multipart uploader:
        //dispatcher.setMultipartConfig(new MultipartConfigElement("", 10485760, 20971520, 5242880));

        //h2 console servlet
        ServletRegistration.Dynamic h2ConsoleServlet = servletContext.addServlet("h2-console", new WebServlet());
        h2ConsoleServlet.setLoadOnStartup(2);
        h2ConsoleServlet.addMapping("/console/*");

        //filters
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        filterRegistration.setInitParameter("encoding", "UTF-8");
        filterRegistration.setInitParameter("forceEncoding", "true");
        filterRegistration.addMappingForUrlPatterns(null, false, "/*");
        //not need now. Not used
        FilterRegistration.Dynamic sessionFilter = servletContext.addFilter("disableUrlSessionFilter", new DisableUrlSessionFilter());
        sessionFilter.addMappingForUrlPatterns(null, true, "/*");

        //security
        FilterRegistration.Dynamic securityFilter = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        securityFilter.addMappingForServletNames(null, true, "dispatcher");
    }
}
