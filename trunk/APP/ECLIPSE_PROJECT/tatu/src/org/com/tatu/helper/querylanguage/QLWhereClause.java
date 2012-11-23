package org.com.tatu.helper.querylanguage;

public class QLWhereClause {
	
	private StringBuilder ql = new StringBuilder();
	
	public enum Operator {
		OR, AND
	}
	
	public class Arms {
		private Operator operator;
		private String str;
		public Arms(Operator operator, String str) {
			if (!operator.equals(Operator.OR) && !operator.equals(Operator.AND)) throw new IllegalArgumentException();
			this.operator = operator;
			this.str = str;
		}
		public Operator getOperator() {
			return operator;
		}
		public String getStr() {
			return str;
		}
	}
	
	public Arms arms(Operator operator, String str){
		return new Arms(operator, str);
	}
	
	public QLWhereClause or(String str, Arms... arms) {
		if (!ql.toString().isEmpty()) ql.append(" or ");
		if (arms != null && arms.length > 0) {
			ql.append(" ( ").append(str + " ");
			for (Arms arm : arms) {
				if (arm.getOperator().equals(Operator.AND)) ql.append(" and ").append(arm.getStr());
				else if (arm.getOperator().equals(Operator.OR)) ql.append(" or ").append(arm.getStr());
			}
			ql.append(" ) ");
		} else {
			ql.append(str).append(" ");
		}
		return this;
	}
	
	public QLWhereClause and(String str, Arms... arms) {
		if (!ql.toString().isEmpty()) ql.append(" and ");
		if (arms != null && arms.length > 0) {
			ql.append(" ( ").append(str + " ");
			for (Arms arm : arms) {
				if (arm.getOperator().equals(Operator.AND)) ql.append(" and ").append(arm.getStr());
				else if (arm.getOperator().equals(Operator.OR)) ql.append(" or ").append(arm.getStr());
			}
			ql.append(" ) ");
		} else {
			ql.append(str).append(" ");
		}
		return this;
	}
	
	public String toString() {
		return !ql.toString().isEmpty() ? " where " + ql.toString() : "";
	}
}