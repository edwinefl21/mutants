package com.meli.mutant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.mutant.model.DNASequence;
import com.meli.mutant.model.DNAStats;
import com.meli.mutant.service.IStatsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ActiveProfiles("dev")
@WebMvcTest(MutantStatsController.class)
public class MutantStatsControllerTest {

    @MockBean
    private IStatsService service;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

    @Test
    public void throwsInternalServerErrorForUnhandledExceptions() throws Exception {
        Mockito.doThrow(new RuntimeException("Error")).when(service).getStats();
        mockMvc.perform(get("/stats/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
    @Test
    public void testStatsWithDefaultResponse() throws Exception {
        Mockito.doReturn(new DNAStats()).when(service).getStats();
        ResultActions result = mockMvc.perform(get("/stats/")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(0))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(0.0));

        byte[] content = result.andReturn().getResponse().getContentAsByteArray();

        DNAStats dnaResult = new ObjectMapper().readValue(content, DNAStats.class);
        System.out.println(dnaResult);
        assertTrue(dnaResult.getHumanCount() == 0L);
        assertTrue(dnaResult.getMutantCount() == 0L);
        assertTrue(dnaResult.getTotal() == 0L);
        assertEquals(dnaResult.getRatio(), BigDecimal.valueOf(0.0));
    }

}
