package com.example.Laundry.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 회원 가입 요청 정보를 담는 DTO
 * regdate는 서버에서 자동 설정되므로 입력받지 않습니다.
 * manager 필드는 선택 사항이며 기본값은 'N'입니다.
 */
public record UserCreateDto(
        @NotBlank(message = "아이디는 필수 입력 항목입니다.")
        @Size(min = 4, max = 100, message = "아이디는 4~100자 사이여야 합니다.")
        String id,

        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "유효한 이메일 형식이어야 합니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String pwd,

        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        @Size(max = 100, message = "이름은 최대 100자입니다.")
        String name,

        @Size(max = 200, message = "주소는 최대 200자입니다.")
        String addr,

        @Size(max = 20, message = "전화번호는 최대 20자입니다.")
        String phone,

        @NotBlank(message = "국가 코드는 필수 입력 항목입니다.")
        @Size(min = 2, max = 2, message = "국가 코드는 2자리여야 합니다.")
        String countryCode,

        String profile,

        @NotBlank(message = "다이얼 코드는 필수 입력 항목입니다.")
        @Size(max = 10, message = "다이얼 코드는 최대 10자입니다.")
        String dialCode,

        @Pattern(regexp = "[YN]", message = "관리자 여부는 'Y' 또는 'N'이어야 합니다.")
        String manager
) {
    public UserCreateDto {
        if (manager == null || manager.isBlank()) {
            manager = "N";
        }
    }
}
