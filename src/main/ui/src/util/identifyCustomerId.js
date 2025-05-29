/**
 * Extracts an id from a given String to identify a Customer's ID to be sent to back-end.
 *
 * Dropdown (<option>) shows "customer.fullName + customer.id" to client, to enhance user experience,
 * therefore have to extract the exact ID value to be able to send to the back-end during HTTP requests.
 *
 * @param customer String representation of a customer's full name and id, combined
 * @returns {number} the extracted ID of the Customer as a number
 */
export function identifyCustomerId(customer) {
    const colonIndex = customer.lastIndexOf(":");
    if (colonIndex === -1) {
        throw new Error("Invalid label format. Expected '... - id: <number>'");
    }

    const idString = customer.slice(colonIndex + 1).trim();
    const id = parseInt(idString, 10);

    if (isNaN(id)) {
        throw new Error("Extracted ID is not a valid number.");
    }

    return id;
}