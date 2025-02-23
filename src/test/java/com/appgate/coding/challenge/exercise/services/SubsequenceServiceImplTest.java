package com.appgate.coding.challenge.exercise.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.appgate.coding.challenge.exercise.exceptions.NotFoundException;
import com.appgate.coding.challenge.exercise.model.Sequence;
import com.appgate.coding.challenge.exercise.model.TotalSequences;

@ExtendWith(SpringExtension.class)
class SubsequenceServiceImplTest {

	@TestConfiguration
    static class SubsequenceServiceImplTestContextConfiguration {
        @Bean
        public SubsequenceService subsequenceService() {
            return new SubsequenceServiceImpl();
        }
    }
	
	@Autowired
	private SubsequenceService subsequenceService;
	
	@Test
	void whenValidSequences_thenCountSequenceShouldBeCalculated() {
		Sequence sequence = new Sequence("rabbbit", "rabit");
		
		TotalSequences totalSequences = subsequenceService.countSequences(sequence);
		
		assertThat(totalSequences.getTotal()).isEqualTo(3);
	}
	
	@Test
	void whenValidSequencesWithoutCoincidences_thenCountSequenceShouldBeZero() {
		Sequence sequence = new Sequence("rabbbit", "tab");
		
		TotalSequences totalSequences = subsequenceService.countSequences(sequence);
		
		assertThat(totalSequences.getTotal()).isEqualTo(0);
	}
	
	@Test
	void whenInvalidSequences_thenSequenceShouldCatchAnException() {
		try {
			Sequence sequence = new Sequence("rabit", "rabbbit");
			
			TotalSequences totalSequences = subsequenceService.countSequences(sequence);
			
			assertThat(totalSequences.getTotal()).isEqualTo(0);
		} catch (NotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Bad Request Exception (400). The sequence size should be greater than the subsequence");
		}
	}
	
	@Test
	void whenSequencesAndSubsequenceValuesAreNull_thenSequenceShouldCatchAnException() {
		try {
			Sequence sequence = new Sequence(null, null);
			
			TotalSequences totalSequences = subsequenceService.countSequences(sequence);
			
			assertThat(totalSequences.getTotal()).isEqualTo(0);
		} catch (NotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Bad Request Exception (400). The sequence or subsequence can not be null");
		}
	}
	
	@Test
	void whenSequenceValueIsNull_thenSequenceShouldCatchAnException() {
		try {
			Sequence sequence = new Sequence(null, "rabit");
			
			TotalSequences totalSequences = subsequenceService.countSequences(sequence);
			
			assertThat(totalSequences.getTotal()).isEqualTo(0);
		} catch (NotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Bad Request Exception (400). The sequence or subsequence can not be null");
		}
	}
	
	@Test
	void whenSubsequenceValueIsNull_thenSequenceShouldCatchAnException() {
		try {
			Sequence sequence = new Sequence("rabbbit", null);
			
			TotalSequences totalSequences = subsequenceService.countSequences(sequence);
			
			assertThat(totalSequences.getTotal()).isEqualTo(0);
		} catch (NotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Bad Request Exception (400). The sequence or subsequence can not be null");
		}
	}
}
