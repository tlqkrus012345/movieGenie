package com.project.moviegenie.member;

import com.project.moviegenie.member.dto.MemberSignUpRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberValidationTest {
    private static Validator validator;
    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @DisplayName("유효하지 않은 이메일을 입력하면 유효성 검사에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {"test", "test@", "test@c", "test@gmail", "test@gmail.12", "test@gmail.co2"})
    void emailTest(String input) {
        MemberSignUpRequest dto = new MemberSignUpRequest(input, "a2345678","nick");

        Set<ConstraintViolation<MemberSignUpRequest>> violations = validator.validate(dto);

        ConstraintViolation<MemberSignUpRequest> violation = violations.iterator().next();

        assertThat(violation.getMessage()).isEqualTo("유효하지 않은 이메일입니다.");
    }

    @DisplayName("유효하지 않은 비밀번호를 입력하면 유효성 검사에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1234567", "12345678", "한글123456"})
    void passwordTest(String input) {
        MemberSignUpRequest dto = new MemberSignUpRequest("test@gmail.com", input,"nick");

        Set<ConstraintViolation<MemberSignUpRequest>> violations = validator.validate(dto);

        ConstraintViolation<MemberSignUpRequest> violation = violations.iterator().next();

        assertThat(violation.getMessage()).isEqualTo("8글자 이상 16글자 이하 최소 한개 이상의 영문과 숫자를 포함하여 작성해주세요.");
    }

    @DisplayName("유효하지 않은 닉네임을 입력하면 유효성 검사에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {"닉", "닉네임!"})
    void nickNameTest(String input) {
        MemberSignUpRequest dto = new MemberSignUpRequest("test@gmail.com", "a1234567", input);

        Set<ConstraintViolation<MemberSignUpRequest>> violations = validator.validate(dto);

        ConstraintViolation<MemberSignUpRequest> violation = violations.iterator().next();

        assertThat(violation.getMessage()).isEqualTo("특수문자와 공백을 허용하지 않으며 2글자 이상 16글자 이하로 작성해주세요.");
    }
}
