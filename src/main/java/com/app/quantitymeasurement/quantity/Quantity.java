package com.app.quantitymeasurement.quantity;

import java.util.function.DoubleBinaryOperator;

import com.app.quantitymeasurement.unit.IMeasurable;

public class Quantity<U extends IMeasurable> {
	private final double value;
	private final U unit;
	private static final double EPSILON = 0.0001;
	
	public Quantity(double value, U unit) {
		if(unit == null) {
			throw new IllegalArgumentException("Unit can't be null");
		}
		if(Double.isNaN(value) || !Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		
		this.value = value;
		this.unit = unit;
	}
	
	public double getValue() {
		return value;
	}
	public U getUnit() {
		return unit;
	}
	
	// equals()
	public boolean equals(Object obj) {
		if(this == obj) return true;
		
		if(obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		
		Quantity<?> thatObj = (Quantity<?>) obj; 
		
		 if(!this.unit.getClass().equals(thatObj.unit.getClass())) {
		     return false;
		 }
		
		double thisBase = unit.convertToBaseUnit(value);
		double thatBase = thatObj.unit.convertToBaseUnit(thatObj.value);
		 
		return Math.abs(thisBase - thatBase) < EPSILON;  
	}

	
	// ConvertTo()
	public Quantity<U> convertTo(U targetUnit) {
		if(targetUnit == null) { 
			throw new IllegalArgumentException("Target Unit can't be null!!!");
		}
		if(!this.unit.getClass().equals(targetUnit.getClass())) {
			throw new IllegalArgumentException("Unit types must be not null!!!");
		}
		
		double thisBase = unit.convertToBaseUnit(value);
		double thatBase = targetUnit.convertFromBaseUnit(thisBase);
		
		return new Quantity<>(thatBase, targetUnit);  
	}
	
	// add()
	public Quantity<U> add(Quantity<U> other) {
		validateArithmeticOperands(other, this.unit, true); 
		return new Quantity<>(performArithmetic(other, this.unit, ArithmeticOperation.ADD), this.unit);  
	}
	
	// add (., .)
	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		validateArithmeticOperands(other, targetUnit, true); 
		return new Quantity<>(performArithmetic(other, targetUnit, ArithmeticOperation.ADD), targetUnit);  
	}
	
	
	@Override
	public String toString() {
		return value + " " + unit;
	}
	
	
	
	// --------------- subtract() ------------------
	public Quantity<U> subtract(Quantity<U> other) {
		validateArithmeticOperands(other, this.unit, true); 
		return new Quantity<>(performArithmetic(other, this.unit, ArithmeticOperation.SUBTRACT), this.unit);  
	}
	
	
	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		validateArithmeticOperands(other, targetUnit, true); 
		return new Quantity<>(performArithmetic(other, targetUnit, ArithmeticOperation.SUBTRACT), targetUnit);  
	}
	
	
	// ------------------ Divide() -------------------
	public double divide(Quantity<U> other) {
		validateArithmeticOperands(other, this.unit, true); 
		return performArithmetic(other, this.unit, ArithmeticOperation.DIVIDE);  
	}
	
	public double divide(Quantity<U> other, U targetUnit) { 
		if(other.getValue() == 0.0) {
			throw new ArithmeticException("Divisor must be not zero!!!");
		}
		
		
		validateArithmeticOperands(other, targetUnit, true); 
		return performArithmetic(other, targetUnit, ArithmeticOperation.DIVIDE);  
	} 
	 
		
	private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {
		if(other == null) {
	        throw new IllegalArgumentException("Other quantity cannot be null");
	    }

	    if(this.unit == null || other.unit == null) {
	        throw new IllegalArgumentException("Unit cannot be null"); 
	    }

	    if(!this.unit.getClass().equals(other.unit.getClass())) {
	        throw new IllegalArgumentException("Unit types are not compatible");
	    }

	    if(!Double.isFinite(this.value) || !Double.isFinite(other.value)) {
	        throw new IllegalArgumentException("Values must be finite");
	    }

	    if(targetUnitRequired && targetUnit == null) {
	        throw new IllegalArgumentException("Target unit is required");
	    }

	    if(targetUnit != null && !this.unit.getClass().equals(targetUnit.getClass())) {
	        throw new IllegalArgumentException("Target unit type is not compatible");
	    }
	}
	
	enum ArithmeticOperation {
		
		ADD((a, b) -> a + b),
		SUBTRACT((a, b) -> a - b),
		DIVIDE((a, b) -> {
			if(b == 0.0) throw new ArithmeticException("Cannot divide by zero!!!");
			return a / b;
		});
		
		private final DoubleBinaryOperator operation;
		
		ArithmeticOperation(DoubleBinaryOperator operation) {
			this.operation = operation;
		}
		
		public double compute(double a, double b) {
			return operation.applyAsDouble(a, b); 
		}
	}
	
	private double performArithmetic(Quantity<U> other, U targetUnit, ArithmeticOperation operation) {
		boolean targetUnitRequired = operation != ArithmeticOperation.DIVIDE;

	    validateArithmeticOperands(other, targetUnit, targetUnitRequired);

	    double thisBase = this.unit.convertToBaseUnit(this.value);
	    double thatBase = other.unit.convertToBaseUnit(other.value);

	    double resultBase = operation.compute(thisBase, thatBase);

	    if(operation == ArithmeticOperation.DIVIDE) {
	        return resultBase;
	    } 

	    return targetUnit.convertFromBaseUnit(resultBase);
	}
}
