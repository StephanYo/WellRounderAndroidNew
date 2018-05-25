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

public class MyAdapterComment extends RecyclerView.Adapter<MyAdapterComment.ViewHolder> {


    private List<CommentList> mCommentList;
    private Context context;

    public MyAdapterComment(List<CommentList> mCommentList, Context context) {
        this.mCommentList = mCommentList;
        this.context = context;
    }


    // #1 creates the constructor, When view holder is created
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list, parent, false);
        return new ViewHolder(v);
    }

    //#2 binds the data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final CommentList commentList = mCommentList.get(position);

        //  final String post_id = listItem.getPost_id();

        holder.tvUsernameCMT2.setText(commentList.getUsernameCMT());
        holder.tvCommentCMT2.setText(commentList.getComment());
        holder.tvPost_IDCMT.setText(commentList.getPost_id());


    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    //created after calling #1
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvUsernameCMT2, tvCommentCMT2, tvPost_IDCMT;
        //public LinearLayout llItemListTop, llItemListMiddle, llItemListBottom;


        public ViewHolder(View itemView) {
            super(itemView);

            tvUsernameCMT2 = (TextView) itemView.findViewById(R.id.tvUsernameCMT2);
            tvCommentCMT2 = (TextView) itemView.findViewById(R.id.tvUserCommentCMT);
            tvPost_IDCMT = (TextView) itemView.findViewById(R.id.tvPostIDCMTnew);


            //llItemListTop = (LinearLayout) itemView.findViewById(R.id.llListItemTop);
            //llItemListMiddle = (LinearLayout) itemView.findViewById(R.id.llListItemMiddle);
            //llItemListBottom = (LinearLayout) itemView.findViewById(R.id.llListItemTop);


        }
    }




}

