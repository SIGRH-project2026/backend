package sn.gainde2000.backenmfpai.web.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;

/**
 * @author Abdou Karim CISSOKHO
 * @created 08/11/2023-16:21
 * @project gestion_courriers
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRspDTO {

  private Long id;
  private String originalName;
  private String generatedName;
  private String fileCode;
  private String downloadUrl;
  private String fileType;
  private long fileSize;
  // private long idAppartenance;

  public static File toEntity(FileRspDTO dto) {
    return File.builder()
        .id(dto.id)
        .originalName(dto.originalName)
        .fileCode(dto.fileCode)
        .downloadUrl(dto.downloadUrl)
        .fileType(dto.fileType)
        .generatedName(dto.generatedName)
        .fileSize(dto.fileSize)
        .build();
  }

}
