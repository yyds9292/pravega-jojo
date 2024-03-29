/**
 * Copyright Pravega Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.pravega.segmentstore.storage.chunklayer;

/**
 * Exception thrown when chunk storage is full.
 */
public class ChunkStorageFullException extends ChunkStorageException {
    /**
     * Creates a new instance of the exception.
     *
     * @param message   The message for this exception.
     */
    public ChunkStorageFullException(String message) {
        super("", String.format("Chunk storage is full. %s", message));
    }

    /**
     * Creates a new instance of the exception.
     *
     * @param message   The message for this exception.
     * @param cause     The causing exception.
     */
    public ChunkStorageFullException(String message, Throwable cause) {
        super("", String.format("Chunk storage is full - %s", "", message), cause);
    }
}
