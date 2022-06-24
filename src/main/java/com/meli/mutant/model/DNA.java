package com.meli.mutant.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "dna")
public class DNA {
    @Id
    private Long id;
    private DNASequence dna;
    @Indexed(name = "is_mutant")
    private boolean isMutant;

    public DNA(DNASequence dna,boolean isMutant){
        this.dna=dna;
        this.isMutant=isMutant;
    }


}
