import {useEffect, useState} from "react";

import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";
import {fetchBackendApi} from "../../util/fetchBackendApi";


const ManufacturerDeleteModal = (props) => {
    const modalBody = (
        <DeleteItemInformation manufacturers={props.manufacturers}
                               recordId={props.recordId}
                               setDeleteErrorModalShow={props.setDeleteErrorModalShow}
                               setFormSubmit={props.setFormSubmit}
                               setDeleteModalShow={props.setDeleteModalShow}/>
    );

    return <CrudModal show={props.show} onHide={props.onHide} title="Delete Manufacturer" body={modalBody} />
}


const DeleteItemInformation = (props) => {
    const [currentRecord, setCurrentRecord] = useState({});


    useEffect(() => {
        const currentRecord = props.manufacturers.find(m => m.id.toString() === props.recordId);
        setCurrentRecord(currentRecord !== undefined ? currentRecord : {});
    }, [props.manufacturers, props.recordId])


    async function handleSubmit(event) {
        event.preventDefault();

        try {
            await fetchBackendApi(`/service/manufacturer/delete/${currentRecord.id}`, "DELETE");
            props.setFormSubmit(old => !old);
        } catch (error) {
            if (error.status === 500) {
                props.setDeleteErrorModalShow(true);
                console.error(`Failed to delete Manufacturer with id: ${currentRecord.id} due to foreign key violation:`, error.message);
            } else {
                console.error("Unexpected error:", error.message);
            }
        }
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
                    }
            }>Delete</Button>
        </>
    )
}

export default ManufacturerDeleteModal;
