package com.jean.brasaburguer.addon;

import com.jean.brasaburguer.common.NotFoundException;

public class AddonNotFoundException extends NotFoundException {
    private static final String MSG_ADDON_NOT_FOUND = "Addon with ID: %d not found";

    public AddonNotFoundException(Long addonId) {
        super(String.format(MSG_ADDON_NOT_FOUND, addonId));
    }
}
