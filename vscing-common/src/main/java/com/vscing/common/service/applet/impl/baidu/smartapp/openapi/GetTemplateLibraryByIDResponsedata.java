package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetTemplateLibraryByIDResponsedata {
    @SerializedName("id")
    private String id;

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    @SerializedName("keyword_count")
    private long keywordCount;

    public long getKeywordCount() {
        return this.keywordCount;
    }

    public void setKeywordCount(long keywordCount) {
        this.keywordCount = keywordCount;
    }

    @SerializedName("keyword_list")
    private GetTemplateLibraryByIDResponsedatakeywordListItem[] keywordList;

    public GetTemplateLibraryByIDResponsedatakeywordListItem[] getKeywordList() {
        return this.keywordList;
    }

    public void setKeywordList(GetTemplateLibraryByIDResponsedatakeywordListItem[] keywordList) {
        this.keywordList = keywordList;
    }

    @SerializedName("title")
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
