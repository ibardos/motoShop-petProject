import {useEffect, useState} from "react";

import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";

import {camelCaseToSentenceCase} from "../../util/camelCaseToSentenceCase";
import {getJwtToken} from "../../security/authService";


const MotorcycleStockDeleteModal = (props) => {
    const modalBody = <DeleteItemInformation motorcycleStocks={props.motorcycleStocks}
                                             recordId={props.recordId}
                                             setFormSubmit={props.setFormSubmit}
                                             setDeleteModalShow={props.setDeleteModalShow}/>

    return <CrudModal show={props.show} onHide={props.onHide} title="Delete Motorcycle from stock" body={modalBody} />
}


const DeleteItemInformation = (props) => {
    const [currentRecord, setCurrentRecord] = useState({});


    useEffect(() => {
        const currentRecord = props.motorcycleStocks.find(m => m.id.toString() === props.recordId);
        setCurrentRecord(currentRecord !== undefined ? currentRecord : {});
    }, [props.motorcycleStocks, props.recordId])


    async function handleSubmit(event) {
        event.preventDefault();

        const url = `/service/motorcycle/stock/delete/${currentRecord.id}`;

        const options = {
            method: "DELETE",
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${getJwtToken()}` }
        }

        await fetch(url, options);
        props.setFormSubmit(old => !old);
    }


    return (
        <>
            <h2 style={{textAlign: "center"}}>Are you sure to delete?</h2>
            <p style={{textAlign: "center"}}>All related item will be deleted from stock!</p>

            <div className="delete-modal-container">
                {Object.entries(currentRecord).map(([key, value]) => {
                    if (typeof value === "object") {
                        return (
                            <div key={key}>
                                <span key={key}>{camelCaseToSentenceCase(key)}</span>
                                <h4 key={value.modelName}>{value.modelName}</h4>
                            </div>
                        )
                    } else {
                        return (
                            <div key={key}>
                                <span key={key}>{camelCaseToSentenceCase(key)}</span>
                                <h4 key={value}>{value}</h4>
                            </div>
                        )
                    }
                })}
            </div>

            <Button type="button" variant="danger"
                    style={{margin: 0, position: "relative", left: "40%"}}
                    onClick={async (event) => {
                        await handleSubmit(event);
                        props.setDeleteModalShow(false)
                    }
            }>Delete</Button>
        </>
    )
}

export default MotorcycleStockDeleteModal;
