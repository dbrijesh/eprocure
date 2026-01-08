package com.eprocure.project.exception;

import java.util.UUID;

/**
 * Exception thrown when a project is not found.
 */
public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(UUID id) {
        super("Project not found with id: " + id);
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
