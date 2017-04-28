// ============================================================
// Copyright(c) Pro-Ship Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.persistence.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.persistence.entity.Role;
import com.youthen.master.service.dto.RoleDto;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Repository("roleDao")
public class RoleDao extends EntityDaoImpl<Role> {

    @SuppressWarnings("unchecked")
    public List<Role> getRoleByCompanyId(final Long companyId) {
        final String hql = "from Role where companyId=?";
        final List<Role> roleList = getHibernateTemplate().find(hql, companyId);
        return roleList;
    }

    public List<Role> getRoleByRecorderTypeId(final Long recorderTypeId) {
        return this.getByHql("from Role where recorderTypeId=" + recorderTypeId);
    }

    /**
     * @see com.youthen.framework.persistence.dao.impl.EntityDaoImpl#injectType()
     */
    @Override
    @PostConstruct
    public void injectType() {
        this.setType(Role.class);
    }

    @SuppressWarnings("unchecked")
    public List<Role> getRoleBycompanyId(final String hql, final Object[] param) {
        final List<Role> roleList = getHibernateTemplate().find(hql, param);
        return roleList;
    }

    // 分页查询
    @SuppressWarnings("unchecked")
    public List<RoleDto> getRoleList(final RoleDto aDto) {
        final String hql = getQuerySql(aDto);
        final Query query = this.getSession().createQuery(hql.toString());
        query.setFirstResult((aDto.getGotoPage() - 1) * aDto.getPageSize());
        query.setMaxResults(aDto.getPageSize());
        return converToDtoList(query.list());
    }

    private String getQuerySql(final RoleDto aDto) {
        final StringBuffer hql = new StringBuffer();
        hql.append(" from Role where 1 = 1 ");
        if (aDto.getRecorderTypeId() != null) {
            hql.append(" and recorderTypeId = " + aDto.getRecorderTypeId());
        }
        return hql.toString();
    }

    private List<RoleDto> converToDtoList(final List<Role> entityList) {
        final List<RoleDto> dtoList = new ArrayList<RoleDto>();
        for (int i = 0; i < entityList.size(); i++) {
            if (entityList.get(i) != null) {
                final RoleDto role = new RoleDto();
                BeanUtils.copyProperties(entityList.get(i), role);
                dtoList.add(role);
            }
        }
        return dtoList;
    }

    public int getRoleListCount(final RoleDto aDto) {
        final String hql = getQuerySql(aDto);
        final Query query = this.getSession().createQuery(hql);

        return query.list().size();
    }

}
