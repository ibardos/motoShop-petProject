import {useEffect, useState} from "react";

import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";


const MotorcycleStockDeleteErrorModal = (props) => {
    const modalBody = <DeleteErrorInformation motorcycleStocks={props.motorcycleStocks} recordId={props.recordId}
                                              setDeleteErrorModalShow={props.setDeleteErrorModalShow}/>

    return <CrudModal show={props.show} onHide={props.onHide} title="Error" body={modalBody}/>
}


const DeleteErrorInformation = (props) => {
    const [currentRecord, setCurrentRecord] = useState({});


    useEffect(() => {
        const currentRecord = props.motorcycleStocks.find(m => m.id.toString() === props.recordId);
        setCurrentRecord(currentRecord !== undefined ? currentRecord : {});
    }, [props.motorcycleStocks, props.recordId])


    return (
        <>
            <h2 style={{textAlign: "center"}}>Delete request refused!</h2>

            <div className="delete-modal-container">
                {currentRecord.motorcycleModel ? (
                    <span>
                        The Motorcycle model in stock<br/>
                        <strong>{currentRecord.motorcycleModel.manufacturer.name + " - " + currentRecord.motorcycleModel.modelName}</strong>
                        with <strong>id: {currentRecord.id}</strong><br/>
                        you tried to delete has related orders in database.
                    </span>
                ) : (
                    <span>Loading record details...</span>
                )}
            </div>

            <Button type="button" variant="secondary"
                    style={{margin: 0, position: "relative", left: "40%"}}
                    onClick={() => props.setDeleteErrorModalShow(false)}
            >OK</Button>
        </>
    )
}

export default MotorcycleStockDeleteErrorModal;
