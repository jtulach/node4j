package org.netbeans.demo.node4j;

import net.java.html.lib.Function;
import static net.java.html.lib.node.http.Exports.createServer;
import net.java.html.lib.node.http.IncomingMessage;
import net.java.html.lib.node.http.ServerResponse;
import net.java.html.lib.node.http.Server;

public final class Main  {
    public static void main(String... args) {
        Server server = createServer((IncomingMessage request, ServerResponse response) -> {
            System.err.println("request for " + request.url());
            response.end("It works! Hit " + request.url() + "\n");
            return null;
        });
        server.listen(8080, Function.newFunction((Function.A0<Void>) () -> {
            System.err.println("Server listening on port 8080");
            return null;
        }));
    }


}
