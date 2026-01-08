package com.eprocure.project.entity;

/**
 * Enumeration representing the status of a procurement project.
 */
public enum ProjectStatus {
    /**
     * Project is in draft state - not yet started
     */
    DRAFT,

    /**
     * Project is active and currently in progress
     */
    ACTIVE,

    /**
     * Project has been completed successfully
     */
    COMPLETED
}
