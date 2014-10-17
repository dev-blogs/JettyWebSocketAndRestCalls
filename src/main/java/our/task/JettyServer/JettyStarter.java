package our.task.JettyServer;
 
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import java.net.URL;
import org.eclipse.jetty.webapp.WebAppContext;
import java.security.ProtectionDomain;
 
import our.task.JettyWebSocket.SocketHandler;
 
public class JettyStarter {
    public static void main(String [] args) {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);
 
        // add first handler
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[] { "index.html" });
        resource_handler.setResourceBase(".");
		
		ProtectionDomain domain = JettyStarter.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
		
		// add context
		WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar(location.toExternalForm());
 
        HandlerList handlers = new HandlerList();
        // first element  is webSocket handler
		// second element is first handler,
		// third element is webContext
        handlers.setHandlers(new Handler[] {new SocketHandler(), resource_handler, webapp});
         
        server.setHandler(handlers);
 
        try {
            server.start();
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}