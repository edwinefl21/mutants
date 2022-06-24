package com.meli.mutant.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Setter
@Getter
@ToString
public class DNASequence {
    private List<String> dna;

    @Override
    public String toString() {
        return "DNASequence [ dna =" + dna +"]";
    }
}
