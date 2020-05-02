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

import org.bukkit.configuration.file.YamlConfiguration;
import uk.co.n3fs.mc.wolfstats.platform.Config;
import uk.co.n3fs.mc.wolfstats.utils.Files;

import java.nio.file.Path;

public class YamlConfig implements Config {

    private static final String RESOURCE_NAME = "config.yml";

    private final YamlConfiguration yaml;

    YamlConfig(Path dir) {
        Files.copyDefault(dir, RESOURCE_NAME);
        yaml = YamlConfiguration.loadConfiguration(dir.resolve(RESOURCE_NAME).toFile());
    }

    @Override
    public boolean enabled() {
        return yaml.getBoolean("plugin.enabled", false);
    }

    @Override
    public String getServerTag() {
        return yaml.getString("plugin.server-name", "server");
    }

    @Override
    public String getDaemonUrl() {
        return yaml.getString("daemon.url", "localhost");
    }

    @Override
    public int getDaemonPort() {
        return yaml.getInt("daemon.port", 8125);
    }

    @Override
    public long getReportingInterval() {
        return Math.round(yaml.getDouble("metrics.reporting-interval", 20.0) * 1000);
    }

    @Override
    public String getPrefix() {
        return yaml.getString("metrics.prefix", "mc");
    }

    @Override
    public boolean sendStartEvent() {
        return yaml.getBoolean("events.send-start-event", true);
    }

    @Override
    public boolean sendReloadEvent() {
        return yaml.getBoolean("events.send-reload-event", true);
    }

    @Override
    public boolean sendShutdownEvent() {
        return yaml.getBoolean("events.send-shutdown-event", true);
    }
}
