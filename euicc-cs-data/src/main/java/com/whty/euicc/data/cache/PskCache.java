package com.whty.euicc.data.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.whty.euicc.common.spring.SpringContextHolder;
import com.whty.euicc.data.dao.EuiccScp81Mapper;
import com.whty.euicc.data.pojo.EuiccScp81;


public class PskCache {
	private final static String pskCache = "pskCache";//cache name
    private static final LoadingCache<String, Object> pskCaches = CacheBuilder
            .newBuilder()
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, Object>() {
                @Override
                public Object load(String key) {
                	System.out.println("key " + key);
                    return loadDate();
                }
            });

    public static Map loadDate(){
    	Map<String,String> map = new HashMap<String,String>();
        EuiccScp81Mapper scp81Mapper = SpringContextHolder.getBean(EuiccScp81Mapper.class);
        List<EuiccScp81> scp81s = scp81Mapper.selectLists();
        for(EuiccScp81 scp81 : scp81s){
        	map.put(scp81.getId(), scp81.getData());
        }
        return map;
    }

    public static String getPsk(String pskid){
    	 Map pskMap = (Map) pskCaches.getUnchecked(pskCache);
         return (String) pskMap.get(pskid);
    }

    public static void refresh(){
    	pskCaches.invalidateAll();
    }
}
