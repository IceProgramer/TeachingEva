package com.itmo.teachingeva.dto;

import com.itmo.teachingeva.entity.Admin;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminDto extends Admin {

    private List<Admin> adminList = new ArrayList<>();

    private String token;
}
