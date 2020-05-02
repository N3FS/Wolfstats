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

public interface Scheduler {

    /**
     * Schedules a repeating task.
     *
     * @param name The name of the task to run
     * @param runnable The task to run
     * @param repeatMs The interval between executions of the task, in milliseconds
     * @throws IllegalStateException if a task with the given name already exists
     */
    void scheduleRepeatingTask(String name, Runnable runnable, long repeatMs);

    /**
     * Cancels a pending task, interrupting the task if it is currently running.
     * If no task with this name exists or the task already terminated, this method will silently fail.
     *
     * @param name The name of the task to cancel
     */
    void cancelTask(String name);

    /**
     * Cancels all pending tasks, interrupting any currently-running tasks.
     * This method will also shut down the executor thread if necessary.
     */
    void shutdown();

}
