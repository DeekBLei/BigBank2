package com.example.project_bigbangk.controller;
/**

@Author Bigbangk
*/

import com.example.project_bigbangk.model.DTO.RegistrationDTO;
import com.example.project_bigbangk.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Registration {

    private RegistrationService registrationService;

    public Registration(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Receives http post request with JSON body and sends the DTO to registration service.
     * Then sends response based on what service returns
     * @param registrationDTO
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity receiveRegistrationInput(@RequestBody RegistrationDTO registrationDTO) {
        String registrationReturn = registrationService.registerClient(registrationDTO);

        if(registrationReturn.equals(RegistrationService.Messages.Success.getBody())){
        //stuur naar login, en popup?(voor later)
            return ResponseEntity.status(201).body("Registration Successful");
        }else if(registrationReturn.equals(RegistrationService.Messages.Email.getBody())) {
            return ResponseEntity.status(409).body("This e-mail is already in use");
        }else {
            return ResponseEntity.status(406).body(registrationReturn);
        }
    }

}
