package sn.gainde2000.backenmfpai.security.services;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private static final Logger LOGGER = LogManager.getLogger(LoginAttemptService.class);
    private static final int MAX_ATTEMPTS = 3;
    private static final int DURATION_TIME_MINUTES = 5;

    private final LoadingCache<String, Integer> loadingCache;

    public LoginAttemptService() {
        this.loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(DURATION_TIME_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void loginSucceeded(String key) {
        loadingCache.invalidate(key);
    }

    public void loginFailed(String key) {
        try {
            int attempts = loadingCache.get(key) + 1;
            loadingCache.put(key, attempts);
        } catch (ExecutionException e) {
            LOGGER.warn("Erreur lors de la récupération du nombre de tentatives pour la clé: {}", key, e);
            loadingCache.put(key, 1);
        }
    }

    public boolean isBlocked(String key) {
        try {
            return loadingCache.get(key) >= MAX_ATTEMPTS;
        } catch (ExecutionException e) {
            LOGGER.warn("Erreur lors de la vérification du blocage pour la clé: {}", key, e);
            return false;
        }
    }
}