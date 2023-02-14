import {Table} from "react-bootstrap";

import TableContent from "./TableContent";
import TableTitles from "./TableTitles";


const StripedTable = (props) => {
    return (
        <Table striped bordered hover variant="dark">
            <thead>
            <tr>
                <TableTitles originalData={props.originalData} />
            </tr>
            </thead>
            <TableContent filteredData={props.filteredData} setRecordId={props.setRecordId} setUpdateModalShow={props.setUpdateModalShow} setDeleteModalShow={props.setDeleteModalShow} />
        </Table>
    )
}

export default StripedTable;