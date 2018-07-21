package com.michaelfmnk.aldrin.dtos.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageSortParams {
    private int offset = 0;
    private int limit = 10;
    private String sort = Strings.EMPTY;
    private boolean asc = false;
}
