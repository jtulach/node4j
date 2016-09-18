package org.netbeans.demo.node4jsnippets.startup;

import static net.java.html.lib.Exports.eval;
import static net.java.html.lib.node.http.Exports.*;
import net.java.html.lib.node.http.*;

class Initialization {
    public void run() {
        String welcome = "\n" +
            "=== Welcome to node4j! ===\n" +
            "Use:\n" +
            " eval(String)\n" +
            " createServer(function).listen(port)\n" +
            "or import any other API from\n" +
            " net.java.html.lib.node.* packages\n" +
        "========= Enjoy! =========\n";
    }
}