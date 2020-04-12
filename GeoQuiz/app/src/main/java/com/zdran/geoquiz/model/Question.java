package com.zdran.geoquiz.model;

/**
 * Create by Ranzd on 2020-04-11 22:31
 *
 * @author cm.zdran@foxmail.com
 */
public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int mQuestionText, boolean mAnswerTrue) {
        this.mTextResId = mQuestionText;
        this.mAnswerTrue = mAnswerTrue;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"mTextResId\":\"")
                .append(mTextResId).append('\"');
        sb.append(",\"mAnswerTrue\":")
                .append(mAnswerTrue);
        sb.append('}');
        return sb.toString();
    }
}
