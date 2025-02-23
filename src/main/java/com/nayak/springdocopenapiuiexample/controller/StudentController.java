package com.nayak.springdocopenapiuiexample.controller;

import com.nayak.springdocopenapiuiexample.dto.StudentDTO;
import com.nayak.springdocopenapiuiexample.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
@Tag(name = "Student Management", description = "APIs for managing student information")
public class StudentController {

    private final StudentService studentService;


    @Operation(
            summary = "Get all students",
            description = "Retrieves a list of all students in the system"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved all students",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class),
                            examples = @ExampleObject(
                                    name = "students",
                                    summary = "List of students",
                                    value = """
                                            [
                                                {
                                                    "id": 1,
                                                    "firstName": "John",
                                                    "lastName": "Doe",
                                                    "email": "john.doe@example.com",
                                                    "dateOfBirth": "2000-01-01",
                                                    "status": "ACTIVE",
                                                    "studentId": "ST123456"
                                                },
                                                {
                                                    "id": 2,
                                                    "firstName": "Jane",
                                                    "lastName": "Smith",
                                                    "email": "jane.smith@example.com",
                                                    "dateOfBirth": "2001-02-15",
                                                    "status": "ACTIVE",
                                                    "studentId": "ST789012"
                                                }
                                            ]
                                            """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @Operation(
            summary = "Get student by ID",
            description = "Retrieves a specific student by their ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Student found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class),
                            examples = @ExampleObject(
                                    name = "student",
                                    summary = "Student details",
                                    value = """
                                            {
                                                "id": 1,
                                                "firstName": "John",
                                                "lastName": "Doe",
                                                "email": "john.doe@example.com",
                                                "dateOfBirth": "2000-01-01",
                                                "status": "ACTIVE",
                                                "studentId": "ST123456"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "error",
                                    summary = "Error response",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Student not found with id: 1",
                                                "timestamp": "2023-11-08T12:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(
            @Parameter(description = "ID of the student to retrieve", required = true, example = "1")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @Operation(
            summary = "Create new student",
            description = "Creates a new student record"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Student created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class),
                            examples = @ExampleObject(
                                    name = "student",
                                    summary = "Created student",
                                    value = """
                                            {
                                                "id": 1,
                                                "firstName": "John",
                                                "lastName": "Doe",
                                                "email": "john.doe@example.com",
                                                "dateOfBirth": "2000-01-01",
                                                "status": "ACTIVE",
                                                "studentId": "ST123456"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "validationError",
                                    summary = "Validation error response",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Validation failed",
                                                "timestamp": "2023-11-08T12:00:00Z",
                                                "errors": {
                                                    "firstName": "First name is required",
                                                    "email": "Invalid email format"
                                                }
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(
            @Parameter(
                    description = "Student details",
                    required = true,
                    schema = @Schema(implementation = StudentDTO.class),
                    examples = @ExampleObject(
                            name = "newStudent",
                            summary = "New student request",
                            value = """
                                    {
                                        "firstName": "John",
                                        "lastName": "Doe",
                                        "email": "john.doe@example.com",
                                        "dateOfBirth": "2000-01-01",
                                        "status": "ACTIVE",
                                        "studentId": "ST123456"
                                    }
                                    """
                    )
            )
            @Valid @RequestBody StudentDTO studentDTO
    ) {
        StudentDTO created = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Update student",
            description = "Updates an existing student's information"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Student updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class),
                            examples = @ExampleObject(
                                    name = "updatedStudent",
                                    summary = "Updated student",
                                    value = """
                                            {
                                                "id": 1,
                                                "firstName": "John",
                                                "lastName": "Doe",
                                                "email": "john.doe@example.com",
                                                "dateOfBirth": "2000-01-01",
                                                "status": "GRADUATED",
                                                "studentId": "ST123456"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "error",
                                    summary = "Error response",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Student not found with id: 1",
                                                "timestamp": "2023-11-08T12:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "validationError",
                                    summary = "Validation error response",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Validation failed",
                                                "timestamp": "2023-11-08T12:00:00Z",
                                                "errors": {
                                                    "email": "Invalid email format",
                                                    "status": "Status is required"
                                                }
                                            }
                                            """
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @Parameter(description = "ID of the student to update", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(
                    description = "Updated student details",
                    required = true,
                    schema = @Schema(implementation = StudentDTO.class),
                    examples = @ExampleObject(
                            name = "updateStudent",
                            summary = "Update student request",
                            value = """
                                    {
                                        "firstName": "John",
                                        "lastName": "Doe",
                                        "email": "john.doe@example.com",
                                        "dateOfBirth": "2000-01-01",
                                        "status": "GRADUATED",
                                        "studentId": "ST123456"
                                    }
                                    """
                    )
            )
            @Valid @RequestBody StudentDTO studentDTO
    ) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
    }

    @Operation(
            summary = "Delete student",
            description = "Deletes a student from the system"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Student deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "error",
                                    summary = "Error response",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Student not found with id: 1",
                                                "timestamp": "2023-11-08T12:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "ID of the student to delete", required = true, example = "1")
            @PathVariable Long id
    ) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
