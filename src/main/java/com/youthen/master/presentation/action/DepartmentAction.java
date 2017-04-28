package com.youthen.master.presentation.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.common.exception.OptimisticLockStolenException;
import com.youthen.master.service.CompanyService;
import com.youthen.master.service.DepartmentService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.dto.DepartmentDto;

@Namespace("/mst-department")
@Results({
        @Result(name = "main", location = "/WEB-INF/jsp/master/main.jsp"),
        @Result(name = "view", location = "/WEB-INF/jsp/master/department/edit.jsp"),
        @Result(name = "userList", location = "/mst-user/list.action?dto.departmentId=${dto.id}&dto.companyId=${dto.companyId}&dto.department.name=updateDept", type = "redirect"),
        @Result(name = "deptRoleInit", location = "/WEB-INF/jsp/master/department/deptRole.jsp")})
@Controller
@ExecAuthority(functioncd = "1")
public class DepartmentAction extends CommonMasterMaintenanceAction<DepartmentDto> {

    private static final long serialVersionUID = 1L;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LoginUserService loginUserService;

    private DepartmentDto dto = new DepartmentDto();

    private String optType;

    private String namespace;

    private int tabId = 1;

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

    @ExecAuthority(functioncd = "10")
    @Action("main")
    public String main() {
        this.namespace = "mst-department";
        return "main";
    }

    @ExecAuthority(functioncd = "10")
    @Action("view")
    public String view() {
        try {
            if (this.dto.getId() != null) {
                this.dto = this.departmentService.getById(this.dto.getId());
            }
            if (this.dto.getParentDepartmentId() != null && this.dto.getParentDepartmentId().longValue() > 0) {
                final DepartmentDto parentDto = this.departmentService.getById(this.dto.getParentDepartmentId());
                this.dto.setParentDepartmentName(parentDto.getName());
                this.dto.setCompanyId(parentDto.getCompanyId());
                this.dto.setRootId(parentDto.getRootId());
            }
            this.dto.setCompanyName(this.companyService.getById(this.dto.getCompanyId()).getName());

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "view";
    }

    @SuppressWarnings("boxing")
    @ExecAuthority(functioncd = "10")
    @Action("save")
    public String save() {
        try {

            if (this.dto.getCompanyId() != null && this.dto.getCompanyId() > 0) {
                this.dto.setCompanyName(this.companyService.getById(this.dto.getCompanyId()).getName());
            }
            if (this.dto.getParentDepartmentId() != null && this.dto.getParentDepartmentId() > 0) {
                this.dto.setParentDepartmentName(this.departmentService.getById(this.dto.getParentDepartmentId())
                        .getName());
            }

            final DepartmentDto deptDto = this.departmentService.insertOrUpdate(this.dto);
            this.optType = "saveSuccess";

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "userList";
    }

    @Override
    public void setDto(final DepartmentDto aDto) {
        this.dto = aDto;
    }

    @Override
    public DepartmentDto getDto() {
        return this.dto;
    }

    public String getOptType() {
        return this.optType;
    }

    public void setOptType(final String aOptType) {
        this.optType = aOptType;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(final String aNamespace) {
        this.namespace = aNamespace;
    }

    @Override
    protected void checkVersionno(final String aMstKey, final DepartmentDto aDto) throws OptimisticLockStolenException {
    }

}
