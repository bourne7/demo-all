package middleware;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

/**
 * @author Lawrence Peng
 */
public class CaffeineTest {

    public static void main(String[] args) {

        Cache<Object, Object> cache = Caffeine.newBuilder()
                .maximumSize(100_000)
                // Ticker default is Ticker.systemTicker(). if you want to use disableTicker,
                // you can use Ticker.disabledTicker() for testing.
                .ticker(Ticker.systemTicker())

                /**
                 * why there are Caffeine.weakKeys(), Caffeine.weakValues(), and Caffeine.softValues(),
                 *  but no Caffeine.softKeys()?
                 *
                 * https://groups.google.com/g/guava-discuss/c/dkrx5ifW-g4?pli=1
                 * https://stackoverflow.com/questions/7618129/why-is-softkeys-deprecated-in-guava-10/7618134#7618134
                 *
                 *  One clarification: using softKeys in conjunction with SoftReferences to the keys is the same as using weakKeys with SoftReferences to the keys. The reason is that the key pointed to by the weak + soft reference will be atomically garbage collected, so both will be collected together.
                 *
                 * So the real question is: do you use softKeys in conjunction with WeakReferences to the keys? In this case the softKeys are actually causing the keys to stay in memory longer. Of course this case would behave equivalently to weakKeys in conjunction with SoftReferences, so there should be a clear migration path if anyone is relying on this.
                 *
                 * 如果出现了 soft key 的 cache ，但是入参是 weak 的话，可能会导致 cache 存在，但是weak入参 已经被回收了。我认为这么设计是为了保证
                 *
                 * 入参存留的时间大于 cache 的存留时间。
                 */
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();

        cache.put("key", "value");


        /**
         * https://github.com/jhalterman/expiringmap
         *
         * On Variable Expiration
         * When variable expiration is disabled (default), put and remove operations have a constant O(1) time complexity. When variable expiration is enabled, put and remove operations have a time complexity of O(log n).
         */

        ExpiringMap<String, Object> map = ExpiringMap.builder()
                .variableExpiration()
                .build();

        map.put("key", new Object(), ExpirationPolicy.ACCESSED, 5, TimeUnit.MINUTES);

        map.get("key");

        // 这个作者将为了实现每个 key 的自定义过期，额外维护了一个跳表，然后得到排序的 key，然后再去判断是否过期。
        // 我认为为了维护“Cache保存的总数”，必须要方便的删除过期的 key，当统一过期时间的时候，比较方便，因为队列尾部的就是可以删除的。但是对于自定义过期时间的，就需要维护一个跳表，然后再去判断是否过期。
        // 我认为这个有点重了。可以尝试用一些折中的办法：
        // 在添加的时候，不用 ConcurrentSkipListSet， 而是用一个 双向队列 ArrayDeque，然后在添加的时候，根据过期时间的大小，判断添加到首还是尾。
        // 这种方式我认为更加快捷。


    }

}
