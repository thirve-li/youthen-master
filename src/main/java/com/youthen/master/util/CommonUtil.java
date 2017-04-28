package com.youthen.master.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/*******************************************************************************
 * ���ó���
 */
public class CommonUtil {

    public static String BASE_PATH = "";
    // public static String DOC_PATH = "E:\\Program Files\\Tomcat 5.5\\webapps\\bhdc\\doc\\";
    public static String DOC_PATH = "";

    public static int PROJECTOR_CNT = 3;

    public static int abs(final int num) {
        return Math.abs(num);
    }

    public static boolean isWeekEnd(final String date) {
        final SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Date date2;
        try {
            date2 = s.parse(date);
            final Calendar c = s.getCalendar();
            c.setTime(date2);
            final int day = c.get(Calendar.DAY_OF_WEEK);
            if (day == 1 || day == 7) {
                return true;
            } else {
                return false;
            }
        } catch (final ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String formatFloat(final double fData) {
        final DecimalFormat format = new DecimalFormat("#,##0.##");
        format.setMinimumFractionDigits(2);
        String num = format.format(fData);
        if (Integer.parseInt(num.substring(0, num.indexOf(".")).replaceAll(",", "")) > 99) {
            num = num.substring(0, num.indexOf("."));
        }
        return num;
    }

    public static String formatData2(final double fData) {

        final DecimalFormat format = new DecimalFormat("#,##0.##");
        format.setMinimumFractionDigits(2);
        return format.format(fData);
    }

    public static String divide(String v1, String v2) {
        if (v1 != null) {
            v1 = v1.trim();
        } else {
            v1 = "0";
        }
        if (v2 != null) {
            v2 = v2.trim();
        } else {
            v2 = "0";
        }

        final BigDecimal b1 = new BigDecimal(v1);
        final BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, 3, 4).toString();
    }

    public static String[] getDaysHour(final String date) {
        final String daysHour[] = new String[25];
        daysHour[0] = date + " 08:00";
        daysHour[1] = date + " 08:30";
        daysHour[2] = date + " 09:00";
        daysHour[3] = date + " 09:30";
        daysHour[4] = date + " 10:00";
        daysHour[5] = date + " 10:30";
        daysHour[6] = date + " 11:00";
        daysHour[7] = date + " 11:30";
        daysHour[8] = date + " 12:00";
        daysHour[9] = date + " 12:30";
        daysHour[10] = date + " 13:00";
        daysHour[11] = date + " 13:30";
        daysHour[12] = date + " 14:00";
        daysHour[13] = date + " 14:30";
        daysHour[14] = date + " 15:00";
        daysHour[15] = date + " 15:30";
        daysHour[16] = date + " 16:00";
        daysHour[17] = date + " 16:30";
        daysHour[18] = date + " 17:00";
        daysHour[19] = date + " 17:30";
        daysHour[20] = date + " 18:00";
        daysHour[21] = date + " 18:30";
        daysHour[22] = date + " 19:00";
        daysHour[23] = date + " 19:30";
        daysHour[24] = date + " 20:00";
        return daysHour;

    }

    public static Vector getDays(final String startDate, final String endDate) {
        final SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        final SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        final Vector v = new Vector();
        try {
            final Date date1 = s.parse(startDate);
            final Date date2 = s2.parse(endDate);
            final Calendar c = s.getCalendar();
            v.add(startDate);
            while (true) {
                c.add(Calendar.DAY_OF_YEAR, 1);
                final Date date3 = c.getTime();
                if (date3.after(date2)) break;
                v.add(s.format(date3));
            }
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return v;
    }

    /**
     * �õ��õ�ĳ����ĳ�µĵ�һ��
     * 
     * @return
     */
    public static Vector getWeekDays(final String date) {
        final SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        final Vector v = new Vector();
        try {
            final Calendar cal = Calendar.getInstance();

            cal.set(Calendar.DAY_OF_WEEK, 1);
            System.out.println("��һ��:" + cal.get(Calendar.YEAR) + "-"
                    + (cal.get(Calendar.MONTH) + 1) + "-"
                    + cal.get(Calendar.DATE));
            cal.set(Calendar.DAY_OF_WEEK, 7);
            System.out.println("������:" + cal.get(Calendar.YEAR) + "-"
                    + (cal.get(Calendar.MONTH) + 1) + "-"
                    + cal.get(Calendar.DATE));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    /**
     * �õ��õ�ĳ����ĳ�µĵ�һ��
     * 
     * @return
     */
    public static String getMonthFirstDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateToStr(calendar.getTime());
    }

    /**
     * �õ�ĳ����ĳ�µ����һ��
     * 
     * @return
     */
    public static String getMonthLastDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateToStr(calendar.getTime());
    }

    /**
     * �ж�ĳ2�����Ƿ����
     * 
     * @return
     */
    public static boolean dayEquals(final String day, final Date date) {
        if (day.equals(dateToStr(date))) {
            return true;
        }
        return false;
    }

    /**
     * �ж�ĳ�����Ƿ��ڽ���2����֮��
     * 
     * @return
     */
    public static boolean between2day(final String date, final Date fromDay, final Date toDay) {
        final Date date1 = strToDate(date);
        if (date1.after(fromDay) && date1.before(toDay)) {
            return true;
        }

        return false;
    }

    /**
     * �ж�����1�Ƿ�������2֮ǰ
     * 
     * @return
     */
    public static boolean before(final String fromDay, final Date toDay) {

        final Date date1 = strToDateLong(fromDay);

        if (date1.before(toDay)) {
            return true;
        }

        return false;
    }

    /**
     * �ж�����1�Ƿ�������2֮��
     * 
     * @return
     */
    public static boolean after(final String fromDay, final Date toDay) {
        final Date date1 = strToDate(fromDay);
        if (date1.after(toDay)) {
            return true;
        }
        return false;
    }

    /**
     * ����ʱ���ʽʱ��ת��Ϊ�ַ��� yyyy-MM-dd
     * 
     * @param dateDate
     * @return
     */
    public static String dateToStr(final Date date) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * ����ʱ���ʽʱ��ת��Ϊ�ַ��� yyyy-MM-dd HH:mm:ss
     * 
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(final Date dateDate) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * ����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd HH:mm:ss
     * 
     * @param strDate
     * @return
     */
    public static Date strToDateLong(final String strDate) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date strtodate = null;
        try {
            strtodate = formatter.parse(strDate);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return strtodate;
    }

    /**
     * ����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd
     * 
     * @param strDate
     * @return
     */
    public static Date strToDate(final String strDate) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date strtodate = null;
        try {
            strtodate = formatter.parse(strDate);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return strtodate;
    }

    /**
     * ʱ��ǰ�ƻ���Ʒ���,����JJ��ʾ����.
     */
    public static String getPreTime(final String sj1, final int jj) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            final Date date1 = format.parse(sj1);
            final long Time = (date1.getTime() / 1000) + jj * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return mydate1;
    }

    /**
     * �õ�һ��ʱ���Ӻ��ǰ�Ƽ����ʱ��,nowdateΪʱ��,delayΪǰ�ƻ���ӵ�����
     */
    public static String getNextDay(final String nowdate, final String delay) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            final Date d = strToDate(nowdate);
            final long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24
                    * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (final Exception e) {
            return "";
        }
    }

    public static void main(final String[] args) {
        final SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            System.out.println(CommonUtil.getMonthLastDay(s2.parse("2009-02-01")));
            System.out.println(CommonUtil.formatData2(159.51670));

            System.out.println(6 % 3);
            System.out.println(6 / 3);

        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
