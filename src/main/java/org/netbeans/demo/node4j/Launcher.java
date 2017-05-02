package org.netbeans.demo.node4j;

import com.oracle.truffle.api.impl.TruffleLocator;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import net.java.html.boot.truffle.TrufflePresenters;
import net.java.html.lib.Function;
import net.java.html.lib.Modules;
import net.java.html.lib.Objs;
import org.netbeans.html.boot.spi.Fn;

public final class Launcher extends Modules.Provider implements Executor {
    private final Function require;
    private final Objs global;
    private final Executor delegate;
    private final Fn.Presenter presenter;

    public Launcher(Object require, Object global, Object executor) throws Exception {
        System.getProperties().put("org.netbeans.lib.jshell.agent.AgentWorker.executor", this);
        this.require = Function.$as(require);
        this.global = Objs.$as(global);
        this.presenter = TrufflePresenters.create(this);
        this.delegate = findExecutor(executor);
    }

    public void initialize() throws Exception {
        try (Closeable c = Fn.activate(presenter)) {
            Main.main();
        }
    }

    @Override
    public void execute(Runnable command) {
        delegate.execute(new Runnable() {
            @Override
            public void run() {
                try (Closeable c = Fn.activate(presenter)) {
                    command.run();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    protected Objs find(String id) {
        if ("net.java.html.lib.node".equals(id)) {
            return global;
        }
        Object module = require.apply(null, id);
        return Objs.$as(module);
    }

    private static Executor findExecutor(Object suggested) throws Exception {
        if (suggested instanceof Executor) {
            return (Executor) suggested;
        }
        Method loadClass = TruffleLocator.class.getDeclaredMethod("loadClass", String.class);
        loadClass.setAccessible(true);
        Class<?> loopClass = (Class<?>) loadClass.invoke(null, "com.oracle.truffle.trufflenode.EventLoop");
        Method get = loopClass.getMethod("get");
        get.setAccessible(true);
        try {
            return (Executor) get.invoke(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
