package com.CA326MyBubble.Layout_Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class newsModel implements Parcelable {
    // Have to serialize the data so that it can be converted to json and then back so that it displays to the layout page properly
    @SerializedName("source")
    private SourceNews source;
    @SerializedName("author")
    private String newsAuthor;
    @SerializedName("title")
    private String newsTitle;
    @SerializedName("description")
    private String desc;
    @SerializedName("url")
    private String url;
    @SerializedName("urlToImage")
    private String ImageURL;
    @SerializedName("publishedAt")
    private String publishDate;
    @SerializedName("content")
    private String newsContent;

    public newsModel(SourceNews source, String newsAuthor, String newsTitle, String desc, String url, String ImageURL, String publishedAt, String newsContent) {
        this.source = source;
        this.newsAuthor = newsAuthor;
        this.newsTitle = newsTitle;
        this.desc = desc;
        this.url = url;
        this.ImageURL = ImageURL;
        this.publishDate = publishedAt;
        this.newsContent = newsContent;
    }

    public SourceNews getNewsSource() {
        return source;
    }

    public void setNewsSource(SourceNews source) {
        this.source = source;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        this.ImageURL = imageURL;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    // Parcelable reads in the json data
    public static class SourceNews implements Parcelable {
        @SerializedName("name")
        private String name;

        SourceNews(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel text, int flags) {
            text.writeString(this.name);
        }

        SourceNews(Parcel send) {
            this.name = send.readString();
        }

        public static final Creator<SourceNews> CREATOR = new Creator<SourceNews>() {
            @Override
            public SourceNews createFromParcel(Parcel startPoint) {
                return new SourceNews(startPoint);
            }

            @Override
            public SourceNews[] newArray(int size) {
                return new SourceNews[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) this.source, flags);
        dest.writeString(this.newsAuthor);
        dest.writeString(this.newsTitle);
        dest.writeString(this.desc);
        dest.writeString(this.url);
        dest.writeString(this.ImageURL);
        dest.writeString(this.publishDate);
        dest.writeString(this.newsContent);
    }

    private newsModel(Parcel send) {
        this.source = send.readParcelable(SourceNews.class.getClassLoader());
        this.newsAuthor = send.readString();
        this.newsTitle = send.readString();
        this.desc = send.readString();
        this.url = send.readString();
        this.ImageURL = send.readString();
        this.publishDate = send.readString();
        this.newsContent = send.readString();
    }

    public static final Creator<newsModel> CREATOR = new Creator<newsModel>() {
        @Override
        public newsModel createFromParcel(Parcel startPoint) {
            return new newsModel(startPoint);
        }

        @Override
        public newsModel[] newArray(int size) {
            return new newsModel[size];
        }
    };
}
