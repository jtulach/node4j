package org.netbeans.html.demo.node4j;

import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.java.JavaInterop;
import java.io.Closeable;
import java.util.concurrent.Executor;
import net.java.html.boot.truffle.TrufflePresenters;
import net.java.html.lib.Function;
import net.java.html.lib.Modules;
import net.java.html.lib.Objs;
import net.java.html.lib.node.http.Exports;
import net.java.html.lib.node.http.IncomingMessage;
import net.java.html.lib.node.http.ServerResponse;
import net.java.html.lib.node.http.Server;
import org.netbeans.html.boot.spi.Fn;

public final class Launcher extends Modules.Provider implements Executor {
    private final CommonJS require;

    public Launcher(TruffleObject require) {
        this.require = JavaInterop.asJavaFunction(CommonJS.class, require);
    }

    public void main() {
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

    public void run() throws Exception {
        Fn.Presenter p = TrufflePresenters.create(this);
        try (Closeable c = Fn.activate(p)) {
            main();
        }
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }

    @Override
    protected Objs find(String id) {
        Object module = require.require(null, id);
        return module == null ? null : Objs.$as(module);
    }

    private static interface CommonJS {
        public Object require(Object thiz, String name);
    }

}
