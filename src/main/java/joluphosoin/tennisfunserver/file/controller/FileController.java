package joluphosoin.tennisfunserver.file.controller;

import joluphosoin.tennisfunserver.file.service.FileService;
import joluphosoin.tennisfunserver.payload.ApiResult;
import joluphosoin.tennisfunserver.payload.code.status.SuccessStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/file")
public class FileController {

    @Autowired
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<List<String>> uploadObject(@RequestPart List<MultipartFile> images) {
        return ApiResult.onSuccess(SuccessStatus.CREATED,fileService.uploadObjects(images));
    }

    @DeleteMapping("/delete/{uuid}")
    public ApiResult<String> deleteObject(@PathVariable String uuid) {
        return ApiResult.onSuccess(fileService.deleteObject(uuid));
    }

}
