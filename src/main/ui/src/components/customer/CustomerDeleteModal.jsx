import {useEffect, useState} from "react";

import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";
import {fetchBackendApi} from "../../util/fetchBackendApi";


const CustomerDeleteModal = (props) => {
    const modalBody = (
        <DeleteItemInformation customers={props.customers}
                               recordId={props.recordId}
                               setDeleteErrorModalShow={props.setErrorModalShow}
                               setFormSubmit={props.setFormSubmit}
                               setDeleteModalShow={props.setDeleteModalShow}/>
    );

    return <CrudModal show={props.show} onHide={props.onHide} title="Delete Customer" body={modalBody} />
}


const DeleteItemInformation = (props) => {
    const [currentRecord, setCurrentRecord] = useState({});


    useEffect(() => {
        const currentRecord = props.customers.find(m => m.id.toString() === props.recordId);
        setCurrentRecord(currentRecord !== undefined ? currentRecord : {});
    }, [props.customers, props.recordId])


    async function handleSubmit(event) {
        event.preventDefault();

        try {
            await fetchBackendApi(`/service/customer/delete/${currentRecord.id}`, "DELETE");
            props.setFormSubmit(old => !old);
        } catch (error) {
            if (error.status === 409) {
                props.setDeleteErrorModalShow(true);
                console.error(`Failed to delete Customer with id: ${currentRecord.id} due to foreign key violation:`, error.message);
            } else {
                console.error("Unexpected error:", error.message);
            }
        }
    }


    return (
        <>
            <h2 style={{textAlign: "center"}}>Are you sure to delete?</h2>
            <p style={{textAlign: "center"}}>If the Customer has any order placed,<br/> the delete request will be refused!</p>

            <div className="delete-modal-container">
                <span>Customer id</span>
                <h4>{currentRecord.id}</h4>
                <span>Customer full name</span>
                <h4>{currentRecord.fullName}</h4>
                <span>Customer email</span>
                <h4>{currentRecord.email}</h4>
                <span>Customer phone number</span>
                <h4>{currentRecord.phoneNumber}</h4>
                <span>Customer address</span>
                <h5>{currentRecord.fullAddress}</h5>
                <span>Date of registration</span>
                <h4>{currentRecord.dateOfRegistration}</h4>
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

export default CustomerDeleteModal;
