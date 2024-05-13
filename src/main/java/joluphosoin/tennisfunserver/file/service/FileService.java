package joluphosoin.tennisfunserver.file.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FileService{

    @Autowired
    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    @Value("${spring.cloud.gcp.storage.project-id}")
    private String projectId;


    public FileService(Storage storage) {
        this.storage = storage;
    }

    public List<String> uploadObjects(List<MultipartFile> multipartFiles){

        List<String> urls = new ArrayList<>();
        multipartFiles.forEach(image -> urls.add(uploadObject(image)));

        return urls;
    }

    public String uploadObject(MultipartFile multipartFile) {

        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = multipartFile.getContentType();

        try {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder(bucketName, uuid)
                            .setContentType(ext)
                            .build(),
                    multipartFile.getInputStream()
            );
        } catch (IOException e) {
            throw new GeneralException(ErrorStatus.INTERNAL_SERVER_ERROR);
        }

        return "https://storage.googleapis.com/" + bucketName + "/" + uuid;
    }

    public String deleteObject(String objectName) {

//        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        Blob blob = storage.get(bucketName, objectName);
        if (blob == null) {
            throw new RuntimeException("The object " + objectName + " wasn't found in " + bucketName);
        }

        Storage.BlobSourceOption precondition =
                Storage.BlobSourceOption.generationMatch(blob.getGeneration());

        storage.delete(bucketName, objectName,precondition);

        return "Object " + objectName + " was deleted from " + bucketName;
    }
    public String subStringUrl(String url) {

        URL imageUrl = null;

        try {
            imageUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new GeneralException(ErrorStatus.INVALID_URL);
        }

        String path = imageUrl.getPath();
        return path.substring(path.lastIndexOf("/") + 1);
    }


}
