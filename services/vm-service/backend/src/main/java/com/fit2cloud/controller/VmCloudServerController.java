package com.fit2cloud.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fit2cloud.common.log.annotation.OperatedLog;
import com.fit2cloud.common.log.constants.OperatedTypeEnum;
import com.fit2cloud.common.log.constants.ResourceTypeEnum;
import com.fit2cloud.controller.handler.ResultHolder;
import com.fit2cloud.controller.request.vm.BatchOperateVmRequest;
import com.fit2cloud.controller.request.vm.PageVmCloudServerRequest;
import com.fit2cloud.dto.VmCloudServerDTO;
import com.fit2cloud.service.IVmCloudServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jianneng
 * @date 2022/9/27 14:31
 **/
@RestController
@RequestMapping("/api/server")
@Validated
@Api("虚拟机相关接口")
public class VmCloudServerController {
    @Resource
    private IVmCloudServerService iVmCloudServerService;

    @ApiOperation(value = "分页查询虚拟机", notes = "分页查询虚拟机")
    @GetMapping("/page")
    public ResultHolder<IPage<VmCloudServerDTO>> list(@Validated PageVmCloudServerRequest pageVmCloudServerRequest) {
        return ResultHolder.success(iVmCloudServerService.pageVmCloudServer(pageVmCloudServerRequest));
    }

    @ApiOperation(value = "开机",notes = "启动虚拟机操作系统")
    @PostMapping("powerOn/{serverId}")
    @OperatedLog(resourceType= ResourceTypeEnum.CLOUD_SERVER,operated = OperatedTypeEnum.POWER_ON,
            resourceId = "#serverId",
            param = "#serverId")
    public ResultHolder<Boolean> powerOn(@PathVariable String serverId) {
        return ResultHolder.success(iVmCloudServerService.powerOn(serverId));
    }

    @ApiOperation(value = "重启",notes = "重启虚拟机操作系统")
    @PostMapping("reboot/{serverId}")
    @OperatedLog(resourceType= ResourceTypeEnum.CLOUD_SERVER,operated = OperatedTypeEnum.REBOOT,
            resourceId = "#serverId",
            param = "#serverId")
    public ResultHolder<Boolean> reboot(@PathVariable String serverId) throws Exception {
        return ResultHolder.success(iVmCloudServerService.rebootInstance(serverId));
    }

    @ApiOperation(value = "关机",notes = "关闭虚拟机操作系统")
    @PostMapping("shutdown/{serverId}/{powerOff}")
    @OperatedLog(resourceType= ResourceTypeEnum.CLOUD_SERVER,operated = OperatedTypeEnum.SHUTDOWN,
            resourceId = "#serverId",
            param = "#serverId")
    public ResultHolder<Boolean> shutdown(@PathVariable String serverId,@PathVariable Boolean powerOff) throws Exception {
        return ResultHolder.success(iVmCloudServerService.shutdownInstance(serverId,powerOff));
    }

    @ApiOperation(value = "关闭电源",notes = "关闭虚拟机电源")
    @PostMapping("powerOff/{serverId}")
    @OperatedLog(resourceType= ResourceTypeEnum.CLOUD_SERVER,operated = OperatedTypeEnum.SHUTDOWN,
            resourceId = "#serverId",
            param = "#serverId")
    public ResultHolder<Boolean> powerOff(@PathVariable String serverId) throws Exception {
        return ResultHolder.success(iVmCloudServerService.powerOff(serverId));
    }

    @ApiOperation(value = "删除",notes = "删除虚拟机")
    @PostMapping("delete/{serverId}")
    @OperatedLog(resourceType= ResourceTypeEnum.CLOUD_SERVER,operated = OperatedTypeEnum.SHUTDOWN,
            resourceId = "#serverId",
            param = "#serverId")
    public ResultHolder<Boolean> deleteInstance(@PathVariable String serverId) {
        return ResultHolder.success(iVmCloudServerService.deleteInstance(serverId));
    }

    @ApiOperation(value = "批量操作",notes = "批量操作虚拟机")
    @PostMapping("batchOperate")
    @OperatedLog(resourceType= ResourceTypeEnum.CLOUD_SERVER,operated = OperatedTypeEnum.BATCH_OPERATE)
    public ResultHolder<Boolean> batchOperate(@RequestBody BatchOperateVmRequest request) {
        return ResultHolder.success(iVmCloudServerService.batchOperate(request));
    }

}
