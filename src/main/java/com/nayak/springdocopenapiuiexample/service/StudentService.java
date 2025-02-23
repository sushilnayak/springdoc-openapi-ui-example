package com.nayak.springdocopenapiuiexample.service;

import com.nayak.springdocopenapiuiexample.dto.StudentDTO;
import com.nayak.springdocopenapiuiexample.exception.StudentNotFoundException;
import com.nayak.springdocopenapiuiexample.model.Student;
import com.nayak.springdocopenapiuiexample.repository.StudentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    @Transactional
    public StudentDTO createStudent(@Valid StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    @Transactional
    public StudentDTO updateStudent(Long id, @Valid StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        updateStudentFields(existingStudent, studentDTO);
        Student updatedStudent = studentRepository.save(existingStudent);
        return convertToDTO(updatedStudent);
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setStatus(student.getStatus());
        dto.setStudentId(student.getStudentId());
        return dto;
    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
//        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setStatus(dto.getStatus());
        student.setStudentId(dto.getStudentId());
        return student;
    }

    private void updateStudentFields(Student student, StudentDTO dto) {
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setStatus(dto.getStatus());
        student.setStudentId(dto.getStudentId());
    }
}