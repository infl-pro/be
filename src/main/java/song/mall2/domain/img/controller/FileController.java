package song.mall2.domain.img.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import song.mall2.domain.img.service.FileService;

import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> getDownload(@PathVariable(value = "fileName") String fileName) {
        UrlResource resource = fileService.get(fileName);
        String contentType = getContentType(fileName);

        String resourceName = UriUtils.encode(resource.getFilename(), StandardCharsets.UTF_8);
        String contentDisposition = "inline; filename=\"" + resourceName + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
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
