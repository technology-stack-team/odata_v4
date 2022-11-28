package org.example.trippin.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.example.trippin.model.complex.EventLocation;

public class LocationCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
        descriptor.getInheritancePolicy().setClassIndicatorFieldName("STATUS_TYPE");
        descriptor.getInheritancePolicy().addClassIndicator(EventLocation.class, "E");
    }
}
