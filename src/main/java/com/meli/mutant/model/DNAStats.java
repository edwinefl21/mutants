package com.meli.mutant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class DNAStats {
    @JsonProperty("count_mutant_dna")
    private Long mutantCount = 0L;
    @JsonProperty("count_human_dna")
    private Long humanCount = 0L;
    private BigDecimal ratio= BigDecimal.valueOf(0.0);
    @JsonIgnore
    private Long total = 0L;

    @Override
    public String toString() {
        return "DNAStats [mutantCount=" + mutantCount + ", humanCount=" + humanCount + ", ratio=" + ratio + ", total="+ total + "]";
    }

}
