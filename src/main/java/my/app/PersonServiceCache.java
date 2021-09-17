package my.app;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Component;

@Component
public class PersonServiceCache {

    private final Cache<String, Person> personServiceCache;

    public PersonServiceCache() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("personServiceCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Person.class, ResourcePoolsBuilder.heap(10)))
                .build(true); //Represents cacheManager.init()
        this.personServiceCache = cacheManager.getCache("personServiceCache", String.class, Person.class);
    }

    public void cache(String ssn, Person person){
        personServiceCache.put(ssn, person);
    }

    public Person exists(String ssn) {
        return personServiceCache.get(ssn);
    }

    //cacheManager.removeCache("firstCacheTest");
    //cacheManager.close();
}
