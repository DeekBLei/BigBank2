package com.example.project_bigbangk.controller;

import com.example.project_bigbangk.model.DTO.GraphicsWalletDTO;
import com.example.project_bigbangk.model.DTO.WalletDTO;
import com.example.project_bigbangk.model.Wallet;
import com.example.project_bigbangk.service.Security.AuthenticateService;
import com.example.project_bigbangk.service.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author Kelly Speelman - de Jonge
 */

@CrossOrigin
@RestController
public class WalletController {
    private final Logger logger = LoggerFactory.getLogger(WalletController.class);
    private final AuthenticateService authenticateService;
    private final WalletService walletService;
    private final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    public WalletController(AuthenticateService authenticateService, WalletService walletService) {
        super();
        this.authenticateService = authenticateService;
        this.walletService = walletService;
    }

    @GetMapping("/wallet")
    @ResponseBody
    public ResponseEntity<String> gotoWalletScreen(@RequestHeader String authorization){
        if (!authorization.split(" ")[0].equals("Bearer")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Got to login");
        }
        if (authenticateService.authenticate(authorization)){
            try {
                WalletDTO walletDTO = walletService.getWalletDTO(authorization);
                String jsonWallet = MAPPER.writeValueAsString(walletDTO);
                return ResponseEntity.ok().body(jsonWallet);
            } catch (JsonProcessingException exception) {
                logger.error(exception.getMessage());
            }
        }
        return ResponseEntity.status(401).body("token expired");
    }

    @GetMapping("/walletHistory")
    @ResponseBody
    public ResponseEntity<String> gotoWalletHistoryScreen(@RequestHeader String authorization){ //, @RequestBody String date
        if (!authorization.split(" ")[0].equals("Bearer")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Got to login");
        }
        if (authenticateService.authenticate(authorization)){
            //LocalDateTime dateTime = LocalDateTime.parse(date);
            try {
                walletService.getWallet(authorization);
                Map<String, Double> pieChart = walletService.calculateInformationPie();
                //walletService.calculateInformationLine(dateTime);
                String jsonWalletHistory = MAPPER.writeValueAsString(pieChart);
                return ResponseEntity.ok().body(jsonWalletHistory);
            } catch (JsonProcessingException exception) {
                logger.error(exception.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token expired");
    }
}
