package com.mo.mapper;

import com.mo.pojo.Employee;
import com.mo.pojo.Repository;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@org.springframework.stereotype.Repository
public interface RepositoryMapper {

    @Insert({"insert into repository(name,manager_id,address,telephone,create_by,create_time) " +
            "values(#{name},#{manager_id},#{address},#{telephone},#{create_by},now())"})
    public int insertRepository(Repository repository);

    @Select("select r.*,e.name as manager_name from Repository r,employee e where r.manager_id=e.id ")
    public List<Repository> findAllRepository();

    @Delete("delete from Repository where id=#{id}")
    public int deleteRepository(Integer id);

    @Select("select r.*,e.name as manager_name from Repository r,employee e where r.id=#{id} and r.manager_id=e.id")
    public Repository findOneRepositoryById(Integer id);

    public int updateRepository(Repository repository);

    public List<Repository> findRepositoryListByName(String name);

    @Select("select * from employee where duty_id=#{duty_id}")
    public List<Employee> findEmployeeByDuty_id(Integer duty_id);

}
