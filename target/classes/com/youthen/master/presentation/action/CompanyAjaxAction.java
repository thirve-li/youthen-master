package com.youthen.master.presentation.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.youthen.framework.common.annotation.ExecAuthority;
import com.youthen.framework.presentation.action.AbstractAjaxAction;
import com.youthen.master.persistence.entity.Company;
import com.youthen.master.persistence.entity.Department;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.persistence.entity.Role;
import com.youthen.master.service.CompanyService;
import com.youthen.master.service.DepartmentService;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.dto.CompanyDto;
import com.youthen.master.service.dto.DepartmentDto;

/**
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Namespace("/mst-company")
@Controller
@ExecAuthority(functioncd = "1")
public class CompanyAjaxAction extends AbstractAjaxAction {

    private static final long serialVersionUID = 1L;

    Long companyId;
    Long departmentId;
    String userName;
    String companyName;
    String code;

    @Autowired
    MasterDataMantanceService masterDataMantanceService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    protected Object doExecute(final Object aArgs) throws Exception {
        String resultString = "";
        try {
            final List<CompanyDto> companyList = this.companyService.selectAll();

            for (int i = 0; i < companyList.size(); i++) {
                final CompanyDto companyDto = companyList.get(i);
                resultString +=
                        ",{id:'company_" + companyDto.getId() + "',name:'" + companyDto.getName()
                                + "',open:true,isParent:true}";
            }
            if (resultString.length() > 0) resultString = resultString.substring(1);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "[" + resultString + "]";
    }

    public Object getCompany() {
        try {
            final List<CompanyDto> comLst = new ArrayList<CompanyDto>();
            final List<Company> companyList =
                    this.companyService
                            .getByHql("from Company c where c.id in ( select u.companyId from LoginUser u where u.userId='"
                                    + this.userName + "' u.status.code <> 'DELETED'  ) and c.status=1");
            for (final Company company : companyList) {
                final CompanyDto dto = new CompanyDto();
                dto.setId(company.getId());
                dto.setName(company.getName());
                comLst.add(dto);
            }

            return comLst;

        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<DepartmentDto> getDepartmentByCompanyId() {
        try {
            final List<DepartmentDto> dptLst = new ArrayList<DepartmentDto>();
            final List<Department> departmentList =
                    this.departmentService
                            .getByHql("from Department c where c.id in (select u.departmentId from LoginUser u where u.status.code <> 'DELETED' and u.userId ='"
                                    + this.userName + "' ) and c.status=1 and c.companyId="
                                    + this.companyId);
            for (final Department department : departmentList) {
                final DepartmentDto dto = new DepartmentDto();
                dto.setId(department.getId());
                dto.setName(department.getName() + getSysStr(this.userName + "_" + this.companyId + "_" + dto.getId()));
                dptLst.add(dto);
            }

            return dptLst;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSysStr(final String realUserId) {
        final StringBuffer sysStr = new StringBuffer();
        this.masterDataMantanceService.setType(LoginUser.class);
        final List<LoginUser> userList = this.masterDataMantanceService
                .queryByCondition(" userId='" + realUserId + "'");
        if (userList.size() > 0) {
            final Set<Role> roles = userList.get(0).getRoles();
            String roleCode = null;
            String tmpSys = null;
            for (final Role role : roles) {
                roleCode = role.getCode();
                if (roleCode.indexOf("-") > 0) {
                    tmpSys = roleCode.split("-")[0];
                    if (sysStr.indexOf(tmpSys) < 0) {
                        sysStr.append(" ");
                        sysStr.append(tmpSys);
                    }
                }
            }
        }
        return sysStr.toString().length() > 0 ? "(" + sysStr.toString().substring(1) + ")" : "";
    }

    public String getRealUserName() {
        String realUserName = "";
        this.masterDataMantanceService.setType(LoginUser.class);
        final List<LoginUser> userList =
                this.masterDataMantanceService
                        .queryByCondition(" userId='" + this.userName + "' and company.id=" + this.companyId
                                + "  and department.id=" + this.departmentId);
        if (CollectionUtils.isNotEmpty(userList) && userList.size() > 0) {
            final LoginUser user = userList.get(0);
            realUserName = user.getUserId();
        } else {
            return this.userName;
        }
        return realUserName;
    }

    @SuppressWarnings("boxing")
    public Object isCompanyExisted() {
        return this.companyService.isCompanyExisted(this.companyName);
    }

    @SuppressWarnings("boxing")
    public Object isCompanyCodeExisted() {
        return this.companyService.isCompanyCodeExisted(this.code);
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
     * getter for userName.
     * 
     * @return userName
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * setter for userName.
     * 
     * @param aUserName userName
     */
    public void setUserName(final String aUserName) {
        this.userName = aUserName;
    }

    /**
     * getter for departmentId.
     * 
     * @return departmentId
     */
    public Long getDepartmentId() {
        return this.departmentId;
    }

    /**
     * setter for departmentId.
     * 
     * @param aDepartmentId departmentId
     */
    public void setDepartmentId(final Long aDepartmentId) {
        this.departmentId = aDepartmentId;
    }

    /**
     * getter for companyName.
     * 
     * @return companyName
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * setter for companyName.
     * 
     * @param aCompanyName companyName
     */
    public void setCompanyName(final String aCompanyName) {
        this.companyName = aCompanyName;
    }

    /**
     * getter for code.
     * 
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * setter for code.
     * 
     * @param aCode code
     */
    public void setCode(final String aCode) {
        this.code = aCode;
    }
}
