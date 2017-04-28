// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.youthen.framework.common.exception.DuplicateKeyException;
import com.youthen.framework.common.exception.ObjectNotFoundException;
import com.youthen.framework.util.BeanUtils;
import com.youthen.master.persistence.dao.DemoTestDao;
import com.youthen.master.persistence.entity.DemoTest;
import com.youthen.master.service.DemoTestService;
import com.youthen.master.service.dto.DemoTestDto;

/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
@Service(value = "demoTestService")
@Transactional(rollbackFor = Throwable.class)
public class DemoTestServiceImpl implements DemoTestService {

    @Autowired
    DemoTestDao testDao;

    /**
     * @see com.youthen.master.service.DemoTestService#list()
     */

    @Override
    public List<DemoTestDto> list() {
        try {
            final List<DemoTest> lst = this.testDao.selectAll();
            final List<DemoTestDto> dtoList = new ArrayList<DemoTestDto>();

            for (final DemoTest entity : lst) {
                final DemoTestDto dto = new DemoTestDto();
                BeanUtils.copyAllNullableProperties(entity, dto);
                dtoList.add(dto);
            }

            return dtoList;

        } catch (final ObjectNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @see com.youthen.master.service.DemoTestService#add(com.youthen.master.service.dto.DemoTestDto)
     */

    @Override
    @Transactional
    public Long add(final DemoTestDto aDto) {
        final DemoTest entity = new DemoTest();
        BeanUtils.copyAllNullableProperties(aDto, entity);
        try {
            return (Long) this.testDao.insert(entity);
        } catch (final DuplicateKeyException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @see com.youthen.master.service.DemoTestService#getById(java.lang.Long)
     */

    @Override
    public DemoTestDto getById(final Long aId) {
        final DemoTest entity = this.testDao.getById(aId);
        final DemoTestDto dto = new DemoTestDto();
        BeanUtils.copyAllNullableProperties(entity, dto);
        return dto;
    }

    /**
     * @see com.youthen.master.service.DemoTestService#edit(com.youthen.master.service.dto.DemoTestDto)
     */

    @Override
    public void edit(final DemoTestDto aDto) {
        final DemoTest entity = this.testDao.getById(aDto.getId());
        BeanUtils.copyAllNullableProperties(aDto, entity);
        this.testDao.update(entity);
    }

}
