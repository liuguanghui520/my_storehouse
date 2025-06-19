package com.nep.service;

import com.nep.entity.Supervisor;
import com.nep.io.FileIO;

import java.util.ArrayList;
import java.util.List;

public interface SupervisorService {
    public abstract Supervisor login(String loginCode, String password);

    public abstract boolean register(Supervisor supervisor);
}
