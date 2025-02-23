package com.appgate.coding.challenge.exercise.model;

import com.appgate.coding.challenge.exercise.exceptions.NotFoundException;

public class Sequence {
	
	private final String sequence;
	private final String subSequence;
	
	public Sequence(String sequence, String subSequence) throws NotFoundException {
		if (sequence == null || subSequence == null) {
			throw new NotFoundException("The sequence or subsequence can not be null");
		}
		
		if (sequence.length() < subSequence.length()) {
			throw new NotFoundException("The sequence size should be greater than the subsequence");
		}
		
		this.sequence = sequence;
		this.subSequence = subSequence;
	}

	public String getSequence() {
		return sequence;
	}

	public String getSubSequence() {
		return subSequence;
	}
}
