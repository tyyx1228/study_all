package com.ty.study.distinct.simple;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class LineBean implements WritableComparable<LineBean>{
	public Text a;
	public LongWritable b;
	
	
	
	
	public void set(Text a, LongWritable b) {
		this.a = a;
		this.b = b;
	}
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(a.toString());
		out.writeLong(b.get());
		
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		a = new Text(in.readUTF());
		b = new LongWritable(in.readLong());
		
	}
	
	@Override
	public int compareTo(LineBean o) {
		if(a.compareTo(o.a)!=0){
			return a.compareTo(o.a);
		}else{
			return b.compareTo(o.b);
			
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.a + "\t" + this.b;
	}

}
