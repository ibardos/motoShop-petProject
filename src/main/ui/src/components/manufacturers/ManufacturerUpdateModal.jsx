import {useEffect, useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";


const ManufacturerUpdateModal = (props) => {
    const modalBody = (
        <UpdateForm manufacturers={props.manufacturers} recordId={props.recordId} setFormSubmit={props.setFormSubmit}
                    setUpdateModalShow={props.setUpdateModalShow}/>
    );

    return <CrudModal show={props.show} onHide={props.onHide} title="Update manufacturer" body={modalBody} />
}


const UpdateForm = (props) => {
    const [modalName, setModalName] = useState("");
    const [modalCountry, setModalCountry] = useState("");
    const [modalPartnerSince, setModalPartnerSince] = useState("");

    const [currentRecord, setCurrentRecord] = useState({});


    useEffect(() => {
        setCurrentRecord(props.manufacturers.find(m => m.id.toString() === props.recordId))
    }, [props.recordId])


    async function handleSubmit(event) {
        event.preventDefault();

        const url = "manufacturer/update";

        const requestBody = {
            "id": currentRecord.id,
            "name": modalName ? modalName : currentRecord.name,
            "country": modalCountry ? modalCountry : currentRecord.country,
            "partnerSince": modalPartnerSince ? modalPartnerSince : currentRecord.partnerSince
        }

        const options = {
            method: "PUT",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody),
        }

        if (modalName || modalCountry || modalPartnerSince) {
            await fetch(url, options);
            props.setFormSubmit(old => !old);
        }
    }


    return (
        <Form onSubmit={async (event) => await handleSubmit(event)}>
            <Form.Group className="mb-3" controlId="formTextareaName">
                <Form.Label>Manufacturer name</Form.Label>
                <Form.Control type="text" name="name" defaultValue={currentRecord.name}
                              onChange={(event) => setModalName(event.target.value)}/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formTextareaCountry">
                <Form.Label>Country of origin</Form.Label>
                <Form.Control type="text" name="country" defaultValue={currentRecord.country}
                              onChange={(event) => setModalCountry(event.target.value)}/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formDatePartnerSince">
                <Form.Label>Partner since</Form.Label>
                <Form.Control type="text" onFocus={(event) => (event.target.type = "date")}
                              onBlur={(event) => (event.target.type = "text")}
                              defaultValue={currentRecord.partnerSince}
                              name="partnerSince" onChange={(event) => setModalPartnerSince(event.target.value)}/>
            </Form.Group>

            <div style={{display: "flex", justifyContent: "center"}}>
                <Button type="submit" variant="secondary" style={{paddingInline: "30px"}}
                        onClick={() => props.setUpdateModalShow(false)}>Update</Button>
            </div>
        </Form>
    )
}

export default ManufacturerUpdateModal;