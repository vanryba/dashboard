package com.example.twodemofx.Service;

import com.example.twodemofx.Model.EntryItemDto;
import com.example.twodemofx.Model.InterfaceItemDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RebootService {

    public String getTime() {
        return new FormatterService().timeFormat(LocalDateTime.now().toLocalTime().toString());
    }

    public String getDate() {
        return new FormatterService().dateFormat(LocalDate.now().toString());
    }

    public List<EntryItemDto> getEntryList() {
        return new DatabaseManagerService().getEntryItems();
    }

    public String getTemperature() {
        return new WeatherService().getTemperature();
    }

    public InterfaceItemDto getItems() {
        new DatabaseManagerService().createTable();
        return InterfaceItemDto.builder()
                .date(getDate())
                .time(getTime())
                .nameEntry(getEntryList())
                .temperature(getTemperature())
                .build();
    }
}
