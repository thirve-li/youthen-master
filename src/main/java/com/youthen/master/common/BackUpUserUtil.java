// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.common;

import java.util.Set;
import com.youthen.master.persistence.entity.Role;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class BackUpUserUtil {

    /*
     * 根据登陆人获取其是否有权限设置备用用户 返回备用用户CODE
     */
    public static String getBackUpUserCode(final Set<Role> set, final int isKSHZ) {
        if (isKSHZ == 1) { // 判断是否为理化 微生物主管 存在科室汇总
            for (final Role role : set) {
                if ("LAM".equals(role.getCode())) {
                    return "LAKSHZ";
                } else if ("MAM".equals(role.getCode())) {
                    return "MAKSHZ";
                }
            }
        }
        for (final Role role : set) { // 获取授权角色信息
            if ("LAM".equals(role.getCode())) {
                return "LAMB";
            } else if ("MAM".equals(role.getCode())) {
                return "MAMB";
            } else if ("QCM".equals(role.getCode())) {
                return "QCMB";
            } else if ("QCA".equals(role.getCode())) {
                return "QCAB";
            }
        }
        return "";
    }

    /*
     * 根据登陆人获取其有权限设置备用用户的列表
     */
    public static String canShowUserCode(final Set<Role> set, final int isKSHZ) {
        if (isKSHZ == 1) {
            return "|QCM|";
        }
        for (final Role role : set) { // 获取授权角色信息
            if ("LAM".equals(role.getCode())) {
                return "|LA|"; // 页面显示可选择在用户
            } else if ("MAM".equals(role.getCode())) {
                return "|MA|";
            } else if ("QCM".equals(role.getCode())) {
                return "|QPM|";
            } else if ("QCA".equals(role.getCode())) {
                return "|MA|LA|";
            }
        }
        return "";
    }

    /*
     * 获取登陆用户是否有分配备份权限 0无权限 1QC管理员 QC经理 2理化主管 微生物主管
     */
    public static int getBackUpAuth(final Set<Role> set) {
        for (final Role role : set) { // 判断用户是否有分配备用角色权限
            if ("LAM".equals(role.getCode())) {
                return 2;
            } else if ("MAM".equals(role.getCode())) {
                return 2;
            } else if ("QCM".equals(role.getCode())) {
                return 1;
            } else if ("QCA".equals(role.getCode())) {
                return 1;
            }
        }
        return 0;
    }
}
