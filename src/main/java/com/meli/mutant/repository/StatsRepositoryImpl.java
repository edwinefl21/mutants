package com.meli.mutant.repository;

import com.meli.mutant.model.DNA;
import com.meli.mutant.model.DNAStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

@Repository
public class StatsRepositoryImpl implements IStatsRepository{

    @Autowired
    private transient MongoTemplate template;

    @Override
    public DNAStats calculateSumMutantsAndHuman() {
        Aggregation aggregation = getAggregationWithConditions();
        AggregationResults<DNAStats> result = this.template.aggregate(aggregation, DNA.class, DNAStats.class);
        var stats = result.getUniqueMappedResult();
        return stats == null ? new DNAStats() : stats;
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
