package com.supcon.mes.module_warn.model.bean;

import com.google.gson.annotations.Expose;
import com.supcon.common.com_http.BaseEntity;
import com.supcon.mes.middleware.model.bean.LubricateOil;
import com.supcon.mes.middleware.model.bean.ValueEntity;
import com.supcon.mes.middleware.model.bean.WXGDEam;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DailyLubricateTaskEntity extends BaseEntity {
    public Long id;
    public WXGDEam eamID;
    public String lubricatePart;//润滑部位
    public ValueEntity oilType;
    public LubricateOil lubricateOil;

    public Long nextTime;
    public ValueEntity periodType;//类型

    public int position;

    public Float sum;

    public boolean isCheck;
    @Expose
    public int viewType = 0;
    @Expose
    public boolean isExpand;// 是否展开

    //责任人设备数量
    @Expose
    public Map<String, DailyLubricateTaskEntity> lubricationMap = new LinkedHashMap<>();

    //当前设备润滑部位
    @Expose
    public Map<String, List<DailyLubricateTaskEntity>> lubricationPartMap = new LinkedHashMap<>();

    public WXGDEam getEamID() {
        if (eamID == null) {
            eamID = new WXGDEam();
        }
        return eamID;
    }

    public LubricateOil getLubricateOil() {
        if (lubricateOil == null) {
            lubricateOil = new LubricateOil();
        }
        return lubricateOil;
    }

    public ValueEntity getOilType() {
        if (oilType == null) {
            oilType = new ValueEntity();
        }
        return oilType;
    }

    //是否润滑时长
    public boolean isDuration() {
        if (periodType != null && periodType.id.equals("BEAM014/02")) {
            return true;
        }
        return false;
    }
}
