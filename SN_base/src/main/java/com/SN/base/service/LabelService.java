package com.SN.base.service;

import com.SN.base.dao.LabelDao;
import com.SN.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService  {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Label label){
        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list=new ArrayList<>();
                if(label.getLabelname()!=null&&!"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");//labelname like "%小明%"
                    list.add(predicate);
                }
                if(label.getState()!=null&&!"".equals(label.getState())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());//state="1"
                    list.add(predicate);
                }
                //new一个数组作为最终返回值的条件
                Predicate[] parr=new Predicate[list.size()];
                //把list转换为数组
                list.toArray(parr);
                return cb.and(parr);//where labelname like "%小明%" and state="1"
            }
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        Pageable pageable=PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list=new ArrayList<>();
                if(label.getLabelname()!=null&&!"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");//labelname like "%小明%"
                    list.add(predicate);
                }
                if(label.getState()!=null&&!"".equals(label.getState())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());//state="1"
                    list.add(predicate);
                }
                //new一个数组作为最终返回值的条件
                Predicate[] parr=new Predicate[list.size()];
                //把list转换为数组
                list.toArray(parr);
                return cb.and(parr);//where labelname like "%小明%" and state="1"
            }
        },pageable);
    }
}
