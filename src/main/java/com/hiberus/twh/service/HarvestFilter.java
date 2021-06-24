package com.hiberus.twh.service;

import twitter4j.Status;

@FunctionalInterface
public interface HarvestFilter {
    boolean harvest(Status status);
}
