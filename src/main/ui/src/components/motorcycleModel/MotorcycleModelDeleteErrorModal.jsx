import {useEffect, useState} from "react";

import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";


const MotorcycleModelDeleteErrorModal = (props) => {
    const modalBody = <DeleteErrorInformation motorcycleModels={props.motorcycleModels} recordId={props.recordId}
                                              setDeleteErrorModalShow={props.setDeleteErrorModalShow}/>

    return <CrudModal show={props.show} onHide={props.onHide} title="Error" body={modalBody}/>
}


const DeleteErrorInformation = (props) => {
    const [currentRecord, setCurrentRecord] = useState({});


    useEffect(() => {
        setCurrentRecord(props.motorcycleModels.find(m => m.id.toString() === props.recordId))
    }, [props.recordId])


    return (
        <>
            <h2 style={{textAlign: "center"}}>Delete request refused!</h2>

            <div className="delete-modal-container">
                <span>The Motorcycle model<br/>
                    <strong>{currentRecord.modelName}</strong> with <strong>id: {currentRecord.id}</strong><br/>
                    you tried to delete has related items in stock.</span>
            </div>

            <Button type="button" variant="secondary"
                    style={{margin: 0, position: "relative", left: "40%"}}
                    onClick={() => props.setDeleteErrorModalShow(false)}
            >OK</Button>
        </>
    )
}

export default MotorcycleModelDeleteErrorModal;