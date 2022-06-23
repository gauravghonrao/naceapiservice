package com.luxoft.proj.naceapi.csvhelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.luxoft.proj.naceapi.entity.NaceEntity;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CSVHelper
{
    public List<NaceEntity> csvParse(MultipartFile file) throws IOException
    {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(),
                                                                                  StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(fileReader,
                                                    CSVFormat.DEFAULT))
        {

            List<CSVRecord> records = csvParser.getRecords();

            return getEntities(records);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    private List<NaceEntity> getEntities(List<CSVRecord> records)
    {
        List<NaceEntity> entities = new ArrayList<>();
        for (int i = 1; i < records.size(); i++)
        {
            NaceEntity naceEntity = NaceEntity.builder()
                                              .id(Integer.valueOf(records.get(i).get(0).trim()))
                                              .level(Integer.valueOf(records.get(i).get(1).trim()))
                                              .code(records.get(i).get(2).trim())
                                              .parent(records.get(i).get(3).trim())
                                              .description(records.get(i).get(4).trim())
                                              .includes(records.get(i).get(5).trim())
                                              .also_includes(records.get(i).get(6).trim())
                                              .rulings(records.get(i).get(7).trim())
                                              .excludes(records.get(i).get(8).trim())
                                              .references(records.get(i).get(9).trim())
                                              .build();
            entities.add(naceEntity);
        }
        return entities;
    }
}
