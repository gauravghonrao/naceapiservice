package com.luxoft.proj.naceapi.validator;

import org.springframework.web.multipart.MultipartFile;

public class CSVFileTypeValidator implements IValidator<MultipartFile>
{
    String csvType = "text/csv";

    @Override
    public boolean isValid(MultipartFile file)
    {
        return csvType.equalsIgnoreCase(file.getContentType());
    }
}
