package com.SN.qa.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.SN.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{


    @Query(value = "select * from tb_problem,tb_pl where tb_pl.problemid=tb_problem.id and labelid=? order by replytime desc",nativeQuery = true)
    public Page<Problem> newList(String labelid, Pageable pageable);

    @Query(value = "select * from tb_problem,tb_pl where tb_pl.problemid=tb_problem.id and labelid=? order by reply desc",nativeQuery = true)
    public Page<Problem> hotList(String labelid,Pageable pageable);

    @Query(value = "select * from tb_problem,tb_pl where tb_pl.problemid=tb_problem.id and labelid=? and reply=0 order by createtime desc",nativeQuery = true)
    public Page<Problem> waitList(String labelid,Pageable pageable);

}
