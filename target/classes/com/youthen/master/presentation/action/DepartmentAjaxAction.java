package com.youthen.master.presentation.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.presentation.action.AbstractAjaxAction;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.persistence.entity.Department;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.service.CompanyService;
import com.youthen.master.service.DepartmentService;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.RoleService;
import com.youthen.master.service.dto.CompanyDto;
import com.youthen.master.service.dto.DepartmentDto;
import com.youthen.master.service.dto.RoleDto;

@Namespace("/mst-department")
@Controller
public class DepartmentAjaxAction extends AbstractAjaxAction {

    private static final long serialVersionUID = 5777011856331394619L;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private MasterDataMantanceService masterDataMantanceService;

    List<LoginUser> userList;

    private String id;

    private Long systemId;

    private Long recorderTypeId;

    private Long roleId;

    private String departmentName;

    private Long companyId;

    private Long deptId;

    private Long roomId;

    @Override
    protected Object doExecute(final Object aArgs) throws Exception {
        String resultString = "";
        try {
            List<DepartmentDto> deptList = new ArrayList<DepartmentDto>();
            if (this.id.startsWith("company")) {
                deptList = this.departmentService.getChildrenByCompanyId(new Long(this.id.split("_")[1]));
            } else {
                deptList = this.departmentService.getByParentId(new Long(this.id));
            }
            for (int i = 0; i < deptList.size(); i++) {
                final DepartmentDto departmentDto = deptList.get(i);
                resultString +=
                        ",{id:'" + departmentDto.getId() + "',name:'" + departmentDto.getName()
                                + "',open:true,isParent:"
                                + (departmentDto.getChildrenDto().size() > 0 ? "true" : "false")
                                + ",pId:'" + departmentDto.getParentDepartmentId() + "',cId:'"
                                + departmentDto.getCompanyId() + "'}";
            }
            if (resultString.length() > 0) resultString = resultString.substring(1);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "[" + resultString + "]";
    }

    public List<LoginUser> getUserByDept() {
        try {
            this.userList =
                    this.departmentService.getByHql("from LoginUser where status.code !='DELETED' and departmentId="
                            + this.deptId);
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return this.userList;
    }

    /**
     * 获取公司的部门
     * 
     * @return deptList
     */
    public Object getDeptByCompanyId() {
        final List<DepartmentDto> deptList = new ArrayList<DepartmentDto>();
        try {

            final List<DepartmentDto> lst = this.departmentService.getByCompanyId(this.companyId);

            if (!CollectionUtils.isEmpty(lst)) {
                for (final DepartmentDto dept : lst) {
                    final DepartmentDto dto = new DepartmentDto();
                    BeanUtils.copyAllNullableProperties(dept, dto);
                    dto.setUsers(null);
                    dto.setChildrenDto(null);
                    deptList.add(dto);
                }
            }

        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return deptList;
    }

    /**
     * 获取部门的子部门
     * 
     * @return deptList
     */
    public int getChildDeptByDeptId() {
        try {
            return this.departmentService.getByHql("from " + Department.class.getName()
                    + " where status=1 and parentDepartmentId="
                    + this.deptId).size();
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 根据流程查询相关角色
    public Object getRoleByRecorderTypeId() {
        List<RoleDto> roleDtoList = new ArrayList<RoleDto>();
        try {
            roleDtoList = this.roleService.getRoleByRecorderTypeId(this.recorderTypeId);
        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return roleDtoList;
    }

    // 根据角色查询相关人员
    public Object getUserByRoleId() {
        Set<LoginUser> loginUserDtoList = new HashSet<LoginUser>();
        try {
            loginUserDtoList = this.roleService.getById(this.roleId).getUsers();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return loginUserDtoList;
    }

    // 根据部门名称模糊查询
    public Object getDepartmentByName() throws DuplicateKeyException, ObjectNotFoundException {
        final List<DepartmentDto> result = this.departmentService.getByDepartmentName(this.departmentName);
        for (final DepartmentDto dto : result) {
            dto.setUsers(null);
            dto.setChildrenDto(null);
        }
        return result;
    }

    // 获取部门房间公司树
    public Object getDepartmentTree() throws Exception {
        String resultString = "";
        try {
            List<DepartmentDto> deptList = new ArrayList<DepartmentDto>();
            List<CompanyDto> companyList = new ArrayList<CompanyDto>();
            if (null == this.id) {
                companyList = this.companyService.selectAll();
                for (final CompanyDto company : companyList) {
                    resultString +=
                            ",{id:'company_" + company.getId() + "',name:'" + company.getName()
                                    + "',open:true,isParent:true"
                                    + ",pId:'0'}";
                }
            } else if (this.id.split("_")[0].equals("company")) {
                deptList = this.departmentService.getByCompanyId(Long.parseLong(this.id.split("_")[1]));
                for (int i = 0; i < deptList.size(); i++) {
                    final DepartmentDto departmentDto = deptList.get(i);
                    resultString +=
                            ",{id:'dept_" + departmentDto.getId() + "',name:'" + departmentDto.getName()
                                    + "',open:true,isParent:true"
                                    + ",pId:'" + departmentDto.getCompanyId() + "'}";
                }
            } else if (this.id.split("_")[0].equals("dept")) {

            }
            if (resultString.length() > 0) resultString = resultString.substring(1);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        // final String test =
        // "[{id:1,pId:0,isParent:true,open:true,name:\"a\"},{id:2,pId:1,isParent:false,open:true,name:\"b\"},{id:3,pId:1,isParent:false,open:true,name:\"c\"}]";
        return "[" + resultString + "]";
        // return test;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String aId) {
        this.id = aId;
    }

    public Long getSystemId() {
        return this.systemId;
    }

    public void setSystemId(final Long aSystemId) {
        this.systemId = aSystemId;
    }

    public Long getRecorderTypeId() {
        return this.recorderTypeId;
    }

    public void setRecorderTypeId(final Long aRecorderTypeId) {
        this.recorderTypeId = aRecorderTypeId;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(final Long aRoleId) {
        this.roleId = aRoleId;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(final String aDepartmentName) {
        this.departmentName = aDepartmentName;
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
     * getter for deptId.
     * 
     * @return deptId
     */
    public Long getDeptId() {
        return this.deptId;
    }

    /**
     * setter for deptId.
     * 
     * @param aDeptId deptId
     */
    public void setDeptId(final Long aDeptId) {
        this.deptId = aDeptId;
    }

    /**
     * getter for roomId.
     * 
     * @return roomId
     */
    public Long getRoomId() {
        return this.roomId;
    }

    /**
     * setter for roomId.
     * 
     * @param aRoomId roomId
     */
    public void setRoomId(final Long aRoomId) {
        this.roomId = aRoomId;
    }

    /**
     * getter for userList.
     * 
     * @return userList
     */
    public List<LoginUser> getUserList() {
        return this.userList;
    }

    /**
     * setter for userList.
     * 
     * @param aUserList userList
     */
    public void setUserList(final List<LoginUser> aUserList) {
        this.userList = aUserList;
    }

}
