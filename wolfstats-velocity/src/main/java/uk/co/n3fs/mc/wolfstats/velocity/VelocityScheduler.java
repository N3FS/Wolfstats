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

package uk.co.n3fs.mc.wolfstats.velocity;

import com.google.common.collect.MapMaker;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import uk.co.n3fs.mc.wolfstats.platform.Scheduler;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class VelocityScheduler implements Scheduler {
    private final VelocityBootstrap plugin;
    private final ProxyServer proxy;

    private final ConcurrentMap<String, ScheduledTask> tasks = new MapMaker()
            .weakValues()
            .makeMap();

    public VelocityScheduler(VelocityBootstrap plugin, ProxyServer proxy) {
        this.plugin = plugin;
        this.proxy = proxy;
    }

    @Override
    public void scheduleRepeatingTask(String identifier, Runnable runnable, long repeatMs) {
        if (tasks.containsKey(identifier)) {
            throw new IllegalStateException("Can't create a task with the identifier of an existing task!");
        }

        ScheduledTask task = proxy.getScheduler().buildTask(plugin, runnable)
                .repeat(repeatMs, TimeUnit.MILLISECONDS)
                .schedule();

        tasks.put(identifier, task);
    }

    @Override
    public void cancelTask(String identifier) {
        ScheduledTask task = tasks.get(identifier);
        if (task != null) {
            task.cancel();
            tasks.remove(identifier);
        }
    }

    @Override
    public void shutdown() {
        tasks.forEach((identifier, task) -> task.cancel());
        tasks.clear();
    }
}
