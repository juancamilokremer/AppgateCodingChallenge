package com.appgate.coding.challenge.exercise.services;

import com.appgate.coding.challenge.exercise.model.Sequence;
import com.appgate.coding.challenge.exercise.model.TotalSequences;

public interface SubsequenceService {
	TotalSequences countSequences(Sequence sequence);
}
