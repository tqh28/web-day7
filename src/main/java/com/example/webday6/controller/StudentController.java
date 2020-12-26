package com.example.webday6.controller;

import java.text.DateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webday6.dto.CreateStudentDTO;
import com.example.webday6.dto.UpdateStudentDTO;
import com.example.webday6.entity.Student;
import com.example.webday6.service.StudentService;
import com.example.webday6.transform.StudentTransform;

@RestController
@RequestMapping("/students")
public class StudentController {

	private StudentService studentService;
	private DateFormat dateFormat;

	@Autowired
	public StudentController(StudentService studentService, DateFormat dateFormat) {
		this.studentService = studentService;
		this.dateFormat = dateFormat;
	}

	@GetMapping
	public List<Student> findAll() {
		return studentService.findAll();
	}
	
	@GetMapping("/{studentId}")
	public Student getById(@PathVariable long studentId) {
		return studentService.findById(studentId);
	}

	@PostMapping
	public Student insertStudent(@RequestBody CreateStudentDTO studentDTO) {
		StudentTransform transform = new StudentTransform(dateFormat);
		Student student = transform.apply(studentDTO);
		studentService.insert(student);
		return student;
	}

	@PutMapping("/{studentId}")
	public Student updateStudent(@PathVariable long studentId, @RequestBody UpdateStudentDTO studentDTO) {
		Student student = studentService.findById(studentId);
		StudentTransform transform = new StudentTransform(dateFormat);
		transform.apply(student, studentDTO);
		studentService.insert(student);
		return student;
	}

	@DeleteMapping("/{studentId}")
	public String deleteStudent(@PathVariable long studentId) {
		studentService.delete(studentId);
		return "Delete student success";
	}
}
