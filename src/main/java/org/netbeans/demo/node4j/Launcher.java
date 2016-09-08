package org.netbeans.demo.node4j;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import net.java.html.boot.truffle.TrufflePresenters;
import net.java.html.lib.Array;
import net.java.html.lib.Function;
import net.java.html.lib.Modules;
import net.java.html.lib.Objs;
import net.java.html.lib.node.Exports;
import org.netbeans.html.boot.spi.Fn;

public final class Launcher extends Modules.Provider implements Executor {
    private final Function require;
    private final Objs global;
    private final List<Runnable> queue = new LinkedList<>();
    private final Fn.Presenter presenter;

    public Launcher(Object require, Object global) {
        this.require = Function.$as(require);
        this.global = Objs.$as(global);
        this.presenter = TrufflePresenters.create(this);
        System.getProperties().put("org.netbeans.lib.jshell.agent.AgentWorker.executor", this);
    }

    public void initialize() throws Exception {
        try (Closeable c = Fn.activate(presenter)) {
            Main.main();
            Exports.setInterval((Array<Object> p1) -> {
                clearQueue();
                return null;
            }, 100);
        }
    }

    @Override
    public synchronized void execute(Runnable command) {
        queue.add(command);
    }

    private synchronized Runnable[] emptyQueue() {
        if (queue.isEmpty()) {
            return null;
        }
        Runnable[] arr = queue.toArray(new Runnable[0]);
        queue.clear();
        return arr;
    }

    private void clearQueue() {
        Runnable[] pending = emptyQueue();
        if (pending == null) {
            return;
        }
        try (Closeable c = Fn.activate(presenter)) {
            for (Runnable r : pending) {
                r.run();
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    protected Objs find(String id) {
        if ("net.java.html.lib.node".equals(id)) {
            return global;
        }
        Object module = require.apply(null, id);
        return Objs.$as(module);
    }
}
