const message = {
  vm_cloud_server: {
    label: {
      ip_address: "IP地址",
      info: "主机信息",
      vm: "云主机",
      current_config: "当前配置",
      instance_type: "实例规格",
      new_config: "变更后配置",
    },
    status: {
      creating: "创建中",
      running: "运行中",
      stopped: "已关机",
      rebooting: "重启中",
      wait_recycle: "待回收",
      deleted: "已删除",
    },
    btn: {
      power_on: "启动",
      shutdown: "关机",
      power_off: "关闭电源",
      reboot: "重启",
      change_config: "配置变更",
    },
    message_box: {
      confirm_power_on: "确认启动",
      confirm_shutdown: "确认关机",
      confirm_power_off: "确认关闭电源",
      confirm_reboot: "确认重启",
      confirm_delete: "确认删除",
      confirm_batch_operate: "确认执行批量{0}操作",
      check_vm_tools_status_confirm_shutdown:
        "当前虚拟机未安装VmTools或VmTools未运行，无法软关机，若继续操作则关闭电源，是否继续？",
      confirm_config_update: "配置变更将会对实例执行关机操作，确认继续?",
    },
  },
  vm_cloud_image: {
    label: {
      management_info: "管理信息",
      image_name: "镜像名称",
      image_id: "镜像ID",
    },
    btn: {
      set_management_info: "设置管理信息",
    },
  },
  vm_cloud_disk: {
    label: {
      vm: "所属云主机",
      size: "大小",
      disk_category: "磁盘种类",
      disk_attribute: "磁盘属性",
      disk_type: "磁盘类型",
      mount_info: "挂载信息",
      delete_with_instance: "随实例删除",
      select_vm: "请选择要挂载的云主机",
      cloudVm: "云主机",
      deleteWithVm: "随云主机删除",
      disk_info: "磁盘信息",
      disk_name: "磁盘名称",
      change_config: "配置变更",
      current_config: "当前配置",
      after_config: "变更后配置",
      disk_size: "磁盘大小",
      system_disk: "系统盘",
      data_disk: "数据盘",
      disk_id: "磁盘ID",
    },
    status: {
      creating: "创建中",
      in_use: "已挂载",
      available: "可用",
      enlarging: "扩容中",
      wait_recycle: "待回收",
      deleted: "已删除",
    },
    btn: {
      create: "添加磁盘",
      enlarge: "扩容",
      uninstall: "卸载",
      mount: "挂载",
      delete: "删除",
    },
    confirm: {
      detach: "确认将云磁盘{0}从云主机{1}上卸载",
      delete: "回收站未开启，确认将云磁盘{0}直接删除",
      recycle: "回收站已开启，确认将云磁盘{0}放入回收站",
      batch_detach: "确认批量卸载云磁盘",
      batch_delete: "回收站未开启，确认批量删除云磁盘",
      batch_recycle: "回收站已开启，确认批量将云磁盘放入回收站",
    },
    msg: {
      canceled: "已取消{0}",
      select_one: "至少选择一条记录",
      datastore_null: "存储器不能为空",
    },
  },
  recycle_bin: {
    batch_recover_tips: "是否恢复选中的资源？",
    batch_delete_tips: "是否彻底删除选中的资源？删除后将无法恢复！",
    recover_tips: "是否恢复资源[{0}]？",
    delete_tips: "是否彻底删除[{0}]？删除后将无法恢复！",
    recover: "恢复",
    delete: "彻底删除",
    resource_name: "资源名称",
    resource_type: "资源类型",
    workspace_name: "工作空间",
    resource_status: "状态",
    relate_resource: "关联资源",
    resource_config: "资源配置",
    user_name: "操作人",
    delete_time: "删除时间",
    create_time: "创建时间",
    organization_name: "组织",
    account_name: "云账号",
    ip_array: "IP地址",
  },
};
export default {
  ...message,
};
