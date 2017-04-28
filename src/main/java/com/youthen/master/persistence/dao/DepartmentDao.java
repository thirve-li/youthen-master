package com.youthen.master.persistence.dao;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.master.persistence.entity.Department;

/**
 * 。
 * 
 * @author DYZ
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Repository("departmentDao")
public class DepartmentDao extends EntityDaoImpl<Department> {

    /**
     * @see com.soltoris.sisqp.framework.persistence.dao.impl.EntityDaoImpl#injectType()
     */

    @Override
    @PostConstruct
    public void injectType() {
        this.setType(Department.class);
    }

    public List<Department> getByParentId(final Long parentId) {
        return this.getByHql("from " + this.clazz.getName() + " where parentDepartmentId=?", new Long[] {parentId});
    }

    public List<Department> getByCompanyId(final Long aCompanyId) throws ObjectNotFoundException {
        if (aCompanyId == null) {
            return this.selectAll();
        }
        return this.getByHql("from " + this.clazz.getName() + " where companyId=? and status=1",
                new Long[] {aCompanyId});
    }

    public List<Department> getChildrenByCompanyId(final Long aCompanyId) {
        return this.getByHql("from " + this.clazz.getName()
                + " where companyId=? and parentDepartmentId = 0 ",
                new Long[] {aCompanyId});
    }

    @SuppressWarnings("unchecked")
    public List<Department> getByDepartmentName(final String name) {
        return this.getByHql("from " + this.clazz.getName() + " where name like '%" + name + "%'");
    }
}
