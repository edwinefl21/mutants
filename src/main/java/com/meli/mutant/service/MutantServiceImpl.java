package com.meli.mutant.service;

import com.meli.mutant.exception.InvalidParametersException;
import com.meli.mutant.exception.InvalidStringParametersException;
import com.meli.mutant.model.DNA;
import com.meli.mutant.model.DNASequence;
import com.meli.mutant.repository.IDNARepository;
import com.meli.mutant.util.DNAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional;


//@Transactional
@Service
public class MutantServiceImpl implements IMutantService{
    @Autowired
    private IDNARepository repository;
    public boolean isMutant(DNASequence dnaSequence) {

        validateIfDNAMatchAllowCharacters(dnaSequence);

        var isMutant= validateIfDNAIsMutant(dnaSequence);
        var dna = new DNA(dnaSequence,isMutant);
        repository.save(dna);
        return isMutant;
    }
    private boolean validateIfDNAIsMutant(DNASequence dnaSequence){
        long counterMutant=0;
        counterMutant+=DNAUtil.CountMutantHorizontalDNA(dnaSequence);
        if(counterMutant>1) return true;
        var matrixDNA=DNAUtil.getMatrixByDNASequence(dnaSequence);
        counterMutant+=DNAUtil.CountMutantVerticalDNA(matrixDNA);
        if(counterMutant>1) return true;
        counterMutant+=DNAUtil.CountMutantObliqueDNA(matrixDNA);
        return counterMutant>1?true:false;
    }

    /**
     * Validate if parameters are valid
     * Validate if dna match allow characters (A,T,C,G)
     * @param dnaSequence
     */
    private void validateIfDNAMatchAllowCharacters(DNASequence dnaSequence){
        if(dnaSequence==null || dnaSequence.getDna() ==null || dnaSequence.getDna().isEmpty()){
            throw new InvalidParametersException("dna");
        }else if(!DNAUtil.validateDNA(dnaSequence)){
            throw new InvalidStringParametersException(dnaSequence.getDna().toString());
        }
    }
}
