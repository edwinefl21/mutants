package com.meli.mutant.util;

import com.meli.mutant.exception.InvalidStringParametersException;
import com.meli.mutant.model.DNASequence;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DNAUtilTest {

    @Test
    void GivenDNAInvalidCharactersThenReturnFalse(){
        DNASequence dnaSequence = new DNASequence();
        dnaSequence.setDna(new ArrayList<>(Arrays.asList("ATGCGA","CAXXXC","TTATGT","AGAAGG","CCCCTA","TCACTG")));
        var isValidDNA= DNAUtil.validateDNA(dnaSequence);
        assertEquals(isValidDNA,false);
    }

    @Test
    void GivenDNAValidCharactersThenReturnTrue(){
        DNASequence dnaSequence = new DNASequence();
        dnaSequence.setDna(new ArrayList<>(Arrays.asList("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG")));
        var isValidDNA= DNAUtil.validateDNA(dnaSequence);
        assertEquals(isValidDNA,true);
    }

    @Test
    void GivenDNAInvalidLowercaseCharactersThenReturnTrue(){
        DNASequence dnaSequence = new DNASequence();
        dnaSequence.setDna(new ArrayList<>(Arrays.asList("ATGCGA","cagtxx","TTATGT","AGAAGG","CCCCTA","TCACTG")));
        var isValidDNA= DNAUtil.validateDNA(dnaSequence);
        assertEquals(isValidDNA,false);
    }

    @Test
    void GivenDNAInValidLowercaseAndSpecialCharactersThenReturnTrue(){
        DNASequence dnaSequence = new DNASequence();
        dnaSequence.setDna(new ArrayList<>(Arrays.asList("ATGCGA","TTA*GT","TTATGT","AGAAGG","CCC!TA","TCACTG")));
        var isValidDNA= DNAUtil.validateDNA(dnaSequence);
        assertEquals(isValidDNA,false);
    }

    @Test
    void GivenRowMatrixDNAValidateIFIsMutant()  {
        String row= "AccccACCC";
        boolean isMutant = DNAUtil.MatchIsMutant(row);
        assertEquals(isMutant, true);
    }

    @Test
    void GivenSequenceDNAThenCountMutantsHorizontalDNA()  {
        DNASequence dnaSequence = new DNASequence();
        dnaSequence.setDna(new ArrayList<>(Arrays.asList("AttttA", "CAGTGC", "TTATGT", "AAAAGG", "CCCCTA", "TCACTG")));
        long countMatch = DNAUtil.CountMutantHorizontalDNA(dnaSequence);
        assertEquals(countMatch, 3);
    }

    @Test
    void GivenSequenceDNAThenCountMutantsVerticalDNA()  {
        DNASequence dnaSequence = new DNASequence();
        dnaSequence.setDna(new ArrayList<>(Arrays.asList("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG")));
        Character[][] matrixByDNASequence = DNAUtil.getMatrixByDNASequence(dnaSequence);
        long counter = DNAUtil.CountMutantVerticalDNA(matrixByDNASequence);
        assertEquals(counter, 1);
    }

    @Test
    void GivenSequenceDNAThenCountMutantsObliqueDNA()  {
        DNASequence dnaSequence = new DNASequence();
        dnaSequence.setDna(new ArrayList<>(Arrays.asList("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG")));
        Character[][] matrixByDNASequence = DNAUtil.getMatrixByDNASequence(dnaSequence);
        long counterDNA = DNAUtil.CountMutantObliqueDNA(matrixByDNASequence);
        assertEquals(counterDNA, 1);
    }

    @Test
    void GivenSequenceDNAThenReturnMatrixToValidateMutants() throws InvalidStringParametersException {
        DNASequence dnaSequence = new DNASequence();
        dnaSequence.setDna(new ArrayList<>(Arrays.asList("AttttA", "CAGTGC","TTATGT", "AAAAGG", "CCCCTA", "TcACTG")));

        Character[][] expectedMatrix = {{'A','T','T','T','T','A'},{'C','A','G','T','G','C'},{'T','T','A','T','G','T'},
                               {'A','A','A','A','G','G'},{'C','C','C','C','T','A'},{'T','C','A','C','T','G'} };

        Character[][] matrixByDNASequence = DNAUtil.getMatrixByDNASequence(dnaSequence);
        assertEquals(expectedMatrix.length, matrixByDNASequence.length);
        assertEquals(expectedMatrix[2][2], matrixByDNASequence[2][2]);
        assertEquals(expectedMatrix[5][2], matrixByDNASequence[5][2]);

    }
}
