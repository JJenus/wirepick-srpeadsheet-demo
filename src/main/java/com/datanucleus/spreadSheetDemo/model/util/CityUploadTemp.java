package com.datanucleus.spreadSheetDemo.model.util;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CityUploadTemp {
    private Long countryId;
    private Long stateId;
    private MultipartFile file;
}
