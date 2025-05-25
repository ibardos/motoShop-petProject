import { useContext } from "react";
import { camelCaseToSentenceCase } from "../../../util/camelCaseToSentenceCase";
import { AuthenticationContext } from "../../../security/authenticationProvider";

const HIDDEN_FIELDS = ['firstName', 'lastName'];

const TableTitles = ({ originalData }) => {
    const { userPermissions } = useContext(AuthenticationContext);

    const renderTableHeaders = () => {
        return Object.keys(originalData[0])
            .filter((title) => !HIDDEN_FIELDS.includes(title))
            .map((title) => {
                const displayName = title === 'orderIds' ? 'Orders' : camelCaseToSentenceCase(title);
                return <th key={title}>{displayName}</th>;
            });
    };

    const showOptionsColumn = userPermissions.includes('Update') || userPermissions.includes('Delete');

    return (
        <>
            {renderTableHeaders()}
            {showOptionsColumn && <th key="options">Options</th>}
        </>
    );
};

export default TableTitles;