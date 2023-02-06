package com.jingde.equipment.app.cabinets.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.cabinets.dto.CabinetAddDTO;
import com.jingde.equipment.app.cabinets.dto.CabinetSeatDTO;
import com.jingde.equipment.app.cabinets.dto.CabinetSeatListDTO;
import com.jingde.equipment.app.cabinets.service.CabinetSeatService;
import com.jingde.equipment.app.cabinets.service.CabinetService;
import com.jingde.equipment.app.cabinets.vo.CabinetOpenLogVO;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.Cabinet;
import com.jingde.equipment.model.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 枪弹柜管理
 *
 * @author
 */
@RestController
@RequestMapping("/api/cabinet")
public class CabinetController {

    @Resource
    private CabinetService cabinetService;
    @Resource
    private CabinetSeatService cabinetSeatService;

    /**
     * 查询枪柜列表
     * /api/cabinet/cabinetList
     * 需剔除接口
     * @param page
     * @param size
     * @return
     */
    @LoginRequired
    @GetMapping("/cabinetList")
    public Result<PageInfo<Cabinet>> cabinetList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, String status) {
        PageHelper.startPage(page, size);
        List<Cabinet> list = cabinetService.cabinetList(status);
        PageInfo<Cabinet> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 详细的枪柜列表（包含子弹枪支）
     * /api/cabinet/detailList
     *
     * @return
     */
    @LoginRequired
    @GetMapping("/detailList")
    public Result detailCabinetList() {
        List<Map> list = cabinetService.detailCabinetList();
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 新增枪柜
     * /api/cabinet/cabinetAdd
     *
     * @param currentUser
     * @param form
     * @return
     */
    @LoginRequired
    @PostMapping("/cabinetAdd")
    public Result add(@CurrentUser User currentUser, @RequestBody CabinetAddDTO form) {
        String registerPerson = currentUser.getPoliceName();
        cabinetService.insertCabinet(registerPerson, form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 更新枪柜
     * /api/cabinet/cabinetUpdate
     *
     * @param currentUser
     * @param form
     * @return
     */
    @LoginRequired
    @PostMapping("/cabinetUpdate")
    public Result updateCabinet(@CurrentUser User currentUser, @RequestBody CabinetAddDTO form) {
        String registerPerson = currentUser.getPoliceName();
        cabinetService.updateCabinet(registerPerson, form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 查询枪柜详情
     * /api/cabinet/cabinetDetail/{id}
     *
     * @param id
     * @return
     */
    @LoginRequired
    @GetMapping("/cabinetDetail/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Integer id) {
        Map<String, Object> cabinetDetailVo = cabinetService.cabinetDetail(id);
        return ResultGenerator.genSuccessResult(cabinetDetailVo);
    }

    /**
     * 同步实体柜信息
     * /api/cabinet/cabinetSync
     *
     * @param currentUser
     * @param form
     * @return
     */
    @LoginRequired
    @PostMapping("/cabinetSync")
    public Result cabinetSync(@CurrentUser User currentUser, @RequestBody Map<String, Integer> form) throws Exception {
        String modifier = currentUser.getPoliceName();
        Integer cabinetId = form.get("cabinetId");
        cabinetService.syncCabinetInfo(modifier, cabinetId);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 修改枪座
     * /api/cabinet/seatUpdate
     *
     * @param form
     * @return
     */
    @LoginRequired
    @PostMapping("/seatUpdate")
    public Result updateCabinetSeat(@RequestBody CabinetSeatListDTO form) {
        cabinetService.updateCabinetSeat(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 开柜
     * /api/cabinet/cabinetOpen
     *
     * @param form
     * @return
     */
    @LoginRequired
    @PostMapping("/cabinetOpen")
    public Result cabinetOpen(@RequestBody CabinetSeatListDTO form) throws Exception {
        cabinetService.cabinetOpen(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 枪柜开锁 (同时改变枪座状态)
     * /api/cabinet/openWithStatus
     *
     * @param form
     * @return
     */
    @LoginRequired
    @PostMapping("/openWithStatus")
    public Result cabinetOpenWithStatus(@RequestBody CabinetSeatListDTO form) throws Exception {
        cabinetService.cabinetOpenWithStatus(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 删除枪柜
     * /api/cabinet/cabinetDel/{id}
     *
     * @param id
     * @return
     */
    @LoginRequired
    @DeleteMapping("/cabinetDel/{id}")
    public Result delete(@PathVariable Integer id) {
        cabinetService.deleteCabinet(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 查询领用记录列表
     * /api/cabinet/openLogList
     *
     * @param page
     * @param size
     * @return
     */
    @LoginRequired
    @GetMapping("/openLogList")
    public Result cabinetOpenLogList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<CabinetOpenLogVO> list = cabinetService.cabinetOpenLogList();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 查询枪柜/弹柜筛选列表
     * /api/cabinet/select
     *
     * @param type
     */
    @LoginRequired
    @GetMapping("/select")
    public Result cabinetSelect(Integer type) {
        List<Cabinet> list = cabinetService.cabinetSelect(type);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 查询枪柜中枪支列表
     * /api/cabinet/select/cabinet
     *
     * @param
     */
    @LoginRequired
    @GetMapping("/select/cabinet")
    public Result cabinetSelectById(@NotNull(message = "柜子id为空") String cabinetId, String status ) {
        List<CabinetSeatDTO> list = cabinetSeatService.selectByCabinetId(cabinetId, status);
        return ResultGenerator.genSuccessResult(list);
    }

}
