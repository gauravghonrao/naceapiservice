package com.luxoft.proj.naceapi.services;

import java.util.List;
import java.util.Optional;

import com.luxoft.proj.naceapi.csvhelper.CSVHelper;
import com.luxoft.proj.naceapi.dto.NaceDTO;
import com.luxoft.proj.naceapi.dto.NaceDTOConverter;
import com.luxoft.proj.naceapi.entity.NaceEntity;
import com.luxoft.proj.naceapi.exception.NaceRecordNotFoundException;
import com.luxoft.proj.naceapi.exception.NaceRecordNotSavedException;
import com.luxoft.proj.naceapi.repository.INaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NaceService
{
    @Autowired
    private INaceRepository naceRepo;

    @Autowired
    private CSVHelper helper;

    @Transactional
    public NaceDTO getNaceById(Integer id)
    {
        Optional<NaceEntity> response = naceRepo.findById(id);
        if (response.isPresent())
        {
            return new NaceDTOConverter().convert(response.get());
        }
        throw new NaceRecordNotFoundException("Nace Record with Order Id:" + id + " not found.");
    }

    @Transactional
    public ResponseEntity<NaceDTO> addNace(NaceDTO naceDTO) throws NaceRecordNotSavedException
    {
        try
        {
            naceRepo.save(new NaceDTOConverter().convert(naceDTO));
            return new ResponseEntity<>(naceDTO, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            throw new NaceRecordNotSavedException("Error Saving Nace record with Id: " + naceDTO.getOrderId());
        }

    }

    @Transactional
    public ResponseEntity<String> process(MultipartFile file) throws NaceRecordNotSavedException
    {
        try
        {
            List<NaceEntity> naceRecords = helper.csvParse(file);
            if (!naceRecords.isEmpty())
            {
                naceRepo.saveAll(naceRecords);
            }
            return new ResponseEntity<String>("File Processed Successfully", HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            throw new NaceRecordNotSavedException("Error Saving Nace records in File: " + file.getName());
        }
    }
}
