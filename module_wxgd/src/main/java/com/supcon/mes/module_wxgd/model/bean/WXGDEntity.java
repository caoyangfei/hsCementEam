package com.supcon.mes.module_wxgd.model.bean;

import com.supcon.common.com_http.BaseEntity;
import com.supcon.mes.middleware.model.bean.FaultInfo;
import com.supcon.mes.middleware.model.bean.JWXItem;
import com.supcon.mes.middleware.model.bean.PendingEntity;
import com.supcon.mes.middleware.model.bean.RepairGroupEntity;
import com.supcon.mes.middleware.model.bean.Staff;
import com.supcon.mes.middleware.model.bean.SystemCodeEntity;
import com.supcon.mes.middleware.model.bean.WXGDEam;

import java.math.BigDecimal;
import java.util.List;

/**
 * WXGDEntity 维修工单实体
 * created by zhangwenshuai1 2018/8/8
 */
public class WXGDEntity extends BaseEntity implements Cloneable {

    public Long id;

    public Staff chargeStaff; //负责人

    public SystemCodeEntity checkResult; //验收结论

    public String claim; //要求

    public String content; //内容

    public WXGDEam eamID; //设备

    public FaultInfo faultInfo; //隐患信息

    public BigDecimal lastDuration; //上次执行时长(H)
    public Long lastTime; //上次执行时间

    public String lubricateOil; //润滑油
    public Long lubricateOilId; //润滑油ID

    public String lubricatingPart; //润滑部位
    public Long lubricatingPartId; //润滑部位ID

    public Long nextTime; //下次执行时间

    public SystemCodeEntity oilType; //加/换油

    public Integer period; //周期

    public SystemCodeEntity periodUnit; //周期单位

    public Long planEndDate; //计划完成时间

    public Long planStartDate; //计划开始时间

    public Long createTime; //创建时间

    public Long realEndDate; //实际完成时间

    public Long realStartDate; //实际开始时间

    public String receiptInfo; //派单标识

    public RepairGroupEntity repairGroup; //维修组

    public Long repairSum; //维修次数

    public Long sum; //数量

    public long version;//版本

    public BigDecimal thisDuration; //本次运行时长(H)

    public BigDecimal totalDuration; //累计运行时长(H)

    public String unitName; //单位

    public SystemCodeEntity workSource; //工单来源

    public SystemCodeEntity workType; //工单状态

    public String tableNo ;//单据编号

    public PendingEntity pending;  //待办

    public Long tableInfoId;//单据ID

    public List<SparePartEntity> sparePart;  //备件

    public List<LubricateOilsEntity> lubricateOils;  //润滑

    public List<RepairStaffEntity> repairStaffs;  //维修人员

    public List<AcceptanceCheckEntity> accceptanceCheck;  //验收

    public JWXItem jwxItem; //业务规则

    public Boolean whetherBornSparePart; // 是否生成领用单

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
