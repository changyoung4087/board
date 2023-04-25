package org.koreait.controllers.commons;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
@RequiredArgsConstructor
public class ErrorsController {

    private final HttpServletResponse response;
    @RequestMapping("/401") // Get, Post 모두 접근 가능.
    public String error401() {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);;
        return "error/401";
    }
}
