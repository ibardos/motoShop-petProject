import {useEffect, useState} from "react";

import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";


const ManufacturerDeleteModal = (props) => {
    const modalBody = (
        <DeleteItemInformation manufacturers={props.manufacturers} recordId={props.recordId}
                               setDeleteErrorModalShow={props.setErrorModalShow} setFormSubmit={props.setFormSubmit}
                               setDeleteModalShow={props.setDeleteModalShow}/>
    );

    return <CrudModal show={props.show} onHide={props.onHide} title="Delete Manufacturer" body={modalBody} />
}


const DeleteItemInformation = (props) => {
    const [currentRecord, setCurrentRecord] = useState({});


    useEffect(() => {
            setCurrentRecord(props.manufacturers.find(m => m.id.toString() === props.recordId))
        },[props.recordId])


    async function handleSubmit(event) {
        event.preventDefault();

        const url = `/manufacturer/delete/${currentRecord.id}`;

        const options = {
            method: "DELETE",
            headers: { 'Content-Type': 'application/json' }
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
            <p style={{textAlign: "center"}}>If the Manufacturer has any model in stock,<br/> the delete request will be refused!</p>

            <div className="delete-modal-container">
                <span>Manufacturer name</span>
                <h4>{currentRecord.name}</h4>
                <span>Country of origin</span>
                <h4>{currentRecord.country}</h4>
                <span>Partner since</span>
                <h4>{currentRecord.partnerSince}</h4>
            </div>

            <Button type="button" variant="danger"
                    style={{margin: 0, position: "relative", left: "40%"}}
                    onClick={async (event) => {
                        await handleSubmit(event);
                        props.setDeleteModalShow(false)
                    }}>Delete</Button>
        </>
    )
}

export default ManufacturerDeleteModal;