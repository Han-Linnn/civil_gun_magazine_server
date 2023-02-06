package com.jingde.equipment.util;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by oceanover on 2019-04-01.
 */
public class FileUitl {
    private static final Logger logger = LoggerFactory.getLogger(FileUitl.class);
    private static String[] imageTypes = {"bmp", "jpeg", "jpg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd", "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf"};
    // 图片类型
    private static List<String> IMAGE_TYPE = Arrays.asList(imageTypes);

    /**
     * 判断是否是图片
     *
     * @param extName 后缀
     * @return boolean
     */
    private static boolean isImage(String extName) {
        if (null != IMAGE_TYPE && !IMAGE_TYPE.isEmpty()) {
            return IMAGE_TYPE.contains(extName);
        }
        return false;
    }

    /**
     * 获取文件扩展名
     */
    private static String ext(String filename) {
        int index = filename.lastIndexOf(".");

        if (index == -1) {
            return null;
        }
        return filename.substring(index + 1);
    }

    /*
     * 处理上传文件并保存到磁盘
     */
    public static String upload(MultipartFile file, String uploadFolder) throws FileUploadException {
        try {
            if (file.isEmpty()) {
                throw new FileUploadException("文件不能为空");
            }
            InputStream is = file.getInputStream();
            long fileSize = is.available();
            //文件最大为10M
            int FILE_MAX_SIZE = 10 * 1024 * 1024;
            if (fileSize > FILE_MAX_SIZE) {
                throw new FileUploadException("文件大小超出限制!");
            }
            String fileName = file.getOriginalFilename();
            String extName = ext(fileName);
            if (!isImage(extName)) {
                throw new FileUploadException("不支持的图片格式");
            }
            String filePath = "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + UUID.randomUUID() + "." + extName;
            // parh = /home/test/files//yyyyMMdd/UUID.png
            String path = uploadFolder + filePath;
            Path destination = Paths.get(path).normalize();
            logger.info("图片路径:"+destination.toString());
            Path parentPath = destination.getParent();
            //创建目录
            if (!Files.exists(parentPath)) {
                Files.createDirectories(parentPath);
            }
            //创建文件
            Files.copy(file.getInputStream(), destination);
            return filePath;
        } catch (FileUploadException e) {
            throw e;
        } catch (Exception e) {
            throw new FileUploadException("上传失败");
        }
    }
}
