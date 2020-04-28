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

package uk.co.n3fs.mc.wolfstats.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class Files {

    public static void copyDefault(Path configDir, String resourceName) {
        if (java.nio.file.Files.notExists(configDir)) {
            try {
                java.nio.file.Files.createDirectory(configDir);
            } catch (IOException e) {
                throw new IllegalStateException("Could not create config directory", e);
            }
        }

        Path resourcePath = configDir.resolve(resourceName);

        if (java.nio.file.Files.notExists(resourcePath)) {
            try (InputStream input = Files.class.getResourceAsStream("/" + resourceName)) {
                java.nio.file.Files.copy(input, resourcePath);
            } catch (IOException e) {
                throw new IllegalStateException("Could not create default config file", e);
            }
        }
    }

}
