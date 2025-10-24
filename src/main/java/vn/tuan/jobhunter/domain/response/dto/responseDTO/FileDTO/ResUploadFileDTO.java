package vn.tuan.jobhunter.domain.response.dto.responseDTO.FileDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class ResUploadFileDTO {
    private String fileName;
    private Instant uploadAt;

}
