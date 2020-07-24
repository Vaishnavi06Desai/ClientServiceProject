package com.example.AuthServer;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    RestClient client;

    Log log = new Log();

    @PostMapping("validate")
    public ResponseEntity<String> validatereq(@RequestBody JwtResponse jwtToken){
        String username = null;
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken.getToken());
            } catch (IllegalArgumentException e) {
                log.setLogString("Unable to get JWT.");
                log.setSender("Authentication Server");
                client.Logger(log);
                return new ResponseEntity<String>("Unable to get JWT Token", HttpStatus.UNAUTHORIZED);
            } catch (ExpiredJwtException e) {
                log.setLogString("JWT has expired.");
                log.setSender("Authentication Server");
                client.Logger(log);
                return new ResponseEntity<String>("JWT Token has expired", HttpStatus.UNAUTHORIZED);
            }

        // Once we get the token validate it.
        if (username != null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken.getToken(), userDetails)) {
                log.setLogString("Token is valid. Sending response...");
                log.setSender("Authentication Server");
                client.Logger(log);
                return new ResponseEntity<String>("ok", HttpStatus.OK);
            }

            else{
                log.setLogString("Token is invalid.");
                log.setSender("Authentication Server");
                client.Logger(log);
                return new ResponseEntity<String>("Token is invalid.", HttpStatus.UNAUTHORIZED);
            }
        }

        else{
            return new ResponseEntity<String>("Username is invalid.", HttpStatus.UNAUTHORIZED);
        }
    }
}
