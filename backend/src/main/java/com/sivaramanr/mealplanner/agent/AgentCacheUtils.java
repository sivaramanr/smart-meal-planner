package com.sivaramanr.mealplanner.agent;

import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AgentCacheUtils {

    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> CACHE = new ConcurrentHashMap<>();

    public static Object get(String sessionId, String key) {
        ConcurrentHashMap<String, Object> sessionCache = CACHE.get(sessionId);
        if (sessionCache == null) {
            return null;
        }
        return sessionCache.get(key);
    }

    public static Object put(String sessionId, String key, Object value) {
        ConcurrentHashMap<String, Object> sessionCache = CACHE.computeIfAbsent(sessionId, k -> new ConcurrentHashMap<>());
        return sessionCache.put(key, value);
    }

    public static void remove(String sessionId, String key) {
        ConcurrentHashMap<String, Object> sessionCache = CACHE.get(sessionId);
        if (sessionCache != null) {
            sessionCache.remove(key);
        }
    }

    public static void clear(String sessionId) {
        CACHE.remove(sessionId);
    }

}
