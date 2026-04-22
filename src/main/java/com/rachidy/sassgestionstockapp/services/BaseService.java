package com.rachidy.sassgestionstockapp.services;

import com.rachidy.sassgestionstockapp.common.PageResponse;

import java.util.List;

public interface BaseService <I,O>{

    void create(final I request);

    void update(final String id,final I request);

    PageResponse<O> findAll(final int page, final int size);

    O findById(final String id );

    void delete(final String id);
}
