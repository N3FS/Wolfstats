/*
 * Copyright (c) 2020 md678685
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package uk.co.n3fs.mc.wolfstats.paper;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import uk.co.n3fs.mc.wolfstats.platform.Scheduler;

import java.util.Map;
import java.util.concurrent.*;

public class PaperScheduler implements Scheduler {

    private final PaperBootstrap plugin;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder()
            .setNameFormat("wolfstats-paper-scheduler")
            .setDaemon(true)
            .build()
    );
    private final Map<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();

    public PaperScheduler(PaperBootstrap plugin) {
        this.plugin = plugin;
    }

    @Override
    public void scheduleRepeatingTask(String name, Runnable runnable, long repeatMs) {
        if (tasks.containsKey(name)) {
            throw new IllegalStateException("Can't create a task with the identifier of an existing task! Identifier: " + name);
        }

        ScheduledFuture<?> task = scheduler.scheduleAtFixedRate(runnable, 0, repeatMs, TimeUnit.MILLISECONDS);
        tasks.put(name, task);
    }

    @Override
    public void cancelTask(String name) {
        ScheduledFuture<?> task = tasks.get(name);
        if (task != null) {
            task.cancel(true);
            tasks.remove(name);
        }
    }

    @Override
    public void shutdown() {
        scheduler.shutdown();
        tasks.clear();
    }
}
