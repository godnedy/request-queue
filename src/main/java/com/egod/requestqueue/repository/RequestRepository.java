package com.egod.requestqueue.repository;

import com.egod.requestqueue.domain.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestRepository extends CrudRepository <Request, Long>{

}
