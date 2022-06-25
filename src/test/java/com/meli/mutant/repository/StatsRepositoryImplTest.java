package com.meli.mutant.repository;

import com.meli.mutant.model.DNA;
import com.meli.mutant.model.DNAStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

@SpringBootTest
public class StatsRepositoryImplTest{
    @Autowired
    private IStatsRepository repository;
    @SpyBean
    private MongoTemplate mongoTemplate;

    @Test
    public void calculateSumMutantsAndHumanTest(){
       AggregationResults<DNAStats> aggregationResultsMock = mock(AggregationResults.class);
       when(mongoTemplate.aggregate(getAggregationWithConditions(), DNA.class, DNAStats.class)).thenReturn(aggregationResultsMock);
       when(aggregationResultsMock.getUniqueMappedResult()).thenReturn(null);
       DNAStats stats = repository.calculateSumMutantsAndHuman();
       assertNotNull(stats);
    }
    private Aggregation getAggregationWithConditions() {
        return  Aggregation.newAggregation(
                group()
                        .sum(ConditionalOperators
                                .when(ComparisonOperators.valueOf("isMutant").equalToValue(true)).then(1).otherwise(0)).as("mutantCount")
                        .sum(ConditionalOperators
                                .when(ComparisonOperators.valueOf("isMutant").equalToValue(false)).then(1).otherwise(0)).as("humanCount")
                        .count().as("total")
        );
    }


}
