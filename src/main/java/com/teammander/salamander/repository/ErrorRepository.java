package com.teammander.salamander.repository;

import com.teammander.salamander.map.DataError;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ErrorRepository {
    List<Error> errorList;

    public DataError findErrorById (int eid) {
        return null;
    }

    public void updateChanged() {
    }
}
