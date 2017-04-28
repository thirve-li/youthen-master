// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.logic.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.youthen.framework.common.annotation.BusinessLogic;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.logic.KbnLogic;
import com.youthen.master.persistence.dao.ArticleDao;
import com.youthen.master.persistence.dao.KbnDao;
import com.youthen.master.persistence.entity.Article;
import com.youthen.master.persistence.entity.Kbn;
import com.youthen.master.service.dto.KbnDto;

/**
 * 。
 * 
 * @author LiXin
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@BusinessLogic("kbnLogic")
public class KbnLogicImpl implements KbnLogic {

    @Autowired
    ArticleDao articleDao;

    @Resource
    private KbnDao kbnDao;

    /**
     * @see com.youthen.master.logic.KbnLogic#getKbn(java.lang.String, java.lang.String)
     */

    @Override
    public KbnDto getKbn(final String aType, final String aCode) {
        final Kbn kbn = this.kbnDao.getKbn(aType, aCode);
        final KbnDto dto = new KbnDto();
        BeanUtils.copyAllProperties(kbn, dto);
        return dto;
    }

    /**
     * @see com.youthen.master.logic.KbnLogic#getKbnListByType(java.lang.String)
     */

    @Override
    public List<KbnDto> getKbnListByType(final String aType) {
        final List<Kbn> lst = this.kbnDao.getKbnListByType(aType);

        final List<KbnDto> result = new ArrayList<KbnDto>();
        for (final Kbn kbn : lst) {
            final KbnDto dto = new KbnDto();
            BeanUtils.copyAllProperties(kbn, dto);
            final List<Kbn> childList = this.kbnDao.getByHql("from Kbn where parentTypeId =" + kbn.getId());
            dto.setChildKbnList(childList);

            final List<Article> articleList =
                    this.articleDao.getByPage("from Article where bigColumnId=" + kbn.getId()
                            + "and status=1 order by createTime desc", 1, 5);
            dto.setArticleKbnList(articleList);
            result.add(dto);
        }
        return result;
    }

    /**
     * @see com.youthen.master.logic.KbnLogic#getKbnList(java.lang.String, java.lang.String)
     */

    @Override
    public List<KbnDto> getBigColum() {
        final List<Kbn> lst = this.kbnDao.getByHql("from Kbn where type='OA_BIG_MENU' and code not in ('GSXW','BMLF')");

        final List<KbnDto> result = new ArrayList<KbnDto>();
        for (final Kbn kbn : lst) {
            final KbnDto dto = new KbnDto();
            BeanUtils.copyAllProperties(kbn, dto);
            final List<Kbn> childList =
                    this.kbnDao.getByHql("from Kbn where parentTypeId = " + kbn.getId());
            dto.setChildKbnList(childList);

            final List<Article> articleList =
                    this.articleDao.getByPage("from Article where bigColumnId=" + kbn.getId()
                            + "and status=1 order by createTime desc,updTime desc", 1, 5);
            dto.setArticleKbnList(articleList);
            result.add(dto);
        }
        return result;
    }

    /**
     * @see com.youthen.master.logic.KbnLogic#getBigXiaoQuColum()
     */

    @Override
    public List<KbnDto> getBigXiaoQuColum() {
        final List<Kbn> lst = this.kbnDao.getByHql("from Kbn where type='XIAOQU'");
        final List<KbnDto> result = new ArrayList<KbnDto>();
        for (final Kbn kbn : lst) {
            final KbnDto dto = new KbnDto();
            BeanUtils.copyAllProperties(kbn, dto);
            final List<Kbn> childList =
                    this.kbnDao.getByHql("from Kbn where parentTypeId = " + kbn.getId());
            dto.setChildKbnList(childList);
            result.add(dto);
        }
        return result;
    }

    /**
     * @see com.youthen.master.logic.KbnLogic#getKbnListBy(com.youthen.master.service.dto.KbnDto)
     */
    @Override
    public List<KbnDto> getKbnListBy(final KbnDto aDto) {
        final Kbn kbn = new Kbn();
        BeanUtils.copyAllProperties(aDto, kbn);
        final List<Kbn> kbnList = this.kbnDao.getKbnListBy(kbn);

        final List<KbnDto> result = new ArrayList<KbnDto>();
        for (final Kbn tmp : kbnList) {
            final KbnDto dto = new KbnDto();
            BeanUtils.copyAllProperties(tmp, dto);
            result.add(dto);
        }
        return result;
    }

    /**
     * @see com.youthen.master.logic.KbnLogic#getById(java.lang.Long)
     */

    @Override
    public KbnDto getById(final Long aId) {
        final KbnDto dto = new KbnDto();
        final Kbn kbn = this.kbnDao.getById(aId);
        BeanUtils.copyAllNullableProperties(kbn, dto);
        return dto;
    }

    /**
     * @see com.youthen.master.logic.KbnLogic#getByHql()
     */

    @Override
    public List<KbnDto> getByParentTypeId(final Long id) {

        final String hql = "from Kbn where status=1 and parentTypeId='" + id + "' ";
        final List<Kbn> list = this.kbnDao.getByHql(hql);
        final ArrayList<KbnDto> result = new ArrayList<KbnDto>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (final Kbn entity : list) {
                final KbnDto dto = new KbnDto();
                BeanUtils.copyAllNullableProperties(entity, dto);
                result.add(dto);
            }
        }

        return result;
    }

}
