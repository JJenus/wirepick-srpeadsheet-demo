package com.datanucleus.spreadSheetDemo.model.util;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StateUploadTemp {
    private Long regionId;
    private Long countryId;
    private MultipartFile file;
}
