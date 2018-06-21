package com.ty.study.flowcountsort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;


public class FlowBean implements WritableComparable<FlowBean>{

	long upflow;
	long downflow;
	long sumflow;

	//如果空参构造函数被覆盖，一定要显示定义一下，否则在反序列时会抛异常
	public FlowBean(){}

	public FlowBean(long upflow, long downflow) {
		super();
		this.upflow = upflow;
		this.downflow = downflow;
		this.sumflow = upflow + downflow;
	}




	public long getSumflow() {
		return sumflow;
	}




	public void setSumflow(long sumflow) {
		this.sumflow = sumflow;
	}




	public long getUpflow() {
		return upflow;
	}
	public void setUpflow(long upflow) {
		this.upflow = upflow;
	}
	public long getDownflow() {
		return downflow;
	}
	public void setDownflow(long downflow) {
		this.downflow = downflow;
	}




	//序列化，将对象的字段信息写入输出流
	@Override
	public void write(DataOutput out) throws IOException {

		out.writeLong(upflow);
		out.writeLong(downflow);
		out.writeLong(sumflow);

	}



	//反序列化，从输入流中读取各个字段信息
	@Override
	public void readFields(DataInput in) throws IOException {
		upflow = in.readLong();
		downflow = in.readLong();
		sumflow = in.readLong();

	}


	@Override
	public String toString() {

		return upflow + "\t" + downflow + "\t" + sumflow;
	}

	@Override
	public int compareTo(FlowBean o) {

		return sumflow > o.getSumflow() ? -1:1;
	}

}
