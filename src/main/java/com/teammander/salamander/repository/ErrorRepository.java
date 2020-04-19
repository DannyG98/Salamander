package com.teammander.salamander.repository;

import com.teammander.salamander.map.DataError;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ErrorRepository extends JpaRepository<DataError, Integer>{
    // List<Error> errorList;

    // public DataError findErrorById (int eid) {
    //     return null;
    // }

    // public void updateChanged() {
    // }
}
