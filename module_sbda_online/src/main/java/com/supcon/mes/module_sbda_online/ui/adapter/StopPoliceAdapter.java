package com.supcon.mes.module_sbda_online.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

import com.app.annotation.BindByTag;
import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.mes.mbap.view.CustomTextView;
import com.supcon.mes.middleware.util.Util;
import com.supcon.mes.module_sbda_online.R;
import com.supcon.mes.module_sbda_online.model.bean.StopPoliceEntity;

import java.text.SimpleDateFormat;

public class StopPoliceAdapter extends BaseListDataRecyclerViewAdapter<StopPoliceEntity> {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");

    public StopPoliceAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseRecyclerViewHolder<StopPoliceEntity> getViewHolder(int viewType) {
        return new ViewHolder(context);
    }

    class ViewHolder extends BaseRecyclerViewHolder<StopPoliceEntity> {

        @BindByTag("itemStopPoliceName")
        CustomTextView itemStopPoliceName;
        @BindByTag("itemStopPoliceStartTime")
        CustomTextView itemStopPoliceStartTime;
        @BindByTag("itemStopPoliceCloseTime")
        CustomTextView itemStopPoliceCloseTime;
        @BindByTag("itemStopPoliceTotalTime")
        CustomTextView itemStopPoliceTotalTime;


        public ViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_stop_police;
        }

        @SuppressLint("StringFormatInvalid")
        @Override
        protected void update(StopPoliceEntity data) {
            itemStopPoliceName.setContent(Util.strFormat(data.getEamType().name));
            itemStopPoliceStartTime.setContent(data.openTime != null ? dateFormat.format(data.openTime) : "--");
            itemStopPoliceCloseTime.setContent(data.closedTime != null ? dateFormat.format(data.closedTime) : "--");
            itemStopPoliceTotalTime.setContent(Util.big2(data.totalHour));
        }
    }
}
