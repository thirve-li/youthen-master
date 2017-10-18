package com.youthen.master.persistence.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.youthen.framework.common.StringUtils;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.persistence.dao.impl.EntityDaoImpl;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.service.dto.LoginUserDto;

@Repository("loginUserDao")
public class LoginUserDao extends EntityDaoImpl<LoginUser> {

    /**
     * @see com.youthen.framework.persistence.dao.impl.EntityDaoImpl#injectType()
     */

    @Override
    @PostConstruct
    public void injectType() {
        this.setType(LoginUser.class);
    }

    @SuppressWarnings("unchecked")
    public List<LoginUser> selectUserByCompanyId(final Long companyId) {
        final String hql =
                "from LoginUser where company.id = '" + companyId.longValue() + "'";
        return this.getByHql(hql);
    }

    @SuppressWarnings("unchecked")
    public List<LoginUser> selectUserByDepartmentId(final Long departmentId) {
        final String hql =
                "from LoginUser where department.id = '" + departmentId.longValue() + "'";//
        return this.getByHql(hql);
    }

    @SuppressWarnings("unchecked")
    public List<LoginUser> selectBackUser(final Long companyId, final Long departmentId) {
        final StringBuffer hql = new StringBuffer();
        hql.append(" from LoginUser user where 1 = 1 ");
        // hql.append(" where user.userId = userRole.user.userId   ");
        // hql.append(" and userRole.role.code ='BACKUP_USER' ");
        //
        if (companyId != null) {
            hql.append(" and user.company.id = '" + companyId.longValue() + "'");
        }

        if (departmentId != null) {
            hql.append(" and user.department.id = '" + departmentId.longValue() + "'");
        }

        hql.append(" and user.status.code  = 'NORMAL' ");

        return this.getByHql(hql.toString());
    }

    public int getUserListCount(final LoginUserDto userDto) {
        final String hql = getQuerySql(userDto);
        final Query query = this.getSession().createQuery(hql);

        return query.list().size();
    }

    @SuppressWarnings("unchecked")
    public List<LoginUser> getUserByRoleCode(final String roleCode) {
        final String hql =
                "select distinct user from LoginUser user, Role role  where user.status.code<>'DELETED' and user in elements(role.users) and role in elements(user.roles) and role.code='"
                        + roleCode + "'";
        final Query query = this.getSession().createQuery(hql);
        return query.list();
    }

    public boolean isExceccedMaxNumUser() {
        final String hql = "from LoginUser where status.code != 'DELETED' and parentUser is null";
        final Query query = this.getSession().createQuery(hql);
        final int cnt = query.list().size();
        if (cnt > 1200) {
            return true;
        }
        return false;
    }

    // 分页查询
    public List<LoginUserDto> getUserList(final LoginUserDto userDto) {
        final String hql = getQuerySql(userDto);
        final Query query = this.getSession().createQuery(hql.toString());
        query.setFirstResult((userDto.getGotoPage() - 1) * userDto.getPageSize());
        query.setMaxResults(userDto.getPageSize());
        return converToDtoList(query.list());
    }

    private String getQuerySql(final LoginUserDto userDto) {
        final StringBuffer hql = new StringBuffer();
        hql.append("select distinct user from LoginUser user where 1=1  ");

        if (userDto.getCompanyId() != null) {
            hql.append(" and user.company.id = " + userDto.getCompanyId());
        }

        if (StringUtils.isNotEmpty(userDto.getMobile())) {
            hql.append(" and user.mobile = '" + userDto.getMobile() + "'");
        }

        if (StringUtils.isNotEmpty(userDto.getSex())) {
            hql.append(" and user.sex = '" + userDto.getSex() + "'");
        }

        if (userDto.getScore() != null) {
            hql.append(" and user.score = '" + userDto.getScore() + "'");
        }

        if (userDto.getDepartmentId() != null) {
            hql.append(" and user.department.id = " + userDto.getDepartmentId());
        }

        if (StringUtils.isNotEmpty(userDto.getName())) {
            hql.append(" and ( user.name like '%" + userDto.getName() + "%' or user.userId like '%" + userDto.getName()
                    + "%') ");
        }

        if (userDto.getRoleId() != null) {
            hql.append(" and " + userDto.getRoleId() + " in ( user.roles )");
        }

        if (StringUtils.isNotEmpty(userDto.getStatusId())) {
            hql.append(" and user.status.id = '" + userDto.getStatusId() + "'");
        }

        return hql.toString();
    }

    /**
     * 根据手机查找用户
     * 
     * @param mobile 手机号
     * @return LoginUser
     */
    public LoginUser getByMobile(final String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }

        final String hql = "from LoginUser where mobile=?";
        final Object[] param = {mobile};
        final List<LoginUser> userlist = this.getByHql(hql, param);
        LoginUser user = null;
        if (userlist.size() > 0) {
            user = new LoginUser();
            user = userlist.get(0);
        }
        return user;

    }

    /**
     * 根据openId查找用户
     * 
     * @param mobile 手机号
     * @return LoginUser
     */
    public LoginUser getByOpenId(final String openId) {
        if (StringUtils.isEmpty(openId)) {
            return null;
        }

        final String hql = "from LoginUser where openId=?";
        final Object[] param = {openId};
        final List<LoginUser> userlist = this.getByHql(hql, param);
        LoginUser user = null;
        if (userlist.size() > 0) {
            user = new LoginUser();
            user = userlist.get(0);
        }
        return user;

    }

    private List<LoginUserDto> converToDtoList(final List<LoginUser> entityList) {
        final List<LoginUserDto> dtoList = new ArrayList<LoginUserDto>();

        if (entityList != null) {
            for (int i = 0; i < entityList.size(); i++) {
                if (entityList.get(i) != null) {
                    final LoginUserDto d = new LoginUserDto();
                    BeanUtils.copyProperties(entityList.get(i), d);
                    dtoList.add(d);
                }
            }
        }
        return dtoList;
    }

    public LoginUser getUserByUserId(final String aUserId) {
        return this.getById(aUserId);
    }

    public LoginUser getUserByName(final String aUserName) {
        final List<LoginUser> userlist = this.getByHql("from LoginUser where name='" + aUserName + "'");
        LoginUser user = new LoginUser();
        if (userlist.size() > 0) {
            user = userlist.get(0);
        }
        return user;

    }

    public List<LoginUser> getAvailableUser() throws ObjectNotFoundException {
        final List<LoginUser> userlist =
                this.getByHql("from LoginUser where status.code <> 'DELETED' and status.code <> 'HOLIDAY')");
        return userlist;

    }
}
