package com.zdran.geoquiz.model;

/**
 * Create by Ranzd on 2020-04-11 22:31
 *
 * @author cm.zdran@foxmail.com
 */
public class Question {
    /**
     * 文本的 resID
     */
    private int mTextResId;
    /**
     * 正确答案
     */
    private boolean mAnswerTrue;
    /**
     * 是否回答过
     */
    private boolean mUsed;

    public Question(int mQuestionText, boolean mAnswerTrue) {
        this.mTextResId = mQuestionText;
        this.mAnswerTrue = mAnswerTrue;
        mUsed = false;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isUsed() {
        return mUsed;
    }

    public void setUsed(boolean used) {
        mUsed = used;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"mTextResId\":")
                .append(mTextResId);
        sb.append(",\"mAnswerTrue\":")
                .append(mAnswerTrue);
        sb.append(",\"mUsed\":")
                .append(mUsed);
        sb.append('}');
        return sb.toString();
    }
}
