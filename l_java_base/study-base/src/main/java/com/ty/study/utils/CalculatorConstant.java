package com.ty.study.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class CalculatorConstant {
	public static final Double TAX_RATE_LEVEL1 = 0.03;
	public static final Double TAX_RATE_LEVEL2 = 0.1;
	public static final Double TAX_RATE_LEVEL3 = 0.2;
	public static final Double TAX_RATE_LEVEL4 = 0.25;
	public static final Double TAX_RATE_LEVEL5 = 0.3;
	public static final Double TAX_RATE_LEVEL6 = 0.35;
	public static final Double TAX_RATE_LEVEL7 = 0.45;

	public static final Double BASE_MONEY_LEVEL1 = 0.0;
	public static final Double BASE_MONEY_LEVEL2 = 105.0;
	public static final Double BASE_MONEY_LEVEL3 = 555.0;
	public static final Double BASE_MONEY_LEVEL4 = 1005.0;
	public static final Double BASE_MONEY_LEVEL5 = 27550.;
	public static final Double BASE_MONEY_LEVEL6 = 5505.0;
	public static final Double BASE_MONEY_LEVEL7 = 13505.0;

	public static final Double BASE_MONEY = 3500.00;

	public static String calculatorTax(String str){
		if(isNull(str)){
			return null;
		}
		Double salary = Double.parseDouble(str);
		Double tax = (Double) getTaxRate(salary).get("tax");
		Double baseMoney = (Double) getTaxRate(salary).get("baseMoney");
		Double taxMoney = tax * (Math.abs(salary-BASE_MONEY)) - baseMoney; //税
		return getCurrencyFormat().format(taxMoney);
	}

	public static boolean isNull(Object obj){
		if(obj == null){
			return true;
		}
		if(obj instanceof String){
			return "".equals(obj);
		}

		return false;
	}

	public static NumberFormat getCurrencyFormat(){
		return new DecimalFormat("##.##");
	}

	public static Map getTaxRate(Double salary){
		Map map = new HashMap<String, Double>();
		try {
			Double needRate = salary - BASE_MONEY;
			BigDecimal switchMoney = BigDecimal.valueOf(needRate);
			if(switchMoney.compareTo(BigDecimal.valueOf(0.0))<0){
				map.put("tax", 0.0);
				map.put("baseMoney", 0.0);
				return map;
			}

			if(switchMoney.compareTo(BigDecimal.valueOf(1500))<0
					&& switchMoney.compareTo(BigDecimal.valueOf(0))>=0){
				map.put("tax", TAX_RATE_LEVEL1);
				map.put("baseMoney", BASE_MONEY_LEVEL1);
			}else if(switchMoney.compareTo(BigDecimal.valueOf(4500))<0
					&& switchMoney.compareTo(BigDecimal.valueOf(1500))>=0){
				map.put("tax", TAX_RATE_LEVEL2);
				map.put("baseMoney", BASE_MONEY_LEVEL2);
			}else if(switchMoney.compareTo(BigDecimal.valueOf(9000))<0
					&& switchMoney.compareTo(BigDecimal.valueOf(4500))>=0){
				map.put("tax", TAX_RATE_LEVEL3);
				map.put("baseMoney", BASE_MONEY_LEVEL3);
			}else if(switchMoney.compareTo(BigDecimal.valueOf(35000))<0
					&& switchMoney.compareTo(BigDecimal.valueOf(9000))>=0){
				map.put("tax", TAX_RATE_LEVEL4);
				map.put("baseMoney", BASE_MONEY_LEVEL4);
			}else if(switchMoney.compareTo(BigDecimal.valueOf(55000))<0
					&& switchMoney.compareTo(BigDecimal.valueOf(35000))>=0){
				map.put("tax", TAX_RATE_LEVEL5);
				map.put("baseMoney", BASE_MONEY_LEVEL5);
			}else if(switchMoney.compareTo(BigDecimal.valueOf(80000))<0
					&& switchMoney.compareTo(BigDecimal.valueOf(55000))>=0){
				map.put("tax", TAX_RATE_LEVEL6);
				map.put("baseMoney", BASE_MONEY_LEVEL6);
			}else if(switchMoney.compareTo(BigDecimal.valueOf(80000))>=0){
				map.put("tax", TAX_RATE_LEVEL7);
				map.put("baseMoney", BASE_MONEY_LEVEL7);
			}else{
				map.put("tax", 1.0);
				map.put("baseMoney", BASE_MONEY_LEVEL1);
			}
		}catch(Exception e){
			System.err.println(e);
		}
		return map;
	}

	public static void main(String args[]){
		String salary = "12000";
		String befitMoney = "2664";
		String rate = String.valueOf(Double.parseDouble(salary) - Double.parseDouble(befitMoney));

		String geShui = calculatorTax(rate);
		System.out.println("个税："+ geShui);
		System.out.println("社保公积金："+ befitMoney);
		System.out.println("税后："+ (Double.parseDouble(salary) - Double.parseDouble(befitMoney)-Double.parseDouble(geShui)));
		//System.out.println(BigDecimal.valueOf(1600).compareTo(BigDecimal.valueOf(1500)));
		//System.out.println(getCurrencyFormat().format(Double.parseDouble(ss)));
	}
}
