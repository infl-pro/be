package song.mall2.domain.img.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import song.mall2.domain.img.dto.UploadFileDto;
import song.mall2.exception.invalid.exceptions.InvalidImageException;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;
import song.mall2.exception.notfound.exceptions.FileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${upload.path}")
    private String uploadPath;

    public List<UploadFileDto> upload(List<MultipartFile> multipartFileList) {
        List<UploadFileDto> fileDtoList = new ArrayList<>();

        try {
            for (MultipartFile multipartFile : multipartFileList) {
                if (multipartFile == null || multipartFile.isEmpty()) {
                    return fileDtoList;
                }
                canUpload(multipartFile);
                String originalFilename = multipartFile.getOriginalFilename().replace(" ", "");
                String savedFileName = createSavedFileName(originalFilename);
                multipartFile.transferTo(new File(getFullPath(savedFileName)));

                UploadFileDto upload = new UploadFileDto(savedFileName);
                fileDtoList.add(upload);
            }
            return fileDtoList;
        } catch (IOException e) {
            throw new InvalidImageException("이미지를 저장할 수 없습니다.");
        }
    }

    public UploadFileDto upload(MultipartFile multipartFile) {
        try {
            if (multipartFile == null || multipartFile.isEmpty()) {
                throw new InvalidImageException("이미지를 저장할 수 없습니다.");
            }
            canUpload(multipartFile);

            String originalFilename = multipartFile.getOriginalFilename().replace(" ", "");
            String savedFileName = createSavedFileName(originalFilename);
            multipartFile.transferTo(new File(getFullPath(savedFileName)));

            return new UploadFileDto(savedFileName);
        } catch (IOException e) {
            throw new InvalidImageException("이미지를 저장할 수 없습니다.");
        }
    }

    private void canUpload(MultipartFile multipartFile) {
        String ext = getExt(multipartFile.getOriginalFilename());

        if (!ext.equals("jpeg") && !ext.equals("jpg") && !ext.equals("png")) {
            throw new InvalidRequestException("지원되지 않는 파일 형식입니다.");
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

    public void delete(String savedFileName) {
        File file = new File(getFullPath(savedFileName));

        if (file.exists()) {
            file.delete();
        }
    }

    public void isExists(String saveFileName) {
        File file = new File(getFullPath(saveFileName));

        if (!file.exists()) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }
    }
}
