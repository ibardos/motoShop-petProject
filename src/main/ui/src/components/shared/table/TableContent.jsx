import {useContext} from "react";

import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";

import {AuthenticationContext} from "../../../security/authenticationProvider";


const TableContent = (props) => {
    const {userPermissions} = useContext(AuthenticationContext);

    
    // Handle Options column minWidth based on how many buttons need to be rendered
    const showUpdateButton = userPermissions.includes('Update');
    const showDeleteButton = userPermissions.includes('Delete');
    const showSellButton = props.isSellButtonVisible;

    let visibleButtons = 0;
    if (showUpdateButton) visibleButtons++;
    if (showDeleteButton) visibleButtons++;
    if (showSellButton) visibleButtons++;

    let optionsColumnWidth = "100px";
    if (visibleButtons === 2) {
        optionsColumnWidth = "200px";
    } else if (visibleButtons === 3) {
        optionsColumnWidth = "300px";
    }


    // Provides unique values for React child keys
    let columnIndexer = 0;

    return (
        <tbody>
        {props.filteredData.map(record => (
            <tr key={record.id}>
                {Object.values(record).map(value => {
                    if (typeof value === "object") {
                        columnIndexer++;
                        return <td key={columnIndexer + value.name || columnIndexer + value.modelName}
                                   style={{paddingTop: "15px"}}>{value.name || value.manufacturer.name + " - " + value.modelName}</td>
                    } else {
                        columnIndexer++;
                        return <td key={columnIndexer.toString() + value.toString()}
                                   style={{paddingTop: "15px"}}>{value}</td>
                    }
                })}
                {(userPermissions.includes('Update') || userPermissions.includes('Delete')) && (
                    <td style={{minWidth: optionsColumnWidth, width: optionsColumnWidth}}>
                        <Row>
                            { /* Conditionally render Sell button for MotorcycleStock page only */}
                            {props.isSellButtonVisible && (
                                <Col style={{paddingInline: "10px", minWidth: "100px"}}>
                                    <Button variant={"success"} style={{width: "80px"}} data-id={record.id}
                                            onClick={(event) => {
                                                props.setRecordId(event.target.dataset.id);
                                                props.setSellModalShow(true); // TODO Implement Sell modal
                                            }
                                            }>Sell</Button>
                                </Col>
                            )}
                            { /* Conditionally render Update button */}
                            {userPermissions.includes('Update') && (
                                <Col style={{paddingInline: "10px", minWidth: "100px"}}>
                                    <Button variant={"secondary"} style={{width: "80px"}} data-id={record.id}
                                            onClick={(event) => {
                                                props.setRecordId(event.target.dataset.id);
                                                props.setUpdateModalShow(true);
                                            }
                                    }>Update</Button>
                                </Col>
                            )}
                            { /* Conditionally render Delete button */}
                            {userPermissions.includes('Delete') && (
                                <Col style={{paddingInline: "10px", minWidth: "100px"}}>
                                    <Button variant={"danger"} style={{width: "80px"}} data-id={record.id}
                                            onClick={(event) => {
                                                props.setRecordId(event.target.dataset.id);
                                                props.setDeleteModalShow(true);
                                            }
                                    }>Delete</Button>
                                </Col>
                            )}
                        </Row>
                    </td>
                )}
            </tr>
        ))}
        </tbody>
    )
}

export default TableContent;
