package com.richardduclos.tierlist.exceptions;

import java.util.UUID;

public class MvcEntityNotFoundException extends RuntimeException {
    public MvcEntityNotFoundException(Integer id) {
        super("Could not find entity " + id);
    }

    public MvcEntityNotFoundException(UUID id) {
        super("Could not find entity " + id);
    }
}
