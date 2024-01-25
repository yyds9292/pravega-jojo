package io.pravega.storage.OBS;

import com.obs.services.ObsClient;
import io.pravega.segmentstore.storage.chunklayer.ChunkedSegmentStorageConfig;
import io.pravega.storage.obs.OBSStorageConfig;
import io.pravega.test.common.TestUtils;


import java.util.UUID;
public class OBSTestContext {
    public static final String BUCKET_NAME_PREFIX = "pravega-unit-test/";

    public final OBSStorageConfig obsConfig;

    public final int port;
    public final ObsClient obsClient;
    public final String configUri;

    public final ChunkedSegmentStorageConfig defaultConfig = ChunkedSegmentStorageConfig.DEFAULT_CONFIG;

    public OBSTestContext()throws Exception {
        try {
            this.port = TestUtils.getAvailableListenPort();
            this.configUri = "https://localhost";
            String bucketName = "test-bucket";
            String prefix = BUCKET_NAME_PREFIX + UUID.randomUUID();
            this.obsConfig = OBSStorageConfig.builder()
                    .with(OBSStorageConfig.CONFIGURI, configUri)
                    .with(OBSStorageConfig.BUCKET, bucketName)
                    .with(OBSStorageConfig.PREFIX, prefix)
                    .with(OBSStorageConfig.ACCESS_KEY, "access")
                    .with(OBSStorageConfig.SECRET_KEY, "secret")
                    .build();
                   obsClient = null;
        } catch (Exception e) {
            close();
            throw e;
        }
    }

    public void close() throws Exception {
        if (null != obsClient) {
            obsClient.close();
        }
    }
}
