/**
 * Identifies an object of type motorcycleModel from a collection, based on a given String expression (manufacturer.name - model.name)
 * by checking the expression against the "constructed" names of the objects.
 *
 * Dropdown (<option>) shows "manufacturer.name + motorcycleModel.name" to client, to enhance user experience,
 * therefore have to find the right motorcycleModel based on these String values only, among existing objects.
 *
 * @param motorcycleModelsCollection set of motorcycleModel objects currently available in DB
 * @param motorcycleModelNameFromDropdown that was chosen by the client as "manufacturer.name + motorcycleModel.name"
 * @returns object of type motorcycleModel if there is a match, otherwise undefined
 */
export function identifyMotorcycleModelObject(motorcycleModelsCollection, motorcycleModelNameFromDropdown) {
    for (const motorcycleModelObject of motorcycleModelsCollection) {
        if (motorcycleModelNameFromDropdown === motorcycleModelObject.manufacturer.name + " - " + motorcycleModelObject.modelName) {
            return motorcycleModelObject;
        }
    }

    return undefined;
}
