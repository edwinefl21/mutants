package com.meli.mutant.controller;

import com.meli.mutant.exception.InvalidParametersException;
import com.meli.mutant.exception.InvalidStringParametersException;
import com.meli.mutant.model.DNASequence;
import com.meli.mutant.service.IMutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    @Autowired
    private IMutantService mutantService;

    @PostMapping("/")
    public ResponseEntity<Void> mutants(@RequestBody DNASequence dnaSequence ) {
        try {
            var isMutant = mutantService.isMutant(dnaSequence);
            if(isMutant){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch (InvalidStringParametersException ex){
            return new ResponseEntity<>(ex.getStatus());
        }catch (InvalidParametersException ex){
            return new ResponseEntity<>(ex.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
