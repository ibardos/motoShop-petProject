import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";
import {useEffect, useState} from "react";


const ManufacturerDeleteErrorModal = (props) => {
    return <CrudModal show={props.show} onHide={props.onHide} title="Error"
                      body={<DeleteErrorInformation manufacturers={props.manufacturers} recordId={props.recordId}
                                                    setDeleteErrorModalShow={props.setDeleteErrorModalShow}/>}/>
}


const DeleteErrorInformation = (props) => {
    const [currentRecord, setCurrentRecord] = useState({});


    useEffect((props) => {
        setCurrentRecord(props.manufacturers.find(m => m.id.toString() === props.recordId))
    }, [props.recordId])


    return (
        <>
            <h2 style={{textAlign: "center"}}>Delete request refused!</h2>

            <div className="delete-modal-container">
                <span>The Manufacturer<br/>
                    <strong>{currentRecord.name}</strong> with <strong>id: {currentRecord.id}</strong><br/>
                    you tried to delete has related items in stock.</span>
            </div>

            <Button type="button" variant="secondary"
                    style={{margin: 0, position: "relative", left: "40%"}}
                    onClick={async () => {props.setDeleteErrorModalShow(false);}}>OK</Button>
        </>
    )
}

export default ManufacturerDeleteErrorModal;
