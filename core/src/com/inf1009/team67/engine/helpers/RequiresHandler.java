package com.inf1009.team67.engine.helpers;

import java.util.EnumSet;

public interface RequiresHandler {
    public EnumSet<HandleEnum> getRequiredHandles();
    public void setRequiredHandles(EnumSet<HandleEnum> requiredHandles);
    public void addRequiredHandle(HandleEnum requiredHandle);
    public void removeRequiredHandle(HandleEnum requiredHandle);
}
