package com.whty.euicc.data.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.whty.euicc.common.spring.SpringContextHolder;
import com.whty.euicc.data.dao.EuiccScp80Mapper;
import com.whty.euicc.data.pojo.EuiccScp80;

public class Scp80Cache {
	private final static String scp80Cache = "scp80Cache";//cache name
    private static final LoadingCache<String, Object> scp80Caches = CacheBuilder
            .newBuilder()
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, Object>() {
                @Override
                public Object load(String key) {
                    return loadDate();
                }
            });

    public static Map loadDate(){
    	Map<String,String> map = new HashMap<String,String>();
        EuiccScp80Mapper scp80Mapper = SpringContextHolder.getBean(EuiccScp80Mapper.class);
        List<EuiccScp80> scp80s = scp80Mapper.selectLists();
        for(EuiccScp80 scp80 : scp80s){
        	map.put(scp80.getId()+"_"+scp80.getVersion(), scp80.getData());
        }
        return map;
    }

    public static String getScp80(String id_version){
    	 Map scp80Map = (Map) scp80Caches.getUnchecked(scp80Cache);
         return (String) scp80Map.get(id_version);
    }

    public static void refresh(){
    	scp80Caches.invalidateAll();
    }
}
