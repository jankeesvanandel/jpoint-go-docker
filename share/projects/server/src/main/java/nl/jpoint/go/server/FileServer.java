package nl.jpoint.go.server;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.server.accesslog.AccessLogBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Class initializes and starts the echo server, based on Grizzly 2.3
 */
public class FileServer {

    private static final Logger LOGGER = Logger.getLogger(FileServer.class.getName());

    public static void main(final String[] args) throws IOException {
        String host = args[0];
        int port = Integer.valueOf(args[1]);

        HttpServer server = HttpServer.createSimpleServer(".", host, port);
        AccessLogBuilder builder = new AccessLogBuilder("/tmp/access.log");
        builder.instrument(server.getServerConfiguration());

        server.getServerConfiguration().addHttpHandler(
                new HttpHandler() {
                    public void service(Request request, Response response) throws Exception {
                        final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
                        final String date = format.format(new Date(System.currentTimeMillis()));
                        response.setContentType("text/plain");
                        response.setContentLength(date.length());
                        response.getWriter().write(date);
                    }
                },
                "/time");
        try {
            // start the server
            server.start();

            LOGGER.info("Press any key to stop the server...");
            int read = System.in.read();
            LOGGER.info("Read " + read);
        } finally {
            LOGGER.info("Stopping server...");
            // stop the server
            server.shutdownNow();

            LOGGER.info("Stopped server...");
        }
    }
}
