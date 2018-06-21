package com.ty.study.mrjoin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.codehaus.jackson.map.util.BeanUtil;

public class OrderJoin {

	static class OrderJoinMapper extends Mapper<LongWritable, Text, Text, OrderJoinBean> {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			// 拿到一行数据，并且要分辨出这行数据所属的文件
			String line = value.toString();

			String[] fields = line.split("\t");

			// 获取到这一行所在的文件名（通过inpusplit）
			String name = "你拿到的文件名";

			// 根据文件名，切分出各字段（如果是a，切分出两个字段，如果是b，切分出3个字段）
			// 拿到product id
			String pid = fields[0];
			// 拿其他字段

			// 再将各字段放入bean中
			OrderJoinBean bean = new OrderJoinBean();
			bean.set(null, null, null, null, null);
			// 将join条件pid作为key发出去
			context.write(new Text(pid), bean);

		}

	}

	static class OrderJoinReducer extends Reducer<Text, OrderJoinBean, OrderJoinBean, NullWritable> {

		@Override
		protected void reduce(Text key, Iterable<OrderJoinBean> beans, Context context) throws IOException, InterruptedException {
			// 拿到的key是某一个pid,比如pd00001
			// 拿到的beans是来自于两类文件的bean
			// {1000,amount} {1000,amount} {1000,amount} --- {1000,price,name}

			// 注意迭代器的问题!!!!!
			/*
			 * OrderJoinBean pdBean = null; ArrayList<OrderJoinBean> orderBeans
			 * = new ArrayList<OrderJoinBean>();
			 * 
			 * for(OrderJoinBean bean :beans){
			 * 
			 * if("product.txt".equals(bean.getTable_name())){ pdBean= bean;
			 * //这是错误的！ }else{ OrderJoinBean newBean = new OrderJoinBean();
			 * BeanUtils.copyProperties(newBean, bean); orderBeans.add(newBean);
			 * }
			 * 
			 * }
			 */

			// 将来自于product表的bean里面的字段，跟来自于order表的所有bean进行字段拼接并输出

		}

	}

}
