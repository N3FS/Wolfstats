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

import org.bukkit.Bukkit;
import uk.co.n3fs.mc.wolfstats.platform.Server;
import uk.co.n3fs.mc.wolfstats.platform.TickSampler;

import java.util.Optional;

public class PaperServer implements Server {

    @Override
    public Optional<TickSampler> getTickSampler() {
        // TODO: implement
        return Optional.empty();
    }

    @Override
    public int getCurrentPlayers() {
        return Bukkit.getOnlinePlayers().size();
    }

    @Override
    public Optional<Integer> getMaxPlayers() {
        return Optional.of(Bukkit.getMaxPlayers());
    }

    @Override
    public Optional<Integer> getKnownPlayers() {
        return Optional.of(Bukkit.getOfflinePlayers().length);
    }

    @Override
    public ServerType getType() {
        return ServerType.SERVER;
    }
}
