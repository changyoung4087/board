package org.koreait.commons.validators;

public interface MobileValidator {
    default boolean mobileCheck(String mobile){
        // 1. 형식을 통일화(replace)
        // 2. 형식을 체크
        mobile = mobile.replaceAll("\\D", ""); // 숫자가 아닌 식을 모두 제거
        //          010, 011, 016 \\ {3자리, 4자리} \\ {4자리} :: 전화번호 형식
        String pattern = "^01[016]\\d{3,4}\\d{4}$";

        return mobile.matches(pattern);
    }
}
