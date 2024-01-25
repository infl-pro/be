package song.mall2.domain.img.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UploadFileDto {
    private String fileUrl;

    public UploadFileDto(String storedName) {
        this.fileUrl = "/file/downloadFile/" + storedName;
    }
}
