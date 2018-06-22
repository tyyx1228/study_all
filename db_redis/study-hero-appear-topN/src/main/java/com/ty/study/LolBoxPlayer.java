package com.ty.study;

import java.util.Random;

import redis.clients.jedis.Jedis;

public class LolBoxPlayer {
	
	
	public static void main(String[] args) throws Exception {
		
		Jedis jedis = new Jedis("mini1", 6379);
		
		
		Random random = new Random();
		String[] heros = {"易大师","德邦","剑姬","盖伦","阿卡丽","金克斯","提莫","猴子","亚索"};
		while(true){
			
			int index = random.nextInt(heros.length);
			//选择一个英雄
			String hero = heros[index];
			
			//开始玩游戏
			Thread.sleep(1000);
			
			//给集合中的该英雄的出场次数加1
			//第一次添加的时候，集合不存在，zincrby方法会创建
			jedis.zincrby("hero:ccl:phb", 1, hero);
			
			
			System.out.println(hero+" 出场了.......");
			
		}
	}
	

}
