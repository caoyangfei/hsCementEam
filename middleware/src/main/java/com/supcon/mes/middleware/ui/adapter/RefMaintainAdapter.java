package com.supcon.mes.middleware.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.annotation.BindByTag;
import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.mes.mbap.utils.DateUtil;
import com.supcon.mes.mbap.view.CustomVerticalEditText;
import com.supcon.mes.mbap.view.CustomVerticalTextView;
import com.supcon.mes.middleware.R;
import com.supcon.mes.middleware.model.bean.MaintainEntity;
import com.supcon.mes.middleware.util.Util;

/**
 * @author yangfei.cao
 * @ClassName RefLubricateAdapter
 * @date 2018/9/5
 * 维保参照
 * ------------- Description -------------
 */
public class RefMaintainAdapter extends BaseListDataRecyclerViewAdapter<MaintainEntity> {
    public RefMaintainAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseRecyclerViewHolder<MaintainEntity> getViewHolder(int viewType) {
        return new RefProductViewHolder(context);
    }

    class RefProductViewHolder extends BaseRecyclerViewHolder<MaintainEntity> {
        @BindByTag("layout_refproduct")
        RelativeLayout layout_refproduct;

        @BindByTag("sparePartName")
        CustomVerticalTextView sparePartName;
        @BindByTag("attachEam")
        CustomVerticalTextView attachEam;
        @BindByTag("claim")
        CustomVerticalEditText claim;
        @BindByTag("content")
        CustomVerticalEditText content;


        public RefProductViewHolder(Context context) {
            super(context);
        }

        @Override
        protected void initListener() {
            super.initListener();
            layout_refproduct.setOnClickListener(v -> onItemChildViewClick(v, 0));
        }

        @Override
        protected int layoutId() {
            return R.layout.item_maintain;
        }

        @Override
        protected void update(MaintainEntity data) {
            sparePartName.setContent(Util.strFormat(data.getSparePartId().getProductID().productName));
            attachEam.setContent(data.getAccessoryEamId().getAttachEamId().name);
            claim.setContent(Util.strFormat(data.claim));
            content.setContent(Util.strFormat(data.content));
        }
    }

}
