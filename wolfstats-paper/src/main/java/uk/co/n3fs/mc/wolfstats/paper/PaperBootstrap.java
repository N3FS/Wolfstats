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

import org.bukkit.plugin.java.JavaPlugin;
import uk.co.n3fs.mc.wolfstats.WolfstatsPlugin;
import uk.co.n3fs.mc.wolfstats.platform.*;

import java.util.Optional;

public final class PaperBootstrap extends JavaPlugin implements Bootstrap {

    private PaperServer server;
    private YamlConfig config;
    private PaperScheduler scheduler;
    private PaperTickSampler sampler;
    private WolfstatsPlugin plugin;

    @Override
    public void onEnable() {
        server = new PaperServer();
        config = new YamlConfig(getDataFolder().toPath());
        scheduler = new PaperScheduler(this);
        sampler = new PaperTickSampler();
        plugin = new WolfstatsPlugin(this);

        getServer().getPluginManager().registerEvents(sampler, this);
    }

    @Override
    public void onDisable() {
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
        return Optional.of(sampler);
    }
}
