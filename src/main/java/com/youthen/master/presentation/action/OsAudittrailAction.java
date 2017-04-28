// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.presentation.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.framework.util.CommonUtils;
import com.youthen.master.service.CompanyService;
import com.youthen.master.service.KbnService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.OsAudittrailService;
import com.youthen.master.service.dto.CompanyDto;
import com.youthen.master.service.dto.KbnDto;
import com.youthen.master.service.dto.OsAudittrailDto;
import com.youthen.master.util.SystemConst;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Namespace("/mst-audittrail")
@Results({
        @Result(name = "list", location = "/WEB-INF/jsp/master/audittrail/list.jsp"),
        @Result(name = "main", location = "/WEB-INF/jsp/master/main.jsp"),
        @Result(name = "auditTrailPdf", location = "/WEB-INF/jsp/master/audittrail/" + SystemConst.CUSTOMER_NAME
                + "/auditTrailPdf.jsp")})
@Controller
@ExecAuthority(functioncd = "1")
public class OsAudittrailAction extends CommonMasterMaintenanceAction<OsAudittrailDto> {

    private static final long serialVersionUID = -1225915663779332584L;
    @Autowired
    private OsAudittrailService osAudittrailService;

    @Autowired
    private KbnService kbnService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LoginUserService loginUserService;

    private OsAudittrailDto dto;

    private List<OsAudittrailDto> dtoList;

    private List<KbnDto> kbnDtoList;

    private List<CompanyDto> companyDtoList;

    private Long companyId;

    private String namespace;

    private String userName;

    private String startDate;

    private String endDate;
    // 操作对象的类型
    private final String TRAC_TYPE = "TRAC_TYPE";

    private final int tabId = 8;

    private String sysName;

    @ExecAuthority(functioncd = "1")
    @Action("main")
    public String main() {
        this.namespace = "mst-audittrail";
        return "main";
    }

    @SuppressWarnings("unchecked")
    @Action("list")
    public String list() throws ObjectNotFoundException {

        // try {
        // this.kbnDtoList = this.kbnService.getKbnListByType(this.TRAC_TYPE);
        // this.companyDtoList = this.companyService.selectAll();
        // } catch (final ObjectNotFoundException e) {
        // this.setErrorMessages(e);
        // }

        if (this.startDate != null && this.endDate != null && !this.startDate.isEmpty() && !this.endDate.isEmpty()) {
            // 只限于sqlserver oracle需加to_date()
            this.dto.setUpdTime(" and updTime between '" + this.startDate + " 00:00:00" + "' and '" + this.endDate
                    + " 23:59:59" + "' ");
        } else if (this.startDate != null && !this.startDate.isEmpty()) {
            this.dto.setUpdTime(" and updTime >= '" + this.startDate + " 00:00:00" + "' ");
        } else if (this.endDate != null && !this.endDate.isEmpty()) {
            this.dto.setUpdTime(" and updTime <= '" + this.endDate + " 23:59:59" + "' ");
        }

        this.dtoList = this.osAudittrailService.getOsAudittrailList(this.dto);

        final int listSize = this.osAudittrailService.getOsAudittrailCount(this.dto);
        final int pages = CommonUtils.countPages(listSize, this.dto.getPageSize());
        this.dto.setPages(pages);
        this.dto.setListSize(listSize);

        return "list";
    }

    @Action("createList")
    public String createList() {
        try {
            final HttpServletRequest request = ServletActionContext.getRequest();
            final HttpServletResponse response = ServletActionContext.getResponse();

            final StringBuilder requestParamBuilder = new StringBuilder();
            requestParamBuilder.append("dto.id=1");
            if (this.dto.getObjectName() != null && this.dto.getObjectName().length() > 0) {
                requestParamBuilder.append("&dto.objectName=").append(
                        URLEncoder.encode(this.dto.getObjectName(), "UTF-8"));
            }
            if (this.dto.getCreater() != null && this.dto.getCreater().getName() != null
                    && this.dto.getCreater().getName().length() > 0) {
                requestParamBuilder.append("&dto.creater.name=").append(
                        URLEncoder.encode(this.dto.getCreater().getName(), "UTF-8"));
            }
            if (this.startDate != null && this.startDate.length() > 0) {
                requestParamBuilder.append("&startDate=").append(this.startDate);
            }
            if (this.endDate != null && this.endDate.length() > 0) {
                requestParamBuilder.append("&endDate=").append(this.endDate);
            }
            if (this.dto.getChangedContent() != null && this.dto.getChangedContent().length() > 0) {
                requestParamBuilder.append("&dto.changedContent=").append(
                        URLEncoder.encode(this.dto.getChangedContent(), "UTF-8"));
            }
            if (this.dto.getSysAndArea() > 0) {
                requestParamBuilder.append("&dto.sysAndArea=").append(
                        URLEncoder.encode(this.dto.getSysAndArea() + "", "UTF-8"));
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=" + "audittrail" + ".pdf");

            final String url =
                    "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
                            + "/mst-audittrail/" + "createListPdf.action?"
                            + requestParamBuilder.toString();

        } catch (final IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Action("createListPdf")
    public String createListPdf() {
        if (this.startDate != null && this.endDate != null && !this.startDate.isEmpty() && !this.endDate.isEmpty()) {
            this.dto.setUpdTime(" and updTime between '" + this.startDate + " 00:00:00" +
                    "' and '" + this.endDate + " 23:59:59" + "' ");
        } else if (this.startDate != null && !this.startDate.isEmpty()) {
            this.dto.setUpdTime(" and updTime >= '" + this.startDate + " 00:00:00" + "' ");
        } else if (this.endDate != null && !this.endDate.isEmpty()) {
            this.dto.setUpdTime(" and updTime <= '" + this.endDate + " 23:59:59" + "' ");
        }

        this.dtoList = this.osAudittrailService.getAllOsAudittrailList(this.dto);

        return "auditTrailPdf";
    }

    /**
     * @see com.youthen.master.presentation.action.CommonMasterMaintenanceAction#setDto(com.youthen.master.service.dto.MasterEntryDto)
     */

    @Override
    public void setDto(final OsAudittrailDto aDto) {
        this.dto = aDto;
    }

    /**
     * @see com.youthen.master.presentation.action.CommonMasterMaintenanceAction#getDto()
     */

    @Override
    public OsAudittrailDto getDto() {
        return this.dto;
    }

    /**
     * @see com.youthen.master.presentation.action.CommonMasterMaintenanceAction#checkVersionno(java.lang.String,
     *      com.youthen.master.service.dto.MasterEntryDto)
     */

    @Override
    protected void checkVersionno(final String aMstKey, final OsAudittrailDto aDto)
            throws OptimisticLockStolenException {
    }

    /**
     * getter for dtoList.
     * 
     * @return dtoList
     */
    public List<OsAudittrailDto> getDtoList() {
        return this.dtoList;
    }

    /**
     * setter for dtoList.
     * 
     * @param aDtoList dtoList
     */
    public void setDtoList(final List<OsAudittrailDto> aDtoList) {
        this.dtoList = aDtoList;
    }

    /**
     * getter for companyId.
     * 
     * @return companyId
     */
    public Long getCompanyId() {
        return this.companyId;
    }

    /**
     * setter for companyId.
     * 
     * @param aCompanyId companyId
     */
    public void setCompanyId(final Long aCompanyId) {
        this.companyId = aCompanyId;
    }

    /**
     * getter for namespace.
     * 
     * @return namespace
     */
    public String getNamespace() {
        return this.namespace;
    }

    /**
     * setter for namespace.
     * 
     * @param aNamespace namespace
     */
    public void setNamespace(final String aNamespace) {
        this.namespace = aNamespace;
    }

    /**
     * getter for tabId.
     * 
     * @return tabId
     */
    public int getTabId() {
        return this.tabId;
    }

    /**
     * getter for kbnDtoList.
     * 
     * @return kbnDtoList
     */
    public List<KbnDto> getKbnDtoList() {
        return this.kbnDtoList;
    }

    /**
     * setter for kbnDtoList.
     * 
     * @param aKbnDtoList kbnDtoList
     */
    public void setKbnDtoList(final List<KbnDto> aKbnDtoList) {
        this.kbnDtoList = aKbnDtoList;
    }

    /**
     * getter for companyDtoList.
     * 
     * @return companyDtoList
     */
    public List<CompanyDto> getCompanyDtoList() {
        return this.companyDtoList;
    }

    /**
     * setter for companyDtoList.
     * 
     * @param aCompanyDtoList companyDtoList
     */
    public void setCompanyDtoList(final List<CompanyDto> aCompanyDtoList) {
        this.companyDtoList = aCompanyDtoList;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(final String aUserName) {
        this.userName = aUserName;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(final String aStartDate) {
        this.startDate = aStartDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(final String aEndDate) {
        this.endDate = aEndDate;
    }

    public String getSysName() {
        if (this.sysName == null) {
            if (this.dto != null && (this.dto.getSysAndArea() + "").startsWith("12")) {
                return "dms-" + SystemConst.CUSTOMER_NAME;
            } else if (this.dto != null && (this.dto.getSysAndArea() + "").startsWith("13")) {
                return "qms-" + SystemConst.CUSTOMER_NAME;
            }
            return "cms-" + SystemConst.CUSTOMER_NAME;
        }
        return this.sysName;
    }

    public void setSysName(final String aSysName) {
        this.sysName = aSysName;
    }

}
