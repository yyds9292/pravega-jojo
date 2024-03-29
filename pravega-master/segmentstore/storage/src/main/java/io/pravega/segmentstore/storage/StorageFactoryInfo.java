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
package io.pravega.segmentstore.storage;

import lombok.Builder;
import lombok.Data;

/**
 * Information about the capabilities supported by a {@link StorageFactory}.
 */
@Data
@Builder
public class StorageFactoryInfo {
    /**
     * Name of storage binding.
     * This name is used in config file to uniquely identify storage binding to load.
     */
    private final String name;

    /**
     * Type of storage layout supported.
     */
    private final StorageLayoutType storageLayoutType;

}
