package co.Example.controller;
import java.io.IOException;
// import org.apache.tika.io.IOExceptionWithCause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
// import  org.springframework.web.*;
import co.Example.services.*;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fidelius")
public class EncryptionController {

    // @Autowired
    // private FideliusCLIService fideliusCLIService;
    
    @Autowired
    private Services fideliusKeyService;

    @Autowired
    private Services fideliusService;

    @PostMapping("/encrypt")
    public String encryptFile(@RequestParam("file") MultipartFile file, @RequestParam String senderKey, @RequestParam String privateKey) {
        if (file.isEmpty()) {
            return "File is empty. Please upload a file.";
        }

        try {
            byte[] fileBytes = file.getBytes();
            String plaintext = new String(fileBytes);
            String encryptedContent = fideliusService.encryptFile(plaintext, senderKey, privateKey);

            if (encryptedContent != null) {
                return encryptedContent;
            } else {
                return "Encryption failed.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read the uploaded file.";
        }
    }

    @PostMapping("/decrypt")
    public String decryptContent(@RequestBody String encryptedContent, @RequestParam String senderKey, @RequestParam String privateKey) {
        if (encryptedContent.isEmpty()) {
            return "Encrypted content is empty.";
        }

        String decryptedContent = fideliusService.decryptFile(encryptedContent, senderKey, privateKey);

        if (decryptedContent != null) {
            return decryptedContent;
        } else {
            return "Decryption failed.";
        }
    }
    @GetMapping("/generateKeys")
    public String generateKeys() {
        return fideliusKeyService.generateKeyPair();
    }
}


