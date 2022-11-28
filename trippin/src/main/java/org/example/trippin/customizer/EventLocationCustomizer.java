package org.example.trippin.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.example.trippin.model.complex.EventLocation;
import org.example.trippin.model.complex.Location;

public class EventLocationCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
        descriptor.getInheritancePolicy().setParentClass(Location.class);;
    }
}
