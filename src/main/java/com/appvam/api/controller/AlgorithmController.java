package com.appvam.api.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.appvam.api.exception.ResourceNotFoundException;
import com.appvam.api.model.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appvam.api.repository.AlgorithmRepository;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/algorithms")
public class AlgorithmController {

	private final AlgorithmRepository algorithmRepository;

	@GetMapping("")
	public List<Algorithm> getAllAlgorithms() {
		return algorithmRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Algorithm> getAlgorithmById(@PathVariable(value = "id") Long AlgorithmId)
			throws ResourceNotFoundException {
		Algorithm algorithm = algorithmRepository.findById(AlgorithmId)
				.orElseThrow(() -> new ResourceNotFoundException("Algorithm not found for this id :: " + AlgorithmId));
		return ResponseEntity.ok().body(algorithm);
	}

	@PostMapping("")
	public Algorithm createAlgorithm(@Valid @RequestBody Algorithm algorithm) {
		return algorithmRepository.save(algorithm);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Algorithm> updateAlgorithm(@PathVariable(value = "id") Long id,
													@Valid @RequestBody Algorithm algorithmDetails) throws ResourceNotFoundException {
		Algorithm algorithm = algorithmRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Algorithm not found for this id :: " + id));

		algorithmDetails.setId(id);
		final Algorithm updatedAlgorithm = algorithmRepository.save(algorithmDetails);
		return ResponseEntity.ok(updatedAlgorithm);
	}

	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteAlgorithm(@PathVariable(value = "id") Long AlgorithmId)
			throws ResourceNotFoundException {
		Algorithm algorithm = algorithmRepository.findById(AlgorithmId)
				.orElseThrow(() -> new ResourceNotFoundException("Algorithm not found for this id :: " + AlgorithmId));

		algorithmRepository.delete(algorithm);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
