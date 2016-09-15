# Using [node.js](https://nodejs.org/en/) from [Java](http://java.sun.com)

* First of all you need suitable Java virtual machine: Download [Graal VM](http://www.oracle.com/technetwork/oracle-labs/program-languages/)
* Then you need [Maven](http://maven.apache.org/)
* And you need to clone the sources: `git clone https://github.com/jtulach/node4j`

With these components, it should be easy to execute the your first [node.js](https://nodejs.org/en/) 
application from [Java](http://java.sun.com):

```bash
$ cd node4j
$ JAVA_HOME=/path/to/graalvm/ mvn package exec:exec
Server listening on port 8080
```

Now you can connect to the `http://localhost:8080/someurl` and verify the application works. It should.
But the best thing is that the application isn't [written in JavaScript, but in Java](https://github.com/jtulach/node4j/blob/afbff13bceb3f960c13949816f02fb55f994a505/src/main/java/org/netbeans/demo/node4j/Main.java):

```java
package org.netbeans.demo.node4j;

import net.java.html.lib.Function;
import net.java.html.lib.node.http.Exports;
import net.java.html.lib.node.http.IncomingMessage;
import net.java.html.lib.node.http.ServerResponse;
import net.java.html.lib.node.http.Server;

public final class Main  {
    public static void main(String... args) {
        Server server = Exports.createServer((IncomingMessage request, ServerResponse response) -> {
            System.err.println("request for " + request.url());
            response.end("It works! Hit " + request.url());
            return null;
        });
        server.listen(8080, Function.newFunction((Function.A0<Void>) () -> {
            System.err.println("Server listening on port 8080");
            return null;
        }));
    }
}
```

All the power of [node.js](https://nodejs.org/en/) engine accessible from regular [Java](http://java.sun.com) virtual machine
via type-safe [Java APIs](https://dukescript.com/javadoc/libs/)!

# Using NetBeans and JShell

***TBD***
