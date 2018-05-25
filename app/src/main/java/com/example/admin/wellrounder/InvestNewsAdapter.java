package com.example.admin.wellrounder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 12/21/2017.
 */

public class InvestNewsAdapter
        extends RecyclerView.Adapter<InvestNewsAdapter.FeedModelViewHolder> {

    private List<Tab1Fragment.RssFeedModel> mRssFeedModels;
    private Context context;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
        private View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }
    }

    public InvestNewsAdapter(List<Tab1Fragment.RssFeedModel> rssFeedModels, Context context ) {
        mRssFeedModels = rssFeedModels;
        this.context = context;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.explore_list_item, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, int position) {
        final Tab1Fragment.RssFeedModel rssFeedModel = mRssFeedModels.get(position);

        ((TextView)holder.rssFeedView.findViewById(R.id.tvTittleRssExplore)).setText(rssFeedModel.title);
        ((TextView)holder.rssFeedView.findViewById(R.id.tvDescriptionRssExplore))
                .setText(rssFeedModel.description);
        ((TextView)holder.rssFeedView.findViewById(R.id.tvPostLinkExploreRSS)).setText(rssFeedModel.link);
        ((TextView)holder.rssFeedView.findViewById(R.id.tvDateOfPubRssRow)).setText(rssFeedModel.pubDate);

        ((LinearLayout)holder.rssFeedView.findViewById(R.id.llExploreListItemRSS)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "just work you now please <3", Toast.LENGTH_SHORT).show();
                String url = rssFeedModel.link;
                Intent openUrl = new Intent(Intent.ACTION_VIEW);

                openUrl.setData(Uri.parse(url));
                context.startActivity(openUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRssFeedModels.size();
    }
}