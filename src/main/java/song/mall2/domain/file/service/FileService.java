package song.mall2.domain.file.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import song.mall2.domain.file.dto.UploadFileDto;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;
import song.mall2.exception.notfound.exceptions.FileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${upload.path}")
    private String uploadPath;

    public void canUpload(List<MultipartFile> multipartFileList) {
        List<String> extList = multipartFileList.stream()
                .map(multipartFile -> getExt(multipartFile.getOriginalFilename()))
                .toList();

        for (String ext : extList) {
            if (!ext.equals("jpeg") && !ext.equals("jpg") && !ext.equals("png")) {
                throw new InvalidRequestException("지원되지 않는 파일 형식입니다.");
            }
        }
    }

    public UploadFileDto upload(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename().replace(" ", "");
        String savedFileName = createSavedFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(savedFileName)));

        return new UploadFileDto(savedFileName);
    }

    public void delete(String savedFileName) {
        File file = new File(getFullPath(savedFileName));

        if (file.exists()) {
            file.delete();
        }
    }

    public UrlResource get(String savedFileName) {
        try {
            UrlResource urlResource = new UrlResource("file:" + getFullPath(savedFileName));
            if (!urlResource.exists()) {
                throw new FileNotFoundException("파일이 존재하지 않습니다");
            }
            return urlResource;
        } catch (MalformedURLException | FileNotFoundException exception) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }
    }

    private String getFullPath(String saveFileName) {
        return uploadPath + saveFileName;
    }

    private String createSavedFileName(String originalFilename) {
        String ext = getExt(originalFilename);
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return originalFilename.substring(0, originalFilename.lastIndexOf(".")) + uuid + "." + ext;
    }

    private String getExt(String originalFilename) {
        int p = originalFilename.lastIndexOf(".");
        return originalFilename.substring(p + 1);
    }

    public void isExists(String saveFileName) {
        File file = new File(getFullPath(saveFileName));

        if (!file.exists()) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }
    }
}
