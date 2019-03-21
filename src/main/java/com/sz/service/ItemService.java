package com.sz.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sz.dao.mapper.GoodsMapper;
import com.sz.dao.mapper.ItemMapper;
import com.sz.entity.Goods;
import com.sz.entity.Item;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class ItemService {
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
	private ItemMapper itemMapper;
	@Resource
	private GoodsMapper goodsMapper;
	@Resource
	private AmqpTemplate amqpTemplate;

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Item getItemInfo(String id) {
		Item item=itemMapper.selectByPrimaryKey(id);
		return item;
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Item> getItemList() {
		logger.info("######执行了getItemList方法");
		Example exp=new Example(Item.class);
		//exp.createCriteria().andLike("title", "%测试%");
		List<Item> itemList=itemMapper.selectByExample(exp);
		return itemList;
	}
	
	public void addItem() {
		Item item=new Item();
		item.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		item.setTitle("测试的tt");
		item.setMemo("备注的说i");
		item.setMoney(new BigDecimal("12"));
		itemMapper.insert(item);
		throw new RuntimeException("自定义异常");
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Item> queryItem() {
		Map<String,Object> params=new HashMap<>();
		params.put("title", "%的%");
		List<Item> list=itemMapper.queryItem(params);
		return list;
	}
	
	public void addGoods() {
		Goods goods=new Goods();
		goods.setTitle("test商品");
		goods.setMemo("ss");
		int n=goodsMapper.insert(goods);
		System.out.println("######"+goods.getId());
	}
	
	public void addMq() {
		System.out.println("######加入MQ"+new Date());
		Map<String,Object> map=new HashMap<>();
		map.put("a", "你好");
		map.put("b", "123");
		amqpTemplate.convertAndSend("testExchange","testQueueKey",map,new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setDelay(3000);
				return message;
			}
		});
	}
	
	//@RabbitListener(queues="testQueue")
	public void doMq(Map<String,Object> map) {
		System.out.println("######消费MQ"+new Date());
		String a=(String)map.get("a");
		String b=(String)map.get("b");
		System.out.println(a+"|"+b);
	}
	
}
