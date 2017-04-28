// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.context.SessionContext;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.common.BusinessCheckException;
import com.youthen.master.logic.CompanyLogic;
import com.youthen.master.persistence.dao.DepartmentDao;
import com.youthen.master.persistence.dao.KbnDao;
import com.youthen.master.persistence.entity.Company;
import com.youthen.master.persistence.entity.Department;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.service.CompanyService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.MasterDataMantanceService;
import com.youthen.master.service.dto.CompanyDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "companyService")
@Transactional(rollbackFor = Throwable.class)
public class CompanyServiceImpl implements CompanyService {

    @Resource
    KbnDao kbnDao;

    @Resource
    private CompanyLogic companyLogic;

    @Resource
    MasterDataMantanceService masterDataMantanceService;

    @Resource
    LoginUserService loginUserService;

    @Resource
    private DepartmentDao departmentDao;

    /**
     * @see com.youthen.master.service.CompanyService#add(com.youthen.master.service.dto.CompanyDto)
     */

    @Override
    public void add(final CompanyDto aDto) throws BusinessCheckException {
        try {
            final Company company = new Company();
            BeanUtils.copyAllNullableProperties(aDto, company);
            this.masterDataMantanceService.setType(Company.class);
            company.setStatus(1L);
            company.setActionName("新增公司");
            final Long id = (Long) this.masterDataMantanceService.insert(company);
            aDto.setId(id);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @see com.youthen.master.service.CompanyService#find(com.youthen.master.service.dto.CompanyDto)
     */

    @Override
    public CompanyDto find(final CompanyDto aConditionDto) throws BusinessCheckException {
        CompanyDto companyDto = new CompanyDto();
        companyDto = this.companyLogic.getById(aConditionDto.getId());
        return companyDto;
    }

    /**
     * @see com.youthen.master.service.CompanyService#update(com.youthen.master.service.dto.CompanyDto)
     */

    @Override
    public CompanyDto update(final CompanyDto aDto) {

        this.masterDataMantanceService.setType(Company.class);
        final Company company = (Company) this.masterDataMantanceService.getById(aDto.getId());
        BeanUtils.copyAllNullableProperties(aDto, company);
        this.masterDataMantanceService.update(company);

        return aDto;
    }

    /**
     * @see com.youthen.master.service.CompanyService#getById(java.lang.Long)
     */

    @Override
    public CompanyDto getById(final Long aCompanyId) {
        final CompanyDto aDto = new CompanyDto();
        this.masterDataMantanceService.setType(Company.class);
        final Company company = (Company) this.masterDataMantanceService.getById(aCompanyId);
        BeanUtils.copyAllNullableProperties(company, aDto);
        return aDto;
    }

    /**
     * @see com.youthen.master.service.CompanyService#selectAll()
     */

    @Override
    public List<CompanyDto> selectAll() {
        this.masterDataMantanceService.setType(Company.class);
        List<Company> list = null;

        final LoginUser loginUser = this.loginUserService.getUserByUserId(SessionContext.getUser().getUserId());
        if (loginUser.hasRole("ADMIN")) {
            list = this.getByHql(" from Company where status=1");
        } else {
            list = this.getByHql(" from Company where status=0");
        }

        final List<CompanyDto> dtoList = new ArrayList<CompanyDto>();

        if (!CollectionUtils.isEmpty(list)) {
            for (final Company company : list) {
                final CompanyDto dto = new CompanyDto();
                BeanUtils.copyAllNullableProperties(company, dto);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    /**
     * @see com.youthen.master.service.CompanyService#getByHql(java.lang.String)
     */

    @Override
    public List<Company> getByHql(final String aHql) {
        this.masterDataMantanceService.setType(Company.class);
        return this.masterDataMantanceService.queryByHql(aHql);
    }

    /**
     * @throws ObjectNotFoundException
     * @see com.youthen.master.service.CompanyService#del(com.youthen.master.service.dto.CompanyDto)
     */

    @Override
    @Transactional
    public void del(final CompanyDto aDto) throws BusinessCheckException {

        final Company company = (Company) this.masterDataMantanceService.getById(aDto.getId());
        company.setStatus(0L);
        company.setChangedContent("[项目名称：状态 原值：正常  新值：失效]");
        company.setObjectName(company.getName());
        company.setTableName(Company.class.getName());
        company.setReason(aDto.getReason());
        company.setActionName("失效");
        this.masterDataMantanceService.setType(Company.class);
        this.masterDataMantanceService.update(company);

        // 失效部门
        try {
            final List<Department> deptList = this.departmentDao.getByCompanyId(aDto.getId());

            for (final Department dept : deptList) {
                dept.setStatus(0L);
                dept.setChangedContent("[项目名称：状态 原值：正常  新值：失效]");
                this.masterDataMantanceService.setType(Department.class);
                this.masterDataMantanceService.update(dept);
            }

            // 失效用户
            this.masterDataMantanceService.setType(LoginUser.class);
            final List<LoginUser> userList =
                    this.masterDataMantanceService
                            .queryByHql("from LoginUser where company.id =" + aDto.getId()
                                    + " and status.code != 'DELETED'");

            for (final LoginUser user : userList) {
                final Kbn kbn = this.kbnDao.getKbn("USER_STATUS", "DELETED");
                user.setStatus(kbn);
                user.setChangedContent("[项目名称：状态 原值：" + user.getStatus().getNameCn() + "  新值：" + kbn.getNameCn() + "]");
                this.masterDataMantanceService.setType(LoginUser.class);
                this.masterDataMantanceService.update(user);
            }

        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * @see com.youthen.master.service.CompanyService#isCompanyExisted(java.lang.String)
     */

    @Override
    public boolean isCompanyExisted(final String CompanyName) {
        try {
            final List<CompanyDto> list = this.companyLogic.selectAll();
            for (final CompanyDto company : list) {
                if (CompanyName.equals(company.getName())) {
                    return true;
                }
            }
            return false;
        } catch (final ObjectNotFoundException e) {
            return false;
        }
    }

    /**
     * @see com.youthen.master.service.CompanyService#isCompanyCodeExisted(java.lang.String)
     */

    @Override
    public boolean isCompanyCodeExisted(final String aCompanyCode) {
        try {
            final List<CompanyDto> list = this.companyLogic.selectAll();
            for (final CompanyDto company : list) {
                if (aCompanyCode.equals(company.getCode())) {
                    return true;
                }
            }
            return false;
        } catch (final ObjectNotFoundException e) {
            return false;
        }
    }
}
