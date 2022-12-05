package com.refapps.trippin.customizer;

import com.refapps.trippin.model.complex.EventLocation;
import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

public class LocationCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
        descriptor.getInheritancePolicy().setClassIndicatorFieldName("STATUS_TYPE");
        descriptor.getInheritancePolicy().addClassIndicator(EventLocation.class, "E");
    }
}
