package com.ty.study.secondarysort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 用于控制shuffle过程中reduce端对kv对的聚合逻辑
 * @author duanhaitao@itcast.cn
 *
 */
public class ItemidGroupingComparator extends WritableComparator {

	protected ItemidGroupingComparator() {

		super(OrderBean.class, true);
	}
	

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		OrderBean abean = (OrderBean) a;
		OrderBean bbean = (OrderBean) b;
		
		//将item_id相同的bean都视为相同，从而聚合为一组
		return abean.getItemid().compareTo(bbean.getItemid());
		
	}
	
	

}
