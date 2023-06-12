package com.fit2cloud.dao.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.io.Serial;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * <p>
 * 云主机运行关机状态计时
 * </p>
 *
 *
 * @author jianneng
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("vm_cloud_server_status_timing")
public class VmCloudServerStatusTiming implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "cloud_server_id",updateStrategy = FieldStrategy.IGNORED)
    private String cloudServerId;

    /**
     * 开机时间
     */
    @TableField(value = "on_time",updateStrategy = FieldStrategy.IGNORED)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime onTime;

    /**
     * 关机时间
     */
    @TableField(value = "off_time",updateStrategy = FieldStrategy.IGNORED)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime offTime;

    /**
     * 关机时长秒
     */
    @TableField("shutdown_duration")
    private Long shutdownDuration;

    /**
     * 运行时长秒
     */
    @TableField("running_duration")
    private Long runningDuration;
}
