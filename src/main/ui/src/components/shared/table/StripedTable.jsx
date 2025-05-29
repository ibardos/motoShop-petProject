import {Table} from "react-bootstrap";

import TableContent from "./TableContent";
import TableTitles from "./TableTitles";


const StripedTable = (props) => {
    // Check for errors during fetch data. If there's any, show an error message.
    if (props.error) {
        console.error(`Error during fetchData. Error message: ${props.error.message}`)
        return (
            <div>
                <p>
                    Oops! Something went wrong while fetching data.
                </p>
                <p>
                    Please contact your IT support for assistance.
                </p>
            </div>
        );
    // If no errors, show "Loading..." while data is under fetching
    } else if (!props.isLoaded) {
        return <div style={{padding: "30px"}}>Loading...</div>;
    } else if (props.filteredData.length === 0) {
        return <div style={{padding: "30px"}}>No data.</div>;
    }


    return (
        <>
            <Table responsive striped bordered hover variant="dark">
                <thead>
                    <tr>
                        <TableTitles originalData={props.originalData}/>
                    </tr>
                </thead>
                <TableContent filteredData={props.filteredData}
                              setRecordId={props.setRecordId}
                              setUpdateModalShow={props.setUpdateModalShow}
                              setDeleteModalShow={props.setDeleteModalShow}
                              error={props.error}
                              isLoaded={props.isLoaded}
                              isSellButtonVisible={props.isSellButtonVisible}/>
            </Table>

            <p style={{textAlign: "center"}}>*Responsive table. Scroll horizontally if needed.</p>
        </>
    )
}

export default StripedTable;
