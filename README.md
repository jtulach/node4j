# Using [node.js](https://nodejs.org/en/) from [Java](http://java.sun.com)

* First of all you need suitable Java virtual machine: Download [Graal VM](http://www.oracle.com/technetwork/oracle-labs/program-languages/)
* Then you need [Maven](http://maven.apache.org/)
* And you need to clone the sources: `git clone https://github.com/jtulach/node4j`

With these components, it should be easy to execute your first [node.js](https://nodejs.org/en/) 
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

The whole `node4j` project is a typical Maven project and you can work with it from any IDE that speaks *Maven*. However, if you download NetBeans IDE with built in support for JShell, we can do even more!

* Start the appropriate NetBeans IDE: `netbeans --jdkhome /path/to/graalvm`
* Open the `node4j` project
* Execute the project
 
A JShell console opens up in the editor and you can easily start using all the [node.js](https://nodejs.org/en/) features interactively from Java. Type:

```java
cre
```
and press *Ctrl-Space* to invoke code completion. From the list of available choices select `createServer` method with one `requestListener` parameter. Following will be generated:
```java
[19] -> createServer(requestListener)
```
press *Ctrl-Space* once again and let the IDE generate the lamda function body:
```java
[19] -> createServer((p1, p2) -> {
            return null;
})
```
optionally rename `p1` to `in` and `p2` to `out` as that is the meaning of the callback parameters in this context. Type in a lamda body to generate a reply:
```java
[19] -> createServer((p1, p2) -> {
    p2.end("Hello from Java!\n");
    return null;
})
```
after placing the caret at the end of the code snippet and pressing *Enter*, the snippet is evaluated:
```java
|  Expression value is: [object Object]
|    assigned to temporary variable $53 of type Server
```
and the value is stored in variable `$53`. Try to use that variable, type:
```java
$53.
```
and press *Ctrl-Space* to see methods available on this object. Choose `listen` method with one number parameter and bind the server to that port:
```java
[20] -> $53.listen(2345)
|  Expression value is: 
|    assigned to temporary variable $64 of type Server
```
Your first interactive **JShell+Node.js** session is done. Connect to `http://localhost:2345` to verify the server works. 
