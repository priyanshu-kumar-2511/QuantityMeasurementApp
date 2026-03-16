package com.app.quantitymeasurement.entity;

import java.io.Serializable;

import com.app.quantitymeasurement.unit.IMeasurable;

public class QuantityModel<U extends IMeasurable> implements Serializable {
	private static final long serialVersionUID = 1L;
	public double value; 
	public U unit;
	 
	public QuantityModel(double value, U unit) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}
	public U getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return "QuantityModel [value=" + value + ", unit=" + unit + "]";
	}
	
	
	public static void main(String[] args) {
		System.out.println("Quantity Model class"); 
	}
}
