package song.mall2.domain.file.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import song.mall2.domain.file.dto.UploadFileDto;
import song.mall2.domain.file.service.FileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/uploadFile")
    public ResponseEntity<List<UploadFileDto>> postUploadFile(@RequestParam("files") List<MultipartFile> files) throws IOException {
        List<UploadFileDto> uploadFileDtoList = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            UploadFileDto uploadFile = fileService.upload(multipartFile);
            uploadFileDtoList.add(uploadFile);
        }

        return ResponseEntity.ok(uploadFileDtoList);
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

    @GetMapping("/getfile")
    public String getFile() {

        return "filepage";
    }

    @GetMapping("/getfiles")
    public String getFiles() {

        return "filespage";
    }
}
