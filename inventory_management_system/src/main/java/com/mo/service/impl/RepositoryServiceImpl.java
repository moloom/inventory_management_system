package com.mo.service.impl;

import com.mo.mapper.RepositoryMapper;
import com.mo.pojo.Employee;
import com.mo.pojo.Repository;
import com.mo.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private RepositoryMapper repositoryMapper;

    /**
     * 插入一条 repository
     *
     * @param repository
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int insertRepository(Repository repository) {
        return repositoryMapper.insertRepository(repository);
    }

    /**
     * 查找所有的 Repository
     *
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Repository> findAllRepository() {
        return repositoryMapper.findAllRepository();
    }

    /**
     * 删除一条 Repository
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int deleteRepository(Integer id) {
        return repositoryMapper.deleteRepository(id);
    }

    /**
     * 修改一条 Repository
     *
     * @param repository
     * @return
     */
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED, timeout = 15)
    @Override
    public int updateRepository(Repository repository) {
        return repositoryMapper.updateRepository(repository);
    }

    /**
     * 查询一条 Repository
     * 条件：id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public Repository findOneRepositoryById(Integer id) {
        return repositoryMapper.findOneRepositoryById(id);
    }

    /**
     * 查询 Repository 集合
     * 条件：name
     *
     * @param name
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Repository> findRepositoryListByName(String name) {
        return repositoryMapper.findRepositoryListByName(name);
    }

    /**
     * 查询员工
     * 条件：duty_id
     *
     * @param duty_id
     * @return
     */
    @Transactional(readOnly = true, timeout = 15)
    @Override
    public List<Employee> findEmployeeByDuty_id(Integer duty_id) {
        return repositoryMapper.findEmployeeByDuty_id(duty_id);
    }

}
