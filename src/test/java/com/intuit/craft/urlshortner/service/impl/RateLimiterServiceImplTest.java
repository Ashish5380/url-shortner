package com.intuit.craft.urlshortner.service.impl;

import static org.mockito.Mockito.when;

import com.intuit.craft.urlshortner.cache.DistributedCache;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RateLimiterServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RateLimiterServiceImplTest {
    @MockBean
    private DistributedCache distributedCache;

    @Autowired
    private RateLimiterServiceImpl rateLimiterServiceImpl;

    @Test
    @Disabled("TODO: Complete this test")
    void testIsTooManyRequests() {
        when(distributedCache.atomicInteger(Mockito.<String>any())).thenReturn(null);
        rateLimiterServiceImpl.isTooManyRequests("https://ashish.com/test", 1L);
    }
}
