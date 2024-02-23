import {useContext} from "react";

import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";

import {AuthenticationContext} from "../../../security/authenticationProvider";


const TableContent = (props) => {
    const {userPermissions} = useContext(AuthenticationContext);

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
                    <td style={{width: "175px", minWidth: "175px"}}>
                        <Row>
                            { /* Conditionally render Update button */}
                            {userPermissions.includes('Update') && (
                                <Col style={{paddingInline: "0px", minWidth: "80px"}}>
                                    <Button variant={"secondary"} data-id={record.id}
                                            onClick={(event) => {
                                                props.setRecordId(event.target.dataset.id);
                                                props.setUpdateModalShow(true);
                                            }
                                    }>Update</Button>
                                </Col>
                            )}
                            { /* Conditionally render Delete button */}
                            {userPermissions.includes('Delete') && (
                                <Col style={{paddingInline: "0px"}}>
                                    <Button variant={"danger"} data-id={record.id}
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
