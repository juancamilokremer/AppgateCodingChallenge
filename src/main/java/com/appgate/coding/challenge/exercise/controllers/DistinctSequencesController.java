package com.appgate.coding.challenge.exercise.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appgate.coding.challenge.exercise.exceptions.NotFoundException;
import com.appgate.coding.challenge.exercise.model.Sequence;
import com.appgate.coding.challenge.exercise.model.TotalSequences;
import com.appgate.coding.challenge.exercise.services.SubsequenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/sequences")
public class DistinctSequencesController {
	private SubsequenceService subsequenceService;
	
	public DistinctSequencesController(SubsequenceService subsequenceService) {
		this.subsequenceService = subsequenceService;
	}
	
	@GetMapping("/distinct/{sequence}/{subsequence}")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Results are ok and return the total subsequences", content = { @Content(mediaType = "application/json") }),
		@ApiResponse(responseCode = "400", description = "Bad Request Exception (400). The sequence size should be greater than the subsequence", content = @Content),
		@ApiResponse(responseCode = "404", description = "resource not found", content = @Content)
	})
	@Operation(summary = "Given two strings s and t, return the number of distinct subsequences of s which equals t")
	public ResponseEntity<Object> distinct(@PathVariable String sequence, @PathVariable String subsequence) {
		try {
			Sequence sequences = new Sequence(sequence, subsequence);
			TotalSequences totalSequences = subsequenceService.countSequences(sequences);
			
			return new ResponseEntity<Object>(totalSequences, HttpStatus.OK);
		} catch (NotFoundException errorResponse) {
			return new ResponseEntity<Object>(errorResponse.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
