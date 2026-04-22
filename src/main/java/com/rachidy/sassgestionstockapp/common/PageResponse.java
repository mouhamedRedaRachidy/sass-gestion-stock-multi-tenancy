package com.rachidy.sassgestionstockapp.common;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private List<T>content;
    private int size;
    private int page;
    private long totalElement;
    private int totalPage;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isNext;
    private boolean isLast;


    public static <T>PageResponse<T> of(final Page<T> page){
        return PageResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .isNext(page.hasNext())
                .isLast(page.isLast())
                .build();
    }

}
