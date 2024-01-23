package song.mall2.domain.file.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.file.dto.UploadFileDto;
import song.mall2.domain.file.service.FileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/uploadFile")
    public ResponseApi<List<UploadFileDto>> postUploadFile(@RequestParam("files") List<MultipartFile> files) throws IOException {
        fileService.canUpload(files);

        List<UploadFileDto> uploadFileDtoList = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            UploadFileDto uploadFile = fileService.upload(multipartFile);
            uploadFileDtoList.add(uploadFile);
        }

        return new ResponseApi<>(HttpStatus.OK.value(), "이미지 업로드 성공", uploadFileDtoList);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> getDownload(@PathVariable(value = "fileName") String fileName) {
        UrlResource resource = fileService.get(fileName);
        String contentType = getContentType(fileName);

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
