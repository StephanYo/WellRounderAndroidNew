package com.example.admin.wellrounder;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 12/24/2017.
 */

public class StockAdapterTab1 extends RecyclerView.Adapter<StockAdapterTab1.ViewHolder> {


    private List<StockItemList> mStockItemList;
    private Context context;



    public StockAdapterTab1(List<StockItemList> mStockItemList, Context context) {
        this.mStockItemList = mStockItemList;
        this.context = context;
    }


    // #1 creates the constructor, When view holder is created
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_item_list, parent, false);
        return new ViewHolder(v);



    }

    //#2 binds the data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //UserCurrent userCurrent = new UserCurrent();
        //String currentUserID = userCurrent.getId2().toString(); //change it get the data and stuff



        final StockItemList stockItemList = mStockItemList.get(position);
        holder.tvStockDate.setText(stockItemList.getDate());
        holder.tvStockOpen.setText("$" + stockItemList.getOpen());
        holder.tvStockHigh.setText("$" + stockItemList.getHigh());
        holder.tvStockLow.setText("$" + stockItemList.getLow());
        holder.tvStockClose.setText("$" + stockItemList.getClose());
        holder.tvStockAdjustedClose.setText("$" + stockItemList.getAdjustedClose());
        holder.tvStockVolume.setText(stockItemList.getVolume());
        holder.tvStockDivAmount.setText(stockItemList.getDivAmount());
        holder.tvSplitCoe.setText(stockItemList.getSplitCoe());

        holder.llStockItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap hashMap = stockItemList.getHashMap();
                String ticker = stockItemList.getTicker();
                String date = stockItemList.getDate();
                String open = stockItemList.getOpen();
                String high = stockItemList.getHigh();
                String low = stockItemList.getLow();
                String close = stockItemList.getClose();
                String adjclose = stockItemList.getAdjustedClose();
                String volume = stockItemList.getVolume();
                String divAmount = stockItemList.getDivAmount();
                String splitCoe = stockItemList.getSplitCoe();


                Intent myIntent = new Intent(view.getContext(), SpecificStockItem.class);
                myIntent.putExtra("map", hashMap);
                myIntent.putExtra("getTicker", ticker);
                myIntent.putExtra("getDate", date);
                myIntent.putExtra("getOpen", open);
                myIntent.putExtra("getHigh", high);
                myIntent.putExtra("getLow", low);
                myIntent.putExtra("getClose", close);
                myIntent.putExtra("getAdjClose", adjclose);
                myIntent.putExtra("getVolume", volume);
                myIntent.putExtra("getDivAmount", divAmount);
                myIntent.putExtra("getSplitCoe", splitCoe);


                context.startActivity(myIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStockItemList.size();
    }

    //created after calling #1
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvStockName, tvStockOpen, tvStockHigh, tvStockLow, tvStockClose,
        tvStockAdjustedClose, tvStockVolume, tvStockDivAmount, tvStockDate, tvSplitCoe;
        public ConstraintLayout clMessageMainWhole;
        //public LinearLayout llItemListTop, llItemListMiddle, llItemListBottom;
        public LinearLayout llStockItemList;


        public ViewHolder(View itemView) {
            super(itemView);

            tvStockName = (TextView) itemView.findViewById(R.id.tvStockNameLIST);
            tvStockDate = (TextView) itemView.findViewById(R.id.tvStockDate);
            tvStockOpen = (TextView) itemView.findViewById(R.id.tvStockOpenLIST);
            tvStockHigh = (TextView) itemView.findViewById(R.id.tvStockHighLIST);
            tvStockLow = (TextView) itemView.findViewById(R.id.tvStockLowLIST);
            tvStockClose = (TextView) itemView.findViewById(R.id.tvStockCloseLIST);
            tvStockAdjustedClose = (TextView) itemView.findViewById(R.id.tvStockAdjustedCloseLIST);
            tvStockVolume = (TextView) itemView.findViewById(R.id.tvStockVolumeLIST);
            tvStockDivAmount = (TextView) itemView.findViewById(R.id.tvStockDivAmountLIST);
            tvSplitCoe = (TextView) itemView.findViewById(R.id.tvStockSplitCoeLIST);

            llStockItemList = (LinearLayout) itemView.findViewById(R.id.llStockItemLayout);




        }
    }



}
