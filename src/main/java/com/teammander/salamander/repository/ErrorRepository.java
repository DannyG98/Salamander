package com.teammander.salamander.repository;

import java.util.List;

import com.teammander.salamander.map.DataError;
import com.teammander.salamander.map.ErrorType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepository extends JpaRepository<DataError, Integer> {
    List<DataError> findAllByResolved(boolean status);
    List<DataError> findAllByEType(ErrorType type);
    List<DataError> findAllByAffectedState(String stateName);
    List<DataError> findAllByAffectedDistrict(String districtName);
    List<DataError> findAllByAffectedPrct(String precinctName);

    List<DataError> findAllByAffectedStateAndResolved(String stateName, boolean status);
    List<DataError> findAllByAffectedDistrictAndResolved(String districtName, boolean status);
    List<DataError> findAllByAffectedPrecinctAndResolved(String precinctName, boolean status);
}
