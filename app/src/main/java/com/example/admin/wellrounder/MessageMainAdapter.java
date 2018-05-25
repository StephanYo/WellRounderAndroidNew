package com.example.admin.wellrounder;

/**
 * Created by admin on 12/7/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class MessageMainAdapter extends RecyclerView.Adapter<MessageMainAdapter.ViewHolder> {


    private List<MessageMainList> mMessageMainList;
    private Context context;



    public MessageMainAdapter(List<MessageMainList> mMessageMainList, Context context) {
        this.mMessageMainList = mMessageMainList;
        this.context = context;
    }


    // #1 creates the constructor, When view holder is created
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messaging_main_list_item, parent, false);
        return new ViewHolder(v);



    }

    //#2 binds the data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //UserCurrent userCurrent = new UserCurrent();
        //String currentUserID = userCurrent.getId2().toString(); //change it get the data and stuff

        String currentUserID = SharedPrefManager.getInstance(context).getCurrtUserIDString();

        final MessageMainList messageMainList = mMessageMainList.get(position);

       if(messageMainList.getId_1() == currentUserID){
           holder.tvUserMSGListItem.setText(messageMainList.getSend_username());
           holder.tvTime_stamp.setText(messageMainList.getTime_stamp());
           holder.tvLastPostMSGListItem.setText(messageMainList.getLast_message());
       }else{
           holder.tvUserMSGListItem.setText(messageMainList.getRec_username());
           holder.tvTime_stamp.setText(messageMainList.getTime_stamp());
           holder.tvLastPostMSGListItem.setText(messageMainList.getLast_message());
       }

       holder.clMessageMainWhole.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String id_1 = messageMainList.getId_1();
               String id_2 = messageMainList.getId_2();


               Intent myIntent = new Intent(view.getContext(), Messaging.class);
               myIntent.putExtra("id_1", id_1 );
               myIntent.putExtra("id_2", id_2);
               context.startActivity(myIntent);
           }
       });


    }

    @Override
    public int getItemCount() {
        return mMessageMainList.size();
    }

    //created after calling #1
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvUserMSGListItem, tvLastPostMSGListItem, tvTime_stamp;
        public ConstraintLayout clMessageMainWhole;
        //public LinearLayout llItemListTop, llItemListMiddle, llItemListBottom;


        public ViewHolder(View itemView) {
            super(itemView);

            tvUserMSGListItem = (TextView) itemView.findViewById(R.id.tvUsernameMSGListItem);
            tvLastPostMSGListItem = (TextView) itemView.findViewById(R.id.tvLastPostMSGListItem);
            tvTime_stamp = (TextView) itemView.findViewById(R.id.tvTimestampMSGListItem);

            clMessageMainWhole = (ConstraintLayout) itemView.findViewById(R.id.clMessageMainListWhole);

            //llItemListTop = (LinearLayout) itemView.findViewById(R.id.llListItemTop);
            //llItemListMiddle = (LinearLayout) itemView.findViewById(R.id.llListItemMiddle);
            //llItemListBottom = (LinearLayout) itemView.findViewById(R.id.llListItemTop);


        }
    }


}