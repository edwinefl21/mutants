package com.meli.mutant.repository;

import com.meli.mutant.model.DNAStats;

public interface IStatsRepository {
    DNAStats calculateSumMutantsAndHuman();
}
