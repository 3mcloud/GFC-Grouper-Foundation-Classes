package com.mmm.his.cer.foundation.demo;

import com.mmm.his.cer.foundation.Release;

public enum DemoRelease implements Release {

	/**
	 * Test loading from the legacy package
	 */
    LEGACY("legacy"),
    
    
    _08_0_0("v08.0.0"),
    
    /**
     * Test the loading a simple value-versioned 
     * component
     */
    _09_0_0("v09.0.0"),
    
    /**
     * Test the extension of a component
     */
    _10_0_0("v10.0.0"),
    _11_0_0("v11.0.0"),
    _12_0_0("v12.0.0");
    
    private String value;
    private DemoRelease(String value) { this.value = value; }
    @Override
    public String getPackageValue() { return value.replaceAll("\\.", ""); }
    @Override
    public String getConfigurationValue() { return value.replace("v", ""); }
}
