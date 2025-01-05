package com.example.twodemofx.Model;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterfaceItemDto {
    public String time;
    public String date;
    public List<EntryItemDto> nameEntry;
    public String temperature;
}
