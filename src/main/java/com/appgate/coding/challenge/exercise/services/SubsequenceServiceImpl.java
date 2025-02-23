package com.appgate.coding.challenge.exercise.services;

import org.springframework.stereotype.Service;

import com.appgate.coding.challenge.exercise.model.Sequence;
import com.appgate.coding.challenge.exercise.model.TotalSequences;

@Service
public class SubsequenceServiceImpl implements SubsequenceService {

	/**
	 * Solución tomada de https://algo.monster/liteproblems/115
	 * basado en dynamic programming.
	 */
	@Override
	public TotalSequences countSequences(Sequence sequence) {
		int targetLength = sequence.getSubSequence().length();
		int[] subsequenceCount = new int[targetLength + 1];
		subsequenceCount[0] = 1;

		for (char sourceChar : sequence.getSequence().toCharArray()) {
			for (int j = targetLength; j > 0; --j) {
				char targetChar = sequence.getSubSequence().charAt(j - 1);

				if (sourceChar == targetChar) {
					subsequenceCount[j] += subsequenceCount[j - 1];
				}
			}
		}

		return new TotalSequences(subsequenceCount[targetLength]);
	}

}
