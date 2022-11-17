package ru.mirea.agency.service;

import ru.mirea.agency.db.model.Property;
import ru.mirea.agency.db.dto.PropertyForm;

public interface PropertyService {
    Property createProperty(PropertyForm form);
    Property updateProperty(Property property);
}
