package com.meli.mutant.service;

import com.meli.mutant.model.DNAStats;
import com.meli.mutant.repository.IStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
//@Transactional
public class StatsServiceImpl implements IStatsService{
   @Autowired
    private IStatsRepository repository;
    public DNAStats getStats() {
        var stats = repository.calculateSumMutantsAndHuman();
        stats.setRatio(calculateRatio(stats));
        return stats;
    }

    private BigDecimal calculateRatio(DNAStats stats) {
        BigDecimal ratio = BigDecimal.valueOf(0.0);

        if (stats.getMutantCount() != 0) {
            if (stats.getHumanCount() == 0) {
                ratio = BigDecimal.ONE;
            } else {
                BigDecimal mutant = BigDecimal.valueOf(stats.getMutantCount());
                BigDecimal human = BigDecimal.valueOf(stats.getHumanCount());
                ratio = mutant.divide(human, 2, RoundingMode.HALF_UP);
            }
        }
        return ratio;
    }
}
