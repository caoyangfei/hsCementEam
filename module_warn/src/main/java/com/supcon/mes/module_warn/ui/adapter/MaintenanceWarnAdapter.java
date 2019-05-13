package com.supcon.mes.module_warn.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.app.annotation.BindByTag;
import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.mes.mbap.view.CustomTextView;
import com.supcon.mes.middleware.util.HtmlParser;
import com.supcon.mes.middleware.util.HtmlTagHandler;
import com.supcon.mes.middleware.util.Util;
import com.supcon.mes.module_warn.R;
import com.supcon.mes.module_warn.model.bean.MaintenanceWarnEntity;

import java.text.SimpleDateFormat;


/**
 * @author yangfei.cao
 * @ClassName hongShiCementEam
 * @date 2019/4/1
 * ------------- Description -------------
 */
public class MaintenanceWarnAdapter extends BaseListDataRecyclerViewAdapter<MaintenanceWarnEntity> {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public MaintenanceWarnAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseRecyclerViewHolder<MaintenanceWarnEntity> getViewHolder(int viewType) {
        return new ViewHolder(context);
    }

    class ViewHolder extends BaseRecyclerViewHolder<MaintenanceWarnEntity> {

        @BindByTag("itemMaintenanceEquipmentNameTv")
        CustomTextView itemMaintenanceEquipmentNameTv;
        @BindByTag("itemMaintenanceLastDateTv")
        CustomTextView itemMaintenanceLastDateTv;
        @BindByTag("itemMaintenanceNextDateTv")
        CustomTextView itemMaintenanceNextDateTv;
        @BindByTag("itemMaintenanceAdvanceTimeTv")
        CustomTextView itemMaintenanceAdvanceTimeTv;
        @BindByTag("itemMaintenanceClaimTv")
        CustomTextView itemMaintenanceClaimTv;
        @BindByTag("itemMaintenanceContentTv")
        CustomTextView itemMaintenanceContentTv;

        public ViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_maintenance_warn;
        }


        @Override
        protected void update(MaintenanceWarnEntity data) {
            String eam = String.format(context.getString(R.string.device_style10), Util.strFormat(data.getEamID().name)
                    , Util.strFormat(data.getEamID().code));
            itemMaintenanceEquipmentNameTv.contentView().setText(HtmlParser.buildSpannedText(eam, new HtmlTagHandler()));

            itemMaintenanceLastDateTv.setValue(data.lastTime != null ? dateFormat.format(data.lastTime) : "");
            itemMaintenanceNextDateTv.setValue(data.nextTime != null ? dateFormat.format(data.nextTime) : "");
            itemMaintenanceAdvanceTimeTv.setContent(data.advanceTime != null ? String.valueOf(data.advanceTime) : "");

            if (!TextUtils.isEmpty(data.claim)) {
                itemMaintenanceClaimTv.setVisibility(View.VISIBLE);
                itemMaintenanceClaimTv.setContent(data.claim);
            }

            if (!TextUtils.isEmpty(data.content)) {
                itemMaintenanceContentTv.setVisibility(View.VISIBLE);
                itemMaintenanceContentTv.setContent(data.content);
            }
        }
    }
}
