import {useContext} from "react";

import {camelCaseToSentenceCase} from "../../../util/camelCaseToSentenceCase";
import {AuthenticationContext} from "../../../security/authenticationProvider";


const TableTitles = (props) => {
    const {userPermissions} = useContext(AuthenticationContext);

    return (
        <>
            {Object.keys(props.originalData[0]).map(tableTitle => (
                <th key={tableTitle}>{camelCaseToSentenceCase(tableTitle)}</th>
            ))}
            {(userPermissions.includes('Update') || userPermissions.includes('Delete')) && (
                <th key="options">Options</th>
            )}
        </>
    )
}

export default TableTitles;
