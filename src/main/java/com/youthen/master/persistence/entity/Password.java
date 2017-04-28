package com.youthen.master.persistence.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 密码强度检查项
 */
public class Password {

    /**
     * 初始化
     */
    public void init() {
        PasswordStyle style = new PasswordStyle();
        style.setType("config.password.num");
        this.style.add(style);

        style = new PasswordStyle();
        style.setType("config.password.lower");
        this.style.add(style);

        style = new PasswordStyle();
        style.setType("config.password.upper");
        this.style.add(style);

        style = new PasswordStyle();
        style.setType("config.password.special");
        this.style.add(style);
    }

    /**
     * 密码最大长度
     */
    private int maxLen;

    /**
     * 密码最小长度
     */
    private int minLen;

    /**
     * 密码检查项
     */
    private List<PasswordStyle> style = new ArrayList<PasswordStyle>();

    /**
     * @return the style
     */
    public List<PasswordStyle> getStyle() {
        return this.style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(final List<PasswordStyle> style) {
        this.style = style;
    }

    /**
     * @return the maxLen
     */
    public int getMaxLen() {
        return this.maxLen;
    }

    /**
     * @param maxLen the maxLen to set
     */
    public void setMaxLen(final int maxLen) {
        this.maxLen = maxLen;
    }

    /**
     * @return the minLen
     */
    public int getMinLen() {
        return this.minLen;
    }

    /**
     * @param minLen the minLen to set
     */
    public void setMinLen(final int minLen) {
        this.minLen = minLen;
    }

    /**
     * add style
     * 
     * @return
     */
    public PasswordStyle addStyle() {
        final PasswordStyle style = new PasswordStyle();
        this.style.add(style);
        return style;
    }

    /**
     * 设置检查项
     * 
     * @param requied
     */
    public void changeIsRequired(final String[] requied) {
        if (requied == null) return;

        for (final String type : requied) {
            for (final PasswordStyle style : this.style) {
                if (type.equals(style.getType())) {
                    style.setIsRequired(true);
                }
            }
        }
    }

}
