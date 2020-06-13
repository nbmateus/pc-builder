package com.nbmateus.pcbuilder.dto;

import com.nbmateus.pcbuilder.model.Socket;

public class CpuDto {
    private Socket socket;
    private boolean unlockedMultiplier;

    public CpuDto() {
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isUnlockedMultiplier() {
        return unlockedMultiplier;
    }

    public void setUnlockedMultiplier(boolean unlockedMultiplier) {
        this.unlockedMultiplier = unlockedMultiplier;
    }
}