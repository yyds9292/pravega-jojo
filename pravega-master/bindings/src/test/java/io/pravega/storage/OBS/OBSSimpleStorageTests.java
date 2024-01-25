package io.pravega.storage.OBS;

import io.pravega.segmentstore.storage.chunklayer.*;
import io.pravega.storage.azure.AzureChunkStorage;
import io.pravega.storage.obs.OBSChunkStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link OBSChunkStorage} based {@link io.pravega.segmentstore.storage.Storage}.
 */

public class OBSSimpleStorageTests extends SimpleStorageTests {
    private OBSTestContext testContext = null;

    @Override
    @Before
    public void before() throws Exception {
        this.testContext = new OBSTestContext();
        super.before();
    }
    @Override
    @After
    public void after() throws Exception {
        if (this.testContext != null) {
            this.testContext.close();
        }
        super.after();
    }
    @Override
    protected ChunkStorage getChunkStorage() {
        return new OBSChunkStorage(testContext.obsClient, testContext.obsConfig, executorService(), false);
    }

    protected ChunkedSegmentStorageConfig getDefaultConfig() {
        return this.testContext.defaultConfig;
    }

    /**
     * {@link ChunkedRollingStorageTests} tests for {@link OBSChunkStorage} based {@link io.pravega.segmentstore.storage.Storage}.
     */
    public static class OBSStorageRollingTests extends ChunkedRollingStorageTests {
        private OBSTestContext testContext = null;

        @Before
        public void setUp() throws Exception {
            this.testContext = new OBSTestContext();
        }

        @After
        public void tearDown() throws Exception {
            if (this.testContext != null) {
                this.testContext.close();
            }
        }

        @Override
        protected ChunkStorage getChunkStorage() {
            return new OBSChunkStorage(testContext.obsClient, testContext.obsConfig, executorService(), false);
        }

        @Override
        protected ChunkedSegmentStorageConfig getDefaultConfig() {
            return this.testContext.defaultConfig;
        }
    }

    /**
     * {@link ChunkStorageTests} tests for {@link OBSChunkStorage} based {@link io.pravega.segmentstore.storage.Storage}.
     */
    public static class OBSChunkStorageTests extends ChunkStorageTests {
        private OBSTestContext testContext = null;

        @Override
        @Before
        public void before() throws Exception {
            this.testContext = new OBSTestContext();
            super.before();
        }

        @Override
        @After
        public void after() throws Exception {
            if (this.testContext != null) {
                this.testContext.close();
            }
            super.after();
        }

        @Override
        protected ChunkStorage createChunkStorage() {
            return new OBSChunkStorage(testContext.obsClient, testContext.obsConfig, executorService(), false);
        }

        @Override
        protected int getMinimumConcatSize() {
            return Math.max(1, Math.toIntExact(this.testContext.defaultConfig.getMinSizeLimitForConcat()));
        }

        /**
         * Test default capabilities.
         */
        @Override
        @Test
        public void testCapabilities() {
            assertFalse(getChunkStorage().supportsAppend());
            assertFalse(getChunkStorage().supportsTruncation());
            assertTrue(getChunkStorage().supportsConcat());
        }
    }

    /**
     * {@link SystemJournalTests} tests for {@link OBSChunkStorage} based {@link io.pravega.segmentstore.storage.Storage}.
     */
    public static class OBSChunkStorageSystemJournalTests extends SystemJournalTests {
        private OBSTestContext testContext = null;

        @Override
        @Before
        public void before() throws Exception {
            this.testContext = new OBSTestContext();
            super.before();
        }

        @Override
        @After
        public void after() throws Exception {
            if (this.testContext != null) {
                this.testContext.close();
            }
            super.after();
        }

        @Override
        protected ChunkStorage getChunkStorage() {
            return new OBSChunkStorage(testContext.obsClient, testContext.obsConfig, executorService(), false);
        }
    }
}

