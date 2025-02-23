package com.appgate.coding.challenge.exercise.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.appgate.coding.challenge.exercise.model.TotalSequences;
import com.appgate.coding.challenge.exercise.services.SubsequenceService;
import com.appgate.coding.challenge.exercise.services.SubsequenceServiceImpl;

@WebMvcTest(DistinctSequencesController.class)
class DistinctSequencesControllerIntegrationTest {

	@TestConfiguration
    static class SubsequenceServiceImplTestContextConfiguration {
        @Bean
        public SubsequenceService subsequenceService() {
            return new SubsequenceServiceImpl();
        }
    }
	
	@MockitoBean
	private SubsequenceService subsequenceService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void whenValidSequences_thenCountSequenceShouldBeCalculated() throws Exception {
		given(subsequenceService.countSequences(Mockito.any())).willReturn(new TotalSequences(3));
		
		mockMvc.perform(get("/api/sequences/distinct/{sequence}/{subsequence}", "rabbbit", "rabit")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
        	.andExpect(jsonPath("$.total", equalTo(3)));
	}
	
	@Test
	void whenValidSequencesWithoutCoincidences_thenCountSequenceShouldBeZero() throws Exception {
		given(subsequenceService.countSequences(Mockito.any())).willReturn(new TotalSequences(0));
		
		mockMvc.perform(get("/api/sequences/distinct/{sequence}/{subsequence}", "rabbbit", "tab")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
        	.andExpect(jsonPath("$.total", equalTo(0)));
	}

	@Test
	void whenInvalidSequences_thenSequenceShouldCatchAnException() throws Exception {
		mockMvc.perform(get("/api/sequences/distinct/{sequence}/{subsequence}", "rabit", "rabbbit")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(400))
        	.andExpect(content().string("Bad Request Exception (400). The sequence size should be greater than the subsequence"));
	}
}
