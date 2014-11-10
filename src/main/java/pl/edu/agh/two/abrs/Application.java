package pl.edu.agh.two.abrs;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import pl.edu.agh.two.abrs.config.ApplicationConfig;

import java.io.File;
import java.io.IOException;

public class Application {

    private static final int DEFAULT_PORT = 8080;

    public static void main(String... args) throws Exception {
        new Application().start();
    }

    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/*");
        contextHandler.addEventListener(new ContextLoaderListener(context));
        contextHandler.setResourceBase(new File("src/main/webapp/").getAbsolutePath());
        return contextHandler;
    }

    private static WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfig.class);
        return context;
    }

    private void start() throws Exception {
        Server server = new Server(DEFAULT_PORT);
        server.setHandler(getServletContextHandler(getContext()));
        server.start();
        server.join();
    }
}

