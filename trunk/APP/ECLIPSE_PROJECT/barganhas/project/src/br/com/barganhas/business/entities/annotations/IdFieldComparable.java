package br.com.barganhas.business.entities.annotations;

import java.lang.reflect.Field;

public class IdFieldComparable implements Comparable<IdFieldComparable> {
	
	public static final int MIN_VALUE = 1;
	
	private int 			score;
	private Field 			field;
	
	public IdFieldComparable(int score, Field field) {
		if (score > 0 || field == null) {
			throw new IllegalArgumentException();
		}
		this.score = score;
		this.field = field;
	}
	
	@Override
	public int compareTo(IdFieldComparable anotherInstance) {
		return this.score < anotherInstance.score ? -1 : this.score > anotherInstance.score ? 1 : 0;
	}

	public int getScore() {
		return score;
	}

	public Field getField() {
		return field;
	}
}
