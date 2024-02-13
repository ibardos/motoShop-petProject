import {camelCaseToSentenceCase} from "../../../util/camelCaseToSentenceCase";


const TableTitles = (props) => {
    return (
        <>
            {Object.keys(props.originalData[0]).map(tableTitle => (
                <th key={tableTitle}>{camelCaseToSentenceCase(tableTitle)}</th>
            ))}
            <th key="options">Options</th>
        </>
    )
}

export default TableTitles;
