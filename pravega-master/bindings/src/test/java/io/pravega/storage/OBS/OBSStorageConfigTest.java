package io.pravega.storage.OBS;

import io.pravega.common.util.ConfigBuilder;
import io.pravega.common.util.Property;
import io.pravega.storage.obs.OBSStorageConfig;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OBSStorageConfigTest {
    @Test
    public void testDefaultOBSConfig() {
        ConfigBuilder<OBSStorageConfig> builder = OBSStorageConfig.builder();
        builder.with(Property.named("configUri"), "http://127.0.0.1:9020")
                .with(Property.named("bucket"), "testBucket")
                .with(Property.named("prefix"), "testPrefix");
        OBSStorageConfig config = builder.build();
        assertEquals("testBucket", config.getBucket());
        assertEquals("testPrefix/", config.getPrefix());
        assertEquals("us-east-1", config.getRegion());
        assertEquals(false, config.isShouldOverrideUri());
    }

    @Test
    public void testConstructOBSConfig() {
        ConfigBuilder<OBSStorageConfig> builder = OBSStorageConfig.builder();
        builder.with(Property.named("connect.config.uri"), "http://example.com")
                .with(Property.named("bucket"), "testBucket")
                .with(Property.named("prefix"), "testPrefix")
                .with(Property.named("connect.config.region"), "my-region")
                .with(Property.named("connect.config.access.key"), "key")
                .with(Property.named("connect.config.secret.key"), "secret")
                .with(Property.named("connect.config.role"), "role")
                .with(Property.named("connect.config.uri.override"), true)
                .with(Property.named("connect.config.assumeRole.enable"), true);
        OBSStorageConfig config = builder.build();
        assertEquals("testBucket", config.getBucket());
        assertEquals("testPrefix/", config.getPrefix());
        assertEquals("my-region", config.getRegion());
        assertEquals(true, config.isShouldOverrideUri());
        assertEquals("http://example.com", config.getObsConfig());
        assertEquals( "my-region", config.getRegion());
        assertEquals( "key", config.getAccessKey());
        assertEquals( "secret", config.getSecretKey());
        assertEquals( "role", config.getUserRole());
        assertEquals( true, config.isAssumeRoleEnabled());
    }
}
