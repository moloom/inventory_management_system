package com.mo.service;

import com.mo.pojo.Employee;
import com.mo.pojo.Repository;

import java.util.List;

public interface RepositoryService {


    /**
     * 插入一条 repository
     *
     * @param repository
     * @return
     */
    public int insertRepository(Repository repository);

    /**
     * 查找所有的 repository
     *
     * @return
     */
    public List<Repository> findAllRepository();

    /**
     * 删除一条 repository
     *
     * @param id
     * @return
     */
    public int deleteRepository(Integer id);

    /**
     * 修改一条 repository
     *
     * @param repository
     * @return
     */
    public int updateRepository(Repository repository);

    /**
     * 查询一条 repository
     * 条件：id
     *
     * @param id
     * @return
     */
    public Repository findOneRepositoryById(Integer id);

    /**
     * 查询 Repository 集合
     * 条件：name
     *
     * @param name
     * @return
     */
    public List<Repository> findRepositoryListByName(String name);

    /**
     * 查询员工
     * 条件：duty_id
     *
     * @param duty_id
     * @return
     */
    public List<Employee> findEmployeeByDuty_id(Integer duty_id);

}
