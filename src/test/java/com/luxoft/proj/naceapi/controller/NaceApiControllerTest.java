package com.luxoft.proj.naceapi.controller;

import java.util.Optional;

import com.luxoft.proj.naceapi.dto.NaceDTO;
import com.luxoft.proj.naceapi.entity.NaceEntity;
import com.luxoft.proj.naceapi.exception.NaceRecordNotSavedException;
import com.luxoft.proj.naceapi.repository.INaceRepository;
import com.luxoft.proj.naceapi.services.NaceService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class NaceApiControllerTest
{
    @InjectMocks
    private NaceApiController naceApiController;

    @Mock
    private NaceService service;

    @Mock
    private INaceRepository repo;

    private MockMvc mockMvc;

    @Before
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(naceApiController).build();
    }

    @Test
    public void shouldReturnAddedObjectAfterCallingInsert() throws Exception, NaceRecordNotSavedException
    {
        String requestJson
                = "{\n"
                + "  \"orderId\": 123455556,\n"
                + "  \"level\": 0,\n"
                + "  \"code\": \"1\",\n"
                + "  \"parent\": \"1\",\n"
                + "  \"description\": \"1\",\n"
                + "  \"includes\": \"1\",\n"
                + "  \"also_includes\": \"1\",\n"
                + "  \"rulings\": \"1\",\n"
                + "  \"excludes\": \"1\",\n"
                + "  \"referenceISIC_Rev4\": \"1\"\n"
                + "}";

        Mockito.when(service.addNace(Mockito.any(NaceDTO.class)))
               .thenReturn(new ResponseEntity<NaceDTO>(HttpStatus.CREATED));
        mockMvc.perform(put(
                       "/nace-api/add")
                                .header("accept", "*/*")
                                .contentType("application/json")
                                .content(requestJson))
               .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnObjectAfterCallingGet() throws Exception, NaceRecordNotSavedException
    {
        NaceDTO dto = new NaceDTO();
        dto.setOrderId(1234);
        Mockito.when(service.getNaceById(1234))
               .thenReturn(dto);
        mockMvc.perform(get(
                       "/nace-api/1234/get")
                                .header("accept", "*/*")
                                .contentType("application/json")
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionAfterCallingGet() throws Exception
    {
        NaceDTO dto = new NaceDTO();
        dto.setOrderId(1234);
        Mockito.when(repo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(service.getNaceById(Mockito.anyInt())).thenCallRealMethod();
        ReflectionTestUtils.setField(service, "naceRepo", repo);
        mockMvc.perform(get(
                "/nace-api/1234/get")
                                .header("accept", "*/*")
                                .contentType("application/json")
                                .accept(MediaType.APPLICATION_JSON));
    }
}
