import {useEffect, useState} from "react";

import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";
import {getJwtToken} from "../../security/authService";


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
    }, [props.manufacturers, props.recordId])


    async function handleSubmit(event) {
        event.preventDefault();

        const url = `/service/customer/delete/${currentRecord.id}`;

        const options = {
            method: "DELETE",
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${getJwtToken()}` }
        }

        const response = await fetch(url, options);

        if (response.status === 409) {
            // Specific handling for foreign key violation
            props.setDeleteErrorModalShow(true);
        } else if (!response.ok) {
            // Generic error handler
            console.error("Unexpected error:", response.status);
            // You could show a generic error modal or toast here
        }

        props.setFormSubmit(old => !old);
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
