package com.nayak.springdocopenapiuiexample.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.nayak.springdocopenapiuiexample.model.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Student Data Transfer Object", example = """
        {
            "id": 1,
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@example.com",
            "dateOfBirth": "2000-01-01",
            "status": "ACTIVE",
            "studentId": "ST123456"
        }
        """)
public class StudentDTO {
    @Schema(
            description = "Student's unique identifier",
            example = "1",
            minimum = "1",
            type = "integer",
            readOnly = true
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Schema(
            description = "Student's first name",
            example = "John",
            minLength = 2,
            maxLength = 50,
            pattern = "^[a-zA-Z]+$",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Schema(
            description = "Student's last name",
            example = "Doe",
            minLength = 2,
            maxLength = 50,
            pattern = "^[a-zA-Z]+$",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(
            description = "Student's email address",
            example = "john.doe@example.com",
            pattern = "^[A-Za-z0-9+_.-]+@(.+)$",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Schema(
            description = "Student's date of birth",
            example = "2000-01-01",
            format = "date",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate dateOfBirth;

    @NotNull(message = "Status is required")
    @Schema(
            description = "Student's current status",
            example = "ACTIVE",
            allowableValues = {"ACTIVE", "INACTIVE", "GRADUATED", "SUSPENDED"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Student.StudentStatus status;

    @NotBlank(message = "Student ID is required")
    @Pattern(regexp = "^[A-Z]{2}\\d{6}$", message = "Student ID must be in format: XX123456")
    @Schema(
            description = "Student's unique ID number",
            example = "ST123456",
            pattern = "^[A-Z]{2}\\d{6}$",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String studentId;
}