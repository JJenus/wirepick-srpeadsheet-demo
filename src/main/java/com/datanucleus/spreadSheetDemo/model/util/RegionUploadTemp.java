package com.datanucleus.spreadSheetDemo.model.util;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegionUploadTemp {
    private Long countryId;
    private MultipartFile file;
}
