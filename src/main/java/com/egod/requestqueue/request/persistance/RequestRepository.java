package com.egod.requestqueue.request.persistance;

import com.egod.requestqueue.request.domain.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestRepository extends CrudRepository <Request, Long>{

}
