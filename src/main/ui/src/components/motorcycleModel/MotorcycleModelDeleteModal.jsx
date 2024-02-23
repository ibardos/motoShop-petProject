import {useEffect, useState} from "react";

import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";

import {camelCaseToSentenceCase} from "../../util/camelCaseToSentenceCase";
import {getJwtToken} from "../../security/authService";


const MotorcycleModelDeleteModal = (props) => {
    const modalBody = <DeleteItemInformation motorcycleModels={props.motorcycleModels}
                                             recordId={props.recordId}
                                             setDeleteErrorModalShow={props.setErrorModalShow}
                                             setFormSubmit={props.setFormSubmit}
                                             setDeleteModalShow={props.setDeleteModalShow}/>

    return <CrudModal show={props.show} onHide={props.onHide} title="Delete Motorcycle model" body={modalBody} />
}


const DeleteItemInformation = (props) => {
    const [currentRecord, setCurrentRecord] = useState({});


    useEffect(() => {
        const currentRecord = props.motorcycleModels.find(m => m.id.toString() === props.recordId);
        setCurrentRecord(currentRecord !== undefined ? currentRecord : {});
    }, [props.motorcycleModels, props.recordId])


    async function handleSubmit(event) {
        event.preventDefault();

        const url = `/service/motorcycle/model/delete/${currentRecord.id}`;

        const options = {
            method: "DELETE",
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${getJwtToken()}` }
        }

        const response = await fetch(url, options);

        if (!response.ok) {
            props.setDeleteErrorModalShow(true);
        }

        props.setFormSubmit(old => !old);
    }


    return (
        <>
            <h2 style={{textAlign: "center"}}>Are you sure to delete?</h2>
            <p style={{textAlign: "center"}}>If the Motorcycle model has any related item in stock,<br/> the delete request will be refused!</p>

            <div className="delete-modal-container">
                {Object.entries(currentRecord).map(([key, value]) => {
                    if (typeof value === "object") {
                        return (
                            <div key={key}>
                                <span key={key}>{camelCaseToSentenceCase(key)}</span>
                                <h4 key={value.name}>{value.name}</h4>
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

export default MotorcycleModelDeleteModal;
