package com.example.twodemofx.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EntryItemDto {
    public Integer id;
    public String name;
    public String valueEntry;
}
