package com.example.twodemofx.Service;

public class FormatterService {

    public String dateFormat(String date) {
        return splitDate(date)[2] + "." + splitDate(date)[1] + "." + splitDate(date)[0];
    }

    public String timeFormat(String time) {
        return splitTime(time)[0] + ":" + splitTime(time)[1];
    }

    private String[] splitDate(String date) {
        return date.split("-");
    }

    private String[] splitTime(String time) {
        String[] split = time.split(":");
        if(split[0].length() < 2) {
            split[0] = "0" + split[0];
        }
        if(split[1].length() < 2) {
            split[1] = "0" + split[1];
        }
        return split;
    }
}
