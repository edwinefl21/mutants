package com.meli.mutant.controller;

import com.meli.mutant.model.DNAStats;
import com.meli.mutant.service.IStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class MutantStatsController {

    @Autowired
    private IStatsService serviceStats;

    @GetMapping("/")
    public ResponseEntity<DNAStats> statsMutants() {
        try {
            var stats = serviceStats.getStats();
            return new ResponseEntity<>(stats,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
