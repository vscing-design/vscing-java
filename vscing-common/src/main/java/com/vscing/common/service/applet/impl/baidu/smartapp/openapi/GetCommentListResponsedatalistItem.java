package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

public class GetCommentListResponsedatalistItem {
    @SerializedName("content")
    private String content;

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @SerializedName("create_time")
    private String createTime;

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @SerializedName("is_uped")
    private String isUped;

    public String getIsUped() {
        return this.isUped;
    }

    public void setIsUped(String isUped) {
        this.isUped = isUped;
    }

    @SerializedName("like_count")
    private String likeCount;

    public String getLikeCount() {
        return this.likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    @SerializedName("reply_list")
    private GetCommentListResponsedatalistItemreplyListItem[] replyList;

    public GetCommentListResponsedatalistItemreplyListItem[] getReplyList() {
        return this.replyList;
    }

    public void setReplyList(GetCommentListResponsedatalistItemreplyListItem[] replyList) {
        this.replyList = replyList;
    }

    @SerializedName("srid")
    private String srid;

    public String getSrid() {
        return this.srid;
    }

    public void setSrid(String srid) {
        this.srid = srid;
    }

    @SerializedName("thread_id")
    private String threadId;

    public String getThreadID() {
        return this.threadId;
    }

    public void setThreadID(String threadId) {
        this.threadId = threadId;
    }
}
