package com.project.moviegenie.member;

import com.project.moviegenie.member.controller.dto.MemberSignUpRequest;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@SpringBootTest
public class MemberValidationTest {
    //@Autowired
    private MemberRepository memberRepository;
    private static Validator validator;
    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    //@Test
    @DisplayName("컬럼이 @NotNull이고 값이 null이면 유효성 검증 예외가 발생한다.")
    public void notNullTest() {
        assertThrows(ConstraintViolationException.class, () -> {
            memberRepository.save(new Member());
        });
    }
    //@Test
    @DisplayName("컬럼이 @Column(nullable = false)이고 값이 null이면 데이터베이스 제약 조건 에러가 발생한다 ")
    public void columnNullableFalseTest() {
        memberRepository.save(new Member());
    }

    @DisplayName("@NotBlank 테스트")
    //@ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void notBlankTest(String input) {
        MemberSignUpRequest dto = new MemberSignUpRequest(input, "12345678","nick");

        Set<ConstraintViolation<MemberSignUpRequest>> violations = validator.validate(dto);

        ConstraintViolation<MemberSignUpRequest> violation = violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("email"))
                .findFirst()
                .get();

        assertThat(violation.getMessage()).isEqualTo("공백일 수 없습니다");
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
