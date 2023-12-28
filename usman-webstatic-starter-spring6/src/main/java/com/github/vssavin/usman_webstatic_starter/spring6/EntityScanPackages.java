package com.github.vssavin.usman_webstatic_starter.spring6;

import java.util.function.Supplier;

/**
 * Supplier implementation with entity packages to scan.
 *
 * @author vssavin on 28.12.2023.
 */
class EntityScanPackages implements Supplier<String[]> {

    private final String[] packagesToScan;

    public EntityScanPackages(String... packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    @Override
    public String[] get() {
        return packagesToScan;
    }

}
