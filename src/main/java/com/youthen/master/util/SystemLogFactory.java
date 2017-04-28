package com.youthen.master.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Sisqp Project Source Code
 * 
 * @copyright youthen
 * @author William Shen
 * @date 2011-5-11
 */
public class SystemLogFactory {

    protected static final String DEBUG = "DEBUG";
    protected static final String ACCESS = "ACCESS";
    protected static final String ERROR = "ERROR";

    /**
     * get defalut errror log
     * 
     * @return
     */
    public static Log getErrorLog() {

        return LogFactory.getLog(ERROR);
    }

    /**
     * default access log
     * 
     * @return
     */
    public static Log getAccessLog() {
        return LogFactory.getLog(ACCESS);
    }

    /**
     * @return
     */
    public static Log getDebugLog() {
        return LogFactory.getLog(DEBUG);
    }

}
