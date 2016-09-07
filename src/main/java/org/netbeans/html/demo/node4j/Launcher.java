package org.netbeans.html.demo.node4j;

import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.java.JavaInterop;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Executor;
import net.java.html.boot.truffle.TrufflePresenters;
import net.java.html.lib.Modules;
import net.java.html.lib.Objs;
import org.netbeans.html.boot.spi.Fn;

public final class Launcher extends Modules.Provider implements Executor {
    private final CommonJS require;

    public Launcher(TruffleObject require) {
        this.require = JavaInterop.asJavaFunction(CommonJS.class, require);
        System.getProperties().put("org.netbeans.lib.jshell.agent.AgentWorker.executor", this);
    }

    public void run() throws Exception {
        Fn.Presenter p = TrufflePresenters.create(this);
        try (Closeable c = Fn.activate(p)) {
            Main.main();
        }
    }

    @Override
    public void execute(Runnable command) {
        Fn.Presenter p = TrufflePresenters.create(this);
        try (Closeable c = Fn.activate(p)) {
            command.run();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
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
