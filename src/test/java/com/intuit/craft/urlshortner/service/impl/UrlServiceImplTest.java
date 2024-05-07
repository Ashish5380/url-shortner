package com.intuit.craft.urlshortner.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.craft.urlshortner.cache.Cache;
import com.intuit.craft.urlshortner.cache.DistributedCache;
import com.intuit.craft.urlshortner.cache.impl.RedisCacheImpl;
import com.intuit.craft.urlshortner.exceptions.fatal.CacheOperationException;
import com.intuit.craft.urlshortner.exceptions.service.UrlNotFoundException;
import com.intuit.craft.urlshortner.models.bo.ResolveUrlBo;
import com.intuit.craft.urlshortner.models.dto.request.LongUrlUpdateRequest;
import com.intuit.craft.urlshortner.models.dto.request.ShortenUrlRequest;
import com.intuit.craft.urlshortner.models.entity.CustomUrlEntity;
import com.intuit.craft.urlshortner.models.entity.UrlEntity;
import com.intuit.craft.urlshortner.repository.CustomUrlDataAccess;
import com.intuit.craft.urlshortner.repository.UrlDataAccess;
import com.intuit.craft.urlshortner.repository.impl.CustomUrlDataAccessImpl;
import com.intuit.craft.urlshortner.repository.impl.UrlDataAccessImpl;
import com.intuit.craft.urlshortner.repository.impl.UserDataAccessImpl;
import com.intuit.craft.urlshortner.repository.mongo.CustomUrlMongoRepository;
import com.intuit.craft.urlshortner.repository.mongo.UrlMongoRepository;
import com.intuit.craft.urlshortner.repository.mongo.UserMongoRepository;
import com.intuit.craft.urlshortner.service.Conversion;
import com.intuit.craft.urlshortner.service.RateLimiter;
import com.intuit.craft.urlshortner.service.UserService;

import java.time.LocalDate;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UrlServiceImpl.class, Cache.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UrlServiceImplTest {
    @MockBean
    private Cache cache;

    @MockBean
    private Conversion conversion;

    @MockBean
    private CustomUrlDataAccess customUrlDataAccess;

    @MockBean
    private DistributedCache distributedCache;

    @MockBean
    private RateLimiter rateLimiter;

    @MockBean
    private UrlDataAccess urlDataAccess;

    @Autowired
    private UrlServiceImpl urlServiceImpl;

    @MockBean
    private UserService userService;

    @Test
    @Disabled("TODO: Complete this test")
    void testConvertToShortUrl() {
        urlServiceImpl.convertToShortUrl(new ShortenUrlRequest());
    }

    @Test
    void testConvertToLongUrl() {
        RedisCacheImpl cache = mock(RedisCacheImpl.class);
        Optional<ResolveUrlBo> emptyResult = Optional.empty();
        when(cache.get(Mockito.<String>any(), Mockito.<Class<ResolveUrlBo>>any())).thenReturn(emptyResult);

        CustomUrlEntity customUrlEntity = new CustomUrlEntity();
        customUrlEntity.setActualUrl("https://ashish.com/test");
        customUrlEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customUrlEntity.setExpiry(LocalDate.of(1970, 1, 1).atStartOfDay());
        customUrlEntity.setId(ObjectId.get());
        customUrlEntity.setShortUrl("https://ashish.com/test");
        customUrlEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customUrlEntity.setUserId("https://ashish.com/test");
        Optional<CustomUrlEntity> ofResult = Optional.of(customUrlEntity);
        CustomUrlMongoRepository urlMongoRepository = mock(CustomUrlMongoRepository.class);
        when(urlMongoRepository.findDistinctByShortUrl(Mockito.<String>any())).thenReturn(ofResult);
        CustomUrlDataAccessImpl customUrlDataAccess = new CustomUrlDataAccessImpl(urlMongoRepository);
        UrlDataAccessImpl urlDao = new UrlDataAccessImpl(mock(UrlMongoRepository.class));
        ConversionImpl conversion = new ConversionImpl();
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        RedisCacheImpl cache2 = new RedisCacheImpl(redisTemplate, new ObjectMapper());

        UserServiceImpl userService = new UserServiceImpl(cache2,
                new UserDataAccessImpl(mock(UserMongoRepository.class), null));

        RateLimiter rateLimiter = mock(RateLimiter.class);
        RedisTemplate<String, String> redisTemplate2 = new RedisTemplate<>();
        assertThrows(CacheOperationException.class,
                () -> (new UrlServiceImpl(cache, urlDao, conversion, userService, rateLimiter,
                        new RedisCacheImpl(redisTemplate2, new ObjectMapper()), customUrlDataAccess))
                        .convertToLongUrl("https://ashish.com/test"));
        verify(cache).get(eq("https://ashish.com/test"), isA(Class.class));
        verify(urlMongoRepository).findDistinctByShortUrl(eq("https://ashish.com/test"));
    }

    @Test
    @Disabled("TODO: Complete this test")
    void testConvertToLongUrl2() {
        ResolveUrlBo.ResolveUrlBoBuilder builderResult = ResolveUrlBo.builder();
        ResolveUrlBo buildResult = builderResult.expiry(LocalDate.of(1970, 1, 1).atStartOfDay())
                .longUrl("https://ashish.com/test")
                .tps(1L)
                .build();
        Optional<ResolveUrlBo> ofResult = Optional.of(buildResult);
        when(cache.get(Mockito.<String>any(), Mockito.<Class<ResolveUrlBo>>any())).thenReturn(ofResult);
        urlServiceImpl.convertToLongUrl("https://ashish.com/test");
    }

    @Test
    void testUpdateLongUrl() {
        Cache cache = mock(Cache.class);
        doNothing().when(cache).put(Mockito.<String>any(), Mockito.<String>any());

        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setActualUrl("https://ashish.com/test");
        urlEntity.setBaseValue(42);
        urlEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        urlEntity.setExpiry(LocalDate.of(1970, 1, 1).atStartOfDay());
        urlEntity.setId(ObjectId.get());
        urlEntity.setShortUrl("https://ashish.com/test");
        urlEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        urlEntity.setUserId("https://ashish.com/test");
        Optional<UrlEntity> ofResult = Optional.of(urlEntity);

        UrlEntity urlEntity2 = new UrlEntity();
        urlEntity2.setActualUrl("https://ashish.com/test");
        urlEntity2.setBaseValue(42);
        urlEntity2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        urlEntity2.setExpiry(LocalDate.of(1970, 1, 1).atStartOfDay());
        urlEntity2.setId(ObjectId.get());
        urlEntity2.setShortUrl("https://ashish.com/test");
        urlEntity2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        urlEntity2.setUserId("https://ashish.com/test");
        UrlMongoRepository urlMongoRepository = mock(UrlMongoRepository.class);
        when(urlMongoRepository.save(Mockito.<UrlEntity>any())).thenReturn(urlEntity2);
        when(urlMongoRepository.findDistinctByBaseValue(Mockito.<Integer>any())).thenReturn(ofResult);
        UrlDataAccessImpl urlDao = new UrlDataAccessImpl(urlMongoRepository);
        ConversionImpl conversion = new ConversionImpl();
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        RedisCacheImpl cache2 = new RedisCacheImpl(redisTemplate, new ObjectMapper());

        UserServiceImpl userService = new UserServiceImpl(cache2,
                new UserDataAccessImpl(mock(UserMongoRepository.class), null));

        RateLimiter rateLimiter = mock(RateLimiter.class);
        RedisTemplate<String, String> redisTemplate2 = new RedisTemplate<>();
        RedisCacheImpl distributedCache = new RedisCacheImpl(redisTemplate2, new ObjectMapper());

        UrlServiceImpl urlServiceImpl = new UrlServiceImpl(cache, urlDao, conversion, userService, rateLimiter,
                distributedCache, new CustomUrlDataAccessImpl(mock(CustomUrlMongoRepository.class)));
        String actualUpdateLongUrlResult = urlServiceImpl
                .updateLongUrl(new LongUrlUpdateRequest("https://ashish.com/test", 1, "https://ashish.com/test"));
        verify(cache).put(eq("test"), eq("https://ashish.com/test"));
        verify(urlMongoRepository).findDistinctByBaseValue(eq(6967093));
        verify(urlMongoRepository).save(isA(UrlEntity.class));
        assertEquals("http://localhost:9015/url/r/test", actualUpdateLongUrlResult);
    }

    @Test
    void testUpdateLongUrl2() {
        UrlMongoRepository urlMongoRepository = mock(UrlMongoRepository.class);
        Optional<UrlEntity> emptyResult = Optional.empty();
        when(urlMongoRepository.findDistinctByBaseValue(Mockito.<Integer>any())).thenReturn(emptyResult);
        UrlDataAccessImpl urlDao = new UrlDataAccessImpl(urlMongoRepository);
        Cache cache = mock(Cache.class);
        ConversionImpl conversion = new ConversionImpl();
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        RedisCacheImpl cache2 = new RedisCacheImpl(redisTemplate, new ObjectMapper());

        UserServiceImpl userService = new UserServiceImpl(cache2,
                new UserDataAccessImpl(mock(UserMongoRepository.class), null));

        RateLimiter rateLimiter = mock(RateLimiter.class);
        RedisTemplate<String, String> redisTemplate2 = new RedisTemplate<>();
        RedisCacheImpl distributedCache = new RedisCacheImpl(redisTemplate2, new ObjectMapper());

        UrlServiceImpl urlServiceImpl = new UrlServiceImpl(cache, urlDao, conversion, userService, rateLimiter,
                distributedCache, new CustomUrlDataAccessImpl(mock(CustomUrlMongoRepository.class)));
        assertThrows(UrlNotFoundException.class, () -> urlServiceImpl
                .updateLongUrl(new LongUrlUpdateRequest("https://ashish.com/test", 1, "https://ashish.com/test")));
        verify(urlMongoRepository).findDistinctByBaseValue(eq(6967093));
    }

    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateLongUrl3() {
        urlServiceImpl.updateLongUrl(new LongUrlUpdateRequest());
    }
}
