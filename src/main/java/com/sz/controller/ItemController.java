package com.sz.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sz.aop.TestAnnotation;
import com.sz.entity.Item;
import com.sz.service.ItemService;
import com.sz.solr.IndexEntity;
import com.sz.solr.SolrHandler;
import com.sz.util.RedisUtils;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
	private ItemService itemService;
	@Resource
	private RedisUtils redisUtils;
	@Resource
	private SolrHandler solrHandler;

	@RequestMapping(value="/info")
	@ResponseBody
	public Item getItemInfo(String id) {
		Item item=itemService.getItemInfo(id);
		return item;
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	@TestAnnotation
	public List<Item> getItemList() {
		List<Item> itemList=itemService.getItemList();
		return itemList;
	}
	
	@RequestMapping(value="/add")
	@ResponseBody
	public String addItem() {
		try {
			itemService.addItem();
			return "200";
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			return "500";
		}
	}
	
	@RequestMapping(value="/query")
	@ResponseBody
	public List<Item> queryItem() {
		List<Item> list=itemService.queryItem();
		return list;
	}
	
	@RequestMapping(value="/redis/set")
	@ResponseBody
	public String setRedis() {
		try {
			redisUtils.set("key1", "你好aaa123", 60*60l);
			return "200";
		}catch(Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}
	
	@RequestMapping(value="/redis/get")
	@ResponseBody
	public String getRedis() {
		String val=redisUtils.get("key1");
		return val;
	}
	
	@RequestMapping(value="/addGoods")
	@ResponseBody
	public String addGoods() {
		try {
			itemService.addGoods();
			return "200";
		}catch(Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}
	
	@RequestMapping(value="/addMq")
	@ResponseBody
	public String addMq() {
		try {
			itemService.addMq();
			return "200";
		}catch(Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}
	
	@RequestMapping(value="/testSolr")
	@ResponseBody
	public List<IndexEntity> testSolr() throws SolrServerException, IOException {
		List<IndexEntity> list=solrHandler.search("*2*", 1, 10);
		for(IndexEntity entity : list) {
			System.out.println(entity.getId()+"|"+entity.getTitle()+"|"+entity.getContent());
		}
		return list;
	}
}
