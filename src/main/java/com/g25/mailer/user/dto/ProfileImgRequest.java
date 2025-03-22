package com.g25.mailer.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class ProfileImgRequest {
    @NotNull private MultipartFile file;
}
