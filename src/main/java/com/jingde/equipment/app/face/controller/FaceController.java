package com.jingde.equipment.app.face.controller;

import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.face.service.FaceRecognizeService;
import com.jingde.equipment.app.face.service.FaceService;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by JingDe on 2019/02/20.
 */
@RestController
@RequestMapping(value = "/api/face")
public class FaceController {
    @Resource
    FaceService faceService;
    @Resource
    FaceRecognizeService recognizeService;

    // 缓存session
    @GetMapping("/session")
//    @LoginRequired
    public Result cacheSeesion() throws Exception {
        Map res = faceService.cacheSeesion();
        return ResultGenerator.genSuccessResult(res);
    }

    // 人脸识别记录查询
    @GetMapping("/log")
//    @LoginRequired
    public Result getFaceLogInfo() throws Exception {
        Map res = faceService.getFaceLogInfo();
        return ResultGenerator.genSuccessResult(res);
    }

    // 人脸识别记录查询
    @GetMapping("/logs")
//    @LoginRequired
    public Result getFaceRecognizeLogInfo() throws Exception {
        faceService.test();
        return ResultGenerator.genSuccessResult();
    }
}
