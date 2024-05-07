package com.intuit.craft.urlshortner.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.intuit.craft.urlshortner.cache.Cache;
import com.intuit.craft.urlshortner.exceptions.service.UserCreationException;
import com.intuit.craft.urlshortner.exceptions.service.UserNotFoundException;
import com.intuit.craft.urlshortner.models.bo.UserBO;
import com.intuit.craft.urlshortner.models.bo.UserCacheBO;
import com.intuit.craft.urlshortner.models.dto.request.CreateUserRequest;
import com.intuit.craft.urlshortner.models.dto.request.UpdateUserRequest;
import com.intuit.craft.urlshortner.models.entity.UserEntity;
import com.intuit.craft.urlshortner.repository.UserDataAccess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserServiceImplTest {
    @MockBean
    private Cache cache;

    @MockBean
    private UserDataAccess userDataAccess;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testCreateUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        userEntity.setEmail("https://example.org/example");
        userEntity.setId(ObjectId.get());
        userEntity.setName("https://example.org/example");
        userEntity.setTps(1);
        userEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userDataAccess.addToDB(Mockito.<UserBO>any())).thenReturn(ofResult);
        userServiceImpl.createUser(new CreateUserRequest());
        verify(userDataAccess).addToDB(isA(UserBO.class));
    }

    @Test
    void testCreateUser2() {
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getId()).thenReturn(ObjectId.get());
        doNothing().when(userEntity).setCreatedAt(Mockito.<LocalDateTime>any());
        doNothing().when(userEntity).setId(Mockito.<ObjectId>any());
        doNothing().when(userEntity).setUpdatedAt(Mockito.<LocalDateTime>any());
        doNothing().when(userEntity).setEmail(Mockito.<String>any());
        doNothing().when(userEntity).setName(Mockito.<String>any());
        doNothing().when(userEntity).setTps(Mockito.<Integer>any());
        userEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        userEntity.setEmail("https://example.org/example");
        userEntity.setId(ObjectId.get());
        userEntity.setName("https://example.org/example");
        userEntity.setTps(1);
        userEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userDataAccess.addToDB(Mockito.<UserBO>any())).thenReturn(ofResult);
        userServiceImpl.createUser(new CreateUserRequest());
        verify(userEntity).getId();
        verify(userEntity).setCreatedAt(isA(LocalDateTime.class));
        verify(userEntity).setId(isA(ObjectId.class));
        verify(userEntity).setUpdatedAt(isA(LocalDateTime.class));
        verify(userEntity).setEmail(eq("https://example.org/example"));
        verify(userEntity).setName(eq("https://example.org/example"));
        verify(userEntity).setTps(eq(1));
        verify(userDataAccess).addToDB(isA(UserBO.class));
    }


    @Test
    void testCreateUser3() {
        Optional<UserEntity> emptyResult = Optional.empty();
        when(userDataAccess.addToDB(Mockito.<UserBO>any())).thenReturn(emptyResult);
        assertThrows(UserCreationException.class, () -> userServiceImpl.createUser(new CreateUserRequest()));
        verify(userDataAccess).addToDB(isA(UserBO.class));
    }

    @Test
    void testGetUserCachedObject() {
        doNothing().when(cache).put(Mockito.<String>any(), Mockito.<UserCacheBO>any());
        UserCacheBO userCacheBO = new UserCacheBO();
        Optional<UserCacheBO> ofResult = Optional.of(userCacheBO);
        when(cache.get(Mockito.<String>any(), Mockito.<Class<UserCacheBO>>any())).thenReturn(ofResult);
        UserCacheBO actualUserCachedObject = userServiceImpl.getUserCachedObject("https://example.org/example");
        verify(cache).get(eq("https://example.org/example"), isA(Class.class));
        verify(cache).put(eq("https://example.org/example"), isA(UserCacheBO.class));
        assertSame(userCacheBO, actualUserCachedObject);
    }

    @Test
    void testGetUserCachedObject2() {
        doThrow(new UserCreationException("https://example.org/example", "https://example.org/example")).when(cache)
                .put(Mockito.<String>any(), Mockito.<UserCacheBO>any());
        Optional<UserCacheBO> ofResult = Optional.of(new UserCacheBO());
        when(cache.get(Mockito.<String>any(), Mockito.<Class<UserCacheBO>>any())).thenReturn(ofResult);

        assertThrows(UserCreationException.class, () -> userServiceImpl.getUserCachedObject("https://example.org/example"));
        verify(cache).get(eq("https://example.org/example"), isA(Class.class));
        verify(cache).put(eq("https://example.org/example"), isA(UserCacheBO.class));
    }

    @Test
    void testGetUserCachedObject3() {
        doNothing().when(cache).put(Mockito.<String>any(), Mockito.<Object>any());
        doNothing().when(cache).put(Mockito.<String>any(), Mockito.<UserCacheBO>any());
        Optional<UserCacheBO> emptyResult = Optional.empty();
        when(cache.get(Mockito.<String>any(), Mockito.<Class<UserCacheBO>>any())).thenReturn(emptyResult);

        UserEntity userEntity = new UserEntity();
        userEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        userEntity.setEmail("https://example.org/example");
        userEntity.setId(ObjectId.get());
        userEntity.setName("https://example.org/example");
        userEntity.setTps(1);
        userEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userDataAccess.findUserById(Mockito.<String>any())).thenReturn(ofResult);
        UserCacheBO actualUserCachedObject = userServiceImpl.getUserCachedObject("https://example.org/example");
        verify(cache).get(eq("https://example.org/example"), isA(Class.class));
        verify(cache).put(eq("https://example.org/example"), isA(UserCacheBO.class));
        verify(userDataAccess).findUserById(eq("https://example.org/example"));
        assertEquals(1, actualUserCachedObject.getTps().intValue());
    }

    @Test
    void testGetUserCachedObject4() {
        Optional<UserCacheBO> emptyResult = Optional.empty();
        when(cache.get(Mockito.<String>any(), Mockito.<Class<UserCacheBO>>any())).thenReturn(emptyResult);
        when(userDataAccess.findUserById(Mockito.<String>any()))
                .thenThrow(new UserCreationException("https://example.org/example", "https://example.org/example"));

        assertThrows(UserCreationException.class, () -> userServiceImpl.getUserCachedObject("https://example.org/example"));
        verify(cache).get(eq("https://example.org/example"), isA(Class.class));
        verify(userDataAccess).findUserById(eq("https://example.org/example"));
    }

    @Test
    void testGetUserCachedObject5() {
        doNothing().when(cache).put(Mockito.<String>any(), Mockito.<Object>any());
        doNothing().when(cache).put(Mockito.<String>any(), Mockito.<UserCacheBO>any());
        Optional<UserCacheBO> emptyResult = Optional.empty();
        when(cache.get(Mockito.<String>any(), Mockito.<Class<UserCacheBO>>any())).thenReturn(emptyResult);
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getTps()).thenReturn(1);
        doNothing().when(userEntity).setCreatedAt(Mockito.<LocalDateTime>any());
        doNothing().when(userEntity).setId(Mockito.<ObjectId>any());
        doNothing().when(userEntity).setUpdatedAt(Mockito.<LocalDateTime>any());
        doNothing().when(userEntity).setEmail(Mockito.<String>any());
        doNothing().when(userEntity).setName(Mockito.<String>any());
        doNothing().when(userEntity).setTps(Mockito.<Integer>any());
        userEntity.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        userEntity.setEmail("https://example.org/example");
        userEntity.setId(ObjectId.get());
        userEntity.setName("https://example.org/example");
        userEntity.setTps(1);
        userEntity.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userDataAccess.findUserById(Mockito.<String>any())).thenReturn(ofResult);
        UserCacheBO actualUserCachedObject = userServiceImpl.getUserCachedObject("https://example.org/example");
        verify(cache).get(eq("https://example.org/example"), isA(Class.class));
        verify(cache).put(eq("https://example.org/example"), isA(UserCacheBO.class));
        verify(userEntity).setCreatedAt(isA(LocalDateTime.class));
        verify(userEntity).setId(isA(ObjectId.class));
        verify(userEntity).setUpdatedAt(isA(LocalDateTime.class));
        verify(userEntity).getTps();
        verify(userEntity).setEmail(eq("https://example.org/example"));
        verify(userEntity).setName(eq("https://example.org/example"));
        verify(userEntity).setTps(eq(1));
        verify(userDataAccess).findUserById(eq("https://example.org/example"));
        assertEquals(1, actualUserCachedObject.getTps().intValue());
    }

    @Test
    void testGetUserCachedObject6() {
        Optional<UserCacheBO> emptyResult = Optional.empty();
        when(cache.get(Mockito.<String>any(), Mockito.<Class<UserCacheBO>>any())).thenReturn(emptyResult);
        Optional<UserEntity> emptyResult2 = Optional.empty();
        when(userDataAccess.findUserById(Mockito.<String>any())).thenReturn(emptyResult2);
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserCachedObject("https://example.org/example"));
        verify(cache).get(eq("https://example.org/example"), isA(Class.class));
        verify(userDataAccess).findUserById(eq("https://example.org/example"));
    }

    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser() {
        userServiceImpl.updateUser(new UpdateUserRequest());
    }
}
