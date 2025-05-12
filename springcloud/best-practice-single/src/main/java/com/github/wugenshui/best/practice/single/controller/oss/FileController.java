package com.github.wugenshui.best.practice.single.controller.oss;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.github.wugenshui.best.practice.single.constant.MinioConstant;
import com.github.wugenshui.best.practice.single.entity.AjaxResult;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : chenbo
 * @date : 2021-03-27
 */
@Tag(name = "文件上传")
@Controller
@RequestMapping("/oss/file")
@Slf4j
public class FileController {
    @Autowired
    private MinioClient minioClient;

    @Operation(summary = "上传附件")
    @PostMapping
    @ResponseBody
    public AjaxResult<String> upload(@RequestPart MultipartFile file) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        if (file == null || file.isEmpty()) {
            return AjaxResult.error("上传失败，请选择文件");
        }
        String filename = file.getOriginalFilename();
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        // 年月 + UUID + 拓展名
        String newFilename = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN) + IdUtil.fastSimpleUUID();
        if (StringUtils.isNotEmpty(fileExtension)) {
            newFilename += fileExtension;
        }
        InputStream inputStream = file.getInputStream();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .stream(inputStream, file.getSize(), 0)
                        .bucket(MinioConstant.DEFAULT_BUCKET)
                        .contentType(file.getContentType())
                        .object(newFilename)
                        .userMetadata(new HashMap() {
                            {
                                put(MinioConstant.META_FILENAME, filename);
                            }
                        })
                        .build()
        );
        return AjaxResult.success(newFilename);
    }

    @Schema(description = "下载附件")
    @GetMapping("/{name}")
    public void download(@PathVariable String name, HttpServletResponse response) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {

        ObjectStat objectStat = minioClient.statObject(StatObjectArgs.builder().bucket(MinioConstant.DEFAULT_BUCKET).object(name).build());
        Map<String, List<String>> attrListMap = objectStat.httpHeaders();
        List<String> strings = attrListMap.get(MinioConstant.META_PREFIX + MinioConstant.META_FILENAME);
        String filename = strings.get(0);
        // 读取文件输入流
        InputStream inputSteam = minioClient.getObject(
                GetObjectArgs.builder().bucket(MinioConstant.DEFAULT_BUCKET)
                        .object(name).build());

        response.setContentType(objectStat.contentType());
        response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(filename, "UTF-8"));
        byte[] buffer = new byte[1024];

        try (BufferedInputStream bis = new BufferedInputStream(inputSteam);
             OutputStream os = response.getOutputStream()) {
            ;
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
