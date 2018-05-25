package com.example.admin.wellrounder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.util.List;

/**
 * Created by admin on 10/18/2017.
 */

public class TestMyAdapterStockList extends RecyclerView.Adapter<TestMyAdapterStockList.ViewHolder> {


    private List<TestStockList> mStockList;
    private Context context;

    public TestMyAdapterStockList(List<TestStockList> mStockList, Context context) {
        this.mStockList = mStockList;
        this.context = context;
    }


    // #1 creates the constructor, When view holder is created
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_list, parent, false);
        return new ViewHolder(v);
    }

    //#2 binds the data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final TestStockList testStockList = mStockList.get(position);

        //  final String post_id = listItem.getPost_id();

       // holder.tvStockName.setText(testStockList.getStockName());
        holder.tvStockTicker.setText(testStockList.getSymbol());
        holder.tvStockClose.setText(testStockList.getStockClose());
       // holder.tvStockLow.setText(testStockList.getStockLow());
       // holder.stvStockHigh.setText(testStockList.getStockHigh());


    }

    @Override
    public int getItemCount() {
        return mStockList.size();
    }

    //created after calling #1
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvStockClose, tvStockTicker, tvStockPrice, tvStockLow, stvStockHigh;
        //public LinearLayout llItemListTop, llItemListMiddle, llItemListBottom;


        public ViewHolder(View itemView) {
            super(itemView);

           // tvStockName = (TextView) itemView.findViewById(R.id.tvStockName);
            tvStockTicker = (TextView) itemView.findViewById(R.id.tvStockTicker);
            tvStockClose = (TextView) itemView.findViewById(R.id.tvStockClose);
           // tvStockLow = (TextView) itemView.findViewById(R.id.tvStockLow);
           // stvStockHigh = (TextView) itemView.findViewById(R.id.tvStockHigh);


            //llItemListTop = (LinearLayout) itemView.findViewById(R.id.llListItemTop);
            //llItemListMiddle = (LinearLayout) itemView.findViewById(R.id.llListItemMiddle);
            //llItemListBottom = (LinearLayout) itemView.findViewById(R.id.llListItemTop);


        }
    }




}