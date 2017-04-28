// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.DateFormatUtils;
import com.youthen.framework.common.context.SessionContext;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.persistence.dao.ArticleDao;
import com.youthen.master.persistence.dao.LoginUserDao;
import com.youthen.master.persistence.entity.Article;
import com.youthen.master.persistence.entity.LoginUser;
import com.youthen.master.service.ArticleService;
import com.youthen.master.service.LoginUserService;
import com.youthen.master.service.dto.ArticleDto;
import com.youthen.master.service.dto.LoginUserDto;
import com.youthen.master.util.CommonUtil;

/**
 * 。
 * 
 * @author XS
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Autowired
    LoginUserDao loginUserDao;

    @Autowired
    LoginUserService loginUserService;

    /**
     * @see com.youthen.master.service.ArticleService#getArticleList(com.youthen.master.service.dto.ArticleDto, boolean)
     */

    @SuppressWarnings("static-access")
    @Override
    public List<ArticleDto> getArticleList(final ArticleDto aDto) {
        final CommonUtil cu = new CommonUtil();
        final LoginUser loginUser = this.loginUserService.getUserByUserId(SessionContext.getUser().getUserId());

        String hql = "from Article where 1=1 ";

        if (!loginUser.hasRole("ADMIN")) {
            hql += " and status = 1";
        }

        final String orderByString = " order by status asc, createTime desc";
        if (aDto.getBigColumnId() != null) {
            hql += " and bigColumnId = '" + aDto.getBigColumnId() + "'";
        }

        if (aDto.getSmallColumnId() != null) {
            hql += " and smallColumnId = '" + aDto.getSmallColumnId() + "'";
        }

        if (aDto.getCreateTime() != null) {
            hql += " and createTime ='" + cu.dateToStrLong(aDto.getCreateTime()) + "'";
        }

        if (aDto.getTitle() == null) {

            hql += " ";
        }

        if (aDto.getTitle() != null) {

            hql += " and title like '%" + aDto.getTitle() + "%'";
        }

        if (aDto.getStatus() != null) {
            hql += " and status = '" + aDto.getStatus() + "'";
        }

        hql += orderByString;

        final List<Article> list =
                this.articleDao.getByPage(hql, aDto.getGotoPage(), aDto.getPageSize());
        final ArrayList<ArticleDto> result = new ArrayList<ArticleDto>();

        if (CollectionUtils.isNotEmpty(list)) {
            for (final Article entity : list) {
                final ArticleDto dto = new ArticleDto();
                BeanUtils.copyAllNullableProperties(entity, dto);
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * @see com.youthen.master.service.ArticleService#getArticleCount(com.youthen.master.service.dto.ArticleDto,
     *      boolean)
     */

    @SuppressWarnings("static-access")
    @Override
    public int getArticleCount(final ArticleDto aDto) {
        final CommonUtil cu = new CommonUtil();
        final LoginUser loginUser = this.loginUserService.getUserByUserId(SessionContext.getUser().getUserId());

        String hql = "from Article where 1=1 ";
        if (!loginUser.hasRole("ADMIN")) {
            hql += " and status=1 ";
        }

        if (aDto.getBigColumnId() != null) {
            hql += " and bigColumnId = '" + aDto.getBigColumnId() + "'";
        }

        if (aDto.getSmallColumnId() != null) {
            hql += " and smallColumnId = '" + aDto.getSmallColumnId() + "'";
        }

        if (aDto.getCreateTime() != null) {
            hql += " and createTime ='" + cu.dateToStrLong(aDto.getCreateTime()) + "'";
        }

        if (aDto.getTitle() == null) {

            hql += " ";
        }

        if (aDto.getTitle() != null) {

            hql += " and title like '%" + aDto.getTitle() + "%'";
        }

        final String orderByString = " order by status asc, createTime desc";

        if (aDto.getStatus() != null) {
            hql += " and status = '" + aDto.getStatus() + "'";
        }

        hql += orderByString;

        return this.articleDao.getCount(hql);
    }

    /**
     * @see com.youthen.master.service.ArticleService#insert(com.youthen.master.service.dto.ArticleDto)
     */

    @Override
    @Transactional
    public Long insert(final ArticleDto articleDto) throws DuplicateKeyException {

        try {
            final Article article = new Article();
            BeanUtils.copyAllNullableProperties(articleDto, article);
            article.setStatus(1);
            article.setCreateTime(new Date());
            final LoginUserDto createrDto = this.loginUserService.getById(SessionContext.getUser().getUserId());
            final LoginUser creater = new LoginUser();
            BeanUtils.copyAllNullableProperties(createrDto, creater);
            article.setCreater(creater);

            return (Long) this.articleDao.insert(article);

        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @see com.youthen.master.service.ArticleService#getById(java.lang.Long)
     */

    @Override
    public ArticleDto getById(final Long aId) {
        final ArticleDto dto = new ArticleDto();
        BeanUtils.copyAllProperties(this.articleDao.getById(aId), dto);
        dto.setUpdTime(DateFormatUtils.format("yyyy-MM-dd HH:mm:ss", new Date()));
        BeanUtils.setNAProperty(dto);
        return dto;
    }

    /**
     * @see com.youthen.master.service.ArticleService#update(com.youthen.master.service.dto.ArticleDto)
     */

    @Override
    @Transactional
    public ArticleDto update(final ArticleDto aDto) throws DuplicateKeyException {
        final Article e = this.articleDao.getById(aDto.getId());
        BeanUtils.copyNullableProperties(aDto, e);
        this.articleDao.update(e);
        BeanUtils.copyProperties(e, aDto);
        return aDto;
    }

    /**
     * @see com.youthen.master.service.ArticleService#getUserById(java.lang.String)
     */

    @Override
    public LoginUser getUserById(final String aUserId) {
        return this.loginUserDao.getById(aUserId);
    }

    /**
     * @see com.youthen.master.service.ArticleService#getArticleTitle(java.lang.String)
     */

    @SuppressWarnings("unchecked")
    @Override
    public List<ArticleDto> getArticleTitle(final String aTitle) {
        final LoginUser loginUser = this.loginUserService.getUserByUserId(SessionContext.getUser().getUserId());

        String hql = "from Article where title like '%" + aTitle + "%'";
        if (!loginUser.hasRole("ADMIN")) {
            hql += " and status=1 ";
        }

        final String orderByString = " order by status asc, createTime desc";

        hql += orderByString;

        final List<Article> list =
                this.articleDao.getByPage(hql, 1, 15);
        final ArrayList<ArticleDto> result = new ArrayList<ArticleDto>();

        if (CollectionUtils.isNotEmpty(list)) {
            for (final Article entity : list) {
                final ArticleDto dto = new ArticleDto();
                BeanUtils.copyAllNullableProperties(entity, dto);
                result.add(dto);
            }
        }

        return result;
    }

}
