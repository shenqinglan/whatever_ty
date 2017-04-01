package com.whty.cache.redis.cache;

import com.whty.cache.Cache;
import com.whty.cache.exception.DataAccessException;
import com.whty.cache.redis.connection.RedisConnection;
import com.whty.cache.redis.core.RedisCallback;
import com.whty.cache.redis.core.RedisTemplate;
import com.whty.cache.redis.core.TimeoutUtils;
import com.whty.cache.redis.serializer.RedisSerializer;
import com.whty.cache.redis.serializer.StringRedisSerializer;
import com.whty.util.Assert;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class RedisCache implements Cache
{
    private static final int PAGE_SIZE = 128;
    private final String name;
    private final RedisTemplate template;
    private final byte[] setName;
    private final byte[] cacheLockName;
    private long WAIT_FOR_LOCK = 300L;
    private final byte[] prefix;
    private final long expiration;

    public RedisCache(String name, byte[] prefix, RedisTemplate<? extends Object, ? extends Object> template, long expiration)
    {
        Assert.hasText(name, "non-empty cache name is required");
        this.name = name;
        this.template = template;
        this.prefix = prefix;
        this.expiration = expiration;

        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        this.setName = stringSerializer.serialize(name + "_keys");
        this.cacheLockName = stringSerializer.serialize(name + "_lock");
    }

    public String getName()
    {
        return this.name;
    }

    public Object getNativeCache()
    {
        return this.template;
    }

    public <T> T get(final Object key, final Class<T> type)
    {
        return (T)this.template.execute(new RedisCallback()
        {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException
            {
                //RedisCache.this.waitForLock(connection);
                byte[] bs = connection.get(RedisCache.this.computeKey(key));
                if (bs == null) {
                    return null;
                    //throw new DataAccessFailureException("Did not get the value specified keywords. the keywords may not exist or has expired.");
                }
                Object value = RedisCache.this.template.getValueSerializer() != null ? RedisCache.this.template.getValueSerializer().deserialize(bs, type) : bs;
                return value;
            }
        }, true);
    }

    public void put(Object key, Object value)
    {
        final byte[] keyBytes = computeKey(key);
        final byte[] valueBytes = convertToBytesIfNecessary(this.template.getValueSerializer(), value);

        this.template.execute(new RedisCallback()
        {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException
            {
                //RedisCache.this.waitForLock(connection);

                connection.multi();

                connection.set(keyBytes, valueBytes);
                connection.zAdd(RedisCache.this.setName, 0.0D, keyBytes);
                if (RedisCache.this.expiration > 0L)
                {
                    RedisCache.this.expire(connection, keyBytes, RedisCache.this.expiration);

                    RedisCache.this.expire(connection, RedisCache.this.setName, RedisCache.this.expiration);
                }
                connection.exec();

                return null;
            }
        }, true);
    }

    public boolean expire(Object key, final long millis)
    {
        final byte[] rawKey = computeKey(key);

        return ((Boolean)this.template.execute(new RedisCallback()
        {
            public Boolean doInRedis(RedisConnection connection)
            {
                return RedisCache.this.expire(connection, rawKey, millis);
            }
        }, true)).booleanValue();

    }

    private Boolean expire(RedisConnection connection, byte[] rawKey, long millis)
    {
        try
        {
            return connection.pExpire(rawKey, millis);
        }
        catch (Exception e) {}
        return connection.expire(rawKey, TimeoutUtils.toSeconds(millis, TimeUnit.MILLISECONDS));
    }

    public boolean persist(Object key)
    {
        final byte[] rawKey = computeKey(key);

        return ((Boolean)this.template.execute(new RedisCallback()
        {
            public Boolean doInRedis(RedisConnection connection)
            {
                return connection.persist(rawKey);
            }
        }, true)).booleanValue();
    }

    public void remove(Object key)
    {
        final byte[] k = computeKey(key);

        this.template.execute(new RedisCallback()
        {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException
            {
                connection.del(new byte[][] { k });

                connection.zRem(RedisCache.this.setName, new byte[][] { k });
                return null;
            }
        }, true);
    }

    public void clear()
    {
        this.template.execute(new RedisCallback()
        {
            /* Error */
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException
            {
                return null;
            }
        }, true);
    }

    private byte[] computeKey(Object key)
    {
        byte[] keyBytes = convertToBytesIfNecessary(this.template.getKeySerializer(), key);
        if ((this.prefix == null) || (this.prefix.length == 0)) {
            return keyBytes;
        }
        byte[] result = Arrays.copyOf(this.prefix, this.prefix.length + keyBytes.length);
        System.arraycopy(keyBytes, 0, result, this.prefix.length, keyBytes.length);

        return result;
    }

    private boolean waitForLock(RedisConnection connection)
    {
        boolean foundLock = false;
        boolean retry;
        do
        {
            retry = false;
            if (connection.exists(this.cacheLockName).booleanValue())
            {
                foundLock = true;
                try
                {
                    Thread.sleep(this.WAIT_FOR_LOCK);
                }
                catch (InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                retry = true;
            }
        } while (retry);
        return foundLock;
    }

    private byte[] convertToBytesIfNecessary(RedisSerializer serializer, Object value)
    {
        if ((serializer == null) && ((value instanceof byte[]))) {
            return (byte[])value;
        }
        return serializer.serialize(value);
    }
}

