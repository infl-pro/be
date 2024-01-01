package song.mall2.domain.file.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UploadFileDto {
    private String fileUrl;

    public UploadFileDto(String savedFileName) {
        this.fileUrl = "/file/downloadFile/" + savedFileName;
    }
}
