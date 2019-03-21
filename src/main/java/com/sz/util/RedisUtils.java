package com.sz.util;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.jboss.logging.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
	
	Logger logger=Logger.getLogger(RedisUtils.class);
	
	@Resource
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 写入
	 * @param key
	 * @param value
	 * @param expire 过期时间(秒)，传null不过期
	 */
	public void set(String key,String value,Long expire){
		ValueOperations<String, String> valueOperations=redisTemplate.opsForValue();
		if(expire!=null){
			valueOperations.set(key, value, expire, TimeUnit.SECONDS);
		}else{
			valueOperations.set(key, value);
		}
	}
	
	/**
	 * 读取
	 * @param key
	 * @return
	 */
	public String get(String key){
		ValueOperations<String, String> valueOperations=redisTemplate.opsForValue();
		String value=(String)valueOperations.get(key);
		return value;
	}
	
	
	/**
	 * 删除指定key值
	 * @param key
	 */
	public void delete(String key) {
	    redisTemplate.delete(key);
	}
	
	/**
	 * 查询key，传*时获取全部key
	 * @param key
	 * @return
	 */
	public Set<String> getKeys(String key){
		Set<String> set=redisTemplate.keys(key);
		return set;
	}
	
	/**
	 * 删除所有
	 */
	public void deleteAll(){
		Set<String> keyList=this.getKeys("*");
		redisTemplate.delete(keyList);
	}
	
}
