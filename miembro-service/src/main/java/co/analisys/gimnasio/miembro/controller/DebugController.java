package co.analisys.gimnasio.miembro.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);

    @GetMapping("/auth")
    public Map<String, Object> getAuthenticationInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> authInfo = new HashMap<>();

        if (auth != null) {
            authInfo.put("name", auth.getName());
            authInfo.put("principal", auth.getPrincipal().toString());
            authInfo.put("authenticated", auth.isAuthenticated());

            List<String> authorities = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            authInfo.put("authorities", authorities);

            logger.info("Authentication info: {}", authInfo);
        } else {
            authInfo.put("message", "No authentication found");
            logger.warn("No authentication found in SecurityContext");
        }

        return authInfo;
    }
}