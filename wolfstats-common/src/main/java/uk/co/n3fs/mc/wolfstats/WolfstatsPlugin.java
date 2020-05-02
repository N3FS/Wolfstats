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

package uk.co.n3fs.mc.wolfstats;

import com.timgroup.statsd.Event;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import uk.co.n3fs.mc.wolfstats.platform.*;

import java.util.Optional;

public class WolfstatsPlugin {

    private static final String STATS_TASK_ID = "STATS_TASK";

    private final Bootstrap bootstrap;
    private final StatsDClient statsd;

    private final StatsCollector statsTask;

    public WolfstatsPlugin(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;

        this.statsd = new NonBlockingStatsDClient(
                getConfig().getPrefix(),
                getConfig().getDaemonUrl(),
                getConfig().getDaemonPort(),
                getTags());

        this.statsTask = new StatsCollector(this);
    }

    private String[] getTags() {
        return new String[]{
                "mc_server_type:" + getServerType(),
                "mc_server_name:" + getConfig().getServerTag()
        };
    }

    public void enable() {
        if (!getConfig().enabled()) {
            getLogger().warn("Wolfstats is disabled in the config. Edit the config file to enable the plugin.");
            return;
        }

        getScheduler().scheduleRepeatingTask(STATS_TASK_ID, statsTask::run, getConfig().getReportingInterval());
    }

    public void disable() {
        getScheduler().cancelTask(STATS_TASK_ID);

        statsd.stop();
    }

    public void onServerStartup() {
        if (getConfig().sendStartEvent()) {
            Event event = Event.builder()
                    .withTitle("Minecraft " + getServerType() + " '" + getConfig().getServerTag() + "' started up.")
                    .withText("This may be either the result of a full restart or a " + getServerType() + " reload.")
                    .withAlertType(Event.AlertType.INFO)
                    .build();

            statsd.recordEvent(event);
        }
    }

    public void onServerReload() {
        if (getConfig().sendReloadEvent()) {
            Event event = Event.builder()
                    .withTitle("Minecraft " + getServerType() + " '" + getConfig().getServerTag() + "' reloading.")
                    .withText("This is a result of either a plugin reload or " + getServerType() + " reload.")
                    .withAlertType(Event.AlertType.INFO)
                    .build();

            statsd.recordEvent(event);
        }
    }

    public void onServerShutdown() {
        if (getConfig().sendShutdownEvent()) {
            Event event = Event.builder()
                    .withTitle("Minecraft " + getServerType() + " '" + getConfig().getServerTag() + "' shutting down.")
                    .withText("This may be either the result of a full restart or a " + getServerType() + " reload.")
                    .withAlertType(Event.AlertType.INFO)
                    .build();

            statsd.recordEvent(event);
        }
    }

    private String getServerType() {
        return getServer().getType().name().toLowerCase();
    }

    public Server getServer() {
        return bootstrap.getServerWrapper();
    }

    public Config getConfig() {
        return bootstrap.getPluginConfig();
    }

    public Scheduler getScheduler() {
        return bootstrap.getSchedulerWrapper();
    }

    public Optional<TickSampler> getTickSampler() {
        return bootstrap.getTickSampler();
    }

    public StatsDClient getStatsd() {
        return statsd;
    }

    public Logger getLogger() {
        return bootstrap.getPlatformLogger();
    }
}
