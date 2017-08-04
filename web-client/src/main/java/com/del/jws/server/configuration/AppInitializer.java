package com.del.jws.server.configuration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by dodolinel
 * date: 27.03.2017
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                AppConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(javax.servlet.ServletRegistration.Dynamic registration) {
        boolean done = registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        if (!done) throw new RuntimeException();
    }

    @Override
    protected Filter[] getServletFilters() {
        Filter[] filters;

        CharacterEncodingFilter encFilter;
        HiddenHttpMethodFilter httpMethodFilter = new HiddenHttpMethodFilter();

        encFilter = new CharacterEncodingFilter();

        encFilter.setEncoding("UTF-8");
        encFilter.setForceEncoding(true);

        filters = new Filter[]{httpMethodFilter, encFilter};
        return filters;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic h2Servlet = servletContext.addServlet("rtmpt", new org.red5.server.net.rtmpt.RTMPTServlet());
        h2Servlet.setLoadOnStartup(1);
        h2Servlet.addMapping("/fcs/*", "/open/*", "/close/*", "/send/*", "/idle/*");
        super.onStartup(servletContext);
    }
}
