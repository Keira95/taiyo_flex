package com.erp.Taiyo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.erp.Taiyo.R;
import com.erp.Taiyo.item.LuOillerIListtem;

import java.util.ArrayList;


public class LuOillerListAdapter extends BaseAdapter implements Filterable {
    TextView tvOillerId, tvOillerCode, tvOillerDesc ,tvYuChk;
    Button btnMenu;
    private ArrayList<LuOillerIListtem> ListArray = new ArrayList<>();
    private ArrayList<LuOillerIListtem> FilterListArray = ListArray;
    Filter listFilter;
    String filiterName;
    View convertView2;
    LayoutInflater inflater2;

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
    public LuOillerIListtem getItem(int position) {
        return FilterListArray.get(position) ;
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
            convertView = inflater.inflate(R.layout.list_lu_oiller, parent, false);
        }
        tvOillerId = (TextView) convertView.findViewById(R.id.lu_oiller_id); //공정명
        tvOillerCode = (TextView) convertView.findViewById(R.id.lu_oiller_code); //ds pnl
        tvOillerDesc = (TextView) convertView.findViewById(R.id.lu_oiller_desc); // ds sqm
        tvYuChk     = (TextView) convertView.findViewById(R.id.lu_chk_flag); // ds sqm

        LuOillerIListtem FilterListArray = getItem(position);

        tvOillerId.setText(FilterListArray.getStrItemId());
        tvOillerCode.setText(FilterListArray.getStrItemCode());
        tvOillerDesc.setText(FilterListArray.getStrItemDesc());
        tvYuChk.setText(FilterListArray.getStrYuChkFlag());
        if(FilterListArray != null){
            convertView.setBackgroundColor((position & 1) == 1 ? Color.LTGRAY: Color.WHITE);
        }


        return convertView;
    }



    public void clearItem(){
        ListArray.clear();
    }

    public void remove(int position)
    {
        ListArray.remove(position);
    }
    public void addItem(LuOillerIListtem Item)
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
                ArrayList<LuOillerIListtem> itemList = new ArrayList<LuOillerIListtem>();

//                for (MenuIListtem item : ListArray) {
//                    if (filiterName.toString().equals("LOT")) {
//                        if (item.getStrLot().toUpperCase().contains(constraint.toString().toUpperCase())) {
//                            itemList.add(item);
//                        }
//                    }
//                    if (filiterName.toString().equals("공정명")) {
//                        if (item.getStrNamek().toUpperCase().contains(constraint.toString().toUpperCase())) {
//                            itemList.add(item);
//                        }
//                    }
//                }

                results.values = itemList;
                results.count = itemList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            FilterListArray = (ArrayList<LuOillerIListtem>) results.values;

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

