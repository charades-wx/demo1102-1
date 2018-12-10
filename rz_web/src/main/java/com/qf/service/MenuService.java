package com.qf.service;



import com.qf.entity.SysMenu;
import com.qf.util.R;
import com.qf.util.TableResult;

import java.util.List;

public interface MenuService {

    TableResult findMenu(int offset, int limit, String search);

    R del(List<Long> ids);

    R getupmenu();

    R insertSelective(SysMenu menu);

    R findById(long menuId);

    R updatemenu(SysMenu menu);

    R getmenu(Long userId);

    List<String> findPermsByUserId(Long userId);
}
