package com.youthen.master.service.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.youthen.framework.common.StringUtils;
import com.youthen.framework.common.annotation.Dto;
import com.youthen.master.persistence.entity.Company;
import com.youthen.master.persistence.entity.Department;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.persistence.entity.Role;
import com.youthen.master.persistence.entity.UserRole;

/**
 * 。
 * 
 * @author LiXin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */

@Dto
public class LoginUserDto extends MasterEntryDto {

    private static final long serialVersionUID = 3750410734301522021L;
    private String userId;
    private String password;
    private Long passwordErrorCount;
    private String name;
    private Long companyId;
    private Long departmentId;
    private String statusId;
    private Long roleId;

    private String email;
    private String phone;
    private String mobile;
    private String contacter;
    private String contacterTel;
    private Date loginTime;
    private String isQaUser;
    private String lockTime;
    private Long systemId;
    private Kbn status;
    private String imageName;
    private String qqNum;
    private String weixinNum;
    private String openId;
    private String accessToken;

    private Date expireTime;

    /** 昵称 **/
    private String nickName;
    /** 积分 **/
    private Integer score;
    private Department department;
    private Company company;
    private Set<Role> roles = new HashSet<Role>();
    private Set<Department> departments = new HashSet<Department>();
    private Set<Company> companys = new HashSet<Company>();

    private List<UserRole> userRoleList = new ArrayList<UserRole>();

    private String[] companyIds;
    private Long[] departmentIds;
    private Long[] systemIds;
    private Long[] roleIds;

    private String empNo;

    private String companyCode;

    private Long bigColumnId;

    private String sex;

    /**
     * 是否有某角色
     * 
     * @param roleCode
     * @return
     */
    public boolean hasRole(final String roleCode) {
        if (StringUtils.isEmpty(roleCode) || this.roles == null) {
            return false;
        }

        final Iterator<Role> it = this.roles.iterator();

        while (it.hasNext()) {
            final Role role = it.next();
            if (role.getCode().equalsIgnoreCase(roleCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是系统唯一角色
     * 
     * @param roleCode
     * @return
     */
    public boolean isSystemUniqUser() {

        final Iterator<Role> it = this.roles.iterator();

        while (it.hasNext()) {
            final Role role = it.next();
            if (role.getUsersCountsFlag() == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是部门一角色
     * 
     * @param roleCode
     * @return
     */
    public boolean isDeptUniqUser() {

        final Iterator<Role> it = this.roles.iterator();

        while (it.hasNext()) {
            final Role role = it.next();
            if (role.getUsersCountsFlag() == 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * getter for userId.
     * 
     * @return userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * setter for userId.
     * 
     * @param aUserId userId
     */
    public void setUserId(final String aUserId) {
        this.userId = aUserId;
    }

    /**
     * getter for name.
     * 
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * setter for name.
     * 
     * @param aName name
     */
    public void setName(final String aName) {
        this.name = aName;
    }

    /**
     * getter for password.
     * 
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * setter for password.
     * 
     * @param aPassword password
     */
    public void setPassword(final String aPassword) {
        this.password = aPassword;
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
     * getter for passwordErrorCount.
     * 
     * @return passwordErrorCount
     */
    public Long getPasswordErrorCount() {
        return this.passwordErrorCount;
    }

    /**
     * setter for passwordErrorCount.
     * 
     * @param aPasswordErrorCount passwordErrorCount
     */
    public void setPasswordErrorCount(final Long aPasswordErrorCount) {
        this.passwordErrorCount = aPasswordErrorCount;
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
     * getter for email.
     * 
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * setter for email.
     * 
     * @param aEmail email
     */
    public void setEmail(final String aEmail) {
        this.email = aEmail;
    }

    /**
     * getter for statusId.
     * 
     * @return statusId
     */
    public String getStatusId() {
        return this.statusId;
    }

    /**
     * setter for statusId.
     * 
     * @param aStatusId statusId
     */
    public void setStatusId(final String aStatusId) {
        this.statusId = aStatusId;
    }

    /**
     * getter for phone.
     * 
     * @return phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * setter for phone.
     * 
     * @param aPhone phone
     */
    public void setPhone(final String aPhone) {
        this.phone = aPhone;
    }

    /**
     * getter for mobile.
     * 
     * @return mobile
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * setter for mobile.
     * 
     * @param aMobile mobile
     */
    public void setMobile(final String aMobile) {
        this.mobile = aMobile;
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
     * getter for contacter.
     * 
     * @return contacter
     */
    public String getContacter() {
        return this.contacter;
    }

    /**
     * setter for contacter.
     * 
     * @param aContacter contacter
     */
    public void setContacter(final String aContacter) {
        this.contacter = aContacter;
    }

    /**
     * getter for contacterTel.
     * 
     * @return contacterTel
     */
    public String getContacterTel() {
        return this.contacterTel;
    }

    /**
     * setter for contacterTel.
     * 
     * @param aContacterTel contacterTel
     */
    public void setContacterTel(final String aContacterTel) {
        this.contacterTel = aContacterTel;
    }

    /**
     * getter for loginTime.
     * 
     * @return loginTime
     */
    public Date getLoginTime() {
        return this.loginTime;
    }

    /**
     * setter for loginTime.
     * 
     * @param aLoginTime loginTime
     */
    public void setLoginTime(final Date aLoginTime) {
        this.loginTime = aLoginTime;
    }

    /**
     * getter for isQaUser.
     * 
     * @return isQaUser
     */
    public String getIsQaUser() {
        return this.isQaUser;
    }

    /**
     * setter for isQaUser.
     * 
     * @param aIsQaUser isQaUser
     */
    public void setIsQaUser(final String aIsQaUser) {
        this.isQaUser = aIsQaUser;
    }

    /**
     * getter for company.
     * 
     * @return company
     */
    public Company getCompany() {
        return this.company;
    }

    /**
     * setter for company.
     * 
     * @param aCompany company
     */
    public void setCompany(final Company aCompany) {
        this.company = aCompany;
    }

    /**
     * getter for department.
     * 
     * @return department
     */
    public Department getDepartment() {
        return this.department;
    }

    /**
     * getter for roleIds.
     * 
     * @return roleIds
     */
    public Long[] getRoleIds() {
        return this.roleIds;
    }

    /**
     * setter for roleIds.
     * 
     * @param aRoleIds roleIds
     */
    public void setRoleIds(final Long[] aRoleIds) {
        this.roleIds = aRoleIds;
    }

    /**
     * setter for department.
     * 
     * @param aDepartment department
     */
    public void setDepartment(final Department aDepartment) {
        this.department = aDepartment;
    }

    /**
     * getter for lockTime.
     * 
     * @return lockTime
     */
    public String getLockTime() {
        return this.lockTime;
    }

    /**
     * setter for lockTime.
     * 
     * @param aLockTime lockTime
     */
    public void setLockTime(final String aLockTime) {
        this.lockTime = aLockTime;
    }

    /**
     * getter for status.
     * 
     * @return status
     */
    public Kbn getStatus() {
        return this.status;
    }

    /**
     * setter for status.
     * 
     * @param aStatus status
     */
    public void setStatus(final Kbn aStatus) {
        this.status = aStatus;
    }

    /**
     * getter for roles.
     * 
     * @return roles
     */
    public Set<Role> getRoles() {
        return this.roles;
    }

    /**
     * setter for roles.
     * 
     * @param aRoles roles
     */
    public void setRoles(final Set<Role> aRoles) {
        this.roles = aRoles;
    }

    /**
     * @see com.youthen.master.service.dto.MasterEntryDto#getId()
     */

    @Override
    public String getId() {
        return this.userId;
    }

    /**
     * getter for roleId.
     * 
     * @return roleId
     */
    public Long getRoleId() {
        return this.roleId;
    }

    /**
     * setter for roleId.
     * 
     * @param aRoleId roleId
     */
    public void setRoleId(final Long aRoleId) {
        this.roleId = aRoleId;
    }

    /**
     * getter for empNo.
     * 
     * @return empNo
     */
    public String getEmpNo() {
        return this.empNo;
    }

    /**
     * setter for empNo.
     * 
     * @param aEmpNo empNo
     */
    public void setEmpNo(final String aEmpNo) {
        this.empNo = aEmpNo;
    }

    /**
     * getter for departments.
     * 
     * @return departments
     */
    public Set<Department> getDepartments() {
        return this.departments;
    }

    /**
     * setter for departments.
     * 
     * @param aDepartments departments
     */
    public void setDepartments(final Set<Department> aDepartments) {
        this.departments = aDepartments;
    }

    /**
     * getter for companys.
     * 
     * @return companys
     */
    public Set<Company> getCompanys() {
        return this.companys;
    }

    /**
     * setter for companys.
     * 
     * @param aCompanys companys
     */
    public void setCompanys(final Set<Company> aCompanys) {
        this.companys = aCompanys;
    }

    /**
     * getter for departmentIds.
     * 
     * @return departmentIds
     */
    public Long[] getDepartmentIds() {
        return this.departmentIds;
    }

    /**
     * setter for departmentIds.
     * 
     * @param aDepartmentIds departmentIds
     */
    public void setDepartmentIds(final Long[] aDepartmentIds) {
        this.departmentIds = aDepartmentIds;
    }

    /**
     * getter for companyIds.
     * 
     * @return companyIds
     */
    public String[] getCompanyIds() {
        return this.companyIds;
    }

    /**
     * setter for companyIds.
     * 
     * @param aCompanyIds companyIds
     */
    public void setCompanyIds(final String[] aCompanyIds) {
        this.companyIds = aCompanyIds;
    }

    /**
     * getter for systemId.
     * 
     * @return systemId
     */
    public Long getSystemId() {
        return this.systemId;
    }

    /**
     * setter for systemId.
     * 
     * @param aSystemId systemId
     */
    public void setSystemId(final Long aSystemId) {
        this.systemId = aSystemId;
    }

    /**
     * getter for userRoleList.
     * 
     * @return userRoleList
     */
    public List<UserRole> getUserRoleList() {
        return this.userRoleList;
    }

    /**
     * setter for userRoleList.
     * 
     * @param aUserRoleList userRoleList
     */
    public void setUserRoleList(final List<UserRole> aUserRoleList) {
        this.userRoleList = aUserRoleList;
    }

    /**
     * getter for systemIds.
     * 
     * @return systemIds
     */
    public Long[] getSystemIds() {
        return this.systemIds;
    }

    /**
     * setter for systemIds.
     * 
     * @param aSystemIds systemIds
     */
    public void setSystemIds(final Long[] aSystemIds) {
        this.systemIds = aSystemIds;
    }

    /**
     * getter for imageName.
     * 
     * @return imageName
     */
    public String getImageName() {
        return this.imageName;
    }

    /**
     * setter for imageName.
     * 
     * @param aImageName imageName
     */
    public void setImageName(final String aImageName) {
        this.imageName = aImageName;
    }

    /**
     * getter for companyCode.
     * 
     * @return companyCode
     */
    public String getCompanyCode() {
        return this.companyCode;
    }

    /**
     * setter for companyCode.
     * 
     * @param aCompanyCode companyCode
     */
    public void setCompanyCode(final String aCompanyCode) {
        this.companyCode = aCompanyCode;
    }

    /**
     * getter for bigColumnId.
     * 
     * @return bigColumnId
     */
    public Long getBigColumnId() {
        return this.bigColumnId;
    }

    /**
     * setter for bigColumnId.
     * 
     * @param aBigColumnId bigColumnId
     */
    public void setBigColumnId(final Long aBigColumnId) {
        this.bigColumnId = aBigColumnId;
    }

    /**
     * getter for qqNum.
     * 
     * @return qqNum
     */
    public String getQqNum() {
        return this.qqNum;
    }

    /**
     * setter for qqNum.
     * 
     * @param aQqNum qqNum
     */
    public void setQqNum(final String aQqNum) {
        this.qqNum = aQqNum;
    }

    /**
     * getter for weixinNum.
     * 
     * @return weixinNum
     */
    public String getWeixinNum() {
        return this.weixinNum;
    }

    /**
     * setter for weixinNum.
     * 
     * @param aWeixinNum weixinNum
     */
    public void setWeixinNum(final String aWeixinNum) {
        this.weixinNum = aWeixinNum;
    }

    /**
     * getter for openId.
     * 
     * @return openId
     */
    public String getOpenId() {
        return this.openId;
    }

    /**
     * setter for openId.
     * 
     * @param aOpenId openId
     */
    public void setOpenId(final String aOpenId) {
        this.openId = aOpenId;
    }

    /**
     * getter for accessToken.
     * 
     * @return accessToken
     */
    public String getAccessToken() {
        return this.accessToken;
    }

    /**
     * setter for accessToken.
     * 
     * @param aAccessToken accessToken
     */
    public void setAccessToken(final String aAccessToken) {
        this.accessToken = aAccessToken;
    }

    /**
     * getter for nickName.
     * 
     * @return nickName
     */
    public String getNickName() {
        return this.nickName;
    }

    /**
     * setter for nickName.
     * 
     * @param aNickName nickName
     */
    public void setNickName(final String aNickName) {
        this.nickName = aNickName;
    }

    /**
     * getter for expireTime.
     * 
     * @return expireTime
     */
    public Date getExpireTime() {
        return this.expireTime;
    }

    /**
     * setter for expireTime.
     * 
     * @param aExpireTime expireTime
     */
    public void setExpireTime(final Date aExpireTime) {
        this.expireTime = aExpireTime;
    }

    /**
     * getter for sex.
     * 
     * @return sex
     */
    public String getSex() {
        return this.sex;
    }

    /**
     * setter for sex.
     * 
     * @param aSex sex
     */
    public void setSex(final String aSex) {
        this.sex = aSex;
    }

    /**
     * getter for score.
     * 
     * @return score
     */
    public Integer getScore() {
        return this.score;
    }

    /**
     * setter for score.
     * 
     * @param aScore score
     */
    public void setScore(final Integer aScore) {
        this.score = aScore;
    }

}
