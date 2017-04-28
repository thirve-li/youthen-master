// ============================================================
// Copyright(c) youthen Incorporated All Right Reserved.
// File: $Id$
// ============================================================

package com.youthen.master.service;

import java.util.List;
import com.youthen.master.service.dto.DemoTestDto;

/**
 * 。
 * 
 * @author PRO
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public interface DemoTestService {

    public List<DemoTestDto> list();

    public Long add(DemoTestDto dto);

    public DemoTestDto getById(Long id);

    public void edit(DemoTestDto dto);

}
