package org.koreait.models.user;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.Users;
import org.koreait.repositories.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {

    private final UsersRepository repository;
    @Override // 세션에서 UsernameNotFoundException 를 username 으로 자동으로 넘겨줌.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByUserId(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getType().toString()));
        return UserInfo.builder()
                .userNo(user.getUserNo())
                .userId(user.getUserId())
                .userPw(user.getUserPw())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .authorities(authorities)
                .build();

    }
}
