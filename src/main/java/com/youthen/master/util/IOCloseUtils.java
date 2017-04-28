package com.youthen.master.util;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IOCloseUtils {

    private static Log DEBUG = LogFactory.getLog(IOCloseUtils.class);

    /**
     * @param is
     */
    public static void close(final InputStream is) {

        if (null == is) {
            return;
        }

        try {
            is.close();
            if (DEBUG.isDebugEnabled()) {
                DEBUG.debug("close input stream");
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param os
     */
    public static void close(final OutputStream os) {

        if (null == os) {
            return;
        }

        try {
            os.flush();
            os.close();
            if (DEBUG.isDebugEnabled()) {
                DEBUG.debug("close output stream");
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

    }

}
