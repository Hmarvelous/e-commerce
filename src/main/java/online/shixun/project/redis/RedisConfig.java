package online.shixun.project.redis;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
	
    //RedisTemplate<Object, Object>:序列化Object
//    @Bean
//    public RedisTemplate<Object, Object> empRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
//        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
//        template.setConnectionFactory(redisConnectionFactory);
//        //序列化器
////        Jackson2JsonRedisSerializer<Object> ser = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
//        template.setDefaultSerializer(serializer);
//        return template;
//    }

    
	
	/**
	 * redis 缓存配置 
	 * 修改序列化方式,解决缓存乱码
	 * @param redisConnectionFactory
	 * @return
	 */
    @Bean
    @SuppressWarnings("all")
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);

        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheConfiguration redisCacheConfiguration = config
        		//Key序列化方式redisSerializer
        		.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
        		//value序列化方式jackson2JsonRedisSerializer
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
        		.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
        		//设置前缀
//        		.prefixKeysWith("project:")
        		//设置过期时间
        		.entryTtl(Duration.ofSeconds(60));
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
        return cacheManager;
    }
	
	/**
	 * redisTemplate配置
	 * @param factory
	 * @return
	 * 
	 * 
	 * 自定义RedisTemplate的原因:
	 * 1、修改泛型方式为<String,Object>,避免繁琐的类型转换
	 * 2、将value的序列化方式更改为Jackson2JsonRedisSerializer,因为底层的RedisSerializer序列化value时不会带双引号，而使用Jackson2JsonRedisSerializer序列化String类型时会自动添加双引号。
	 * 
	 */
	
	@Bean
	@SuppressWarnings("all")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
		
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(factory);
		
//		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
		
//		ObjectMapper mapper = new ObjectMapper();
//		//指定要序列化的域,ANY是包括public和private的
//		mapper.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY);
//		//指定序列化输入的类型,类必须是非final类型的,否则会报错
//		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		jackson2JsonRedisSerializer.setObjectMapper(mapper);
		
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		//设置key采用String的序列化方式
		redisTemplate.setKeySerializer(stringRedisSerializer);
		//设置hash的key采用String的序列化方式
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		//设置value采用jackson2的序列化方式
		redisTemplate.setValueSerializer(fastJsonRedisSerializer);
		//设置hash的value采用jackson2的序列化方式
		redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
		
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
	
	
    /**
	 * 重写生成key的方法：类名+方法名+参数名
	 */
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
            public Object generate(Object target, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName()).append(".").append(method.getName()).append(Arrays.toString(objects));
                return sb.toString();
            }
        };
	}
}