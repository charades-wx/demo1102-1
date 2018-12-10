package com.qf.service.impl;

import com.qf.dao.SysLogMapper;
import com.qf.entity.SysLog;
import com.qf.entity.SysLogExample;
import com.qf.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;


    @Override
    public int countByExample(SysLogExample example) {
        return 0;
    }

    @Override
    public int deleteByExample(SysLogExample example) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(SysLog record) {
        return 0;
    }

    @Override
    public int insertSelective(SysLog record) {
        return 0;
    }

    @Override
    public List<SysLog> selectByExample(SysLogExample example) {
        return null;
    }

    @Override
    public SysLog selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByExampleSelective(SysLog record, SysLogExample example) {
        return 0;
    }

    @Override
    public int updateByExample(SysLog record, SysLogExample example) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(SysLog record) {
        return 0;
    }

    @Override
    public void saveSysLog(SysLog sysLog) {
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public int updateByPrimaryKey(SysLog record) {
        return 0;
    }
}
