package com.erp.Taiyo.adapter;

import android.content.Context;
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
import com.erp.Taiyo.item.YuRecentActualListtem;

import java.util.ArrayList;


public class YuRecentActuallAdapter extends BaseAdapter implements Filterable {
    TextView tvFileNo,tvEquipmentName,tvRollPressure,tvRollTemperature,tvRollSpeed,tvParticleDiameter,tvDispersionDegree,tvItemTemperature
    ;
    Button btnMenu;
    private ArrayList<YuRecentActualListtem> ListArray = new ArrayList<>();
    private ArrayList<YuRecentActualListtem> FilterListArray = ListArray;
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
    public YuRecentActualListtem getItem(int position) {
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
            convertView = inflater.inflate(R.layout.list_lu_yu_recent_actual, parent, false);
        }
        tvFileNo = (TextView) convertView.findViewById(R.id.tv_file_no);
        tvEquipmentName = (TextView) convertView.findViewById(R.id.tv_equipment_name);
        tvRollPressure = (TextView) convertView.findViewById(R.id.tv_roll_pressure);
        tvRollTemperature = (TextView) convertView.findViewById(R.id.tv_roll_temperature);
        tvRollSpeed = (TextView) convertView.findViewById(R.id.tv_roll_speed);
        tvParticleDiameter = (TextView) convertView.findViewById(R.id.tv_particle_diameter);
        tvDispersionDegree = (TextView) convertView.findViewById(R.id.tv_dispersion_degree);
        tvItemTemperature = (TextView) convertView.findViewById(R.id.tv_item_temperature);



        YuRecentActualListtem FilterListArray = getItem(position);


        tvFileNo.setText(FilterListArray.getStrFileNo());
        tvEquipmentName.setText(FilterListArray.getStrEquipmentName());
        tvRollPressure.setText(FilterListArray.getStrRollPressure());
        tvRollTemperature.setText(FilterListArray.getStrRollTemperature());
        tvRollSpeed.setText(FilterListArray.getStrRollSpeed());
        tvParticleDiameter.setText(FilterListArray.getStrParrticleDiamter());
        tvDispersionDegree.setText(FilterListArray.getStrDispersionDegree());
        tvItemTemperature.setText(FilterListArray.getStrItemTemperature());



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
    public void addItem(YuRecentActualListtem Item)
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
                ArrayList<YuRecentActualListtem> itemList = new ArrayList<YuRecentActualListtem>();

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
            FilterListArray = (ArrayList<YuRecentActualListtem>) results.values;

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

