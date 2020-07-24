package com.example.LoggerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoggerController {

    @PostMapping("/logger")
    public ResponseEntity logger(@RequestBody Log log)
    {
        System.out.println("\n");
//        System.out.println(new Date());
        System.out.println("Sender: " + log.getSender());
        System.out.println("" + log.getLogString());
        System.out.println("Time:" + log.getDate());
        //System.out.println("--------");
        return ResponseEntity.ok("") ;
    }
}
