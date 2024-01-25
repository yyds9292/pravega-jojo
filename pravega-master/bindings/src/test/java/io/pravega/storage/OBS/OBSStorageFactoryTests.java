package io.pravega.storage.OBS;

import io.pravega.segmentstore.storage.*;
import io.pravega.segmentstore.storage.chunklayer.ChunkedSegmentStorage;
import io.pravega.segmentstore.storage.chunklayer.ChunkedSegmentStorageConfig;
import io.pravega.segmentstore.storage.mocks.InMemoryMetadataStore;
import io.pravega.storage.obs.OBSChunkStorage;
import io.pravega.storage.obs.OBSSimpleStorageFactory;
import io.pravega.storage.obs.OBSStorageConfig;
import io.pravega.storage.obs.OBSStorageFactoryCreator;
import io.pravega.test.common.AssertExtensions;
import io.pravega.test.common.ThreadPooledTestSuite;
import lombok.Cleanup;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;
public class OBSStorageFactoryTests extends ThreadPooledTestSuite {
    @Override
    protected int getThreadPoolSize() {
        return 1;
    }

    @Test
    public void testOBSStorageFactoryCreatorWithoutRole() {
        val config = OBSStorageConfig.builder()
                .with(OBSStorageConfig.CONFIGURI, "http://127.0.0.1")
                .with(OBSStorageConfig.BUCKET, "bucket")
                .with(OBSStorageConfig.PREFIX, "samplePrefix")
                .with(OBSStorageConfig.ACCESS_KEY, "user")
                .with(OBSStorageConfig.SECRET_KEY, "secret")
                .build();

        testOBSStorageFactoryCreator(config);
    }

    @Test
    public void testOBSStorageFactoryCreatorWithRole() {
        val config = OBSStorageConfig.builder()
                .with(OBSStorageConfig.CONFIGURI, "http://127.0.0.1")
                .with(OBSStorageConfig.ASSUME_ROLE, true)
                .with(OBSStorageConfig.BUCKET, "bucket")
                .with(OBSStorageConfig.PREFIX, "samplePrefix")
                .with(OBSStorageConfig.ACCESS_KEY, "user")
                .with(OBSStorageConfig.SECRET_KEY, "secret")
                .with(OBSStorageConfig.USER_ROLE, "role")
                .build();

        testOBSStorageFactoryCreator(config);
    }

    private void testOBSStorageFactoryCreator(OBSStorageConfig config) {
        StorageFactoryCreator factoryCreator = new OBSStorageFactoryCreator();
        val expected = new StorageFactoryInfo[]{
                StorageFactoryInfo.builder()
                        .name("OBS")
                        .storageLayoutType(StorageLayoutType.CHUNKED_STORAGE)
                        .build()
        };

        val factoryInfoList = factoryCreator.getStorageFactories();
        Assert.assertEquals(1, factoryInfoList.length);
        Assert.assertArrayEquals(expected, factoryInfoList);

        // Simple Storage
        ConfigSetup configSetup1 = mock(ConfigSetup.class);
        when(configSetup1.getConfig(any())).thenReturn(ChunkedSegmentStorageConfig.DEFAULT_CONFIG, config);
        val factory1 = factoryCreator.createFactory(expected[0], configSetup1, executorService());
        Assert.assertTrue(factory1 instanceof OBSSimpleStorageFactory);
        Assert.assertEquals(executorService(), ((OBSSimpleStorageFactory) factory1).getExecutor());
        Assert.assertEquals(ChunkedSegmentStorageConfig.DEFAULT_CONFIG, ((OBSSimpleStorageFactory) factory1).getChunkedSegmentStorageConfig());

        // Rolling Storage
        AssertExtensions.assertThrows(
                "createFactory should throw UnsupportedOperationException.",
                () -> factoryCreator.createFactory(StorageFactoryInfo.builder()
                        .name("OBS")
                        .storageLayoutType(StorageLayoutType.ROLLING_STORAGE)
                        .build(), configSetup1, executorService()),
                ex -> ex instanceof UnsupportedOperationException);

        @Cleanup
        Storage storage1 = ((OBSSimpleStorageFactory) factory1).createStorageAdapter(42, new InMemoryMetadataStore(ChunkedSegmentStorageConfig.DEFAULT_CONFIG, executorService()));
        Assert.assertTrue(storage1 instanceof ChunkedSegmentStorage);
        Assert.assertTrue(((ChunkedSegmentStorage) storage1).getChunkStorage() instanceof OBSChunkStorage);

        AssertExtensions.assertThrows(
                "createStorageAdapter should throw UnsupportedOperationException.",
                () -> factory1.createStorageAdapter(),
                ex -> ex instanceof UnsupportedOperationException);
    }

    @Test
    public void testNull() {
    }
}

