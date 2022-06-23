package com.luxoft.proj.naceapi.controller;

import com.luxoft.proj.naceapi.dto.NaceDTO;
import com.luxoft.proj.naceapi.exception.NaceRecordNotSavedException;
import com.luxoft.proj.naceapi.services.NaceService;
import com.luxoft.proj.naceapi.validator.CSVFileTypeValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/nace-api")
public class NaceApiController
{
    @Autowired
    private NaceService naceService;

    @GetMapping(value = "/{orderId}/get",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public NaceDTO getNaceDetails(
            @PathVariable
                    String orderId
    )
    {
        return naceService.getNaceById(Integer.valueOf(orderId));
    }

    @PutMapping(value = "/add",
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NaceDTO> putNaceDetails(
            @RequestBody
                    NaceDTO naceDTO
    ) throws NaceRecordNotSavedException
    {
        return naceService.addNace(naceDTO);
    }

    @PutMapping(value = "/csv/upload",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> processNaceCSVFile(
            @RequestParam("file")
                    MultipartFile file
    ) throws NaceRecordNotSavedException
    {
        if (!new CSVFileTypeValidator().isValid(file))
        {
            return new ResponseEntity<>("CSV file is not selected - Please provide proper file",
                                        HttpStatus.BAD_REQUEST);
        }

        return naceService.process(file);
    }
}