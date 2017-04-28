package com.youthen.master.presentation.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.master.common.BusinessCheckException;
import com.youthen.master.service.CompanyService;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.dto.CompanyDto;

/**
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Namespace("/mst-company")
@Results({
        @Result(name = "main", location = "/WEB-INF/jsp/master/main.jsp"),
        @Result(name = "addInit", location = "/WEB-INF/jsp/master/company/add.jsp"),
        @Result(name = "userList", location = "/mst-user/list.action?dto.companyId=${dto.id}&dto.department.name=updateCompany", type = "redirect"),
        @Result(name = "list", location = "/WEB-INF/jsp/master/company/list.jsp"),
        @Result(name = "view", location = "/WEB-INF/jsp/master/company/view.jsp")})
@Controller
@ExecAuthority(functioncd = "1")
public class CompanyAction extends CommonMasterMaintenanceAction<CompanyDto> {

    /**
     */
    private static final long serialVersionUID = 8997238085997422595L;

    @Autowired
    private CompanyService companyService;

    private CompanyDto dto;
    private List<CompanyDto> companyDtoList;

    @Autowired
    MasterDataMantanceService masterDataMantanceService;

    private String namespace;

    private int tabId = 1;

    private int flag = 1;

    @ExecAuthority(functioncd = "1")
    @Action("main")
    public String main() {
        this.namespace = "mst-company";
        return "main";
    }

    /**
     * 进入公司信息列表初始化页面
     * 
     * @return String
     */
    @Action("list")
    public String list() {

        try {
            this.companyDtoList = this.companyService.selectAll();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return "list";
    }

    /**
     * 公司失效
     * 
     * @return String
     */
    @Action("del")
    public String del() {

        try {
            this.companyService.del(this.dto);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return list();
    }

    /**
     * 显示头部LOGO
     * 显示尾部LOGO
     * 
     * @return String
     */
    @Action("showImg")
    public void showImg() {

        try {
            this.dto = this.companyService.getById(this.dto.getId());
            Blob b = null;
            if (this.flag == 1) {
                b = this.dto.getHeaderLogo();

            } else if (this.flag == 2) {
                b = this.dto.getFooterLogo();

            }
            if (b != null) {
                final InputStream ins = b.getBinaryStream();
                ServletActionContext.getResponse().setContentType("image/jpeg");
                final OutputStream outs = ServletActionContext.getResponse().getOutputStream();
                final byte[] buff = new byte[1024];
                int i = 0;
                while ((i = ins.read(buff)) != -1) {
                    outs.write(buff);
                }
                ins.close();
                outs.close();
            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加公司，初始化页面
     * 
     * @return String
     */
    @Action("addInit")
    public String addInit() {
        try {
            if (this.dto != null && this.dto.getId() != null) {
                this.dto = this.companyService.getById(this.dto.getId());
            }
            this.companyDtoList = this.companyService.selectAll();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "addInit";
    }

    /**
     * 新增公司信息
     * 
     * @return String
     */
    @Action("add")
    public String add() {
        try {
            final File headerFile = this.dto.getHeaderFile();
            if (headerFile != null) {
                this.dto.setHeaderLogo(Hibernate.createBlob(fileToByte(headerFile)));
            }
            final File footerFile = this.dto.getFooterFile();
            if (footerFile != null) {
                this.dto.setFooterLogo(Hibernate.createBlob(fileToByte(footerFile)));
            }
            if (this.dto.getId() == null) {
                this.companyService.add(this.dto);
            } else {
                this.companyService.update(this.dto);
            }

            this.companyDtoList = this.companyService.selectAll();
        } catch (final BusinessCheckException e) {
            this.addActionError(e.getAppMessage().getMesg());
        }
        return "userList";
    }

    private byte[] fileToByte(final File tmpFile) {
        byte len[];
        try {
            final FileInputStream in = new FileInputStream(tmpFile);
            final BufferedInputStream inFile = new BufferedInputStream(in);
            final int a = Integer.parseInt(String.valueOf(tmpFile.length()));
            len = new byte[a];
            while (inFile.read(len) != -1) {
            }
        } catch (final Exception e) {
            throw new java.lang.RuntimeException(e);
        }

        return len;
    }

    /**
     * 修改公司初始化页面
     * 
     * @return String
     */
    @Action("view")
    public String view() {
        try {
            this.companyDtoList = this.companyService.selectAll();
            this.dto = this.companyService.getById(this.dto.getId());

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "view";
    }

    /**
     * 保存修改公司信息
     * 
     * @return String
     */
    @Action("edit")
    public String edit() {
        try {
            this.dto.setActionName("修改公司");
            this.companyService.update(this.dto);
            this.companyDtoList = this.companyService.selectAll();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "list";
    }

    /**
     * getter for companyService.
     * 
     * @return companyService
     */
    public CompanyService getCompanyService() {
        return this.companyService;
    }

    /**
     * setter for companyService.
     * 
     * @param aCompanyService companyService
     */
    public void setCompanyService(final CompanyService aCompanyService) {
        this.companyService = aCompanyService;
    }

    /**
     * 大文本的上传( headerLogo、footerLogo),保存到数据库
     * 
     * @param CompanyDto dto
     * @param aPath
     */
    public String upload(final CompanyDto dto, final String aPath) {

        return null;
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
     * getter for dto.
     * 
     * @return dto
     */
    @Override
    public CompanyDto getDto() {
        return this.dto;
    }

    /**
     * setter for dto.
     * 
     * @param aDto dto
     */
    @Override
    public void setDto(final CompanyDto aDto) {
        this.dto = aDto;
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
     * setter for tabId.
     * 
     * @param aTabId tabId
     */
    public void setTabId(final int aTabId) {
        this.tabId = aTabId;
    }

    /**
     * @see com.youthen.master.presentation.action.CommonMasterMaintenanceAction#checkVersionno(java.lang.String,
     *      com.youthen.master.service.dto.MasterEntryDto)
     */

    @Override
    protected void checkVersionno(final String aMstKey, final CompanyDto aDto) throws OptimisticLockStolenException {
    }

    /**
     * getter for flag.
     * 
     * @return flag
     */
    public int getFlag() {
        return this.flag;
    }

    /**
     * setter for flag.
     * 
     * @param aFlag flag
     */
    public void setFlag(final int aFlag) {
        this.flag = aFlag;
    }

}
