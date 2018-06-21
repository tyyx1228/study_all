package com.ty.study.distinct;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class GpsLogBean implements WritableComparable<GpsLogBean> {
	private String phone_number;
	private String content;

	public GpsLogBean() {

	}

	public GpsLogBean(String phone_number, String content) {
		super();
		this.phone_number = phone_number;
		this.content = content;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(phone_number);
		out.writeUTF(content);

	}

	public void readFields(DataInput in) throws IOException {
		phone_number = in.readUTF();
		content = in.readUTF();

	}

	public int compareTo(GpsLogBean o) {

		if (this.phone_number.compareTo(o.phone_number) != 0) {
			return this.phone_number.compareTo(o.phone_number);
		} else {
			return this.content.compareTo(o.content);
		}

	}

	@Override
	public String toString() {
		return content + "|" + phone_number;
	}

}
