package nl.jpoint.go.server;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.logging.Logger;

import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.utils.StringFilter;

/**
 * Class initializes and starts the echo server, based on Grizzly 2.3
 */
public class EchoServer {

    private static final Logger LOGGER = Logger.getLogger(EchoServer.class.getName());

    public static void main(String[] args) throws IOException {
        String host = args[0];
        int port = Integer.valueOf(args[1]);

        // Create a FilterChain using FilterChainBuilder
        FilterChainBuilder filterChainBuilder = FilterChainBuilder.stateless();

        // Add TransportFilter, which is responsible
        // for reading and writing data to the connection
        filterChainBuilder.add(new TransportFilter());

        // StringFilter is responsible for Buffer <-> String conversion
        filterChainBuilder.add(new StringFilter(Charset.forName("UTF-8")));

        // EchoFilter is responsible for echoing received messages
        filterChainBuilder.add(new EchoFilter());

        // Create TCP transport
        final TCPNIOTransport transport = TCPNIOTransportBuilder.newInstance().build();

        transport.setProcessor(filterChainBuilder.build());
        try {
            // binding transport to start listen on certain host and port
            transport.bind(host, port);

            // start the transport
            transport.start();

            LOGGER.info("Press any key to stop the server...");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                final String s = scanner.nextLine();
                if (s != null && s.equalsIgnoreCase("stop")) {
                    break;
                }
            }
        } finally {
            LOGGER.info("Stopping transport...");
            // stop the transport
            transport.shutdownNow();

            LOGGER.info("Stopped transport...");
        }
    }
}
