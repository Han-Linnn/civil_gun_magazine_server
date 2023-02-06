package com.jingde.equipment.app.file.controller;

import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.util.FileUitl;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oceanover on 2019-04-01.
 */
@RestController
@RequestMapping("/api/file")
public class FileController {
    //使用外网端口访问
    @Value("${server.port.outer}")
    private String port;
    @Value("${file.host}")
    private String fileHost;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${file.prePath}")
    private String prePath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/upload")
    public Result singleFileUpload(@RequestParam("file") MultipartFile file) throws FileUploadException {

        File directory = new File("");// 参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("项目地址路径: "+courseFile);
        String result = FileUitl.upload(file, uploadFolder);
        String url = String.format("http://%s:%s%s%s%s", fileHost, port, contextPath, prePath, result);
        logger.info("URL路径: "+url);
        Map res = new HashMap();
        res.put("url", url);
        return ResultGenerator.genSuccessResult(res);
    }
}
