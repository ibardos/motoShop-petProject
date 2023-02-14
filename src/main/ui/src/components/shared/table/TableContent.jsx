import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";


const TableContent = (props) => {
    return (
        <tbody>
        {props.filteredData.map(m => (
            <tr key={m.id}>{Object.values(m).map(value => (
                <td key={value} style={{paddingTop: "15px"}}>{value}</td>
            ))}
                <td>
                    <Row>
                        <Col style={{paddingInline: "0px"}}>
                            <Button variant={"secondary"} data-id={m.id} onClick={(event) => {
                                props.setRecordId(event.target.dataset.id);
                                props.setUpdateModalShow(true);
                            }}>Update</Button>
                        </Col>
                        <Col style={{paddingInline: "0px"}}>
                            <Button variant={"danger"} data-id={m.id} onClick={(event) => {
                                props.setRecordId(event.target.dataset.id);
                                props.setDeleteModalShow(true);
                            }}>Delete</Button>
                        </Col>
                    </Row>
                </td>
            </tr>
        ))}
        </tbody>
    )
}

export default TableContent;