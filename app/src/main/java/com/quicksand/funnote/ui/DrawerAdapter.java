package com.quicksand.funnote.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.quicksand.funnote.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {
    private static final int TYPE_DIVIDER = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_MENU = 2;
    private List<DrawerItem> datas = new ArrayList<>();
    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DrawerViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_DIVIDER:
                viewHolder = new DrawerViewHolder(inflater.inflate(R.layout.drawer_divider,null));
                break;
            case TYPE_HEADER:
                viewHolder = new HeaderViewHolder(inflater.inflate(R.layout.drawer_header,null));
                break;
            case TYPE_MENU:
                viewHolder = new MenuViewHolder(inflater.inflate(R.layout.drawer_menu,null));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        final DrawerItem drawerItem = datas.get(position);
        if (holder instanceof MenuViewHolder) {
            final MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
            MenuDrawerItem menuDrawerItem = (MenuDrawerItem) drawerItem;
            menuViewHolder.image.setBackgroundResource(menuDrawerItem.imageRes);
            menuViewHolder.text.setText(menuDrawerItem.textRes);
            menuViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMenuItemClick(menuViewHolder);
                }
            });
        } else if (holder instanceof HeaderViewHolder) {

        }
    }

    public OnMenuItemClickListener listener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        listener = listener;
    }
    public interface OnMenuItemClickListener {
        void onMenuItemClick(MenuViewHolder menuViewHolder);
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).type;
    }

    public class DrawerItem {
        public int type;
    }

    public class MenuDrawerItem extends DrawerItem {
        public int imageRes;
        public int textRes;
    }

    public class DrawerViewHolder extends RecyclerView.ViewHolder {

        public DrawerViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MenuViewHolder extends DrawerViewHolder {
        public View view;
        public ImageView image;
        public TextView text;
        public MenuViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }

    public class HeaderViewHolder extends DrawerViewHolder {
        public CircleImageView icon;
        public TextView name;

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class DividerViewHolder extends DrawerViewHolder {

        public DividerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
