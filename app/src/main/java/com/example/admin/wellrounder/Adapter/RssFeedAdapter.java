package com.example.admin.wellrounder.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.wellrounder.Interface.ItemClickListener;
import com.example.admin.wellrounder.Model.RSSObject;
import com.example.admin.wellrounder.NavHomePage;
import com.example.admin.wellrounder.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.admin.wellrounder.R.id.imageView;

/**
 * Created by admin on 11/1/2017.
 */

class RSSFeedHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{


    public TextView tvTitle, tvPubDate, tvContent, tvImgLinkRSS;
    public ImageView imgThumbRSS;

    private ItemClickListener itemClickListener;

    public RSSFeedHolder (View itemView){
        super(itemView);

        tvTitle = (TextView) itemView.findViewById(R.id.tvTittleRssRow);
        tvPubDate = (TextView) itemView.findViewById(R.id.tvDateOfPubRssRow);
        tvContent = (TextView) itemView.findViewById(R.id.tvContentRssReader);
        imgThumbRSS = (ImageView) itemView.findViewById(R.id.imgThumbRSS);
        tvImgLinkRSS = (TextView) itemView.findViewById(R.id.tvImgLinkRSS);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
    itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), true);
        return true;
    }
}

public class RssFeedAdapter extends RecyclerView.Adapter<RSSFeedHolder> {

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public RssFeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RSSFeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View itemView = inflater.inflate(R.layout.row_rss,parent,false);
        return new RSSFeedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RSSFeedHolder holder, int position) {
        holder.tvTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.tvPubDate.setText(rssObject.getItems().get(position).getPubDate());
        holder.tvContent.setText(rssObject.getItems().get(position).getContent());
        holder.tvImgLinkRSS.setText(rssObject.getItems().get(position).getEnclosure().getLink());
        String url = rssObject.getItems().get(position).getEnclosure().getLink();
        Picasso.with(mContext).load(url).into(holder.imgThumbRSS);



    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }
}
