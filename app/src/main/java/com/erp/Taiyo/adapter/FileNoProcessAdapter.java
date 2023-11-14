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
import com.erp.Taiyo.item.FileNoProcessListItem;

import java.util.ArrayList;


public class FileNoProcessAdapter extends BaseAdapter implements Filterable {
    TextView tvMoveId, tvSeq, tvFileNo ,tvActQty ,tvOpPoiserOrderSeq ,tvOpUnitOrderSeq ,  tvReleaseDate ,tvItemDesc ,tvWeekActualQty ,tvRemark ,tvSectionDesc,tvJobNo
            ,tvSplitFlag ,tvOpPoiseOrderId,tvOpUnitOrderId ,tvOperationId ,tvOperationDesc;
    Button btnMenu;
    private ArrayList<FileNoProcessListItem> ListArray = new ArrayList<>();
    private ArrayList<FileNoProcessListItem> FilterListArray = ListArray;
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
    public FileNoProcessListItem getItem(int position) {
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
            convertView = inflater.inflate(R.layout.list_process, parent, false);
        }

        tvSeq = (TextView) convertView.findViewById(R.id.li9_chk);
        tvFileNo  = (TextView) convertView.findViewById(R.id.li9_file_no);
        tvActQty = (TextView) convertView.findViewById(R.id.li9_qctual_qty);
        tvOpPoiserOrderSeq = (TextView) convertView.findViewById(R.id.li9_op_poise_order_seq);
        tvOpUnitOrderSeq = (TextView) convertView.findViewById(R.id.li9_op_unit_order_seq);
        tvReleaseDate = (TextView) convertView.findViewById(R.id.li9_release_date);

        tvItemDesc  = (TextView) convertView.findViewById(R.id.li9_item_desc);
        tvWeekActualQty = (TextView) convertView.findViewById(R.id.li9_week_actual_qty);
        tvRemark = (TextView) convertView.findViewById(R.id.li9_remark);
        tvSectionDesc = (TextView) convertView.findViewById(R.id.li9_section_desc);
        tvJobNo = (TextView) convertView.findViewById(R.id.li9_job_no);
        tvSplitFlag = (TextView) convertView.findViewById(R.id.li9_split_flag);
        tvOpPoiseOrderId = (TextView) convertView.findViewById(R.id.li9_op_poise_order_id);
        tvOpUnitOrderId = (TextView) convertView.findViewById(R.id.li9_op_unit_order_id);
        tvOperationId = (TextView) convertView.findViewById(R.id.li9_operation_id);
        tvOperationDesc = (TextView) convertView.findViewById(R.id.li9_operation_desc);



        FileNoProcessListItem FilterListArray = getItem(position);

        tvSeq.setText(FilterListArray.getStrChk());
        tvFileNo.setText(FilterListArray.getStrFileNo());
        tvActQty.setText(FilterListArray.getStrActualQty());
        tvOpPoiserOrderSeq.setText(FilterListArray.getStrOpPoiseOrderSeq());
        tvOpUnitOrderSeq.setText(FilterListArray.getStrOpUnitOrderSeq());

        tvReleaseDate.setText(FilterListArray.getStrReleaseDate());

        tvItemDesc.setText(FilterListArray.getStrItemDesc());
        tvWeekActualQty.setText(FilterListArray.getStrWeekActualQty());
        tvRemark.setText(FilterListArray.getStrRemark());
        tvSectionDesc.setText(FilterListArray.getStrSectionDesc());
        tvJobNo.setText(FilterListArray.getStrJobNo());
        tvSplitFlag.setText(FilterListArray.getStrSplitFlag());
        tvOpPoiseOrderId.setText(FilterListArray.getStrOpPoiseOrderSeq());
        tvOpUnitOrderId.setText(FilterListArray.getStrOpUnitOrderSeq());
        tvOperationId.setText(FilterListArray.getStrOperationId());
        tvOperationDesc.setText(FilterListArray.getStrOperationDesc());


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
    public void addItem(FileNoProcessListItem Item)
    {
        ListArray.add(Item);
    }



    public void addItem(CharSequence  tvSeq,CharSequence  tvFileNo , CharSequence tvOpPoiserOrderSeq ,CharSequence tvOpUnitOrderSeq
            , CharSequence  tvReleaseDate ,CharSequence tvItemDesc ,CharSequence tvWeekActualQty ,CharSequence tvRemark ,CharSequence tvSectionDesc,CharSequence tvJobNo
            , CharSequence tvSplitFlag ,CharSequence tvOpPoiseOrderId,CharSequence tvOpUnitOrderId ,CharSequence tvOperationId ,CharSequence tvOperationDesc
    )
    {
        //ListArray.add(item);
        FileNoProcessListItem Item = new FileNoProcessListItem();

        Item.setStrChk((String) tvSeq);

        Item.setStrFileNo((String) tvFileNo);
        Item.setStrOpPoiseOrderSeq((String) tvOpPoiserOrderSeq);
        Item.setStrOpUnitOrderSeq((String) tvOpUnitOrderSeq);
        Item.setStrReleaseDate((String) tvReleaseDate);
        Item.setStrItemDesc((String) tvItemDesc);
        Item.setStrWeekActualQty((String) tvWeekActualQty);
        Item.setStrRemark((String) tvRemark);
        Item.setStrSectionDesc((String) tvSectionDesc);
        Item.setStrJobNo((String) tvJobNo);
        Item.setStrSplitFlag((String) tvSplitFlag);
        Item.setStrOpUnitOrderId((String) tvOpUnitOrderId);
        Item.setStrOpPoiseOrderId((String) tvOpPoiseOrderId);
        Item.setStrOperationId((String) tvOperationId);
        Item.setStrOperationDesc((String) tvOperationDesc);




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
                ArrayList<FileNoProcessListItem> itemList = new ArrayList<FileNoProcessListItem>();

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
            FilterListArray = (ArrayList<FileNoProcessListItem>) results.values;

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

