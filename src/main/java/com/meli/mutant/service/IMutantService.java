package com.meli.mutant.service;

import com.meli.mutant.model.DNASequence;

public interface IMutantService {
    boolean isMutant(DNASequence dnaSequence);
}
