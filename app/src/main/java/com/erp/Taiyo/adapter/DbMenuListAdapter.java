package com.erp.Taiyo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.erp.Taiyo.R;
import com.erp.Taiyo.item.DbMenuListItem;

import java.util.ArrayList;

public class DbMenuListAdapter extends BaseAdapter implements Filterable {
    TextView tvTopName, tvAuthorityFlag, tvTopSeq, tvUserId, tvTopMenuId;

    private ArrayList<DbMenuListItem> ListArray = new ArrayList<>();
    private ArrayList<DbMenuListItem> FilterListArray = ListArray;
    Filter listFilter;
    String filiterName;



    @Override
    public Filter getFilter()
    {
        if(listFilter == null)
        {
            listFilter = new ListFilter();
        }
        return listFilter;
    }


    public void filterDate(String filter)
    {
        filiterName = filter;
    }

    @Override
    public int getCount(){
        return FilterListArray.size();
    }

    @Override
    public DbMenuListItem getItem(int position) {
        return FilterListArray.get(position) ;
    }

    public ArrayList getListItem(int position){
        return FilterListArray;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_menu, parent, false);
        }
        tvTopName = (TextView) convertView.findViewById(R.id.li_top_name);
        tvAuthorityFlag = (TextView) convertView.findViewById(R.id.li_authority_flag);
        tvTopSeq = (TextView) convertView.findViewById(R.id.li_top_seq);
        tvUserId = (TextView) convertView.findViewById(R.id.li_user_id);
        tvTopMenuId = (TextView) convertView.findViewById(R.id.li_top_menu_id);

        DbMenuListItem FilterListArray = getItem(position);

        tvTopName.setText(FilterListArray.getStrTopName());
        tvAuthorityFlag.setText(FilterListArray.getStrAuthorityFlag());
        tvTopSeq.setText(FilterListArray.getStrTopSeq());
        tvUserId.setText(FilterListArray.getStrUserId());
        tvTopMenuId.setText(FilterListArray.getStrTopMenuId());

        if(FilterListArray != null){
            convertView.setBackgroundColor((position & 1) == 1 ? Color.LTGRAY : Color.WHITE);
        }

        return convertView;
    }

    public void addItem(CharSequence tvTopName, CharSequence tvAuthorityFlag, CharSequence tvTopSeq, CharSequence tvUserId,
                        CharSequence tvTopMenuId
    )
    {
        //ListArray.add(item);
        DbMenuListItem Item = new DbMenuListItem();

        Item.setStrTopName((String) tvTopName);
        Item.setStrAuthorityFlag((String) tvAuthorityFlag);
        Item.setStrTopSeq((String) tvTopSeq);
        Item.setStrUserId((String) tvUserId);
        Item.setStrTopMenuId((String) tvTopMenuId);


        ListArray.add(Item);
    }

    public void clearItem(){
        ListArray.clear();

    }

    public void remove(int position)
    {
        ListArray.remove(position);
    }


    public void addItem(DbMenuListItem Item)
    {
        ListArray.add(Item);
    }

    private  class ListFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0)
            {
                results.values = ListArray;
                results.count = ListArray.size();

            }
            else
            {
                ArrayList<DbMenuListItem> itemList = new ArrayList<DbMenuListItem>();



                results.values = itemList;
                results.count = itemList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            FilterListArray = (ArrayList<DbMenuListItem>) results.values;

            if(results.count > 0)
            {
                notifyDataSetChanged();
            }
            else
            {
                notifyDataSetInvalidated();
            }
        }
    }
}

