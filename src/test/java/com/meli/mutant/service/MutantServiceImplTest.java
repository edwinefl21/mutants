package com.meli.mutant.service;

import com.meli.mutant.exception.InvalidParametersException;
import com.meli.mutant.exception.InvalidStringParametersException;
import com.meli.mutant.model.DNA;
import com.meli.mutant.model.DNASequence;
import com.meli.mutant.repository.IDNARepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MutantServiceImplTest {
    @Autowired
    private IMutantService service;
    @MockBean
    private IDNARepository repository;

    @Test
    void ValidateParametersNotNull() {
        InvalidParametersException exception = assertThrows(InvalidParametersException.class, () -> {
            service.isMutant(null);
        });
        String expectedMessage = "The parameter content is not valid dna";
        assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getErrorMessage(), expectedMessage);
    }

    @Test
    void ValidateParameterDNAContentIsNull() {
        DNASequence dnaSequence = new DNASequence();
        InvalidParametersException exception = assertThrows(InvalidParametersException.class, () -> {
            service.isMutant(dnaSequence);
        });
        String expectedMessage = "The parameter content is not valid dna";
        assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getErrorMessage(), expectedMessage);
    }

    @Test
    void ValidateParameterDNAContentIsEmpty() {
        DNASequence dnaSequence = new DNASequence();
        dnaSequence.setDna(new ArrayList<>());
        InvalidParametersException exception = assertThrows(InvalidParametersException.class, () -> {
            service.isMutant(dnaSequence);
        });
        String expectedMessage = "The parameter content is not valid dna";
        assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getErrorMessage(), expectedMessage);
        assertEquals(exception.getError(), "dna.invalid.parameters");
    }

    @Test
    void GivenDnaSequenceValidThenReturnIsMutant() {
        DNASequence dnaSequence = new DNASequence();
        dnaSequence.setDna(new ArrayList<>(Arrays.asList("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG")));
        when(repository.save(any(DNA.class))).thenReturn(new DNA(dnaSequence,true));
        boolean mutant = service.isMutant(dnaSequence);
        assertEquals(mutant,true);
        assertEquals(dnaSequence.toString(),"DNASequence [ dna =[ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG]]");
    }

    @Test
    void GivenDnaSequenceInValidThenManageException() throws InvalidStringParametersException {
        DNASequence dnaSequence = new DNASequence();
        dnaSequence.setDna(new ArrayList<>(Arrays.asList("ATGCGA","CAXXXC","TTATGT","AGAAGG","CCCCTA","TCACTG")));
        InvalidStringParametersException exception = assertThrows(InvalidStringParametersException.class, () -> {
            service.isMutant(dnaSequence);
        });
        String expectedMessage = "Only valid characters are (A,T,C,G). Found invalid chars in [ATGCGA, CAXXXC, TTATGT, AGAAGG, CCCCTA, TCACTG]";
        assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getErrorMessage(), expectedMessage);
        assertEquals(exception.getError(), "dna.invalid.string.parameter");
    }



}
