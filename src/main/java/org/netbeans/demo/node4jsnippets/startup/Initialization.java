package org.netbeans.demo.node4jsnippets.startup;

import static net.java.html.lib.Exports.eval;
import static net.java.html.lib.node.http.Exports.*;
import net.java.html.lib.node.http.*;

class Initialization {
    public void run() {
        System.err.println("=== Welcome to node4j! ===");
        System.err.println("Use:");
        System.err.println(" eval(String)");
        System.err.println(" createServer(function).listen(port)");
        System.err.println("or import any other API from");
        System.err.println(" net.java.html.lib.node.* packages");
        System.err.println("========= Enjoy! =========");
    }
}