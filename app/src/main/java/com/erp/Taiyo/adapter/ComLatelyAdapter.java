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
import com.erp.Taiyo.item.CombinLatelyItem;

import java.util.ArrayList;

public class ComLatelyAdapter extends BaseAdapter implements Filterable {
    TextView tvWorkOrderNo, tvWeekActualQty, tvEquipmentName, tvStir1Rpm, tvStir2Rpm, tvStir3Rpm, tvStir1MMinute, tvStir2MMinute, tvStir3MMinute;

    private ArrayList<CombinLatelyItem> ListArray = new ArrayList<>();
    private ArrayList<CombinLatelyItem> FilterListArray = ListArray;
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
    public CombinLatelyItem getItem(int position) {
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
            convertView = inflater.inflate(R.layout.list_lu_com_lately, parent, false);
        }
        tvWorkOrderNo = (TextView) convertView.findViewById(R.id.t2_work_order_no);
        tvWeekActualQty = (TextView) convertView.findViewById(R.id.t2_week_actual_qty);
        tvEquipmentName = (TextView) convertView.findViewById(R.id.t2_equipment_name);
        tvStir1Rpm = (TextView) convertView.findViewById(R.id.t2_stir_1_rpm);
        tvStir2Rpm = (TextView) convertView.findViewById(R.id.t2_stir_2_rpm);
        tvStir3Rpm = (TextView) convertView.findViewById(R.id.t2_stir_3_rpm);
        tvStir1MMinute = (TextView) convertView.findViewById(R.id.t2_stir_1_minute);
        tvStir2MMinute = (TextView) convertView.findViewById(R.id.t2_stir_2_minute);
        tvStir3MMinute = (TextView) convertView.findViewById(R.id.t2_stir_3_minute);


        CombinLatelyItem FilterListArray = getItem(position);

        tvWorkOrderNo.setText(FilterListArray.getStrWorkOrderNo());
        tvWeekActualQty.setText(FilterListArray.getStrWeekActualQty());
        tvEquipmentName.setText(FilterListArray.getStrEquipmentName());
        tvStir1Rpm.setText(FilterListArray.getStrStir1Rpm());
        tvStir2Rpm.setText(FilterListArray.getStrStir2Rpm());
        tvStir3Rpm.setText(FilterListArray.getStrStir3Rpm());
        tvStir1MMinute.setText(FilterListArray.getStrStir1MMinute());
        tvStir2MMinute.setText(FilterListArray.getStrStir2MMinute());
        tvStir3MMinute.setText(FilterListArray.getStrStir3MMinute());


        if(FilterListArray != null){
            convertView.setBackgroundColor((position & 1) == 1 ? Color.LTGRAY : Color.WHITE);
        }

        return convertView;
    }

    public void addItem(CharSequence tvWorkOrderNo, CharSequence tvWeekActualQty, CharSequence tvEquipmentName, CharSequence tvStir1Rpm,
                        CharSequence tvStir2Rpm, CharSequence tvStir3Rpm,
                        CharSequence tvStir1MMinute, CharSequence tvStir2MMinute, CharSequence tvStir3MMinute
    )
    {
        //ListArray.add(item);
        CombinLatelyItem Item = new CombinLatelyItem();

        Item.setStrWorkOrderNo((String) tvWorkOrderNo);
        Item.setStrWeekActualQty((String) tvWeekActualQty);
        Item.setStrEquipmentName((String) tvEquipmentName);
        Item.setStrStir1Rpm((String) tvStir1Rpm);
        Item.setStrStir2Rpm((String) tvStir2Rpm);
        Item.setStrStir3Rpm((String) tvStir3Rpm);
        Item.setStrStir1MMinute((String) tvStir1MMinute);
        Item.setStrStir2MMinute((String) tvStir2MMinute);
        Item.setStrStir3MMinute((String) tvStir3MMinute);




        ListArray.add(Item);
    }

    public void clearItem(){
        ListArray.clear();

    }

    public void remove(int position)
    {
        ListArray.remove(position);
    }


    public void addItem(CombinLatelyItem Item)
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
                ArrayList<CombinLatelyItem> itemList = new ArrayList<CombinLatelyItem>();



                results.values = itemList;
                results.count = itemList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            FilterListArray = (ArrayList<CombinLatelyItem>) results.values;

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

