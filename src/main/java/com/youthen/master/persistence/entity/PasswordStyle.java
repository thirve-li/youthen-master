package com.youthen.master.persistence.entity;

/**
 * 密码检查项
 */
public class PasswordStyle {

    /**
     * 密码设置项名
     */
    private String type;

    /**
     * 是否检查设置项
     */
    private boolean isRequired;

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return the isRequired
     */
    public boolean getIsRequired() {
        return this.isRequired;
    }

    /**
     * @param isRequired the isRequired to set
     */
    public void setIsRequired(final boolean isRequired) {
        this.isRequired = isRequired;
    }
}
