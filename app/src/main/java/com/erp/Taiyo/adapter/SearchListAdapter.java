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
import com.erp.Taiyo.item.SearchListItem;

import java.util.ArrayList;


public class SearchListAdapter extends BaseAdapter implements Filterable {
    TextView tvOperationDesc, tvStepDesc, tvWorkOrderNo, tvItemDesc, tvTotalOnhandQty, tvOpPoiseSeq, tvOpUnitSeq, tvSortNo;
    Button btnMenu;
    private ArrayList<SearchListItem> ListArray = new ArrayList<>();
    private ArrayList<SearchListItem> FilterListArray = ListArray;
    Filter listFilter;
    String filiterName;
    View convertView2;
    LayoutInflater inflater2;

    @Override
    public Filter getFilter()
    {
        if(listFilter == null)
        {
            listFilter = new SearchListAdapter.ListFilter();
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
    public SearchListItem getItem(int position) {
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
            convertView = inflater.inflate(R.layout.list_search, parent, false);
        }

        tvOperationDesc = (TextView) convertView.findViewById(R.id.li10_oreration_description);
        tvStepDesc  = (TextView) convertView.findViewById(R.id.li10_step_desc);
        tvWorkOrderNo = (TextView) convertView.findViewById(R.id.li10_work_order_no);
        tvItemDesc = (TextView) convertView.findViewById(R.id.li10_item_desc);
        tvTotalOnhandQty = (TextView) convertView.findViewById(R.id.li10_qctual_qty);
        tvOpPoiseSeq = (TextView) convertView.findViewById(R.id.li10_op_poise_order_seq);
        tvOpUnitSeq = (TextView) convertView.findViewById(R.id.li10_op_unit_order_seq);
        tvSortNo = (TextView) convertView.findViewById(R.id.li10_sort_no);

        SearchListItem FilterListArray = getItem(position);

        tvOperationDesc.setText(FilterListArray.getStrOperationDesc());
        tvStepDesc.setText(FilterListArray.getStrStepDesc());
        tvWorkOrderNo.setText(FilterListArray.getStrWorkOrderNo());
        tvItemDesc.setText(FilterListArray.getStrItemDesc());
        tvTotalOnhandQty.setText(FilterListArray.getStrTotalOnhandQty());
        tvOpPoiseSeq.setText(FilterListArray.getStrOpPoiseSeq());
        tvOpUnitSeq.setText(FilterListArray.getStrOpUnitSeq());
        tvSortNo.setText(FilterListArray.getStrSortNo());


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
    public void addItem(SearchListItem Item)
    {
        ListArray.add(Item);
    }



    public void addItem(CharSequence  tvOperationDesc,CharSequence  tvStepDesc , CharSequence tvWorkOrderNo ,CharSequence tvItemDesc
            , CharSequence  tvTotalOnhandQty ,CharSequence tvOpPoiseSeq ,CharSequence tvOpUnitSeq ,CharSequence tvSortNo
    )
    {
        //ListArray.add(item);
        SearchListItem Item = new SearchListItem();

        Item.setStrOperationDesc((String) tvOperationDesc);
        Item.setStrStepDesc((String) tvStepDesc);
        Item.setStrWorkOrderNo((String) tvWorkOrderNo);
        Item.setStrItemDesc((String) tvItemDesc);
        Item.setStrTotalOnhandQty((String) tvTotalOnhandQty);
        Item.setStrOpPoiseSeq((String) tvOpPoiseSeq);
        Item.setStrOpUnitSeq((String) tvOpUnitSeq);
        Item.setStrSortNo((String) tvSortNo);


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
                ArrayList<SearchListItem> itemList = new ArrayList<SearchListItem>();

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
            FilterListArray = (ArrayList<SearchListItem>) results.values;

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

