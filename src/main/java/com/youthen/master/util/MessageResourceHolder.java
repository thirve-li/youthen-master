package com.youthen.master.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;

public class MessageResourceHolder {

    private static final String NO_SUCH_MESSAGE = "Empty Message!";

    private final Log log = SystemLogFactory.getErrorLog();

    private final Map<Locale, Properties> messageSet = new HashMap<Locale, Properties>();

    private MessageResourceHolder() {

        load();

    }

    /**
	 * 
	 */
    private void load() {

        this.messageSet.clear();

        InputStream is = null;
        InputStream isEn = null;

        try {
            final Properties defProperties = new Properties();
            is = MessageResourceHolder.class.getClassLoader().getResourceAsStream("webmessage.properties");
            defProperties.load(is);
            this.messageSet.put(Locale.CHINESE, defProperties);

            final Properties defEn = new Properties();
            isEn = MessageResourceHolder.class.getClassLoader().getResourceAsStream("webmessage_en.properties");
            defEn.load(isEn);
            this.messageSet.put(Locale.ENGLISH, defEn);

        } catch (final IOException e) {
            if (this.log.isErrorEnabled()) {
                this.log.error(MessageResourceHolder.class.getName(), e);
            }
        } finally {
            IOCloseUtils.close(is);
            IOCloseUtils.close(isEn);
        }
    }

    private static class Holder {

        private static MessageResourceHolder INSTANCE = new MessageResourceHolder();

    }

    /**
     * @return
     */
    public static MessageResourceHolder getInstance() {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new MessageResourceHolder();
        }
        return Holder.INSTANCE;
    }

    /**
     * @param key
     * @return
     */
    private String getMessage(final String key) {

        final Properties defP = this.messageSet.get(Locale.CHINESE);

        final String message = (String) defP.get(key);
        return message == null ? NO_SUCH_MESSAGE : message;
    }

    /**
     * TODO: 完成国际化
     * 
     * @param locale
     * @param key
     * @return
     */
    public String getMessage(final Locale locale, final String key) {

        Properties defP = this.messageSet.get(locale);
        if (defP == null) {
            defP = this.messageSet.get(Locale.CHINESE);
        }

        return defP.getProperty(key);
    }

    /**
     * TODO: 完成国际化
     * 
     * @param locale
     * @param key
     * @return
     */
    public String getMessage(final Locale locale, final String key, final Object... args) {
        return getMessage(key, args);
    }

    /**
     * @param key
     * @param args
     * @return
     */
    private String getMessage(final String key, final Object... args) {

        final String message = getMessage(key);

        // for (Object obj : args) {
        //
        // message = message.replaceAll("\\(\\?\\)", obj.toString());
        // }

        return MessageFormat.format(message, args);
    }

    /**
     * @param key
     * @param args
     * @return
     */
    public String getMultipleFieldsMessage(final String key, final Object... args) {

        String message = getMessage(key);
        for (final Object obj : args) {

            message = message.replaceFirst("\\(\\?\\)", obj.toString());
        }

        return message;
    }

    /**
	 * 
	 */
    public void reflush() {

        this.load();
    }

}
