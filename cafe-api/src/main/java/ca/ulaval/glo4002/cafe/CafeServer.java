package ca.ulaval.glo4002.cafe;

import ca.ulaval.glo4002.cafe.api.configuration.resource.ResourceConfigFactory;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class CafeServer implements Runnable {

    private static final int PORT = 8181;
    private final ResourceConfigFactory resourceConfigFactory = new ResourceConfigFactory();

    public static void main(String[] args) {
        new CafeServer().run();
    }

    public void run() {
        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        ResourceConfig packageConfig = resourceConfigFactory.create();
        ServletContainer container = new ServletContainer(packageConfig);
        ServletHolder servletHolder = new ServletHolder(container);
        contextHandler.addServlet(servletHolder, "/*");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (server.isRunning()) {
                server.destroy();
            }
        }
    }
}
