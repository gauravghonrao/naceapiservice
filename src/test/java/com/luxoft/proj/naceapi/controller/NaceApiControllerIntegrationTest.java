package com.luxoft.proj.naceapi.controller;

import com.luxoft.proj.naceapi.NaceApiApplication;
import com.luxoft.proj.naceapi.configuration.DBConfig;
import com.luxoft.proj.naceapi.dto.NaceDTO;
import com.luxoft.proj.naceapi.exception.NaceRecordNotSavedException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeType;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NaceApiApplication.class)
@ContextConfiguration(classes = { DBConfig.class })
@Sql(scripts = { "classpath:initData.sql" })
@Transactional
public class NaceApiControllerIntegrationTest
{
    @Autowired
    private NaceApiController naceApiController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void shouldReturnObjectAfterCallingGet() throws Exception, NaceRecordNotSavedException
    {
        NaceDTO response = naceApiController.getNaceDetails("0");
        Assert.assertEquals(0, response.getOrderId());
    }

    @Test
    public void shouldReturnObjectAfterCallingPut() throws Exception, NaceRecordNotSavedException
    {
        NaceDTO naceDto = new NaceDTO();
        naceDto.setOrderId(0);
        naceDto.setCode("1234");
        ResponseEntity<NaceDTO> response = naceApiController.putNaceDetails(naceDto);
        Assert.assertEquals(0, response.getBody().getOrderId());
        Assert.assertEquals("1234", response.getBody().getCode());
    }

    @Test
    public void whenTXTFileUploaded_thenVerifyBadRequestStatus()
            throws Exception, NaceRecordNotSavedException
    {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/nace-api/csv/upload");
        builder.with(new RequestPostProcessor()
        {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request)
            {
                request.setMethod("PUT");
                return request;
            }
        });
        mockMvc.perform(builder
                                .file(file))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void whenCSVFileUploaded_thenVerifyCreatedStatus()
            throws Exception, NaceRecordNotSavedException
    {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.csv",
                MediaType.asMediaType(MimeType.valueOf("text/csv")).toString(),
                ("Order,Level,Code,Parent,Description,includes,also-includes,Rulings,excludes,Reference \n "
                        + "0000,1,Code,Parent,Description,This item includes,This item also includes,Rulings,This item excludes,A").getBytes()
        );

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/nace-api/csv/upload");
        builder.with(new RequestPostProcessor()
        {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request)
            {
                request.setMethod("PUT");
                return request;
            }
        });
        mockMvc.perform(builder
                                .file(file))
               .andExpect(status().isCreated());
    }
}
