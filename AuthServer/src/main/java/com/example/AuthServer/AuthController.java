package com.example.AuthServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    RestClient client;

    Log log = new Log();

    @PostMapping("/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        log.setLogString("User exists.");
        log.setSender("Authentication Server");
        client.Logger(log);

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        JwtResponse jwtrsp = new JwtResponse();
        jwtrsp.setToken(token);
        log.setLogString("Returning token...");
        log.setSender("Authentication Server");
        client.Logger(log);
        return  jwtrsp;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            log.setLogString("Authenticating user...Checking if user exists in database..");
            log.setSender("Authentication Server");
            client.Logger(log);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            log.setLogString("Error: User is disabled.");
            log.setSender("Authentication Server");
            client.Logger(log);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            log.setLogString("Error: Invalid credentials." + username + password);
            log.setSender("Authentication Server");
            client.Logger(log);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
