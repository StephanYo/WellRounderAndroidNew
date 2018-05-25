package com.example.admin.wellrounder;

/**
 * Created by admin on 12/7/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private List<MessageList> mMessageList;
    private Context context;



    public MessageAdapter(List<MessageList> mMessageList, Context context) {
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



        String username = SharedPrefManager.getInstance(context).getUsername();

       // UserCurrent usercurrent = new UserCurrent(username, context);




        //String usercurretntId = usercurrent.getId2();



        final MessageList messageList = mMessageList.get(position);

        messageList.getReciver_id();

        //Toast.makeText(context, messageList.getReciver_id() + " ayo", Toast.LENGTH_SHORT).show();

        //if(messageList.getSender_id())

        //if (messageList.getSender_id() != usercurrent.getId2() || messageList.getSender_id() != usercurrent.getId2()){

           // holder.tvMessageLeft.setText(messageList.getMessage());

        //}else{
           // holder.tvMessageRight.setText(messageList.getMessage());
        //}


        Integer currentidAYO = SharedPrefManager.getInstance(context).getKeyUserId();
       // int mCurrentID = Integer.parseInt(currentidAYO);

        String checkId = messageList.getReciver_id();
        int mReciverId = Integer.parseInt(checkId);
       // Toast.makeText(context, currentidAYO, Toast.LENGTH_SHORT).show();

        String mCurrentUsername = SharedPrefManager.getInstance(context).getUsername();
        //int mCurrentId = new UserCurrent().loadUser(mCurrentUsername);

        //Toast.makeText(context, mCurrentId + "Adapter Current", Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, mReciverId + "Adapter reciver", Toast.LENGTH_SHORT).show();

        //UserCurrent userCurrent = new UserCurrent();

        Integer mCurrentIdint = SharedPrefManager.getInstance(context).getCurretUserIDint();

        if( mCurrentIdint == mReciverId){
            holder.tvMessageLeft.setText(messageList.getMessage());
            holder.tvMessageRight.setVisibility(View.GONE);
        }
        else{
            holder.tvMessageRight.setText(messageList.getMessage());
            holder.tvMessageLeft.setVisibility(View.GONE);
            holder.ivUserIcon.setVisibility(View.GONE);

           // Toast.makeText(context, messageList.getSender_id(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(context, usercurrent.getId2() + " sup", Toast.LENGTH_SHORT).show();
        }


            //  final String post_id = listItem.getPost_id();


        //    holder.tvMessageLeft.setText(messageList.getMessage());
        // holder.tvMessageRight.setText(messageList.getMessage());
        //holder.tvPost_IDCMT.setText(messageList.getPost_id());


    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    //created after calling #1
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMessageLeft, tvMessageRight, tvTime_stamp;
        public ImageView ivUserIcon;
        //public LinearLayout llItemListTop, llItemListMiddle, llItemListBottom;


        public ViewHolder(View itemView) {
            super(itemView);

            tvMessageLeft = (TextView) itemView.findViewById(R.id.tvMessageLeft);
            tvMessageRight = (TextView) itemView.findViewById(R.id.tvMessageRight);
            ivUserIcon = (ImageView) itemView.findViewById(R.id.ivProfPicMessageList);
            // tvTime_stamp = (TextView) itemView.findViewById(R.id.tvTime);


            //llItemListTop = (LinearLayout) itemView.findViewById(R.id.llListItemTop);
            //llItemListMiddle = (LinearLayout) itemView.findViewById(R.id.llListItemMiddle);
            //llItemListBottom = (LinearLayout) itemView.findViewById(R.id.llListItemTop);


        }
    }


}