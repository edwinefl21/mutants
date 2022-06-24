package com.meli.mutant.service;

import com.meli.mutant.model.DNA;
import com.meli.mutant.model.DNASequence;
import com.meli.mutant.model.DNAStats;
import com.meli.mutant.repository.IStatsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StatsServiceImplTest {
    @Autowired
    private IStatsService service;
    @MockBean
    private IStatsRepository repository;
    @Test
    void GivenDNAStatsMockThenReturnStats() {
        var dnaStatsMock = getDNAStatsMock(Long.valueOf(100), Long.valueOf(40));
        when(repository.calculateSumMutantsAndHuman()).thenReturn(dnaStatsMock);
        DNAStats stats = service.getStats();
        assertEquals(stats.getHumanCount(),100);
        assertEquals(stats.getMutantCount(),40);
        assertEquals(stats.getRatio(), new BigDecimal("0.40"));
    }

    @Test
    void GivenDNAStatsWithHumanZeroThenReturnStats() {
        var dnaStatsMock = getDNAStatsMock(Long.valueOf(0), Long.valueOf(40));
        when(repository.calculateSumMutantsAndHuman()).thenReturn(dnaStatsMock);
        DNAStats stats = service.getStats();
        assertEquals(stats.getHumanCount(),0);
        assertEquals(stats.getMutantCount(),40);
        assertEquals(stats.getRatio(), new BigDecimal("1"));
    }

    private DNAStats getDNAStatsMock(Long humanCount, Long mutantCount) {
        DNAStats statsMock = new DNAStats();
        statsMock.setHumanCount(humanCount);
        statsMock.setMutantCount(mutantCount);
        return statsMock;
    }

}
