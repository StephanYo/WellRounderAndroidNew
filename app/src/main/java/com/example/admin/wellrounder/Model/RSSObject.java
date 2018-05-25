package com.example.admin.wellrounder.Model;

import java.util.List;

/**
 * Created by admin on 11/1/2017.
 */






    public class RSSObject
    {
        public String status;
        public Feed feed;
        public List<Item> items;
        public Enclosure enclosure;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Feed getFeed() {
            return feed;
        }

        public void setFeed(Feed feed) {
            this.feed = feed;
        }

        public Enclosure getEnclosure() {
            return enclosure;
        }

        public void setEnclosure(Enclosure enclosure) {
            this.enclosure = enclosure;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public RSSObject(String status, Feed feed, List<Item> items, Enclosure enclosure) {

            this.status = status;
            this.feed = feed;
            this.items = items;
            this.enclosure = enclosure;
        }
    }




