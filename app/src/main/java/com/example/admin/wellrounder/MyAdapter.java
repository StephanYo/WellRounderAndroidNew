package com.example.admin.wellrounder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by admin on 9/27/2017.
 *
 * The adapter class that is used in the UserProfile class (only when clicked on from the news feed)
 * as well as the list item class. This class is the adapter for the
 * Listitems class, it can also be used to get the user data when clicking on the post from the news feed.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<ListItem> listitem;
    private Context context;

    public MyAdapter(List<ListItem> listitem, Context context) {
        this.listitem = listitem;
        this.context = context;
    }



    // #1 creates the constructor, When view holder is created
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    //#2 binds the data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {





        final ListItem listItem = listitem.get(position);

      //  final String post_id = listItem.getPost_id();

        holder.tvPostLI.setText(listItem.getPost());
        holder.tvUserWhoPostedLI.setText(listItem.getUserameWhoPosted());
        holder.tvTimeLI.setText(listItem.getTime());
        holder.tvDateLI.setText(listItem.getDate());


        holder.tvUserWhoPostedLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mUsername = listItem.getUserameWhoPosted();


                Intent myIntent = new Intent(view.getContext(), UserProfile.class);
                myIntent.putExtra("keyUsername" , mUsername );
                context.startActivity(myIntent);
                //Toast.makeText(context, "Yo the username is: " + mUsername, Toast.LENGTH_SHORT).show();

                //Toast.makeText(context, , Toast.LENGTH_SHORT).show();
            }
        });
        holder.llItemListTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyPost_ID = listItem.getPostID();
                String userPost = listItem.getPost();
                String timeOfPost = listItem.getTime();
                String dateOfPost = listItem.getDate();
                String getUser = listItem.getUserameWhoPosted();


                Intent myIntent = new Intent(view.getContext(), Comments.class);
                myIntent.putExtra("keyPost_ID" , keyPost_ID );
                myIntent.putExtra("userPost" , userPost);
                myIntent.putExtra("timeOfPost", timeOfPost);
                myIntent.putExtra("dateOfPost", dateOfPost);
                myIntent.putExtra("getUser", getUser);
                context.startActivity(myIntent);
            }
        });

        holder.llItemListMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyPost_ID = listItem.getPostID();
                String userPost = listItem.getPost();
                String timeOfPost = listItem.getTime();
                String dateOfPost = listItem.getDate();
                String getUser = listItem.getUserameWhoPosted();


                Intent myIntent = new Intent(view.getContext(), Comments.class);
                myIntent.putExtra("keyPost_ID" , keyPost_ID );
                myIntent.putExtra("userPost" , userPost);
                myIntent.putExtra("timeOfPost", timeOfPost);
                myIntent.putExtra("dateOfPost", dateOfPost);
                myIntent.putExtra("getUser", getUser);
                context.startActivity(myIntent);
            }
        });
        holder.llItemListBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyPost_ID = listItem.getPostID();
                String userPost = listItem.getPost();
                String timeOfPost = listItem.getTime();
                String dateOfPost = listItem.getDate();
                String getUser = listItem.getUserameWhoPosted();


                Intent myIntent = new Intent(view.getContext(), Comments.class);
                myIntent.putExtra("keyPost_ID" , keyPost_ID );
                myIntent.putExtra("userPost" , userPost);
                myIntent.putExtra("timeOfPost", timeOfPost);
                myIntent.putExtra("dateOfPost", dateOfPost);
                myIntent.putExtra("getUser", getUser);
                context.startActivity(myIntent);
            }
        });

        holder.tvLikePostLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You Clicked the Like Button", Toast.LENGTH_SHORT).show();
            }
        });

        holder.tvAddCommentLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // displayDialog();
               // Toast.makeText(context, "Post id: " + post_id, Toast.LENGTH_SHORT).show();

                String keyPost_ID = listItem.getPostID();
                String userPost = listItem.getPost();
                String timeOfPost = listItem.getTime();
                String dateOfPost = listItem.getDate();
                String getUser = listItem.getUserameWhoPosted();


                Intent myIntent = new Intent(view.getContext(), Comments.class);
                myIntent.putExtra("keyPost_ID" , keyPost_ID );
                myIntent.putExtra("userPost" , userPost);
                myIntent.putExtra("timeOfPost", timeOfPost);
                myIntent.putExtra("dateOfPost", dateOfPost);
                myIntent.putExtra("getUser", getUser);
                context.startActivity(myIntent);
            }
        });

        holder.tvSharePostLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You Clicked the Share Post ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    //created after calling #1
    public class ViewHolder extends RecyclerView.ViewHolder{

       public TextView tvPostLI, tvUserWhoPostedLI, tvTimeLI, tvDateLI, tvLikePostLI, tvAddCommentLI, tvSharePostLI;
        public LinearLayout llItemListTop, llItemListMiddle, llItemListBottom;


        public ViewHolder(View itemView) {
            super(itemView);

            tvPostLI = (TextView) itemView.findViewById(R.id.tvPostLI);
            tvUserWhoPostedLI = (TextView) itemView.findViewById(R.id.tvUsernameLI);

            tvTimeLI = (TextView) itemView.findViewById(R.id.tvTimeLI);
            tvDateLI = (TextView) itemView.findViewById(R.id.tvDateLI);

            tvLikePostLI = (TextView) itemView.findViewById(R.id.tvLikePostLI);
            tvAddCommentLI = (TextView) itemView.findViewById(R.id.tvAddCommentLI);
            tvSharePostLI = (TextView) itemView.findViewById(R.id.tvSharePostLI);


            llItemListTop = (LinearLayout) itemView.findViewById(R.id.llListItemTop);
            llItemListMiddle = (LinearLayout) itemView.findViewById(R.id.llListItemMiddle);
            llItemListBottom = (LinearLayout) itemView.findViewById(R.id.llListItemTop);


        }
    }







}
