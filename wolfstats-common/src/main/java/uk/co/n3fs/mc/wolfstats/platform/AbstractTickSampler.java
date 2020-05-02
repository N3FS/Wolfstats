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

package uk.co.n3fs.mc.wolfstats.platform;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

public abstract class AbstractTickSampler implements TickSampler {

    private static final int SAMPLE_SIZE = 1200;

    private final LinkedList<Double> lengthSamples = new LinkedList<>();
    private final LinkedList<Double> sleepSamples = new LinkedList<>();

    @Override
    public Optional<Double> getAverageTickLength() {
        synchronized (lengthSamples) {
            if (lengthSamples.size() < SAMPLE_SIZE) {
                return Optional.empty();
            }

            return Optional.of(average(lengthSamples));
        }
    }

    @Override
    public Optional<Double> getAverageTickSleep() {
        synchronized (sleepSamples) {
            if (sleepSamples.size() < SAMPLE_SIZE) {
                return Optional.empty();
            }

            return Optional.of(average(sleepSamples));
        }
    }

    protected void sampleLength(double sample) {
        synchronized (lengthSamples) {
            lengthSamples.add(sample);

            while (lengthSamples.size() > SAMPLE_SIZE) {
                lengthSamples.removeFirst();
            }
        }
    }

    protected void sampleSleep(double sample) {
        synchronized (sleepSamples) {
            sleepSamples.add(sample);

            while (sleepSamples.size() > SAMPLE_SIZE) {
                sleepSamples.removeFirst();
            }
        }
    }

    private static double average(Collection<Double> samples) {
        double accumulator = 0;
        for (double sample : samples) {
            accumulator = accumulator + sample;
        }
        return accumulator / samples.size();
    }
}
