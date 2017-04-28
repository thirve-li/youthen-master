// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.presentation.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.ConfigFileHolder;
import com.youthen.framework.common.StringUtils;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.master.common.BusinessCheckException;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.persistence.entity.Password;
import com.youthen.master.persistence.entity.PasswordStyle;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.OsAudittrailService;
import com.youthen.master.service.SystemConfigService;
import com.youthen.master.service.dto.OsAudittrailDto;
import com.youthen.master.service.dto.SystemConfigDto;
import com.youthen.master.util.PasswordValidate;
import com.youthen.master.util.SystemConst;

/**
 * 。
 * 
 * @author FLN
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Namespace("/mst-config")
@Results({
        @Result(name = "configInit", location = "/WEB-INF/jsp/master/config/config.jsp"),
        @Result(name = SUCCESS, location = "/WEB-INF/jsp/master/config/config.jsp"),
        @Result(name = INPUT, location = "/WEB-INF/jsp/master/config/config.jsp")})
@Controller
@ExecAuthority(functioncd = "1")
public class SystemConfigAction extends CommonMasterMaintenanceAction<SystemConfigDto> {

    private static final long serialVersionUID = -4372884128344672292L;

    private static final String CONFIG_UPLOAD_FILE_MAXSIZE_SELECT_VAL = "config.uploadFileMaxsize.select.val";

    private static final String CONFIG_UPLOAD_FILE_MAXSIZE_SELECT_TXT = "config.uploadFileMaxsize.select.txt";

    private static final String CONFIG_UPLOAD_FILE_FORMAT_SELECT_TXT = "config.uploadFileFormat.select.txt";

    private static final String CONFIG_PWD_NO_SAME_SELECT_VAL = "config.pwdNoSame.select.val";

    private static final String CONFIG_PWD_NO_SAME_SELECT_TXT = "config.pwdNoSame.select.txt";

    private static final String CONFIG_PWD_CHANGE_DAYS_SELECT_VAL = "config.pwdChangeDays.select.val";

    private static final String CONFIG_PWD_CHANGE_DAYS_SELECT_TXT = "config.pwdChangeDays.select.txt";

    private static final String CONFIG_PWD_ERR_TIME_SELECT_VAL = "config.pwdErrTime.select.val";

    private static final String CONFIG_PWD_ERR_TIME_SELECT_TXT = "config.pwdErrTime.select.txt";

    private static final String CONFIG_PWD_CHANGE_MIN_SELECT_VAL = "config.pwdChangeMin.select.val";

    private static final String CONFIG_PWD_CHANGE_MIN_SELECT_TXT = "config.pwdChangeMin.select.txt";

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private OsAudittrailService audittrailService;
    @Autowired
    private LoginUserService loginUserService;

    private SystemConfigDto dto = new SystemConfigDto();

    private List<PasswordStyle> passwordStyles;

    private Map<String, String[][]> initMap = new HashMap<String, String[][]>();

    private String uploadFileFormat;

    private int tabId = 7;

    /**
     * getter for tabId.
     * 
     * @return tabId
     */
    public int getTabId() {
        return this.tabId;
    }

    /**
     * setter for tabId.
     * 
     * @param aTabId tabId
     */
    public void setTabId(final int aTabId) {
        this.tabId = aTabId;
    }

    /**
     * getter for systemConfigService.
     * 
     * @return systemConfigService
     */
    public SystemConfigService getSystemConfigService() {
        return this.systemConfigService;
    }

    /**
     * setter for systemConfigService.
     * 
     * @param aSystemConfigService systemConfigService
     */
    public void setSystemConfigService(final SystemConfigService aSystemConfigService) {
        this.systemConfigService = aSystemConfigService;
    }

    /**
     * getter for passwordStyles.
     * 
     * @return passwordStyles
     */
    public List<PasswordStyle> getPasswordStyles() {
        return this.passwordStyles;
    }

    /**
     * setter for passwordStyles.
     * 
     * @param aPasswordStyles passwordStyles
     */
    public void setPasswordStyles(final List<PasswordStyle> aPasswordStyles) {
        this.passwordStyles = aPasswordStyles;
    }

    /**
     * getter for initMap.
     * 
     * @return initMap
     */
    public Map<String, String[][]> getInitMap() {
        return this.initMap;
    }

    /**
     * setter for initMap.
     * 
     * @param aInitMap initMap
     */
    public void setInitMap(final Map<String, String[][]> aInitMap) {
        this.initMap = aInitMap;
    }

    /**
     * getter for uploadFileFormat.
     * 
     * @return uploadFileFormat
     */
    public String getUploadFileFormat() {
        return this.uploadFileFormat;
    }

    /**
     * setter for uploadFileFormat.
     * 
     * @param aUploadFileFormat uploadFileFormat
     */
    public void setUploadFileFormat(final String aUploadFileFormat) {
        this.uploadFileFormat = aUploadFileFormat;
    }

    public void initDto() {
        try {
            this.dto = this.systemConfigService.load();
            // 密码强度设置
            final Password password = PasswordValidate.parseXml(this.dto.getPwdValidtion());
            this.passwordStyles = password.getStyle();
            this.dto.setPwdLenMax(password.getMaxLen());
            this.dto.setPwdLenMin(password.getMinLen());
            ConfigFileHolder.setFilePath("webmessage.properties");
            this.dto.setSmtpServer(ConfigFileHolder.getString("mail.smtpserver.ip"));
            if (StringUtils.isNotEmpty(ConfigFileHolder.getString("mail.smtpserver.port"))) {
                this.dto.setSmtpPort(Integer.parseInt(ConfigFileHolder.getString("mail.smtpserver.port")));
            }
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 。
     * 
     * @return string
     */
    @SuppressWarnings("boxing")
    @Action("configInit")
    public String configInit() {
        initDto();
        this.initData();

        final HttpServletRequest request = ServletActionContext.getRequest();
        request.getSession().setAttribute("tabId", 5);// 菜单Tab页ID
        return "configInit";
    }

    @Action("save")
    public String save() {
        try {
            this.systemConfigService.saveOrUpdate(this.dto);

            // 审查追踪
            final LoginUser user = this.loginUserService.getUserByUserId(this.getSessionUser().getUserId());
            final OsAudittrailDto auditDto = new OsAudittrailDto();
            auditDto.setCreater(user);
            auditDto.setReason(this.dto.getReason());
            auditDto.setObjectName("系统设置");
            auditDto.setActionName("修改系统设置");

            if (this.dto.getId() != null) {
                auditDto.setBeOptedObjectId(this.dto.getId().toString());
            }
            auditDto.setChangedContent(this.dto.getChangedContent());
            this.audittrailService.add(auditDto);
            initDto();
            initData();
        } catch (final BusinessCheckException e) {
            e.printStackTrace();
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return "configInit";
    }

    /**
     * 页面下拉框初值。
     */
    private void initData() {
        ConfigFileHolder.setFilePath("webmessage.properties");
        // 密码修改间隔
        final String pwdChangeMinText = ConfigFileHolder.getString(CONFIG_PWD_CHANGE_MIN_SELECT_TXT);
        final String pwdChangeMinValue = ConfigFileHolder.getString(CONFIG_PWD_CHANGE_MIN_SELECT_VAL);
        this.initMap.put("pwdChangeMin", createArray(pwdChangeMinText, pwdChangeMinValue));
        // 密码错误锁定用户
        final String pwdErrTimeText = ConfigFileHolder.getString(CONFIG_PWD_ERR_TIME_SELECT_TXT);
        final String pwdErrTimeValue = ConfigFileHolder.getString(CONFIG_PWD_ERR_TIME_SELECT_VAL);
        this.initMap.put("pwdErrTime", createArray(pwdErrTimeText, pwdErrTimeValue));
        // 密码使用有效期
        final String pwdChangeDaysText = ConfigFileHolder.getString(CONFIG_PWD_CHANGE_DAYS_SELECT_TXT);
        final String pwdChangeDaysValue = ConfigFileHolder.getString(CONFIG_PWD_CHANGE_DAYS_SELECT_VAL);
        this.initMap.put("pwdChangeDays", createArray(pwdChangeDaysText, pwdChangeDaysValue));
        // 密码不能和以前多少次相同
        final String pwdNoSameText = ConfigFileHolder.getString(CONFIG_PWD_NO_SAME_SELECT_TXT);
        final String pwdNoSameValue = ConfigFileHolder.getString(CONFIG_PWD_NO_SAME_SELECT_VAL);
        this.initMap.put("pwdNoSame", createArray(pwdNoSameText, pwdNoSameValue));
        // 上传文件格式
        final String uploadFileFormatText = ConfigFileHolder.getString(CONFIG_UPLOAD_FILE_FORMAT_SELECT_TXT);
        final String uploadFileFormatValue = uploadFileFormatText;
        final String[][] uploadFileFormat = createArray(uploadFileFormatText, uploadFileFormatValue);
        for (final String[] format : uploadFileFormat) {
            format[1] = SystemConst.UPLOAD_FILE_FORMAT_SPLIT + format[1] + SystemConst.UPLOAD_FILE_FORMAT_SPLIT;
        }

        // 上传文件大小
        final String maxSizeText = ConfigFileHolder.getString(CONFIG_UPLOAD_FILE_MAXSIZE_SELECT_TXT);
        final String maxSizeValue = ConfigFileHolder.getString(CONFIG_UPLOAD_FILE_MAXSIZE_SELECT_VAL);
        this.initMap.put("uploadFileMaxsize", createArray(maxSizeText, maxSizeValue));
        this.initMap.put("uploadFileFormat", uploadFileFormat);
    }

    /**
     * 把两个字符串转成二维数组。
     * 
     * @param text
     * @param value
     * @return String[][]
     */
    private String[][] createArray(final String text, final String value) {
        if (text == null || value == null) {
            return new String[0][0];
        }
        final String[] textArr = text.split(";");
        final String[] valueArr = value.split(";");
        if (textArr.length != valueArr.length) {
            return new String[0][0];
        }
        final int len = textArr.length;
        final String[][] arr = new String[len][2];
        for (int i = 0; i < len; i++) {
            arr[i][0] = valueArr[i];
            arr[i][1] = textArr[i];
        }
        return arr;
    }

    /**
     * @see com.opensymphony.xwork2.Preparable#prepare()
     */
    @Override
    public void prepare() throws Exception {
    }

    /**
     * @see com.youthen.master.presentation.action.CommonMasterMaintenanceAction#setDto(com.youthen.master.service.dto.MasterEntryDto)
     */

    @Override
    public void setDto(final SystemConfigDto aDto) {
        this.dto = aDto;
    }

    /**
     * getter for dto.
     * 
     * @return dto
     */
    @Override
    public SystemConfigDto getDto() {
        return this.dto;
    }

    /**
     * @see com.youthen.master.presentation.action.CommonMasterMaintenanceAction#checkVersionno(java.lang.String,
     *      com.youthen.master.service.dto.MasterEntryDto)
     */
    @Override
    protected void checkVersionno(final String aMstKey, final SystemConfigDto aDto)
            throws OptimisticLockStolenException {
    }

}
