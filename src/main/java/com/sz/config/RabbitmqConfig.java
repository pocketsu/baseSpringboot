package com.sz.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitmqConfig {
	
	
	@Bean
	public DirectExchange testExchange() {
		DirectExchange directExchange=new DirectExchange("testExchange", true, false);
		directExchange.setDelayed(true);
		return directExchange;
	}
	
	@Bean(name="testQueue")
	public Queue testQueue() {
		return new Queue("testQueue",true);
	}
	
	@Bean
	public Binding testBinding(DirectExchange testExchange,Queue testQueue) {
		return BindingBuilder.bind(testQueue()).to(testExchange()).with("testQueueKey");
	}
}
