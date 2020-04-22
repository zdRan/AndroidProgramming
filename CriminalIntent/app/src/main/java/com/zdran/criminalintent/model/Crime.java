package com.zdran.criminalintent.model;

import java.util.Date;
import java.util.UUID;

/**
 * 陋习模型
 * Create by Ranzd on 2020-04-17 22:06
 *
 * @author cm.zdran@foxmail.com
 */
public class Crime {
    /**
     * 唯一标识
     */
    private UUID mId;
    /**
     * 标题
     */
    private String mTitle;
    /**
     * 日期
     */
    private Date mDate;
    /**
     * 是否修正
     */
    private boolean mSolved;
    /**
     * 是否需要警察介入
     */
    private boolean mRequiresPolice;
    /**
     * 新增排序字段
     */
    private int sorted = 0;

    public Crime() {
        this.mId = UUID.randomUUID();
        this.mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public boolean isRequiresPolice() {
        return mRequiresPolice;
    }

    public void setRequiresPolice(boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }

    public int getSorted() {
        return sorted;
    }

    public void setSorted(int sorted) {
        this.sorted = sorted;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"mId\":")
                .append(mId);
        sb.append(",\"mTitle\":\"")
                .append(mTitle).append('\"');
        sb.append(",\"mDate\":\"")
                .append(mDate).append('\"');
        sb.append(",\"mSolved\":")
                .append(mSolved);
        sb.append(",\"mRequiresPolice\":")
                .append(mRequiresPolice);
        sb.append(",\"sorted\":")
                .append(sorted);
        sb.append('}');
        return sb.toString();
    }
}
