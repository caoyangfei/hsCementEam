package com.supcon.mes.middleware.model.bean;

import android.text.TextUtils;

import com.supcon.common.com_http.BaseEntity;

/**
 * JWXItem 业务规则实体
 * created by zhangwenshuai1 2018/9/18
 */
public class JWXItem extends BaseEntity {
    public Long id;
    public Long period;
    public SystemCodeEntity periodType;//周期类型
    public SystemCodeEntity periodUnit;//周期单位

    public AttachEamEntity attachEamId;
    public String claim;//要求
    public String content;//内容
    public Float lastDuration;
    public Float nextDuration;
    public Long lastTime;
    public Long nextTime;
    public SparePartId sparePartId;//备件编码

    public SystemCodeEntity getPeriodType() {
        if (periodType == null) {
            periodType = new SystemCodeEntity();
        }
        return periodType;
    }

    public SystemCodeEntity getPeriodUnit() {
        if (periodUnit == null) {
            periodUnit = new SystemCodeEntity();
        }
        return periodUnit;
    }

    //是否润滑时长
    public boolean isDuration() {
        if (periodType != null && !TextUtils.isEmpty(periodType.id) && periodType.id.equals("BEAM014/02")) {
            return true;
        }
        return false;
    }


    public SparePartId getSparePartId() {
        if (sparePartId==null) {
            sparePartId = new SparePartId();
        }
        return sparePartId;
    }

    public AttachEamEntity getAttachEamId() {
        if (attachEamId==null) {
            attachEamId = new AttachEamEntity();
        }
        return attachEamId;
    }
}
