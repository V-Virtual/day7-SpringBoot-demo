package org.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingBoyController {
    @PostMapping("/parking-boys")
    public String addParkingBoy() {

        return "Parking";
    }
}
