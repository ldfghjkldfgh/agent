package ru.mirea.agency.service.impl;

import org.springframework.stereotype.Service;
import ru.mirea.agency.db.model.Property;
import ru.mirea.agency.db.model.PropertyType;
import ru.mirea.agency.db.dto.PropertyForm;
import ru.mirea.agency.db.repository.PropertyRepository;
import ru.mirea.agency.db.repository.PropertyTypeRepository;
import ru.mirea.agency.service.PropertyService;

import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    private final PropertyTypeRepository propertyTypeRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository, PropertyTypeRepository propertyTypeRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyTypeRepository = propertyTypeRepository;
    }

    public Property createProperty(PropertyForm propertyForm) {
        Optional<PropertyType> propertyType = propertyTypeRepository.findById(propertyForm.getPropertyTypeId());
        if (propertyType.isEmpty()) {
            return null;
        }
        Property property = new Property();
        property.setArea(propertyForm.getArea());
        property.setType(propertyType.get());
        propertyRepository.save(property);
        return property;
    }

    public Property updateProperty(Property property) {
        return propertyRepository.save(property);
    }
}
