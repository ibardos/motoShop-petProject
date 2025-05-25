import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";
import {useEffect, useState} from "react";


const ManufacturerDeleteErrorModal = (props) => {
    return <CrudModal show={props.show} onHide={props.onHide} title="Error"
                      body={<DeleteErrorInformation customers={props.customers} recordId={props.recordId}
                                                    setDeleteErrorModalShow={props.setDeleteErrorModalShow}/>}/>
}


const DeleteErrorInformation = (props) => {
    const [currentRecord, setCurrentRecord] = useState({});


    useEffect(() => {
        const currentRecord = props.customers.find(m => m.id.toString() === props.recordId);
        setCurrentRecord(currentRecord !== undefined ? currentRecord : {});
    }, [props.customers, props.recordId])


    return (
        <>
            <h2 style={{textAlign: "center"}}>Delete request refused!</h2>

            <div className="delete-modal-container">
                <span>The Customer<br/>
                    <strong>{currentRecord.fullName}</strong> with <strong>id: {currentRecord.id}</strong><br/>
                    you tried to delete has an order placed in database.</span>
            </div>

            <Button type="button" variant="secondary"
                    style={{margin: 0, position: "relative", left: "40%"}}
                    onClick={async () => {props.setDeleteErrorModalShow(false);}}>OK</Button>
        </>
    )
}

export default ManufacturerDeleteErrorModal;
