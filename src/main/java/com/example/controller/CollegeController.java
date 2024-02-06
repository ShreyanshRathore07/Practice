package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.College;
import com.example.repository.CollegeRepository;

@RestController
@RequestMapping("/college")
public class CollegeController {

	private static final Logger logger = LoggerFactory.getLogger(CollegeController.class);

	@Autowired
	CollegeRepository collegeRepository;

	@PostMapping("/save")
	public ResponseEntity<String> saveCollege(@RequestBody College college) {
		try {
			collegeRepository.save(college);
			logger.info("College data saved successfully.");
			return new ResponseEntity<>("College Data Saved Successfully...", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error saving college data: {}", e.getMessage(), e);
			return new ResponseEntity<>("Error saving college data: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get")
	public ResponseEntity<List<College>> getCollege() {
		try {
			List<College> collegeList = new ArrayList<>();
			collegeRepository.findAll().forEach(collegeList::add);
			logger.info("Retrieved {} college records.", collegeList.size());
			return new ResponseEntity<>(collegeList, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error retrieving college data: {}", e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get/getCollegeById/{id}")
	public ResponseEntity<Optional<College>> getCollegeById(@PathVariable("id") long id) {
		try {
			Optional<College> college = collegeRepository.findById(id);
			logger.info("Retrieved {} college records.", college);
			return new ResponseEntity<>(college, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error retrieving college data: {}", e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCollegeById(@PathVariable("id") long id) {
		try {
			collegeRepository.deleteById(id);
			logger.info("College record with id {} deleted successfully.", id);
			return new ResponseEntity<>("College record with " + id + " deleted successfully...", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error deleting college record with id {}: {}", id, e.getMessage(), e);
			return new ResponseEntity<>("Error deleting college record: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/put/{id}")
	public ResponseEntity<String> updateCollegeById(@PathVariable("id") long id, @RequestBody College college) {
		try {
			Optional<College> clgOptional = collegeRepository.findById(id);
			if (clgOptional.isPresent()) {
				college.setCollegeId(id);
				collegeRepository.save(college);
				logger.info("College details with id {} updated successfully.", id);
				return new ResponseEntity<>("College details with " + id + " updated successfully...", HttpStatus.OK);
			} else {
				logger.warn("College details with id {} not found.", id);
				return new ResponseEntity<>("College details with " + id + " not found...", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Error updating college details with id {}: {}", id, e.getMessage(), e);
			return new ResponseEntity<>("Error updating college details: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
