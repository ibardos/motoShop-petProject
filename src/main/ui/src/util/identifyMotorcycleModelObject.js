/**
 * Identifies an object of type motorcycleModel from a collection, based on a given String expression by checking the
 * expression against the names of the objects.
 *
 * Dropdown (<option>) shows "manufacturer.name + motorcycleModel.name" to client, to enhance user experience,
 * therefore have to find the right motorcycleModel based on motorcycleModel.name only, among existing objects,
 * to be passed to back-end.
 *
 * @param motorcycleModelsCollection set of motorcycleModel objects currently available in DB
 * @param motorcycleModelNameFromDropdown that was chosen by the client as "manufacturer.name + motorcycleModel.name"
 * @returns object of type motorcycleModel if there is a match based on motorcycleModel.name, otherwise undefined
 */
export function identifyMotorcycleModelObject(motorcycleModelsCollection, motorcycleModelNameFromDropdown) {
    for (const motorcycleModelObject of motorcycleModelsCollection) {
        if (motorcycleModelNameFromDropdown.includes(motorcycleModelObject.modelName)) {
            return motorcycleModelObject;
        }
    }

    return undefined;
}
