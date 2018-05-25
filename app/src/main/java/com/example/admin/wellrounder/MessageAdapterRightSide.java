package com.example.admin.wellrounder;

/**
 * Created by admin on 12/7/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 10/18/2017.
 */

public class MessageAdapterRightSide extends RecyclerView.Adapter<MessageAdapterRightSide.ViewHolder> {


    private List<MessageList> mMessageList;
    private Context context;

    public MessageAdapterRightSide(List<MessageList> mMessageList, Context context) {
        this.mMessageList = mMessageList;
        this.context = context;
    }


    // #1 creates the constructor, When view holder is created
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list_item, parent, false);
        return new ViewHolder(v);
    }

    //#2 binds the data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {




        final MessageList messageList = mMessageList.get(position);

        //  final String post_id = listItem.getPost_id();

       //String username =  SharedPrefManager.getInstance(context).getUsername();

         //UserCurrent usercurrent =  new UserCurrent(username, context);




       // holder.tvMessageLeft.setText(messageList.getMessage());
        holder.tvMessageRight.setText(messageList.getMessage());
        //holder.tvPost_IDCMT.setText(messageList.getPost_id());


    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    //created after calling #1
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMessageLeft, tvMessageRight, tvTime_stamp;
        //public LinearLayout llItemListTop, llItemListMiddle, llItemListBottom;


        public ViewHolder(View itemView) {
            super(itemView);

           // tvMessageLeft = (TextView) itemView.findViewById(R.id.tvMessageLeft);
            tvMessageRight = (TextView) itemView.findViewById(R.id.tvMessageRightRight);
           // tvTime_stamp = (TextView) itemView.findViewById(R.id.tvTime);


            //llItemListTop = (LinearLayout) itemView.findViewById(R.id.llListItemTop);
            //llItemListMiddle = (LinearLayout) itemView.findViewById(R.id.llListItemMiddle);
            //llItemListBottom = (LinearLayout) itemView.findViewById(R.id.llListItemTop);


        }
    }




}