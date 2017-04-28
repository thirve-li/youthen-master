package com.youthen.master.util;

import org.apache.commons.lang.StringUtils;
import com.youthen.master.persistence.entity.Password;
import com.youthen.master.persistence.entity.PasswordStyle;

/**
 * 密码强度校验用
 * 
 * @copyright youthen
 * @author Yu Shuangyan
 * @date 2011-5-12
 */
public class PasswordValidate {

    /**
     * 解析xml内容，生成密码object.
     * 
     * @param xmlContent
     * @return password
     */
    public static Password parseXml(final String xmlContent) {
        Password password;
        if (StringUtils.isBlank(xmlContent)) {
            password = new Password();
            password.init();
        } else {
            password = (Password) XmlHelper.xml2Object(xmlContent);

        }
        return password;
    }

    /**
     * 把passwod Object转成xml
     * 
     * @param password
     * @return string
     */
    public static String createXml(final Password password) {
        return XmlHelper.object2XML(password);
    }

    /**
     * 解析xml内容，生成密码校验用正则表达式。
     * 
     * @return string
     */
    public static String createPasswordRegex(final String passwordValidateXml) {
        if (StringUtils.isBlank(passwordValidateXml)) {
            return null;
        }
        final Password password = (Password) XmlHelper.xml2Object(passwordValidateXml);

        final StringBuffer sb = new StringBuffer("[");
        for (final PasswordStyle passwordStyle : password.getStyle()) {
            if ("config.password.num".equals(passwordStyle.getType())) {
                if (passwordStyle.getIsRequired()) {
                    sb.append("0-9");
                }
            }
            if ("config.password.lower".equals(passwordStyle.getType())) {
                if (passwordStyle.getIsRequired()) {
                    sb.append("a-z");
                }
            }
            if ("config.password.upper".equals(passwordStyle.getType())) {
                if (passwordStyle.getIsRequired()) {
                    sb.append("A-Z");
                }
            }
            if ("config.password.special".equals(passwordStyle.getType())) {
                if (passwordStyle.getIsRequired()) {
                    sb.append("\u0021-\u002f\u003a-\u0040[\u005a-\u0060&&[^\u005a]]\u007b-\u007e");
                }
            }
        }
        sb.append("]{" + password.getMinLen() + "," + password.getMaxLen() + "}");

        return sb.toString();
    }

    /**
     * 校验密码
     * 
     * @param password 密码
     * @param passwordRegex 密码校验用正则表达式
     * @return boolean
     */
    public static boolean validater(final String password, final String passwordRegex) {
        if (StringUtils.isBlank(passwordRegex)) {
            return true;
        }
        return true;
    }
}
