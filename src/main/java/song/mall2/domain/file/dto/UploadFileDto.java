package song.mall2.domain.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDto {
    private String savedFileName;
    private String url;

    public UploadFileDto(String savedFileName) {
        this.savedFileName = savedFileName;
        this.url = "/file/downloadFile/" + savedFileName;
    }
}
