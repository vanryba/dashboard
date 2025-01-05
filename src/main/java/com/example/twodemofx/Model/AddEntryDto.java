package com.example.twodemofx.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddEntryDto {
    public String name;
    public String valueEntry;
}
