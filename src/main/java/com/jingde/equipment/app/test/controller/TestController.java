package com.jingde.equipment.app.test.controller;

import com.jingde.equipment.core.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by JingDe on 2019/02/20.
 *
 * @author
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public Result<String> test() {
        return new Result().setData("项目启动成功");
    }



}
