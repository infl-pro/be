package song.mall2.domain.file.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import song.mall2.domain.file.dto.UploadFileDto;
import song.mall2.domain.file.service.FileService;

import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/uploadFile")
    public ResponseEntity<UploadFileDto> postUploadFile(MultipartFile upload) throws IOException {
        UploadFileDto uploadFile = fileService.upload(upload);

        return ResponseEntity.ok(uploadFile);
    }

    @GetMapping("/downloadFile/{savedFileName}")
    public ResponseEntity<Resource> getDownload(@PathVariable(value = "savedFileName") String savedFileName) {
        UrlResource resource = fileService.get(savedFileName);
        String contentType = getContentType(savedFileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    private String getContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        }
        return "application/octet-stream";
    }
}
