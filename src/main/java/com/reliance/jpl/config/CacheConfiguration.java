package com.reliance.jpl.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.reliance.jpl.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.reliance.jpl.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.reliance.jpl.domain.User.class.getName());
            createCache(cm, com.reliance.jpl.domain.Authority.class.getName());
            createCache(cm, com.reliance.jpl.domain.User.class.getName() + ".authorities");
            createCache(cm, com.reliance.jpl.domain.Client.class.getName());
            createCache(cm, com.reliance.jpl.domain.Client.class.getName() + ".campaigns");
            createCache(cm, com.reliance.jpl.domain.Campaign.class.getName());
            createCache(cm, com.reliance.jpl.domain.Campaign.class.getName() + ".attributes");
            createCache(cm, com.reliance.jpl.domain.Lead.class.getName());
            createCache(cm, com.reliance.jpl.domain.Lead.class.getName() + ".attributes");
            createCache(cm, com.reliance.jpl.domain.Lead.class.getName() + ".locations");
            createCache(cm, com.reliance.jpl.domain.AttributeKey.class.getName());
            createCache(cm, com.reliance.jpl.domain.AttributeKey.class.getName() + ".attributePossibleValues");
            createCache(cm, com.reliance.jpl.domain.AttributePossibleValue.class.getName());
            createCache(cm, com.reliance.jpl.domain.Attribute.class.getName());
            createCache(cm, com.reliance.jpl.domain.Location.class.getName());
            createCache(cm, com.reliance.jpl.domain.Location.class.getName() + ".leads");
            createCache(cm, com.reliance.jpl.domain.Telecaller.class.getName());
            createCache(cm, com.reliance.jpl.domain.Telecaller.class.getName() + ".telecallerInOuts");
            createCache(cm, com.reliance.jpl.domain.TelecallerInOut.class.getName());
            createCache(cm, com.reliance.jpl.domain.TelecallerAssignment.class.getName());
            createCache(cm, com.reliance.jpl.domain.LeadAssociation.class.getName());
            createCache(cm, com.reliance.jpl.domain.LeadAssignment.class.getName());
            createCache(cm, com.reliance.jpl.domain.Call.class.getName());
            createCache(cm, com.reliance.jpl.domain.LeadUploadFile.class.getName());
            createCache(cm, com.reliance.jpl.domain.Disposition.class.getName());
            createCache(cm, com.reliance.jpl.domain.Disposition.class.getName() + ".fields");
            createCache(cm, com.reliance.jpl.domain.Disposition.class.getName() + ".dispositionSubmissions");
            createCache(cm, com.reliance.jpl.domain.Field.class.getName());
            createCache(cm, com.reliance.jpl.domain.Field.class.getName() + ".fieldPossibleValues");
            createCache(cm, com.reliance.jpl.domain.FieldPossibleValue.class.getName());
            createCache(cm, com.reliance.jpl.domain.DispositionSubmission.class.getName());
            createCache(cm, com.reliance.jpl.domain.DispositionSubmissionValue.class.getName());
            createCache(cm, com.reliance.jpl.domain.TestResult.class.getName());
            createCache(cm, com.reliance.jpl.domain.Build.class.getName());
            createCache(cm, com.reliance.jpl.domain.Build.class.getName() + ".results");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
