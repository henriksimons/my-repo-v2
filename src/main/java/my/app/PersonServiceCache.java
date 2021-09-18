package my.app;

import my.app.mongo.Person;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class PersonServiceCache {

    /**
     * https://www.ehcache.org/documentation/3.8/getting-started.html
     */

    private final Cache<String, Person> personServiceCache;

    public PersonServiceCache() {

        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("personServiceCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(String.class, Person.class, ResourcePoolsBuilder
                                .heap(100000))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(1))))
                .build(true); //Represents cacheManager.init()

        this.personServiceCache = cacheManager.getCache("personServiceCache", String.class, Person.class);
    }

    public void cache(String ssn, Person person) {
        personServiceCache.put(ssn, person);
    }

    public Person exists(String ssn) {
        return personServiceCache.get(ssn);
    }

    public void delete(String ssn) {
        personServiceCache.remove(ssn);
    }

    //cacheManager.removeCache("firstCacheTest");
    //cacheManager.close();
}
