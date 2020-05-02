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

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyReloadEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import uk.co.n3fs.mc.wolfstats.WolfstatsPlugin;
import uk.co.n3fs.mc.wolfstats.platform.*;

import java.nio.file.Path;
import java.util.Optional;

@Plugin(
        id = "wolfstats",
        name = "Wolfstats",
        version = "@version@",
        authors = "md678685",
        description = "Feeds proxy performance statistics to Datadog"
)
public final class VelocityBootstrap implements Bootstrap {

    private final VelocityServer server;
    private final Path pluginDir;

    private TomlConfig config;
    private WolfstatsPlugin plugin;
    private VelocityScheduler scheduler;

    @Inject
    public VelocityBootstrap(ProxyServer proxy, @DataDirectory Path pluginDir) {
        this.pluginDir = pluginDir;

        server = new VelocityServer(proxy);
        config = new TomlConfig(pluginDir);
        scheduler = new VelocityScheduler(this, proxy);
        plugin = new WolfstatsPlugin(this);
    }

    @Subscribe
    public void onProxyStart(ProxyInitializeEvent event) {
        plugin.enable();
    }

    @Subscribe
    public void onProxyReload(ProxyReloadEvent event) {
        plugin.disable();
        config = new TomlConfig(pluginDir);
        plugin = new WolfstatsPlugin(this);
        plugin.enable();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        plugin.disable();
    }

    @Override
    public Server getServerWrapper() {
        return server;
    }

    @Override
    public Config getPluginConfig() {
        return config;
    }

    @Override
    public Scheduler getSchedulerWrapper() {
        return scheduler;
    }

    @Override
    public Optional<TickSampler> getTickSampler() {
        return Optional.empty();
    }

}
