package com.meli.mutant.controller;

import com.meli.mutant.exception.InvalidParametersException;
import com.meli.mutant.exception.InvalidStringParametersException;
import com.meli.mutant.model.DNASequence;
import com.meli.mutant.service.IMutantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
public class MutantControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IMutantService mutantService;

    private static final String request = "{\n" +
            "  \"dna\":[\"AACAGCT\",\n" +
            "	        \"CTCAATT\",\n" +
            "	        \"ACGCTAT\",\n" +
            "	        \"ACGCGCA\",\n" +
            "	        \"CACGGCC\",\n" +
            "	        \"CCACGGT\",\n" +
            "	        \"TAACAGG\"]\n" +
            "}\n" +
            "";

    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

    @Test
    public void throwsInternalServerErrorForUnhandledExceptions() throws Exception {

        Mockito.doThrow(new RuntimeException("Error")).when(mutantService).isMutant(any(DNASequence.class));
        mockMvc.perform(post("/mutant/")
                        .content(request.getBytes())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void throwsInvalidStringParametersExceptionForUnhandledExceptions() throws Exception {

        Mockito.doThrow(new InvalidStringParametersException("String error")).when(mutantService).isMutant(any(DNASequence.class));
        mockMvc.perform(post("/mutant/")
                        .content(request.getBytes())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void throwsInvalidParametersExceptionForUnhandledExceptions() throws Exception {

        Mockito.doThrow(new InvalidParametersException("Parameter error")).when(mutantService).isMutant(any(DNASequence.class));
        mockMvc.perform(post("/mutant/")
                        .content(request.getBytes())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ResponseStatusOKWhenDNAIsMutant() throws Exception {

        Mockito.doReturn(true).when(mutantService).isMutant(any(DNASequence.class));
        mockMvc.perform(post("/mutant/")
                        .content(request.getBytes())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void ResponseStatusFORBIDDENWhenDNAIsNotMutant() throws Exception {

        Mockito.doReturn(false).when(mutantService).isMutant(any(DNASequence.class));
        mockMvc.perform(post("/mutant/")
                        .content(request.getBytes())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }



}
