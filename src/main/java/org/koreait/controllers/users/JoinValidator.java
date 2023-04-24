package org.koreait.controllers.users;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.validators.MobileValidator;
import org.koreait.repositories.UsersRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, MobileValidator {

    private final UsersRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()){ // Bean Validation 검증 실패가 있는 경우..
            return;
        }
        JoinForm joinForm = (JoinForm)target;

        String userId = joinForm.getUserId();
        String userPw = joinForm.getUserPw();
        String userPwRe = joinForm.getUserPwRe();
        String mobile = joinForm.getMobile();

        // 1. 아이디 중복여부
        if(repository.userExists(userId)){
            errors.rejectValue("userId", "Validation.duplicate.userId");
        }

        // 2. userPw, userPwRe 일치 여부
        if(!userPw.equals(userPwRe)){
            errors.rejectValue("userPwRe", "Validation.incorrect.userPwRe");
        }

        // 3. 휴대전화번호(선택)가 있으면 형식체크
        if(mobile != null && !mobile.isBlank()){
            if(!mobileCheck(mobile)) { // 휴대전화번호 형식이 아닌 경우
                errors.rejectValue("mobile", "Validation.mobile");
            }

            mobile = mobile.replaceAll("\\D", "");
            joinForm.setMobile(mobile);;
        }
    }
}
