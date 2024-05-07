package com.intuit.craft.urlshortner.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intuit.craft.urlshortner.cache.Cache;
import com.intuit.craft.urlshortner.cache.DistributedCache;
import com.intuit.craft.urlshortner.models.bo.ResolveUrlBo;
import com.intuit.craft.urlshortner.repository.CustomUrlDataAccess;
import com.intuit.craft.urlshortner.repository.UrlDataAccess;
import com.intuit.craft.urlshortner.service.Conversion;
import com.intuit.craft.urlshortner.service.RateLimiter;
import com.intuit.craft.urlshortner.service.UserService;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UrlServiceImplTest {
    @Mock
    private Cache cache;
    @Mock
    private UrlDataAccess urlDao;
    @Mock
    private Conversion conversion;
    @Mock
    private UserService userService;
    @Mock
    private RateLimiter rateLimiter;
    @Mock
    private DistributedCache distributedCache;
    @Mock
    private CustomUrlDataAccess customUrlDataAccess;

    @InjectMocks
    private UrlServiceImpl urlService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvertToLongUrl_SuccessFromCache() throws Exception {
        String path = "testPath";
        ResolveUrlBo mockUrlBo = new ResolveUrlBo();
        mockUrlBo.setLongUrl("http://example.com");

        when(cache.get(anyString(), (TypeReference<Object>) any())).thenReturn(Optional.of(mockUrlBo));

        String result = urlService.convertToLongUrl(path);

        assertEquals("http://ashish.com", result);
        verify(urlDao, never()).findByBasePath(anyInt());
    }





}
