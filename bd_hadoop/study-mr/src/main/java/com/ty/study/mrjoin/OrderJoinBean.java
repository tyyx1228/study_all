package com.ty.study.mrjoin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class OrderJoinBean implements WritableComparable<OrderJoinBean>{

	private String table_name;
	private String itemid;
	private Double amount;
	private Double price;
	private String name;

	public OrderJoinBean(String table_name, String itemid, Double amount, Double price, String name) {
		super();
		this.table_name = table_name;
		this.itemid = itemid;
		this.amount = amount;
		this.price = price;
		this.name = name;
	}

	public OrderJoinBean() {
		super();

	}

	public void set(String table_name, String itemid, Double amount, Double price, String name) {
		this.table_name = table_name;
		this.itemid = itemid;
		this.amount = amount;
		this.price = price;
		this.name = name;

	}

	public String getTable_name() {
		return table_name;
	}

	public String getItemid() {
		return itemid;
	}

	public Double getAmount() {
		return amount;
	}

	public Double getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		/**
		 * 	private String table_name;
	private String itemid;
	private Double amount;
	private Double price;
	private String name;
		 * 
		 */
		out.writeUTF(table_name);
		out.writeUTF(itemid);
		out.writeDouble(amount);
		out.writeDouble(price);
		out.writeUTF(name);
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		table_name = in.readUTF();
		itemid = in.readUTF();
		amount = in.readDouble();
		price = in.readDouble();
		name = in.readUTF();
		
	}

	@Override
	public int compareTo(OrderJoinBean o) {
		
		return itemid.compareTo(o.getItemid());
	}
	
	
	@Override
	public String toString() {
		
		return "关联结果所有字段";
	}
	

}
